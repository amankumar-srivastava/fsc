package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "job_master")
public class JobMaster {

	@Id
	private Integer uid;

	private String job;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String createdBy;

	private String updatedBy;

}
