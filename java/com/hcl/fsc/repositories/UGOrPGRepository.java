package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.fsc.mastertables.UGOrPG;

@Repository
public interface UGOrPGRepository extends JpaRepository<UGOrPG, String>{
	
	public UGOrPG getByKey(String key);
	public UGOrPG findByValue(String value);
}
