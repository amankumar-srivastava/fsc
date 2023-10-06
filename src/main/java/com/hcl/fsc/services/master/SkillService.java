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
import com.hcl.fsc.mastertables.Skill;
import com.hcl.fsc.repositories.master.SkillRepository;
import com.hcl.fsc.response.Response;

@Service
public class SkillService {

	@Autowired
	private SkillRepository skillRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	public List<Skill> getAllSkills() {
		return this.skillRepository.findAll();
	}

	public Skill getSkillById(Integer uid) throws ValueNotPresentException {
		if (!this.skillRepository.existsById(uid)) {
			throw new ValueNotPresentException("Uid is not present");
		}
		return this.skillRepository.findById(uid).get();
	}
	
	public Response createNewSkill(Skill skill) throws Exception {
		if (skill.getUid() != null && this.skillRepository.existsById(skill.getUid())) {
			throw new DuplicateValueException("Duplicate UID");
		}
		if (skill.getSkill() == null || StringUtils.isBlank(skill.getSkill())) {
			throw new NullValueException("Skill is Empty");
		}
		if (this.skillRepository.existsSkillByskill(skill.getSkill())) {
			throw new DuplicateValueException("Duplicate Skill");
		} else {
			Integer uid=0;
			if (this.skillRepository.findAll().size() == 0) {
				uid=1;
			} else {
				uid = this.skillRepository.findTopByOrderByUidDesc().getUid();
				uid=uid++;
			}
			skill.setUid(uid);
			skill.setCreatedDate(LocalDateTime.now());
			this.skillRepository.save(skill);
			log.info("New Record Succefully Created for uid: "+ uid);
		}
		return new Response("New Record Succefully Created");
	}

	public Response updateSkill(Skill skill, Integer uid) throws Exception {
		if (!this.skillRepository.existsById(uid)) {
			throw new ValueNotPresentException(uid+" UID is not present");
		}
		if (skill.getSkill() != null && StringUtils.isBlank(skill.getSkill())) {
			throw new NullValueException("Skill is Empty");
		}
		if(skill.getUpdatedBy() == null ||  StringUtils.isBlank(skill.getUpdatedBy())) {
			throw new NullValueException("Updated By is Empty");
		}
		if (this.skillRepository.existsSkillByskill(skill.getSkill())) {
			throw new DuplicateValueException("Duplicate Skill: "+ skill.getSkill());
		} else {
			Skill obj = this.skillRepository.findById(uid).get();
			skill.setUid(uid);
			if (skill.getSkill() == null) {
				skill.setSkill(obj.getSkill());
			}
			skill.setCreatedDate(obj.getCreatedDate());
			skill.setUpdatedDate(LocalDateTime.now());
			this.skillRepository.save(skill);
			log.info("Record Updated Successfully for uid: "+uid);
			return new Response("Record Updated Successfully for uid: "+uid);
		}
	}

	public Response deleteSkillById(Integer uid) throws Exception {
		if (!this.skillRepository.existsById(uid)) {
			throw new ValueNotPresentException("UID is Incorrect");
		}
		this.skillRepository.deleteById(uid);
		log.info("Record Deleted Successfully for uid:"+ uid);
		return new Response("Record Deleted Successfully");
	}

}
