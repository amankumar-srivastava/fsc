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
import com.hcl.fsc.mastertables.CustomerName;
import com.hcl.fsc.repositories.master.CustomerNameRepository;
import com.hcl.fsc.response.Response;

@Service
public class CustomerNameService {

	@Autowired
	private CustomerNameRepository customerNameRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public List<CustomerName> getAllCustomerName() {
		log.info("Return All Customer Name Records Successfully");
		return this.customerNameRepository.findAll();
	}

	public CustomerName getCustomerById(Integer uid) {
		log.info("Return Customer Name record Successfully");
		return this.customerNameRepository.findById(uid).get();
	}

	public Response createNewCustomerName(CustomerName customerName) throws Exception {
		if (customerName.getUid() != null && this.customerNameRepository.existsById(customerName.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (customerName == null || StringUtils.isBlank(customerName.getCustomerName())) {
			throw new NullValueException("Customer name  is Empty");
		}
		if (this.customerNameRepository.existsCustomerNameBycustomerName(customerName.getCustomerName())) {
			throw new DuplicateValueException("Duplicate Customer Name: "+customerName.getCustomerName());
		} else {
			Integer uid=0;
			if (this.customerNameRepository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.customerNameRepository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			customerName.setUid(uid);
			customerName.setCreatedDate(LocalDateTime.now());
			this.customerNameRepository.save(customerName);
			log.info("New Record Succefully Created for uid: "+uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateCustomerName(CustomerName customerName, Integer uid) throws Exception {
		if (!this.customerNameRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UId is not present");
		}
		if (customerName.getCustomerName() != null && StringUtils.isBlank(customerName.getCustomerName())) {
			throw new NullValueException("Customer Name is Empty");
		}
		if(customerName.getUpdatedBy() == null || StringUtils.isBlank(customerName.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.customerNameRepository.existsCustomerNameBycustomerName(customerName.getCustomerName())) {
			throw new DuplicateValueException("Duplicate Customer name: "+customerName.getCustomerName());
		} else {
			CustomerName obj = this.customerNameRepository.getById(uid);
			customerName.setUid(uid);
			if (customerName.getCustomerName() == null) {
				customerName.setCustomerName(obj.getCustomerName());
			}
			customerName.setCreatedDate(obj.getCreatedDate());
			customerName.setUpdatedDate(LocalDateTime.now());
			this.customerNameRepository.save(customerName);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully");
		}
	}

	public Response deleteCustomerNameById(Integer uid) throws Exception {
		if (!this.customerNameRepository.existsById(uid)) {
			throw new ValueNotPresentException("No such Record Represent");
		}
		this.customerNameRepository.deleteById(uid);
		log.info("Record Deleted Successfully");
		return new Response("Record Deleted Successfully");
	}

}
