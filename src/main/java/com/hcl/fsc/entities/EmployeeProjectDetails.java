package com.hcl.fsc.entities;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
public class EmployeeProjectDetails {

	@Id
	private Long empSAPID;

	@NotNull(message = "ReportingMgrSAPID is mandatory!")
	private Integer reportingMgrSAPID;

	@NotBlank(message = "Location is mandatory!")
	private String location;

	@NotBlank(message = "Country is mandatory!")
	private String country;

	@NotNull(message = "HrL4Id is mandatory!")
	private Integer hrL4Id;

	private String lastProjectName;

	@NotNull(message = "AssignmentStartDate is mandatory!")
	private LocalDate assignmentStartDate;

	@NotNull(message = "AssignmentEndDate is mandatory!")
	private LocalDate assignmentEndDate;

	private String rasStatus;

	private String fresher;

	@NotBlank(message = "OnOffshore is mandatory!")
	private String onOff;

	@NotNull(message = "ProjUId is mandatory!")
	private Integer projUid;

	@NotBlank(message = "Job is mandatory!")
	private String job;

	@NotBlank(message = "Skill is mandatory!")
	private String skill;

	private Integer fte;

	@NotBlank(message = "CreatedBy is mandatory!")
	private String createdBy;

	@NotNull(message = "CreatedDate is mandatory!")
	private LocalDate createdDate;

	private String updatedBy;

	private LocalDate updatedDate;

	private String sr;

	@Column(name = "transaction_id")
	@NotBlank(message = "Incorrect transactionId")
	private String transactionId;

}