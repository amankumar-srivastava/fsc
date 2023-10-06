package com.hcl.fsc.services.master;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.customExpcetion.DuplicateValueException;
import com.hcl.fsc.customExpcetion.NullValueException;
import com.hcl.fsc.customExpcetion.ValueNotPresentException;
import com.hcl.fsc.entities.EmployeeProjectDetails;
import com.hcl.fsc.mastertables.CustomerName;
import com.hcl.fsc.mastertables.ProjectCode;
import com.hcl.fsc.mastertables.ProjectType;
import com.hcl.fsc.pojo.ProjectCodePojo;
import com.hcl.fsc.repositories.EmployeeProjectDetailRepository;
import com.hcl.fsc.repositories.master.CustomerNameRepository;
import com.hcl.fsc.repositories.master.ProjectCodeRepository;
import com.hcl.fsc.repositories.master.ProjectTypeRepository;
import com.hcl.fsc.response.Response;
import com.hcl.fsc.response.SizeAllocationResponse;

@Service
public class ProjectCodeService {

	@Autowired
	private ProjectCodeRepository projectCodeRepository;

	@Autowired
	private ProjectTypeRepository projectTypeRepository;
	
	private CustomerNameRepository customerNameRepository;
	
	@Autowired
	private EmployeeProjectDetailRepository employeeProjectDetailRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	public List<ProjectCode> getAllProjectCode() {
		log.info("Return All Project Code Records Successfully");
		return this.projectCodeRepository.findAll();
	}

	public ProjectCode getProjectCodeById(Integer uid) {
		log.info("Return Project code record by uid: "+uid);
		return this.projectCodeRepository.findById(uid).get();
	}

	public Response createNewProjectCode(ProjectCodePojo projectCode) throws Exception {
		if (projectCode.getUid() != null && this.projectCodeRepository.existsById(projectCode.getUid())) {
			throw new DuplicateValueException("Duplicate UID: "+projectCode.getUid());
		}
		if (projectCode.getProjectType() == null) {
			throw new NullValueException("Project Type is Empty");
		}
		if (projectCode.getProjectName() == null || StringUtils.isBlank(projectCode.getProjectName())) {
			throw new NullValueException("Project Name is Empty");
		}
		if (projectCode.getProjectCode() == null || StringUtils.isBlank(projectCode.getProjectCode())) {
			throw new NullValueException("Project Code is Empty");
		}
		if (projectCode.getEndDate() == null) {
			throw new NullValueException("End Date is Empty");
		}
		if (projectCode.getCreatedBy() == null || StringUtils.isBlank(projectCode.getCreatedBy())) {
			throw new NullValueException("Created By is Empty");
		}
		if (!this.projectTypeRepository.existsById(projectCode.getProjectType())) {
			throw new ValueNotPresentException("Wrong Project Type: "+projectCode.getProjectType());
		}
		if(this.customerNameRepository.existsById(projectCode.getCustomerName())) {
			throw new ValueNotPresentException("Wrong Customer Name: "+projectCode.getCustomerName());
		}
		if (this.projectCodeRepository.existsProjectCodeByprojectCode(projectCode.getProjectCode())) {
			throw new DuplicateValueException("Duplicate Project Code: "+ projectCode.getProjectCode());
		}
		if (this.projectCodeRepository.existsProjectCodeByprojectName(projectCode.getProjectName())) {
			throw new DuplicateValueException("Duplicate Project Name: "+ projectCode.getProjectName());
		} else {
			int uid = 0;
			ProjectCode project = new ProjectCode();
			if (this.projectCodeRepository.findAll().size() == 0) {
				uid = 1;
			} else {
				Integer id = this.projectCodeRepository.findTopByOrderByUidDesc().getUid();
				uid=++id;
			}
			project.setUid(uid);
			project.setProjectName(projectCode.getProjectName());
			project.setProjectCode(projectCode.getProjectCode());
			project.setBussinessArea(projectCode.getBussinessArea());
			project.setProject_strength(projectCode.getProject_strength());
			ProjectType projectType = this.projectTypeRepository.findById(projectCode.getProjectType()).get();
			CustomerName customerName = this.customerNameRepository.findById(projectCode.getCustomerName()).get();
			project.setProjectType(projectType);
			project.setCustomerName(customerName);
			project.setCreatedDate(LocalDateTime.now());
			project.setEndDate(projectCode.getEndDate());
			project.setCreatedBy(projectCode.getCreatedBy());
			this.projectCodeRepository.save(project);
			log.info("New Record Succefully Created for uid: "+ uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateProjectCode(ProjectCodePojo projectCode, Integer uid) throws Exception {
		if (!this.projectCodeRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (projectCode.getProjectName() != null && StringUtils.isBlank(projectCode.getProjectName())) {
			throw new NullValueException("Project Name is Empty");
		}
		if (projectCode.getProjectCode() != null && StringUtils.isBlank(projectCode.getProjectCode())) {
			throw new NullValueException("Project Code is Empty");
		}
		if (projectCode.getBussinessArea() == null || StringUtils.isBlank(projectCode.getBussinessArea())) {
			throw new NullValueException("Business_area is Empty");
		}
		if (projectCode.getProjectType() != null
				&& !this.projectTypeRepository.existsById(projectCode.getProjectType())) {
			throw new ValueNotPresentException("Wrong Project Type: " +projectCode.getProjectType());
		}
		if(this.customerNameRepository.existsById(projectCode.getCustomerName())) {
			throw new ValueNotPresentException("Wrong Customer Name: "+projectCode.getCustomerName());
		}
		if (projectCode.getUpdatedBy() == null) {		
			throw new NullValueException("Updated By is Empty");
		}
		if (this.projectCodeRepository.existsProjectCodeByprojectCode(projectCode.getProjectCode())) {
			throw new DuplicateValueException("Duplicate Project Code: "+projectCode.getProjectCode());
		}
		if (this.projectCodeRepository.existsProjectCodeByprojectName(projectCode.getProjectName())) {
			throw new DuplicateValueException("Duplicate Project Name: "+projectCode.getProjectName());
		} else {
			ProjectCode pc = this.projectCodeRepository.getById(uid);
			if (projectCode.getProjectCode() != null) {
				pc.setProjectCode(projectCode.getProjectCode());
			}
			if (projectCode.getProjectName() != null) {
				pc.setProjectName(projectCode.getProjectName());
			}
			if (projectCode.getBussinessArea() != null) {
				pc.setBussinessArea(projectCode.getBussinessArea());
			}
			if (projectCode.getProjectType() != null) {
				ProjectType projectType = this.projectTypeRepository.getById(projectCode.getProjectType());
				pc.setProjectType(projectType);
			}
			if (projectCode.getCustomerName() != null) {
				CustomerName customerName = this.customerNameRepository.getById(projectCode.getCustomerName());
				pc.setCustomerName(customerName);
			}
			if (projectCode.getProject_strength() != null) {
				pc.setProject_strength(projectCode.getProject_strength());
			}
			if (projectCode.getUpdatedBy() != null) {
				pc.setUpdatedBy(projectCode.getUpdatedBy());
			}
			pc.setUpdatedDate(LocalDateTime.now());
			this.projectCodeRepository.save(pc);
			log.info("Record Updated Successfully for uid: "+uid);
		}
		return new Response("Record Updated Successfully");
	}

	public Response deleteProjectCodeById(Integer uid) throws Exception {
		if (!this.projectCodeRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.projectCodeRepository.deleteById(uid);
		log.info("Record Deleted Successfully for uid: "+uid);
		return new Response("Record Deleted Successfully");
	}

	public SizeAllocationResponse Sizeallocation(Integer uid) throws Exception {
		if (!this.projectCodeRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		ProjectCode project = this.projectCodeRepository.getById(uid);
		ProjectType ptype = this.projectTypeRepository.getById(uid);
		int freshersAssigned = 0;
		for (EmployeeProjectDetails employeeProjectDetails : employeeProjectDetailRepository.findAll()) {
			if (employeeProjectDetails.getProjUid() == uid)
				freshersAssigned++;
		}
		int projectFresherRequirment = (project.getProject_strength() * project.getProjectType().getPercentage()) / 100;
		SizeAllocationResponse sa = new SizeAllocationResponse();
		sa.setUid(project.getUid());
		sa.setProjectType(ptype.getProjectType());
		sa.setStrength(project.getProject_strength());
		sa.setPercentage(ptype.getPercentage());
		sa.setProjectFresherRequirment(projectFresherRequirment);
		sa.setFreshersAssigned(freshersAssigned);
		sa.setFreshernotassigned(projectFresherRequirment - freshersAssigned);
		return sa;
	}

}
