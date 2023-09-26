package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.GraduationSpecialization;


@Repository
public interface GraduationSpecializationRepository extends JpaRepository<GraduationSpecialization, String> {
	
	public GraduationSpecialization getByKey(String key);
	public GraduationSpecialization findByValue(String value);
	public GraduationSpecialization findTopByOrderByUidDesc();
}
