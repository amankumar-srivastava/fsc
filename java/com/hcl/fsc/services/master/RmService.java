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
import com.hcl.fsc.mastertables.Rm;
import com.hcl.fsc.repositories.master.RmRepository;
import com.hcl.fsc.response.Response;

@Service
public class RmService {

	@Autowired
	private RmRepository rmRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public List<Rm> getAllRM() {
		log.info("Return All RM Records Successfully");
		return this.rmRepository.findAll();
	}

	public Rm getRmById(Integer uid) {
		log.info("Return RM record by UID: "+uid);
		return this.rmRepository.findById(uid).get();
	}

	public Response createNewRm(Rm rm) throws Exception {
		if (rm.getUid() != null && this.rmRepository.existsById(rm.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (rm.getSapId() == null || StringUtils.isBlank(rm.getSapId())) {
			throw new NullValueException("SAP Id is Empty");
		}
		if (rm.getRmName() == null || StringUtils.isBlank(rm.getRmName())) {
			throw new NullValueException("RM name is Empty");
		}
		if (this.rmRepository.existsRmBysapId(rm.getSapId())) {
			throw new DuplicateValueException("Duplicate SAP Id");
		} else {
			Integer uid=0;
			if (this.rmRepository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.rmRepository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			rm.setUid(uid);
			rm.setCreatedDate(LocalDateTime.now());
			this.rmRepository.save(rm);
			log.info("New Record Succefully Created for uid: "+ uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateRm(Rm rm, Integer uid) throws Exception {
		if (!this.rmRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (rm.getSapId() != null && StringUtils.isBlank(rm.getSapId())) {
			throw new NullValueException("SAP Id is Empty");
		}
		if (rm.getRmName() != null && StringUtils.isBlank(rm.getRmName())) {
			throw new NullValueException("RM name is Empty");
		}
		if(rm.getUpdatedBy() == null ||  StringUtils.isBlank(rm.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.rmRepository.existsRmBysapId(rm.getSapId())) {
			throw new DuplicateValueException("Duplicate SAP ID: "+ rm.getSapId());
		} else {
			Rm obj = this.rmRepository.getById(uid);
			rm.setUid(uid);
			if (rm.getSapId() == null) {
				rm.setSapId(obj.getSapId());
			}
			if (rm.getRmName() == null) {
				rm.setRmName(obj.getRmName());
			}
			rm.setCreatedDate(obj.getCreatedDate());
			rm.setUpdatedDate(LocalDateTime.now());
			this.rmRepository.save(rm);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully for uid: "+uid);
		}
	}

	public Response deleteRmById(Integer uid) throws Exception {
		if (!this.rmRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.rmRepository.deleteById(uid);
		log.info("Record Deleted Successfully for uid:"+ uid);
		return new Response("Record Deleted Successfully");
	}

}
