package com.hcl.fsc.controllers;

import java.util.List;
import java.util.Optional;

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
import com.hcl.fsc.mastertables.CustomerName;
import com.hcl.fsc.mastertables.HrL4;
import com.hcl.fsc.mastertables.MasterTablePossibleValues;
import com.hcl.fsc.mastertables.MasterTables;
import com.hcl.fsc.mastertables.ProjectCode;
import com.hcl.fsc.mastertables.ProjectL4;
import com.hcl.fsc.mastertables.ProjectType;
import com.hcl.fsc.mastertables.Rm;
import com.hcl.fsc.pojo.ProjectCodePojo;
import com.hcl.fsc.response.Response;
import com.hcl.fsc.services.MasterTableServiceImpl;
import com.hcl.fsc.services.master.CustomerNameService;
import com.hcl.fsc.services.master.HrL4Service;
import com.hcl.fsc.services.master.ProjectCodeAndTypeExcelService;
import com.hcl.fsc.services.master.ProjectCodeService;
import com.hcl.fsc.services.master.ProjectL4Service;
import com.hcl.fsc.services.master.ProjectTypeService;
import com.hcl.fsc.services.master.RmService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class MasterTableController {

	@Autowired
	private MasterTableServiceImpl masterTableService;

	@Autowired
	private CustomerNameService customerNameService;

	@Autowired
	private HrL4Service hrL4Service;

	@Autowired
	private ProjectCodeService projectCodeService;

	@Autowired
	private ProjectL4Service projectL4Service;

	@Autowired
	private RmService rmService;

	@Autowired
	private ProjectTypeService projectTypeService;

	@Autowired
	private ProjectCodeAndTypeExcelService projectCodeAndTypeExcelService;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	@PostMapping("/masterTablePossibleValues")
	public ResponseEntity<String> addMasterTableValues(@RequestBody List<MasterTablePossibleValues> masterTablePossibleValues){
		int res=this.masterTableService.addmasterTableValues(masterTablePossibleValues);
		if (res == masterTablePossibleValues.size())
		return ResponseEntity.status(HttpStatus.OK).body("All Data saved sucessfully");
		else
			return ResponseEntity.ok("VALUE or KEY is null or empty for "+(masterTablePossibleValues.size()- res)+" dataset");
			
	}

	@GetMapping("master/{mastertable}")
	public ResponseEntity<?> getTable(@PathVariable String mastertable) {
		List masterTableDetails= masterTableService.getRecord(mastertable);
		if(masterTableDetails!=null)
			return ResponseEntity.ok(masterTableDetails);
		else
			return ResponseEntity.ok("No Such MasterTable exists in database");
		 
	}

	@GetMapping("master/{mastertable}/{key}")
	public ResponseEntity<?> getRecordbykey(@PathVariable String mastertable, @PathVariable String key) {
		Optional masterTableDetails=masterTableService.getRecordbyKey(mastertable, key);
	 	 if(masterTableDetails==null)
	 		return ResponseEntity.ok("No Such MasterTable exists in database");
	 	else if(!masterTableDetails.equals(Optional.empty()))
			return ResponseEntity.ok(masterTableDetails);
		else
			return ResponseEntity.ok("No Such Master DataRecords exists in database");
	}

	@PostMapping("/master/{str}")
	public ResponseEntity<String> CreateRecord(@RequestBody MasterTables master, @PathVariable String str) {
		int res = masterTableService.createData(master, str);

		if (res == 1)
			return ResponseEntity.ok("Data saved successfully!");
		else if(res==-1)
			return ResponseEntity.ok("Data already exists in database!");
		else if(res==-2)
			return ResponseEntity.ok("No Such MasterTable exists in database!");
		else
			return ResponseEntity.ok("KEY or Value is null or empty");
	}
	
	@PostMapping("/masterList/{str}")
	public ResponseEntity<String> CreateRecordList(@RequestBody List<MasterTables> master, @PathVariable String str) {
		int res = masterTableService.createListData(master, str);

		if (res == master.size())
			return ResponseEntity.ok("All Data saved successfully!");
		else
			return ResponseEntity.ok(res+"data saved sucesfully! In"+(master.size()-res)+" data records KEY is null or empty");
	}

	@PutMapping("master/{str}/{key}")
	public ResponseEntity<String> updateRecord(@PathVariable String key, @PathVariable String str,
			@RequestBody MasterTables master) {
		int res = masterTableService.updateRecord(key, master, str);
		if (res == 1)
			return ResponseEntity.ok("Data updated successfully!");
		else if(res==-1)
			return ResponseEntity.ok("Data not exists in database!");
		else
			return ResponseEntity.ok("KEY or Value is null or empty");
	}

	@DeleteMapping("master/{str}/{key}")
	public ResponseEntity<String> deleteRecord(@PathVariable String key, @PathVariable String str) {
	int res= masterTableService.deleteRecord(key, str);
	if(res==1)
		return ResponseEntity.ok("Data Deleted successfully!");
	else
		return ResponseEntity.ok("No Such record exist in database!");
	}

//	-----------------------------Get All Mapping --------------------------------------------

	@GetMapping("master/projectCode")
	public List<ProjectCode> getAllProjectCode() {
		log.info("Calling Get All Mapping for Project Code Master Table");
		return this.projectCodeService.getAllProjectCode();
	}

	@GetMapping("master/hrl4")
	public List<HrL4> getAllHrL4() {
		log.info("Calling Get All Mapping for HRL4 Master Table");
		return this.hrL4Service.getAllHrL4();
	}

	@GetMapping("master/projectL4")
	public List<ProjectL4> getAllProjectL4() {
		log.info("Calling Get All Mapping for Project L4 Master Table");
		return this.projectL4Service.getAllProjectL4();
	}

	@GetMapping("master/rm")
	public List<Rm> getAllRM() {
		log.info("Calling Get All Mapping for RM Master Table");
		return this.rmService.getAllRM();
	}

	@GetMapping("master/customerName")
	public List<CustomerName> getAllCustomerName() {
		log.info("Calling Get All Mapping for Customer Name Master Table");
		return this.customerNameService.getAllCustomerName();
	}

	@GetMapping("master/projecttype")
	public List<ProjectType> getAllProjectType() {
		log.info("Calling Get All Mapping for Project Type Master Table");
		return this.projectTypeService.getAllProjectType();
	}

//	-----------------------------Get Mapping By Id--------------------------------------------

	@GetMapping("master/projectCode/{uid}")
	public ResponseEntity<?> getProjectCodeById(@Valid @PathVariable Integer uid) throws ValueNotPresentException {
		log.info("Calling GET Mapping by UID " + uid + " for Project Code Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeService.getProjectCodeById(uid));
	}

	@GetMapping("master/hrl4/{uid}")
	public ResponseEntity<?> getHrL4ById(@Valid @PathVariable Integer uid) {
		log.info("Calling GET Mapping by UID " + uid + " for HRL4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.hrL4Service.getHrL4ById(uid));
	}

	@GetMapping("master/projectL4/{uid}")
	public ResponseEntity<?> getProjectL4ById(@Valid @PathVariable Integer uid) {
		log.info("Calling GET Mapping by UID " + uid + " for Project L4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectL4Service.getProjectL4ById(uid));
	}

	@GetMapping("master/rm/{uid}")
	public ResponseEntity<?> getRMById(@Valid @PathVariable Integer uid) throws ValueNotPresentException {
		log.info("Calling GET Mapping by UID " + uid + " for RM Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.rmService.getRmById(uid));
	}

	@GetMapping("master/customerName/{uid}")
	public ResponseEntity<?> CustomerNameById(@Valid @PathVariable Integer uid) {
		log.info("Calling GET Mapping by UID " + uid + " for Customer Name Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.customerNameService.getCustomerById(uid));
	}

	@GetMapping("master/projecttype/{uid}")
	public ResponseEntity<?> getProjectTypeById(@Valid @PathVariable Integer uid) {
		log.info("Calling GET Mapping by UID " + uid + " for Project Type Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectTypeService.getProjectTypeById(uid));
	}

//	------------------------------Post Mapping--------------------------------------------------------
	@PostMapping("master/projectcode")
	public ResponseEntity<?> createProjectCode(@Valid @RequestBody ProjectCodePojo projectCodePojo) throws Exception {
		log.info("Calling POST MAPPING  for Project Code Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeService.createNewProjectCode(projectCodePojo));
	}

	@PostMapping("master/hrl4")
	public ResponseEntity<?> createHrL4(@Valid @RequestBody HrL4 hrL4) throws Exception {
		log.info("Calling POST MAPPING  for HRL4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.hrL4Service.createNewHrL4(hrL4));
	}

	@PostMapping("master/projectL4")
	public ResponseEntity<?> createProjectL4(@Valid @RequestBody ProjectL4 projectL4) throws Exception {
		log.info("Calling POST MAPPING  for Project L4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectL4Service.createNewProjectL4(projectL4));
	}

	@PostMapping("master/rm")
	public ResponseEntity<?> createRm(@Valid @RequestBody Rm rm) throws Exception {
		log.info("Calling POST MAPPING  for RM Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.rmService.createNewRm(rm));
	}

	@PostMapping("master/customername")
	public ResponseEntity<?> createCustomerName(@Valid @RequestBody CustomerName customerName) throws Exception {
		log.info("Calling POST MAPPING  for Customer Name Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.customerNameService.createNewCustomerName(customerName));
	}

	@PostMapping("master/projecttype")
	public ResponseEntity<?> createProjectType(@Valid @RequestBody ProjectType projectType) throws Exception {
		log.info("Calling POST MAPPING  for Project Type Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectTypeService.createNewProjectType(projectType));
	}

	@PostMapping("master/excel")
	public ResponseEntity<?> createProjectTypeExcel(@RequestParam("file") MultipartFile file) throws Exception {
		if (EmployeeHelper.checkExcelFormate(file)) {
			log.info("Calling POST MAPPING for Project Type Excel API");
			return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeAndTypeExcelService.create(file));
		}
		log.error("Document is not in Excel format");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Please Upload Document in Excel Format"));
	}

//	----------------------------- Put Mapping --------------------------------------------------------
	@PutMapping("master/projectCode/{uid}")
	public ResponseEntity<?> updateProjectCode(@Valid @RequestBody ProjectCodePojo projectcode,
			@PathVariable Integer uid) throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for Project Code Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeService.updateProjectCode(projectcode, uid));
	}

	@PutMapping("master/hrl4/{uid}")
	public ResponseEntity<?> updateHrL4(@Valid @RequestBody HrL4 hrl4, @PathVariable Integer uid) throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for HRL4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.hrL4Service.updateHrL4(hrl4, uid));
	}

	@PutMapping("master/projectL4/{uid}")
	public ResponseEntity<?> updateProjectL4(@Valid @RequestBody ProjectL4 projectL4, @PathVariable Integer uid)
			throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for Project L4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectL4Service.updateProjectL4(projectL4, uid));
	}

	@PutMapping("master/rm/{uid}")
	public ResponseEntity<?> updateRm(@Valid @RequestBody Rm rm, @PathVariable Integer uid) throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for RM Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.rmService.updateRm(rm, uid));
	}

	@PutMapping("master/customername/{uid}")
	public ResponseEntity<?> updateCustomerName(@Valid @RequestBody CustomerName customer, @PathVariable Integer uid)
			throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for Customer Name Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.customerNameService.updateCustomerName(customer, uid));
	}

	@PutMapping("master/projecttype/{uid}")
	public ResponseEntity<?> updateProjectType(@Valid @RequestBody ProjectType projectType, @PathVariable Integer uid)
			throws Exception {
		log.info("Calling PUT MAPPING  by UID " + uid + " for Project Type Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectTypeService.updateProjectType(projectType, uid));
	}

//	-------------------------------- Delete Mapping -------------------------------------------------

	@DeleteMapping("master/projectCode/{uid}")
	public ResponseEntity<?> deleteProjectCode(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for Project Code Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeService.deleteProjectCodeById(uid));
	}

	@DeleteMapping("master/hrl4/{uid}")
	public ResponseEntity<?> deleteHrl4ById(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for HRL4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.hrL4Service.deleteHrL4ById(uid));
	}

	@DeleteMapping("master/projectL4/{uid}")
	public ResponseEntity<?> deleteProjectL4(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for Project L4 Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectL4Service.deleteProjectL4ById(uid));
	}

	@DeleteMapping("master/rm/{uid}")
	public ResponseEntity<?> deleteRmById(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for RM Master Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.rmService.deleteRmById(uid));
	}

	@DeleteMapping("master/customername/{uid}")
	public ResponseEntity<?> deleteCustomerNameById(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for Customer Name Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.customerNameService.deleteCustomerNameById(uid));
	}

	@DeleteMapping("master/projecttype/{uid}")
	public ResponseEntity<?> deleteProjectTypeById(@Valid @PathVariable Integer uid) throws Exception {
		log.info("Calling Delete MAPPING by UID " + uid + " for Project Type Table");
		return ResponseEntity.status(HttpStatus.OK).body(this.projectTypeService.deleteProjectTypeById(uid));
	}

// -------------------------------------- PROJECT TYPE MAPPING-------------------------------------------

	@GetMapping("projectAllocate/{uid}")
	public ResponseEntity<?> projectAllocationSize(@Valid @PathVariable Integer uid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.projectCodeService.Sizeallocation(uid));
		} catch (ValueNotPresentException valueNotPresentException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response(valueNotPresentException.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(e.getMessage()));
		}
	}

}
