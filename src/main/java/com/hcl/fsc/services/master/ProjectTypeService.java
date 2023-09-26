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
import com.hcl.fsc.mastertables.ProjectType;
import com.hcl.fsc.repositories.EmployeeDetailsRepository;
import com.hcl.fsc.repositories.master.ProjectTypeRepository;
import com.hcl.fsc.response.Response;


@Service
public class ProjectTypeService {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	@Autowired
	private ProjectTypeRepository projectTypeRepository;

	public List<ProjectType> getAllProjectType() {
		log.info("Return All Project Type Records Successfully");
		return this.projectTypeRepository.findAll();
	}

	public ProjectType getProjectTypeById(Integer uid) {
		log.info("Return Project Type record by UID: "+uid);
		return this.projectTypeRepository.findById(uid).get();
	}
	
	
	public Response createNewProjectType(ProjectType projectType) throws Exception {
		if (projectType.getUid() != null && this.projectTypeRepository.existsById(projectType.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (projectType.getProjectType() == null || StringUtils.isBlank(projectType.getProjectType())) {
			throw new NullValueException("Project Type is Empty");
		}
		if (projectType.getPercentage() == null) {
			throw new NullValueException("Percentage is Empty");
		}
		if (this.projectTypeRepository.existsProjectTypeByprojectType(projectType.getProjectType())) {
			throw new DuplicateValueException("Duplicate Project Type");
		} else {
			Integer uid=0;
			if (this.projectTypeRepository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.projectTypeRepository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			projectType.setUid(++uid);
			projectType.setCreatedDate(LocalDateTime.now());
			this.projectTypeRepository.save(projectType);
			log.info("New Record Succefully Created for uid: "+uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateProjectType(ProjectType projectType, Integer uid) throws Exception {
		if (!this.projectTypeRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (projectType.getProjectType() != null && StringUtils.isBlank(projectType.getProjectType())) {
			throw new NullValueException("Project Type is Empty");
		}
		if(projectType.getUpdatedBy() == null || StringUtils.isBlank(projectType.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		
		if (this.projectTypeRepository.existsProjectTypeByprojectType(projectType.getProjectType())) {
			throw new DuplicateValueException("Duplicate Project Type :"+projectType.getProjectType());
		} else {
			ProjectType obj = this.projectTypeRepository.getById(uid);
			projectType.setUid(uid);
			if (projectType.getPercentage() == null) {
				projectType.setPercentage(obj.getPercentage());
			}
			if (projectType.getProjectType() == null) {
				projectType.setProjectType(obj.getProjectType());
			}
			projectType.setCreatedDate(obj.getCreatedDate());
			projectType.setUpdatedDate(LocalDateTime.now());
			this.projectTypeRepository.save(projectType);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully");
		}
	}

	public Response deleteProjectTypeById(Integer uid) throws Exception {
		if (!this.projectTypeRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.projectTypeRepository.deleteById(uid);
		log.info("Record Deleted Successfully for uid: "+uid);
		return new Response("Record Deleted Successfully");
	}
	

}
