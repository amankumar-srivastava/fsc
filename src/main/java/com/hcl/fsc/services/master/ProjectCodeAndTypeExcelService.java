package com.hcl.fsc.services.master;

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
import com.hcl.fsc.mastertables.CustomerName;
import com.hcl.fsc.mastertables.ProjectCode;
import com.hcl.fsc.mastertables.ProjectType;
import com.hcl.fsc.repositories.master.CustomerNameRepository;
import com.hcl.fsc.repositories.master.ProjectCodeRepository;
import com.hcl.fsc.repositories.master.ProjectTypeRepository;
import com.hcl.fsc.response.ProjectCodeExcelResponse;

@Service
public class ProjectCodeAndTypeExcelService {
	@Autowired
	private ProjectCodeRepository projectCodeRepository;

	@Autowired
	private ProjectTypeRepository projectTypeRepository;

	@Autowired
	private CustomerNameRepository customerNameRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	public ProjectCodeExcelResponse create(MultipartFile file) throws Exception {

		List<ProjectTypeExcel> list = EmployeeHelper.convertExcelToListOfProjectType(file.getInputStream());

		int uid = 0;

		ProjectCodeExcelResponse projectCodeExcelResponse = new ProjectCodeExcelResponse();

		Set<String> projectTypeSet = list.stream().map(ProjectTypeExcel::getProjectType).filter(x -> x != null)
				.map(val -> val.trim()).map(v -> v.toUpperCase()).collect(Collectors.toSet());
		System.out.println(projectTypeSet);

		Set<String> customerNameSet = list.stream().map(ProjectTypeExcel::getCustomerName).filter(x -> x != null)
				.map(val -> val.trim()).map(v -> v.toUpperCase()).collect(Collectors.toSet());

		System.out.println(customerNameSet.size());

//---------------------------- Project Type ---------------------------------------------
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
			log.info("New Record Successfully Created for Project Type Master Table for uid: " + uid);
			count++;
		}
		projectCodeExcelResponse.setProjectType(count);

//---------------------------------- Customer Name -----------------------------------------

		Iterator<String> itrc = customerNameSet.iterator();
		flag = true;
		count = 0;
		while (itrc.hasNext()) {
			String type = itrc.next();
			if (type == null) {
				continue;
			}
			if (this.customerNameRepository.existsCustomerNameBycustomerName(type)) {
				flag = false;
				continue;
			}
			if (flag && this.customerNameRepository.findAll().size() == 0) {
				uid = 1;
				flag = false;
			} else {
				uid = this.customerNameRepository.findTopByOrderByUidDesc().getUid();
				uid = ++uid;
			}
			CustomerName customerName = new CustomerName();
			customerName.setUid(uid);
			customerName.setCreatedBy("admin");
			customerName.setCreatedDate(LocalDateTime.now());
			customerName.setCustomerName(type);
			this.customerNameRepository.save(customerName);
			log.info("New Record Successfully Created for Customer Name Master Table for uid: " + uid);
			count++;
		}
		projectCodeExcelResponse.setCustomerName(count);

//--------------------------- Project Code --------------------------------

		count = 0;
		uid = 0;
		flag = true;
		Iterator<ProjectTypeExcel> it = list.iterator();
		while (it.hasNext()) {
			ProjectTypeExcel projectTypeExcel = it.next();
			if (projectTypeExcel.getProjectType() == null || projectTypeExcel.getProjectName().equals("Not assigned")) {
				continue;
			}
			ProjectType projectType = this.projectTypeRepository.findByprojectType(projectTypeExcel.getProjectType());
			CustomerName customerName = this.customerNameRepository
					.findBycustomerName(projectTypeExcel.getCustomerName());
			if (this.projectCodeRepository.existsProjectCodeByprojectCode(projectTypeExcel.getProjectCode())) {
				ProjectCode pc = this.projectCodeRepository.findByProjectCode(projectTypeExcel.getProjectCode());
				if (pc.getProjectType() == null) {
					pc.setProjectType(projectType);
				}
				if (pc.getCustomerName() == null) {
					pc.setCustomerName(customerName);
				}
				this.projectCodeRepository.save(pc);
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

			ProjectCode projectCode = new ProjectCode();
			projectCode.setUid(uid);
			projectCode.setProjectCode(projectTypeExcel.getProjectCode());
			projectCode.setProjectName(projectTypeExcel.getProjectName());
			projectCode.setProjectType(projectType);
			projectCode.setProject_strength(0);
			projectCode.setCreatedBy("admin");
			projectCode.setCreatedDate(LocalDateTime.now());
			projectCode.setEndDate(projectTypeExcel.getProjectEndDate());
			projectCode.setBussinessArea("Noida");
			projectCode.setCustomerName(customerName);
			this.projectCodeRepository.save(projectCode);
			log.info("New Record Successfully Created for Project Code Master Table for uid: " + uid);
			count++;
		}
		projectCodeExcelResponse.setProjectCode(count);
		return projectCodeExcelResponse;
	}

}
