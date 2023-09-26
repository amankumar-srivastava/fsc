package com.hcl.fsc.response;

import lombok.Data;

@Data
public class AvailabelCandidateDetailsResponse {
	private Long sapId;
	private String employeeName;
	private String email;
	private String contactNo;
	private String location;
	private String band;
	private String skill;
	private String gradSpecialisation;
	private String collegeTier;
	private String status;
	private String actionId;
	private String taskOwner;

}
