package com.hcl.fsc.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectCodePojo {

	private Integer uid;

	private String projectCode;

	private String projectName;

	private Integer projectType;

	private Integer project_strength;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private LocalDate endDate;

	private String createdBy;

	private String updatedBy;
	
    private String bussinessArea;
    
    private Integer customerName;
    
}
