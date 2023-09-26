package com.hcl.fsc.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Project_assignment_history")
public class ProjectAssignmentHistory {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private int id;

	private Long empSAPID;

	@NotNull(message = "ProjUId is mandatory!")
	private Integer projUid;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate assignment_start_date;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate assignment_end_date;

	private String skill;

	@Column(name = "transaction_id")
	private Integer transactionId;

}