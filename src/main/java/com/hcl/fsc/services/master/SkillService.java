package com.hcl.fsc.services.master;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.mastertables.SkillMaster;
import com.hcl.fsc.repositories.master.SkillMasterRepository;

@Service
public class SkillService {
	@Autowired
	private SkillMasterRepository skillRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	public List<SkillMaster> getAllSkill() {
		log.info("Return All RM Records Successfully");
		return this.skillRepository.findAll();
	}
}
