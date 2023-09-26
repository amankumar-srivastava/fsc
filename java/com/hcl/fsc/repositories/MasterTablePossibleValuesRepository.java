package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.MasterTablePossibleValues;

@Repository
public interface MasterTablePossibleValuesRepository extends JpaRepository<MasterTablePossibleValues, String> {

}
