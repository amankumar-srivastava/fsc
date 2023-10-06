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
import com.hcl.fsc.mastertables.Job;
import com.hcl.fsc.mastertables.Skill;
import com.hcl.fsc.repositories.master.JobRepository;
import com.hcl.fsc.response.Response;

@Service
public class JobService {
	
	@Autowired 
	private JobRepository jobRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public List<Job> getAllJob(){
		return this.jobRepository.findAll();
	}
	
	public Job getJobByUid(Integer uid) throws ValueNotPresentException {
		if(!this.jobRepository.existsById(uid)) {
			throw new ValueNotPresentException("Uid is not present");
		}
		return this.jobRepository.findById(uid).get();
	}
	
	public Response createNewJob(Job job) throws Exception {
		if (job.getUid() != null && this.jobRepository.existsById(job.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (job.getJob() == null || StringUtils.isBlank(job.getJob())) {
			throw new NullValueException("Job is Empty");
		}
		if (this.jobRepository.existsJobByjob(job.getJob())) {
			throw new DuplicateValueException("Duplicate Job");
		} else {
			Integer uid=0;
			if (this.jobRepository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.jobRepository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			job.setUid(uid);
			job.setCreatedDate(LocalDateTime.now());
			this.jobRepository.save(job);
			log.info("New Record Succefully Created for uid: "+ uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateJob(Job job, Integer uid) throws Exception {
		if (!this.jobRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (job.getJob() != null && StringUtils.isBlank(job.getJob())) {
			throw new NullValueException("Job is Empty");
		}
		if(job.getUpdatedBy() == null ||  StringUtils.isBlank(job.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.jobRepository.existsJobByjob(job.getJob())) {
			throw new DuplicateValueException("Duplicate Job: "+ job.getJob());
		} else {
			Job obj = this.jobRepository.findById(uid).get();
			job.setUid(uid);
			if (job.getJob() == null) {
				job.setJob(obj.getJob());
			}
			job.setCreatedDate(obj.getCreatedDate());
			job.setUpdatedDate(LocalDateTime.now());
			this.jobRepository.save(job);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully for uid: "+uid);
		}
	}

	public Response deleteJobById(Integer uid) throws Exception {
		if (!this.jobRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.jobRepository.deleteById(uid);
		log.info("Record Deleted Successfully for uid:"+ uid);
		return new Response("Record Deleted Successfully");
	}
	
	
}
