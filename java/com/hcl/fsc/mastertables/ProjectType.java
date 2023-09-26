package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_type_master")
public class ProjectType {
	@Id
	private Integer uid;

	@Column(name = "project_type")
	private String projectType;

	@Column(name = "percentage")
	private Integer percentage;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="update_by")
	private String updatedBy;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="updated_date")
	private LocalDateTime updatedDate;
}
