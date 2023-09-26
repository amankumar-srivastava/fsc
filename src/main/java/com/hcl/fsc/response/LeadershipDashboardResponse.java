package com.hcl.fsc.response;

import lombok.Data;

@Data
public class LeadershipDashboardResponse {

	private Long totalCandidatesDeployed;
	private Long totalCandidatesAvailable;
	private Long noOfProjectsAvailable;

}
