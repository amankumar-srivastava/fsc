package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.OfferedSubBand;

@Repository
public interface OfferedSubBandRepository extends JpaRepository<OfferedSubBand, String> {

	public OfferedSubBand getByKey(String key);

	public OfferedSubBand findByValue(String value);

}
