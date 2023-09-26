package com.hcl.fsc.entities;

import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.Location;
import com.hcl.fsc.mastertables.OfferedBand;
import com.hcl.fsc.mastertables.OfferedDesignation;
import com.hcl.fsc.mastertables.OfferedSubBand;
import com.hcl.fsc.mastertables.OnboardingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmployeeOnboardingDetails {

	@Id
	private String email;
	private Long sapId;
	private String onbaordingDetails;
	// master table
	@OneToOne
	private OnboardingStatus onboardingStatus;
	private String driveCollege;
	private String driveDate;
	// master table
	@OneToOne
	private OfferedDesignation offeredDesignation;
	// master table
	@OneToOne
	private OfferedBand offeredBand;
	// master table
	@OneToOne
	private OfferedSubBand offeredSubBand;
	// master table
	@OneToOne
	private CollegeTiering collegeTiering;
	private String FPM_SPOC;
	// master table
	@OneToOne
	private Location location;
	private String internJoiningStatus;
	private String internJoiningDate;
	private String internSapID;
	private String BR;
	private String h1OrH2;
	private String preOTPStatus;
	private String tentativeJoiningMonth;
	private String requisitionSource;
	private String plannedDOJ;
	private String month;
	private String tentativeJoiningQTR;
	private String tentativeDOJ;
	private String joiningStatus;
	private String tpPanel;
	private String tpSap;
	
	private String sheetCode;
	// private String finalStatus;
	// private String ownership;

//	@Column(name="DriveDate")
//	@JsonFormat(pattern="dd-MM-yyyy")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@JsonFormat(pattern = "dd/mm/yyyy")
//	private LocalDate driveDate;

}
