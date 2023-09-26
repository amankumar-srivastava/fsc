package com.hcl.fsc.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.fsc.customExpcetion.ValueNotPresentException;
import com.hcl.fsc.entities.EmployeeDetails;
import com.hcl.fsc.entities.EmployeeProjectDetails;
import com.hcl.fsc.entities.ProjectAssignmentHistory;
import com.hcl.fsc.entities.Task;
import com.hcl.fsc.mastertables.ProjectCode;
import com.hcl.fsc.pojo.EmployeeProjectDetailsPojo;
import com.hcl.fsc.repositories.EmployeeDetailsRepository;
import com.hcl.fsc.repositories.EmployeeProjectDetailRepository;
import com.hcl.fsc.repositories.ProjectAssignmentHistoryRepository;
import com.hcl.fsc.repositories.TaskRepository;
import com.hcl.fsc.repositories.master.ProjectCodeRepository;
import com.hcl.fsc.repositories.master.ProjectTypeRepository;
import com.hcl.fsc.response.ProjectDetails;
import com.hcl.fsc.response.ProjectDetailsResponse;
import com.hcl.fsc.response.Response;

@Service
public class EmployeeProjectDetailService {

	@Autowired
	private EmployeeProjectDetailRepository employeeProjectDetailRepository;

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private ProjectAssignmentHistoryRepository projectAssignmentHistoryRepository;

	@Autowired
	private ProjectTypeRepository projectTypeRepository;

	@Autowired
	private ProjectCodeRepository projectCodeRepository;

	@Autowired
	private TaskRepository taskRepository;

	public Response updateProject(EmployeeProjectDetailsPojo employeeProjectDetails, Long empSAPID) throws Exception {
		if (!this.employeeProjectDetailRepository.existsById(empSAPID)) {
			throw new ValueNotPresentException("SAP Id is incorrect");
		}
		EmployeeProjectDetails data = this.employeeProjectDetailRepository.findById(empSAPID).get();
		boolean flag = true;
		if (employeeProjectDetails.getProjUid() != null) {
			data.setProjUid(employeeProjectDetails.getProjUid());
			flag = false;
		}
		if (employeeProjectDetails.getReportingMgrSAPID() != null) {
			data.setReportingMgrSAPID(employeeProjectDetails.getReportingMgrSAPID());
			flag = false;
		}
		if (!StringUtils.isBlank(employeeProjectDetails.getLocation())) {
			data.setLocation(employeeProjectDetails.getLocation());
			flag = false;
		}
		if (employeeProjectDetails.getProjUid() != null) {
			data.setProjUid(employeeProjectDetails.getProjUid());
			flag = false;
		}
		if (employeeProjectDetails.getHrL4Id() != null) {
			data.setHrL4Id(employeeProjectDetails.getHrL4Id());
			flag = false;
		}
		if (!StringUtils.isBlank(employeeProjectDetails.getRasStatus())) {
			data.setRasStatus(employeeProjectDetails.getRasStatus());
			flag = false;
		}
		if (!StringUtils.isBlank(employeeProjectDetails.getJob())) {
			data.setJob(employeeProjectDetails.getJob());
			flag = false;
		}
		if (!StringUtils.isBlank(employeeProjectDetails.getSkill())) {
			data.setSkill(employeeProjectDetails.getSkill());
			flag = false;
		}
		if (!StringUtils.isBlank(employeeProjectDetails.getUpdatedBy())) {
			data.setSkill(employeeProjectDetails.getUpdatedBy());
			flag = false;
		}
		if (flag) {
			throw new ValueNotPresentException("Fields Contain Null Value");
		}
		data.setUpdatedDate(LocalDate.now());
		this.employeeProjectDetailRepository.save(data);
		return new Response("Record Updated Successfully");
	}

	public ProjectDetailsResponse addRecord(EmployeeProjectDetailsPojo employeeProjectDetails) throws Exception {

		ProjectDetailsResponse pa = new ProjectDetailsResponse();
		List<Long> list = employeeProjectDetails.getEmpSAPID();
		ListIterator<Long> itr = list.listIterator();
		List<Long> duplicateSapId = new ArrayList<>();
		List<Long> assignedSapId = new ArrayList<>();
		List<Long> unassignedSapId = new ArrayList<>();
		List<Long> existingAssignedSapId = new ArrayList<>();
		List<Long> nonExistingSapId = new ArrayList<>();
		String randomPattern = "";
		while (itr.hasNext()) {
			EmployeeProjectDetails employee = new EmployeeProjectDetails();
			Long sapId = itr.next();
			if (this.employeeProjectDetailRepository.existsById(sapId)) {
				// throw new ValueNotPresentException("Project cannot be assigned to Duplicate
				// EmpSAPId!");
				duplicateSapId.add(sapId);

			} else if (employeeDetailsRepository.findBySapId(sapId) != null) {
				System.out.println(employeeDetailsRepository.findBySapId(sapId).getProjAssignedStatus()
						+ " getProjAssignedStatus");
				if (!employeeDetailsRepository.findBySapId(sapId).getProjAssignedStatus()) {

					employee.setEmpSAPID(sapId);
					employee.setCountry(employeeProjectDetails.getCountry());
					employee.setReportingMgrSAPID(employeeProjectDetails.getReportingMgrSAPID());
					employee.setLocation(employeeProjectDetails.getLocation());
					employee.setFresher("1");
					employee.setAssignmentStartDate(employeeProjectDetails.getAssignmentStartDate());
					employee.setAssignmentEndDate(employeeProjectDetails.getAssignmentEndDate());
					employee.setFte(1);
					employee.setHrL4Id(employeeProjectDetails.getHrL4Id());
					employee.setJob(employeeProjectDetails.getJob());
					employee.setLastProjectName(employeeProjectDetails.getLastProjectName());
					employee.setOnOff(employeeProjectDetails.getOnOff());
					employee.setProjUid(employeeProjectDetails.getProjUid());
					employee.setSkill(employeeProjectDetails.getSkill());
					employee.setRasStatus(employeeProjectDetails.getRasStatus());
					employee.setCreatedBy(employeeProjectDetails.getCreatedBy());
					employee.setCreatedDate(employeeProjectDetails.getCreatedDate());
					employee.setSr(employeeProjectDetails.getSr());
					int patternLength = 8; // Length of the random pattern
					// Define the character set or range for the pattern
					String charSet = "0123456789";

					// Create an instance of Random
					Random random = new Random();

					// Generate the random pattern
					StringBuilder patternBuilder = new StringBuilder();
					for (int i = 0; i < patternLength; i++) {
						int randomIndex = random.nextInt(charSet.length());
						char randomChar = charSet.charAt(randomIndex);
						patternBuilder.append(randomChar);
					}

					randomPattern = patternBuilder.toString();
					employee.setTransactionId(randomPattern);

					employeeProjectDetailRepository.save(employee);
					assignedSapId.add(sapId);

					EmployeeDetails employeeDetails = employeeDetailsRepository.getBySapId(sapId);
					employeeDetails.setProjAssignedStatus(true);

					employeeDetailsRepository.save(employeeDetails);

					Task task = new Task();
					task.setUserId(sapId);

					if (taskRepository.findByUserId(sapId.longValue()) == null) {
						taskRepository.save(task);
					}
					
				} else {
					existingAssignedSapId.add(sapId);
				}
			} else 

			{
				nonExistingSapId.add(sapId);
			}

		}

		pa.setDuplicateSapId(duplicateSapId);
		pa.setAssignedSapId(assignedSapId);
		pa.setUnassignedSapId(unassignedSapId);
		pa.setTransactionId(randomPattern);
		pa.setExistingAssignedSapId(existingAssignedSapId);
		pa.setNonExistingSapId(nonExistingSapId);
		return pa;
	}

	public Response unassignproject(Long empSAPID) throws Exception {

		if (!this.employeeProjectDetailRepository.existsById(empSAPID)) {

			throw new ValueNotPresentException("empSAPID is Incorrect");

		}
		EmployeeProjectDetails employeeProjectDetails = new EmployeeProjectDetails();
		employeeProjectDetails = this.employeeProjectDetailRepository.getById(empSAPID);

		ProjectAssignmentHistory projectAssignmentHistory = new ProjectAssignmentHistory();
		projectAssignmentHistory.setEmpSAPID(empSAPID);
		projectAssignmentHistory.setAssignment_start_date(employeeProjectDetails.getAssignmentStartDate());
		projectAssignmentHistory.setAssignment_end_date(employeeProjectDetails.getAssignmentEndDate());
		projectAssignmentHistory.setProjUid(employeeProjectDetails.getProjUid());
		projectAssignmentHistory.setSkill(employeeProjectDetails.getSkill());
		projectAssignmentHistory.setTransactionId(employeeProjectDetails.getTransactionId()); 
		projectAssignmentHistoryRepository.save(projectAssignmentHistory);

		this.employeeProjectDetailRepository.deleteById(empSAPID);

		EmployeeDetails employeeDetails = employeeDetailsRepository.getBySapId(empSAPID);
		employeeDetails.setProjAssignedStatus(false);
		this.employeeDetailsRepository.save(employeeDetails);
		return new Response("Project unassigned Successfully");

	}

	public ProjectDetails fetchingProjectDetails(String projectCode) {
		System.out.println(projectCode);
		ProjectCode pCode = this.projectCodeRepository.findByProjectCode(projectCode);
		System.out.println(" pcode " + pCode);
		List<EmployeeProjectDetails> epdetails = this.employeeProjectDetailRepository.findAllByProjUid(pCode.getUid());
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectName(pCode.getProjectName());
		projectDetails.setProjectCode(pCode.getProjectCode());
		projectDetails.setCurrentTeamStrength(pCode.getProject_strength());
		projectDetails.setTotalCandidatesRequired(100);
		projectDetails.setTotalFreshersDeployed(epdetails.size());
		return projectDetails;
	}

}