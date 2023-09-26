package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.OfferedBand;

@Repository
public interface OfferedBandRepository extends JpaRepository<OfferedBand, String> {

	public OfferedBand getByKey(String key);

	public OfferedBand findByValue(String value);

}
