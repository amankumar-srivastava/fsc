package com.hcl.fsc.response;

import java.util.List;

import lombok.Data;

@Data
public class ProjectDetailsResponse {

	private List<Long> assignedSapId;

	private List<Long> unassignedSapId;

	private String transactionId;

	private List<Long> existingAssignedSapId;

	private List<Long> duplicateSapId;

	private List<Long> nonExistingSapId;

}
