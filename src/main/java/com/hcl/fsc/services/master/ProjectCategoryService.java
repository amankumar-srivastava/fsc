package com.hcl.fsc.services.master;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.fsc.customExpcetion.DuplicateValueException;
import com.hcl.fsc.customExpcetion.NullValueException;
import com.hcl.fsc.customExpcetion.ValueNotPresentException;
import com.hcl.fsc.mastertables.ProjectCategory;
import com.hcl.fsc.mastertables.ProjectCategory;
import com.hcl.fsc.repositories.master.ProjectCategoryRepository;
import com.hcl.fsc.repositories.master.ProjectCodeRepository;
import com.hcl.fsc.response.Response;

@Service
public class ProjectCategoryService {

	@Autowired
	private ProjectCategoryRepository projectCategoryRepository;

	public List<ProjectCategory> getAllProjectCategory() {
		return this.projectCategoryRepository.findAll();
	}

	public ProjectCategory getProjectCategoryById(Integer uid) {
		return this.projectCategoryRepository.findById(uid).get();
	}

	public Response createNewProjectCategory(ProjectCategory projectCategory) throws Exception {
		if (projectCategory.getUid() != null && this.projectCategoryRepository.existsById(projectCategory.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (projectCategory.getProjectCategory() == null || StringUtils.isBlank(projectCategory.getProjectCategory())) {
			throw new NullValueException("Project Category is Empty");
		}
		if (this.projectCategoryRepository
				.existsProjectCategoryByprojectCategory(projectCategory.getProjectCategory())) {
			throw new DuplicateValueException("Duplicate Project Category");
		} else {
			if (this.projectCategoryRepository.findAll().size() == 0) {
				projectCategory.setUid(1);
				projectCategory.setCreatedDate(java.time.LocalDateTime.now());
			} else {
				Integer uid = this.projectCategoryRepository.findTopByOrderByUidDesc().getUid();
				projectCategory.setUid(++uid);
				projectCategory.setCreatedDate(java.time.LocalDateTime.now());
			}
			this.projectCategoryRepository.save(projectCategory);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateProjectCategory(ProjectCategory projectCategory, Integer uid) throws Exception {
		if (!this.projectCategoryRepository.existsById(uid)) {
			throw new ValueNotPresentException("UId is not present");
		}
		if (projectCategory.getProjectCategory() != null && StringUtils.isBlank(projectCategory.getProjectCategory())) {
			throw new NullValueException("Project Category is Empty");
		}
		if(projectCategory.getUpdatedBy() == null || StringUtils.isBlank(projectCategory.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.projectCategoryRepository.existsProjectCategoryByprojectCategory(projectCategory.getProjectCategory())) {
			throw new DuplicateValueException("Duplicate Project Category");
		} else {
		 ProjectCategory obj = this.projectCategoryRepository.getById(uid);
			projectCategory.setUid(uid);
			if (projectCategory.getProjectCategory() == null) {
				projectCategory.setProjectCategory(obj.getProjectCategory());
			}
			projectCategory.setCreatedDate(obj.getCreatedDate());
			projectCategory.setUpdatedDate(java.time.LocalDateTime.now());
			this.projectCategoryRepository.save(projectCategory);
			return new Response("Record Updated Successfully");
		}
	}

	public Response deleteProjectCategoryById(Integer uid) throws Exception {
		if (!this.projectCategoryRepository.existsById(uid)) {
			throw new ValueNotPresentException("No such Record Present");
		}
		this.projectCategoryRepository.deleteById(uid);
		return new Response("Record Deleted Successfully");
	}

}
