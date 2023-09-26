package com.hcl.fsc.mastertables;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="customer_name_master")
public class CustomerName {
	
	@Id
	private Integer uid;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Column(name="updated_date")
	private LocalDateTime updatedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="update_by")
	private String updatedBy;
}
