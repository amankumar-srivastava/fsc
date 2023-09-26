package com.hcl.fsc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.customExpcetion.ValueNotPresentException;
import com.hcl.fsc.helpers.EmployeeHelper;
import com.hcl.fsc.pojo.EmployeeProjectDetailsPojo;
import com.hcl.fsc.response.Response;
import com.hcl.fsc.response.ResponseList;
import com.hcl.fsc.services.EmployeeCDACServiceImpl;
import com.hcl.fsc.services.EmployeeDigiBeeServiceImpl;
import com.hcl.fsc.services.EmployeeMoUService;
import com.hcl.fsc.services.EmployeeNonTier1ServiceImpl;
import com.hcl.fsc.services.EmployeeProjectDetailService;
import com.hcl.fsc.services.EmployeeService;
import com.hcl.fsc.services.EmployeeSkilledHiringService;
import com.hcl.fsc.services.EmployeeTier1Service;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class EmployeeController {

	@Autowired
	private EmployeeNonTier1ServiceImpl employeeNonTier1Service;

	@Autowired
	private EmployeeCDACServiceImpl employeeCDACService;

	@Autowired
	private EmployeeDigiBeeServiceImpl employeeDigiBeeService;

	@Autowired
	private EmployeeTier1Service employeeTier1Service;

	@Autowired
	private EmployeeSkilledHiringService employeeSkilledHiringService;

	@Autowired
	private EmployeeMoUService employeeMoUService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeProjectDetailService employeeProjectDetailService;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	@PostMapping("fsc/upload")
	public ResponseEntity<?> elchEmployeeDetailsUplaod(@RequestParam("file") MultipartFile[] file) {
		log.info("Fetching data from Excel Sheet");
		ResponseList responseList = new ResponseList();
		for (int i = 0; i < file.length; i++) {

			if (EmployeeHelper.checkExcelFormate(file[i])) {
				responseList = new ResponseList();

				ResponseList responseList1 = this.employeeNonTier1Service.employeeNonTier1ListSave(file[i]);
				ResponseList responseList2 = (this.employeeDigiBeeService.employeeDigiBeeListSave(file[i]));
				ResponseList responseList3 = (this.employeeCDACService.employeeCDACListSave(file[i]));
				ResponseList responseList4 = this.employeeTier1Service.employeeTier1ListSave(file[i]);
				ResponseList responseList5 = this.employeeSkilledHiringService.employeeSkilledHiringListSave(file[i]);
				ResponseList responseList6 = this.employeeMoUService.employeeMoUListSave(file[i]);

				responseList
						.setTotal_No_Records(responseList1.getTotal_No_Records() + responseList2.getTotal_No_Records()
								+ responseList3.getTotal_No_Records() + responseList4.getTotal_No_Records()
								+ responseList5.getTotal_No_Records() + responseList6.getTotal_No_Records());

				responseList.setSucessful_Records(
						responseList1.getSucessful_Records() + responseList2.getSucessful_Records()
								+ responseList3.getSucessful_Records() + responseList4.getSucessful_Records()
								+ responseList5.getSucessful_Records() + responseList6.getSucessful_Records());

				responseList.setFailed_Records(responseList1.getFailed_Records() + responseList2.getFailed_Records()
						+ responseList3.getFailed_Records() + responseList4.getFailed_Records()
						+ responseList5.getFailed_Records() + responseList6.getFailed_Records());

				responseList.setDuplicate_Records(
						responseList1.getDuplicate_Records() + responseList2.getDuplicate_Records()
								+ responseList3.getDuplicate_Records() + responseList4.getDuplicate_Records()
								+ responseList5.getDuplicate_Records() + responseList6.getDuplicate_Records());

				List<String> duplicate_Email_List = new ArrayList<>();

				duplicate_Email_List.addAll(responseList1.getDuplicate_Email_List());
				duplicate_Email_List.addAll(responseList2.getDuplicate_Email_List());
				duplicate_Email_List.addAll(responseList3.getDuplicate_Email_List());
				duplicate_Email_List.addAll(responseList4.getDuplicate_Email_List());
				duplicate_Email_List.addAll(responseList5.getDuplicate_Email_List());
				duplicate_Email_List.addAll(responseList6.getDuplicate_Email_List());
				responseList.setDuplicate_Email_List(duplicate_Email_List);

				Map<String, List<String>> failed_Record_List = new HashMap<>();
				failed_Record_List.putAll(responseList1.getFailed_Records_List());
				failed_Record_List.putAll(responseList2.getFailed_Records_List());
				failed_Record_List.putAll(responseList3.getFailed_Records_List());
				failed_Record_List.putAll(responseList4.getFailed_Records_List());
				failed_Record_List.putAll(responseList5.getFailed_Records_List());
				failed_Record_List.putAll(responseList6.getFailed_Records_List());
				responseList.setFailed_Records_List(failed_Record_List);

			} else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Excel sheet Only");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@GetMapping("/employeesList/{details}")
	public List getAllEmployeesDetails(@PathVariable String details) {
		System.out.println(this.employeeService.getEmployeesDetails(details));
		return this.employeeService.getEmployeesDetails(details);
	}

	@DeleteMapping("/employee/{emailId}")
	public String deleteEmployeeDetails(@PathVariable String emailId) {
		return this.employeeService.deleteEmployeesDetails(emailId);

	}

//	---------------------------------- PROJECT ASSIGNMENT ------------------------------------------

	@Autowired
	private EmployeeProjectDetailService employeeProjectDetailService1;

	@PostMapping("/savedata")
	public ResponseEntity<?> addRecord(@Valid @RequestBody EmployeeProjectDetailsPojo employeeProjectDetails)
			throws Exception {
		System.out.println(employeeProjectDetails);

		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(this.employeeProjectDetailService.addRecord(employeeProjectDetails));
	}

	@PutMapping("employeeprojectdetail/{empSAPID}")
	public ResponseEntity<?> updateProjectDetails(@Valid @RequestBody EmployeeProjectDetailsPojo employeeProjectDetails,
			@PathVariable Long empSAPID) throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.employeeProjectDetailService.updateProject(employeeProjectDetails, empSAPID));
	}

//  -------------------------------- PROJECT UNASSIGNMENT ----------------------------------------	

	@DeleteMapping("/unassign/{empSAPID}")
	public ResponseEntity<?> unassignemployeeproject(@PathVariable Long empSAPID) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(this.employeeProjectDetailService.unassignproject(empSAPID));
		} catch (ValueNotPresentException valueNotPresentException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(valueNotPresentException.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(e.getMessage()));
		}
	}

	@GetMapping("/getByProjectCode/{projectCode}")
	public ResponseEntity<?> fetchingByProjectCode(@PathVariable String projectCode) {
		System.out.println(projectCode);
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.employeeProjectDetailService.fetchingProjectDetails(projectCode));

	}

	@GetMapping("/availableCandidatesDetails")
	public ResponseEntity<?> availableCandidateDetailsList() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAvailableCandidatesDetails());
	}

	@GetMapping("/leadershipDashboardDetails")
	public ResponseEntity<?> getleadershipDashboardDetails() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.fetchLeadershipDashboardDetails());
	}

}
