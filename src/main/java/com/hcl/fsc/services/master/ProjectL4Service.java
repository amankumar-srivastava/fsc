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
import com.hcl.fsc.mastertables.ProjectL4;
import com.hcl.fsc.repositories.master.ProjectL4Repository;
import com.hcl.fsc.response.Response;

@Service
public class ProjectL4Service {

	@Autowired
	private ProjectL4Repository projectL4Repository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public List<ProjectL4> getAllProjectL4() {
		log.info("Return All Project L4 Records Successfully");
		return this.projectL4Repository.findAll();
	}

	public ProjectL4 getProjectL4ById(Integer uid) {
		log.info("Return Project L4 record Successfully");
		return this.projectL4Repository.findById(uid).get();
	}

	public Response createNewProjectL4(ProjectL4 projectL4) throws Exception {
		if (projectL4.getUid() != null && this.projectL4Repository.existsById(projectL4.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (projectL4.getProjectL4Name() == null || StringUtils.isBlank(projectL4.getProjectL4Name())) {
			throw new NullValueException("Project L4 Name is Empty");
		}
		if (projectL4.getProjectL4Code() == null || StringUtils.isBlank(projectL4.getProjectL4Code())) {
			throw new NullValueException("Project L4 Code is Empty");
		}
		if (this.projectL4Repository.existsProjectL4ByprojectL4Code(projectL4.getProjectL4Code())) {
			throw new DuplicateValueException("Duplicate Project L4 Code");
		}
		if (this.projectL4Repository.existsProjectL4ByprojectL4Name(projectL4.getProjectL4Name())) {
			throw new DuplicateValueException("Duplicate HR L4 Name");
		} else {
			Integer uid=0;
			if (this.projectL4Repository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.projectL4Repository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			projectL4.setUid(uid);
			projectL4.setCreatedDate(LocalDateTime.now());
			this.projectL4Repository.save(projectL4);
			log.info("New Record Succefully Created for uid: "+uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateProjectL4(ProjectL4 projectL4, Integer uid) throws Exception {
		if (!this.projectL4Repository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (projectL4.getProjectL4Name() != null && StringUtils.isBlank(projectL4.getProjectL4Name())) {
			throw new NullValueException("Project L4 Name is Empty");
		}
		if (projectL4.getProjectL4Code() != null && StringUtils.isBlank(projectL4.getProjectL4Code())) {
			throw new NullValueException("Project L4 Code is Empty");
		}
		if(projectL4.getUpdatedBy() == null ||  StringUtils.isBlank(projectL4.getUpdatedBy())){
			throw new DuplicateValueException("Updated By is Empty");
		}
		if (this.projectL4Repository.existsProjectL4ByprojectL4Code(projectL4.getProjectL4Code())) {
			throw new DuplicateValueException("Duplicate Project L4 Code: "+projectL4.getProjectL4Code());
		}
		
		if (this.projectL4Repository.existsProjectL4ByprojectL4Name(projectL4.getProjectL4Name())) {
			throw new DuplicateValueException("Duplicate Project L4 Name: "+projectL4.getProjectL4Name());
		} else {
			ProjectL4 proj = this.projectL4Repository.getById(uid);
			projectL4.setUid(uid);
			if (projectL4.getProjectL4Code() == null) {
				projectL4.setProjectL4Code(proj.getProjectL4Code());
			}
			if (projectL4.getProjectL4Name() == null) {
				projectL4.setProjectL4Name(proj.getProjectL4Name());
			}
			projectL4.setCreatedDate(proj.getCreatedDate());
			projectL4.setUpdatedDate(LocalDateTime.now());
			this.projectL4Repository.save(projectL4);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully");
		}
	}

	public Response deleteProjectL4ById(Integer uid) throws Exception {
		if (!this.projectL4Repository.existsById(uid)) {
			throw new ValueNotPresentException("No such Record Present");
		}
		this.projectL4Repository.deleteById(uid);
		log.info("Record Deleted Successfully for uid: "+uid);
		return new Response("Record Deleted Successfully");
	}

}
