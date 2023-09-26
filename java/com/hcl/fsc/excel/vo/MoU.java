package com.hcl.fsc.excel.vo;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ExcelSheet("MoU")
public class MoU {

	@ExcelCellName("Candidate Name")
	private String name;

	@ExcelCellName("Gender")
	private String gender;

	@ExcelCellName("Mobile No.")
	private String contactNo;

	@ExcelCellName("Alternative Mobile No.")
	private String alternateContactNo;

	@ExcelCellName("Email id")
	private String email;

	@ExcelCellName("Complete address")
	private String address;

	@ExcelCellName("State")
	private String state;

	@ExcelCellName("10th School")
	private String school10th;

	@ExcelCellName("10th Passing year")
	private String highSchoolPassingYear;

	@ExcelCellName("12th School")
	private String school12th;

	@ExcelCellName("12th Passing year")
	private String intermediatePassingYear;

	@ExcelCellName("UG College")
	private String graduationCollege;

	@ExcelCellName("UG Degree")
	private String graduationSpecialization;

	@ExcelCellName("UG Passing year")
	private String graduationPassingYear;

	@ExcelCellName("PG College name")
	private String pgCollegeName;

	@ExcelCellName("PG Degree/Specialisations")
	private String postGraduationDegree;

	@ExcelCellName("PG passing year")
	private String postGraduationPassingYear;

	@ExcelCellName("PG Percentage")
	private String postGraduationPercentage;

	@ExcelCellName("Drive College")
	private String driveCollege;

	@ExcelCellName("Drive Date")
	private String driveDate;
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@JsonFormat(pattern = "dd/mm/yyyy")
//	private LocalDate driveDate;

	@ExcelCellName("SR")
	private String srMapping;

	@ExcelCellName("L2")
	private String l2;

	@ExcelCellName("L4")
	private String l4;

	@ExcelCellName("Band")
	private String offeredBand;

	@ExcelCellName("Sub Band")
	private String offeredSubBand;

	@ExcelCellName("Designation")
	private String offeredDesignation;

	@ExcelCellName("Doj")
	private String plannedDOJ;

	@ExcelCellName("Joining QTR")
	private String tentativeJoiningQTR;

	@ExcelCellName("H1/H2")
	private String h1OrH2;

	@ExcelCellName("Joining status")
	private String joiningStatus;

	@ExcelCellName("Sap id")
	private Long sapId;

}
