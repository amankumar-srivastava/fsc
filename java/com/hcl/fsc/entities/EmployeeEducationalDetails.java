package com.hcl.fsc.entities;

import com.hcl.fsc.mastertables.GraduationSpecialization;
import com.hcl.fsc.mastertables.UGOrPG;
import com.hcl.fsc.mastertables.UgDegree;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Entity
public class EmployeeEducationalDetails {

	@Id
	private String email;
	private Long sapId;
	private String batch;
	private String highSchoolPassingYear;
	private String intermediatePercentage;
	private String intermediatePassingYear;
	private String graduationCollege;
	private String graduationPassingYear;
	private String universityRegistrationId;
	private String postGraduationDegree;
	private String postGraduationSpecialisation;
	private String postGraduationPassingYear;
	private String postGraduationPercentage;
	private String pgCollegeName;
	// master table
	@OneToOne
	private UGOrPG ugOrPg;

	// master table
	@OneToOne
	private UgDegree ugDegree;
	// master table
	@OneToOne
	private GraduationSpecialization graduationSpecialization;

	private String sheetCode;

}
