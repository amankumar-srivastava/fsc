package com.hcl.fsc.entities;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
public class EmployeeProjectDetails {
	
	@Id
    private Long empSAPID;
 
	@NotNull(message ="ReportingMgrSAPID is mandatory!")
    private Integer reportingMgrSAPID;

	@NotBlank(message ="Location is mandatory!")
    private String location;

	@NotBlank(message ="Country is mandatory!")
    private String country;

    @NotNull(message ="HrL4Id is mandatory!")
    private Integer hrL4Id;
 
    private String lastProjectName;

    private String customerName;

    @NotNull(message ="AssignmentStartDate is mandatory!")
//    @Past(message="Please enter date from past in format YYYY-MM-DD")
    private LocalDate assignmentStartDate;

    @NotNull(message ="AssignmentEndDate is mandatory!")
//    @Future(message="Please enter date from future in format YYYY-MM-DD")
    private LocalDate assignmentEndDate;
    
    @NotBlank(message ="RasStatus is mandatory!")
    private String rasStatus; 

    @NotBlank(message ="Fresher is mandatory!")
    private String fresher;
    
    @NotBlank(message ="OnOffshore is mandatory!")
    private String onOff;
    
    @NotNull(message ="ProjUId is mandatory!")
    private Integer projUid;

    @NotBlank(message ="Job is mandatory!")
    private String job;
    
    @NotBlank(message ="Skill is mandatory!")
    private String skill;

    @NotNull(message ="FTE is mandatory!")
    private Integer fte;
    
    @NotBlank(message ="CreatedBy is mandatory!")
    private String createdBy;

    @NotNull(message ="CreatedDate is mandatory!")
    private LocalDate createdDate;

    @NotBlank(message ="UpdatedBy is mandatory!")
    private String updatedBy;

    @NotNull(message ="UpdatedDate is mandatory!")
    private LocalDate updatedDate;
    
    @NotBlank(message ="SR is mandatory!")
    private String sr;

}
