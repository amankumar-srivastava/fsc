package com.hcl.fsc.response;

import lombok.Data;

@Data
public class ProjectDetails {

	private String projectName;

	private String projectCode;

	private Integer currentTeamStrength;

	private Integer totalCandidatesRequired;

	private Integer totalFreshersDeployed;

}
