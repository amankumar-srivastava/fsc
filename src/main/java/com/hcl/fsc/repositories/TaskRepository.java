package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
	public Task findByUserId(long userId);

}
