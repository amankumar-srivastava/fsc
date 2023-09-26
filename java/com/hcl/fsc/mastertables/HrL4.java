package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "hr_l4_master")
@Data
public class HrL4 {
	@Id
	private Integer uid;

	@Column(name = "hr_l4_code")
	private String hrL4Code;

	@Column(name = "hr_l4_name")
	private String hrL4Name;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="updated_date")
	private LocalDateTime updatedDate;
	
	@Column(name="created_by")
	private String createdBy;

	@Column(name="update_by")
	private String updatedBy;

}
