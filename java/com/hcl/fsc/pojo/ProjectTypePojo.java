package com.hcl.fsc.pojo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProjectTypePojo {

	private Integer uid;

	private String projectType;

	private Integer percentage;

	private String createdBy;

	private String updatedBy;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;
}
