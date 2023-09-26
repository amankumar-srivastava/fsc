package com.hcl.fsc.mastertables;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_code_master")
public class ProjectCode {

	@Id
	private Integer uid;

	private String projectCode;

	private String projectName;

	@ManyToOne
	@JoinColumn(name = "project_type_uid")
	private ProjectType projectType;

	@Column(name="project_strength")
	private Integer project_strength;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="update_by")
	private String updatedBy;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="updated_date")
	private LocalDateTime updatedDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="bussiness_area")
	private String bussinessArea;

}
