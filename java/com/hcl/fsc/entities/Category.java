package com.hcl.fsc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_master")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

	@Id 
	@Column(name = "Id")
	private Long userId;

	@Column(name = "name")
	private String category;

}