package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.OnboardingStatus;

@Repository
public interface OnboardingStatusRepository extends JpaRepository<OnboardingStatus, String> {
	public OnboardingStatus getByKey(String key);

	public OnboardingStatus findByValue(String value);

	public OnboardingStatus findTopByOrderByUidDesc();
}
