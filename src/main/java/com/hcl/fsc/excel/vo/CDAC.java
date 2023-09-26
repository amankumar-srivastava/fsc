package com.hcl.fsc.excel.vo;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ExcelSheet("CDAC")
public class CDAC {

	@ExcelCellName("SAP")
	private Long sapId;
	@ExcelCellName("Candidate Name")
	private String name;
	@ExcelCellName("Gender")
	private String gender;
	@ExcelCellName("Mobile Number")
	private String contactNo;
	@ExcelCellName("E-mail ID")
	private String email;
	@ExcelCellName("Onboarding Status")
	private String onboardingStatus;
	@ExcelCellName("Address")
	private String address;
	@ExcelCellName("Batch")
	private String batch;
	@ExcelCellName("State")
	private String state;
	@ExcelCellName("Region")
	private String region;
	@ExcelCellName("10th Passing year")
	private String highSchoolPassingYear;
	@ExcelCellName("12th Passing year")
	private String intermediatePassingYear;
	@ExcelCellName("UG College name")
	private String graduationCollege;
	@ExcelCellName("UG Degree/Specialization")
	private String ugDegree;
	@ExcelCellName("UG Passing Year")
	private String graduationPassingYear;
	@ExcelCellName("Drive College")
	private String driveCollege;
	@ExcelCellName("Drive Date")
	private String driveDate;
	@ExcelCellName("TP Panel")
	private String tpPanel;
	@ExcelCellName("TP Sap")
	private String tpSap;
	@ExcelCellName("LOB")
	private String lob;
	@ExcelCellName("L2")
	private String l2;
	@ExcelCellName("Team/RDU")
	private String team_Rdu;
	@ExcelCellName("Demand Owner")
	private String demandOwner;
	@ExcelCellName("Demand Owner Sap ID")
	private String demandOwnerSap;
	@ExcelCellName("Band")
	private String offeredBand;
	@ExcelCellName("Sub Band")
	private String offeredSubBand;
	@ExcelCellName("Designation")
	private String offeredDesignation;
	@ExcelCellName("H1/H2")
	private String h1OrH2;
	@ExcelCellName("Planned DOJ")
	private String plannedDOJ;
	@ExcelCellName("Joining Status")
	private String joiningStatus;
	@ExcelCellName("Month")
	private String month;
	@ExcelCellName("Tentative Joining QTR")
	private String tentativeJoiningQTR;
	@ExcelCellName("SR Number")
	private String srNumber;

//	@ExcelCellName("College Tiering")
//	private String collegeTiering ;
//	
//	@ExcelCellName("FPM SPOC")
//	private String FPM_SPOC;
//	@ExcelCellName("Location")
//	private String location;
//	@ExcelCellName("Intern Joining Status")
//	private String internJoiningStatus;
//	@ExcelCellName("Intern Joining Date")
//	private String internJoiningDate;
//	@ExcelCellName("Intern SAP ID")
//	private String internSapID;
//	
//	@ExcelCellName("Pre OTP Status")
//	private String preOTPStatus;
//	
//	
//	
//	
//	@ExcelCellName("Final Status")
//	private String finalStatus;

}
