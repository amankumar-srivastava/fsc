package com.hcl.fsc.excel.vo;

import java.time.LocalDate;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

import lombok.Data;
@Data
@ExcelSheet("Project Universe")
public class ProjectTypeExcel {
	
	@ExcelCellName("Proj. Code")
	private String projectCode;
	
	@ExcelCellName("Project Name")
	private String projectName;
	
	@ExcelCellName("Proj Category")
	private String projectType;
	
	@ExcelCellName("Customer Name")
	private String customerName; 
	
	@ExcelCellName("Proj End Dt")
	private LocalDate projectEndDate;
	
}
