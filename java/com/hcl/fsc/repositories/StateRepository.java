package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.State;

@Repository
public interface StateRepository extends JpaRepository<State, String> {

	public State getByKey(String key);

	public State findByValue(String value);

}
