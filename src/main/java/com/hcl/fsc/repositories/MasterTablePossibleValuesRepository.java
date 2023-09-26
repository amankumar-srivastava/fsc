package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.fsc.mastertables.MasterTablePossibleValues;

@Repository
public interface MasterTablePossibleValuesRepository extends JpaRepository<MasterTablePossibleValues, Integer> {

	public MasterTablePossibleValues findByValueAndMasterTable(String value, String masterTable);

	public MasterTablePossibleValues findTopByOrderByIdDesc();

	public MasterTablePossibleValues getByValue(String value);

	public MasterTablePossibleValues getByValueAndMasterTable(String value, String masterTable );

}
