package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.UgDegree;

@Repository
public interface UgDegreeRepository extends JpaRepository<UgDegree, String> {
	public UgDegree getByKey(String key);

	public UgDegree findByValue(String value);
}
