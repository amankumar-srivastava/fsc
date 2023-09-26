package com.hcl.fsc.services.master;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.excel.vo.ProjectTypeExcel;
import com.hcl.fsc.helpers.EmployeeHelper;
import com.hcl.fsc.mastertables.ProjectCode;
import com.hcl.fsc.mastertables.ProjectType;
import com.hcl.fsc.repositories.master.ProjectCodeRepository;
import com.hcl.fsc.repositories.master.ProjectTypeRepository;
import com.hcl.fsc.response.ProjectCodeExcelResponse;
import com.hcl.fsc.response.Response;

@Service
public class ProjectCodeAndTypeExcelService {
	@Autowired
	private ProjectCodeRepository projectCodeRepository;

	@Autowired
	private ProjectTypeRepository projectTypeRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public ProjectCodeExcelResponse create(MultipartFile file) throws Exception {
		List<ProjectTypeExcel> list = EmployeeHelper.convertExcelToListOfProjectType(file.getInputStream());
		int uid = 0;
		ProjectCodeExcelResponse projectCodeExcelResponse = new ProjectCodeExcelResponse();
		Set<String> projectTypeSet = list.stream().map(ProjectTypeExcel::getProjectType).filter(x -> x != null)
				.map(val -> val.trim()).map(v -> v.toUpperCase()).collect(Collectors.toSet());
		System.out.println(projectTypeSet);
		Iterator<String> itr = projectTypeSet.iterator();
		boolean flag = true;
		int count = 0;
		while (itr.hasNext()) {
			String type = itr.next();
			if (type == null) {
				continue;
			}
			if (this.projectTypeRepository.existsProjectTypeByprojectType(type)) {
				flag = false;
				continue;
			}
			if (flag && this.projectTypeRepository.findAll().size() == 0) {
				uid = 1;
				flag = false;
			} else {
				uid = this.projectTypeRepository.findTopByOrderByUidDesc().getUid();
				uid = ++uid;
			}
			ProjectType projectType = new ProjectType();
			projectType.setUid(uid);
			projectType.setCreatedBy("admin");
			projectType.setPercentage(-1);
			projectType.setCreatedDate(LocalDateTime.now());
			projectType.setProjectType(type);
			this.projectTypeRepository.save(projectType);
			log.info("New Record Successfully Created for Project Type Master Table for uid: "+uid);
			count++;
		}
		projectCodeExcelResponse.setProjectType(count);
		count = 0;
		uid = 0;
		flag = true;
		Iterator<ProjectTypeExcel> it = list.iterator();
		while (it.hasNext()) {
			ProjectTypeExcel projectTypeExcel = it.next();
			if (projectTypeExcel.getProjectType() == null) {
				continue;
			}
			if (this.projectCodeRepository.existsProjectCodeByprojectCode(projectTypeExcel.getProjectCode())) {
				flag = false;
				continue;
			}
			if (flag && this.projectCodeRepository.findAll().size() == 0) {
				uid = 1;
				flag = false;
			} else {
				uid = this.projectCodeRepository.findTopByOrderByUidDesc().getUid();
				uid = ++uid;
			}
			ProjectType projectType = this.projectTypeRepository.findByprojectType(projectTypeExcel.getProjectType());
			ProjectCode projectCode = new ProjectCode();
			projectCode.setUid(uid);
			projectCode.setProjectCode(projectTypeExcel.getProjectCode());
			projectCode.setProjectName(projectTypeExcel.getProjectName());
			projectCode.setProjectType(projectType);
			projectCode.setProject_strength(-1);
			projectCode.setCreatedBy("admin");
			projectCode.setCreatedDate(LocalDateTime.now());
			projectCode.setEndDate(projectTypeExcel.getProjectEndDate());
			this.projectCodeRepository.save(projectCode);
			log.info("New Record Successfully Created for Project Code Master Table for uid: "+uid);
			count++;
		}
		projectCodeExcelResponse.setProjectCode(count);
		return projectCodeExcelResponse;
	}

}
