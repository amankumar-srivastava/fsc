package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "job_master")
public class Job {
	@Id
	private Integer uid;

	@Column(name = "job")
	private String job;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "update_by")
	private String updatedBy;
}
