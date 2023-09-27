package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.SkillMaster;

@Repository
public interface SkillMasterRepository extends JpaRepository<SkillMaster,Integer> {

}

