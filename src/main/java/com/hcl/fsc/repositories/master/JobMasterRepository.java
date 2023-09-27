package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.JobMaster;

@Repository
public interface JobMasterRepository extends JpaRepository<JobMaster,Integer> {

}
