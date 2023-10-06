package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.Job;
import com.hcl.fsc.mastertables.Skill;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

	public Job findTopByOrderByUidDesc();

	public boolean existsJobByjob(String job);

}