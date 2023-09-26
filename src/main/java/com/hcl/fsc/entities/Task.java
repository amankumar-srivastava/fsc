package com.hcl.fsc.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long taskId = 1;

	@Column(name = "userId")
	private long userId;

	@Column(name = "task")
	private String task = "NEW_HIRE";

	@Column(name = "Status")
	private String Status ="Completed";

	@Column(name = "duedate")
	private LocalDate duedate = LocalDate.now();

	@Column(name = "approver")
	private String approver;

}