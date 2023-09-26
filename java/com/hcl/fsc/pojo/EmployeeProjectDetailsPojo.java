package com.hcl.fsc.pojo;

import java.time.LocalDate;
import java.util.List;

import com.hcl.fsc.mastertables.ProjectCode;

import lombok.Data;

@Data
public class EmployeeProjectDetailsPojo {


	List<Long> empSAPID;

    private Integer reportingMgrSAPID;

    private String location;

    private String country;

    private Integer hrL4Id;

    private String lastProjectName;

    private String customerName;

    private LocalDate assignmentStartDate;

    private LocalDate assignmentEndDate;
    
    private String rasStatus; 

    private String fresher;
    
    private String onOff;
    
    private Integer projUid;
    
    private String job;
    
    private String skill;

    private Integer fte;
    
    private String createdBy;

    private LocalDate createdDate;

    private String updatedBy;

    private LocalDate updatedDate;
    
    private String sr;
	
}