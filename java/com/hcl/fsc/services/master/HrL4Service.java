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
import com.hcl.fsc.mastertables.HrL4;
import com.hcl.fsc.repositories.master.HrL4Repository;
import com.hcl.fsc.response.Response;

@Service
public class HrL4Service {

	@Autowired
	private HrL4Repository hrL4Repository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	public List<HrL4> getAllHrL4() {
		log.info("Return All HRL4 Records Successfully");
		return this.hrL4Repository.findAll();
	}

	public HrL4 getHrL4ById(Integer uid) {
		log.info("Return HRL4 record by UID: "+uid);
		return this.hrL4Repository.findById(uid).get();
	}

	public Response createNewHrL4(HrL4 hrL4) throws Exception {
		if (hrL4.getUid() != null && this.hrL4Repository.existsById(hrL4.getUid())) {
			throw new DuplicateValueException("Duplicate UID: "+hrL4.getUid());
		}
		if (hrL4.getHrL4Code() == null || StringUtils.isBlank(hrL4.getHrL4Code())) {
			throw new NullValueException("HR L4 Name is Empty");
		}
		if (hrL4.getHrL4Name() == null || StringUtils.isBlank(hrL4.getHrL4Name())) {
			throw new NullValueException("HR L4 Code is Empty");
		}
		if (this.hrL4Repository.existsHrL4ByhrL4Code(hrL4.getHrL4Code())) {
			throw new DuplicateValueException("Duplicate HR L4 Code: "+hrL4.getHrL4Code());
		}
		if (this.hrL4Repository.existsHrL4ByhrL4Name(hrL4.getHrL4Name())) {
			throw new DuplicateValueException("Duplicate HR L4 Name: "+hrL4.getHrL4Name());
		} else {
			Integer uid=0;
			if (this.hrL4Repository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.hrL4Repository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			hrL4.setUid(uid);
			hrL4.setCreatedDate(LocalDateTime.now());
			this.hrL4Repository.save(hrL4);
			log.info("New Record Succefully Created for uid: "+uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateHrL4(HrL4 hrL4, Integer uid) throws Exception {
		if (!this.hrL4Repository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (hrL4.getHrL4Name() != null && StringUtils.isBlank(hrL4.getHrL4Name())) {
			throw new NullValueException("HR L4 Name is Empty");
		}
		if (hrL4.getHrL4Code() != null && StringUtils.isBlank(hrL4.getHrL4Code())) {
			throw new NullValueException("HR L4 Code is Empty");
		}
		if (hrL4.getUpdatedBy() == null || StringUtils.isBlank(hrL4.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.hrL4Repository.existsHrL4ByhrL4Code(hrL4.getHrL4Code())) {
			throw new DuplicateValueException("Duplicate HR L4 Code: "+hrL4.getHrL4Code());
		}
		if (this.hrL4Repository.existsHrL4ByhrL4Name(hrL4.getHrL4Name())) {
			throw new DuplicateValueException("Duplicate HR L4 Name: "+hrL4.getHrL4Name());
		} else {
			HrL4 hr = this.hrL4Repository.getById(uid);
			hrL4.setUid(uid);
			if (hrL4.getHrL4Code() == null) {
				hrL4.setHrL4Code(hr.getHrL4Code());
			}
			if (hrL4.getHrL4Name() == null) {
				hrL4.setHrL4Name(hr.getHrL4Name());
			}
			hrL4.setCreatedDate(hr.getCreatedDate());
			hrL4.setUpdatedDate(LocalDateTime.now());
			this.hrL4Repository.save(hrL4);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully");
		}
	}

	public Response deleteHrL4ById(Integer uid) throws Exception {
		if (!this.hrL4Repository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.hrL4Repository.deleteById(uid);
		log.info("Record Deleted Successfully for uid: "+uid);
		return new Response("Record Deleted Successfully");
	}

}
