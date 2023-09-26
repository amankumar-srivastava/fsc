package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rm_master")
public class Rm {

	@Id
	private Integer uid;
	
	@Column(name="sap_id")
	private String sapId;
	
	@Column(name="rm_name")
	private String rmName;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="updated_date")
	private LocalDateTime updatedDate;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="update_by")
	private String updatedBy;

}
