package com.hcl.fsc.excel.vo;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ExcelSheet("Tier-1")
public class Tier1 {

	@ExcelCellName("Student Name")
	private String name;

	@ExcelCellName("Gender")
	private String gender;

	@ExcelCellName("Email")
	private String email;

	@ExcelCellName("Ownership")
	private String ownership;

	@ExcelCellName("Contact")
	private String contactNo;

	@ExcelCellName("State Name")
	private String state;

	@ExcelCellName("Address")
	private String address;

	@ExcelCellName("Region")
	private String region;

	@ExcelCellName("10th Passing Year")
	private String highSchoolPassingYear;

	@ExcelCellName("12th Passing Year")
	private String intermediatePassingYear;

	@ExcelCellName("Graduation College")
	private String graduationCollege;

	@ExcelCellName("UG Degree")
	private String ugDegree;

	@ExcelCellName("UG Specialization")
	private String graduationSpecialization;

	@ExcelCellName("Graduation Passing Year")
	private String graduationPassingYear;

	@ExcelCellName("PG Degree")
	private String postGraduationDegree;

	@ExcelCellName("PG Specialization")
	private String postGraduationSpecialization;

	@ExcelCellName("Post Graduation Percentage (incase of CGPA please convert and mention %)")
	private String postGraduationPercentage;

	@ExcelCellName("Post Graduation Passing Year")
	private String postGraduationPassingYear;

	@ExcelCellName("PG College Name")
	private String pgCollegeName;

	@ExcelCellName("UG/PG")
	private String ugOrPg;

	@ExcelCellName("Drive College")
	private String driveCollege;

	@ExcelCellName("Drive Date")
	private String driveDate;

	@ExcelCellName("Offered Designation")
	private String offeredDesignation;

	@ExcelCellName("Offered Band")
	private String offeredBand;

	@ExcelCellName("Offered Sub Band")
	private String offeredSubBand;

	@ExcelCellName("College Tiering")
	private String collegeTiering;

	@ExcelCellName("TP Panel")
	private String tPPanel;

	@ExcelCellName("TP SAP Code")
	private String tPSap;

	@ExcelCellName("LOB")
	private String lob;

	@ExcelCellName("Team/RDU")
	private String team_Rdu;

	@ExcelCellName("L1")
	private String l1;

	@ExcelCellName("L2")
	private String l2;

	@ExcelCellName("L3")
	private String l3;

	@ExcelCellName("L4")
	private String l4;

	@ExcelCellName("HR Panel")
	private String hrPanel;

	@ExcelCellName("Demand Owner")
	private String demandOwner;

	@ExcelCellName("Demand Owner SAP")
	private String demandOwnerSAP;

	@ExcelCellName("Final Status")
	private String finalStatus;

	@ExcelCellName("Remark")
	private String remarks;

	@ExcelCellName("Candidate Mapping")
	private String candidateMapping;

	@ExcelCellName("BS")
	private String bs;

	@ExcelCellName("Dummy/Original")
	private String dummy_Original;

	@ExcelCellName("SR Status")
	private String srStatus;

	@ExcelCellName("Joining Status")
	private String joiningStatus;

	@ExcelCellName("SAP ID")
	private Long sapId;

	@ExcelCellName("Requisition Source")
	private String requisitionSource;

	@ExcelCellName("Planned DOJ")
	private String plannedDOJ;

	@ExcelCellName("Month")
	private String month;

	@ExcelCellName("Joining QTR")
	private String tentativeJoiningQTR;

	@ExcelCellName("H1/H2")
	private String h1OrH2;

//	@ExcelCellName("Current Batch Code")
//	private String currentBatchCode;
//	
//	@ExcelCellName("Required Batch Code")
//	private String requiredBatchCode;

	@ExcelCellName("Remapped Profile")
	private String remappedProfile;

	@ExcelCellName("Remapped Location")
	private String remappedLocation;

	@ExcelCellName("Acknowledgement")
	private String acknowledgement;

//	--------------------------------------------------  //
//	private String intermediatePercentage;
//	private String batch;

}
