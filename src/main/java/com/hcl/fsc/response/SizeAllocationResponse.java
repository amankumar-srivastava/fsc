package com.hcl.fsc.response;

import com.hcl.fsc.mastertables.ProjectType;

import lombok.Data;

@Data
public class SizeAllocationResponse {

	private Integer uid;

	private Integer Strength;

	private String projectType;

	private Integer percentage;

	private Integer freshersAssigned;

	private Integer projectFresherRequirment;

	private Integer freshernotassigned;

}