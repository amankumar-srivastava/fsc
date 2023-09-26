package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_category_master")
public class ProjectCategory {

	@Id
	private Integer uid;

	private String projectCategory;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String createdBy;

	private String updatedBy;

}
