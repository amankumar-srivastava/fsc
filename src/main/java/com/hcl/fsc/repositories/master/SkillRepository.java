package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.fsc.mastertables.Rm;
import com.hcl.fsc.mastertables.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
	
	public Skill findTopByOrderByUidDesc();

	public boolean existsSkillByskill(String skill);

}