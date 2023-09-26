package com.hcl.fsc.entities;

import com.hcl.fsc.mastertables.L1;
import com.hcl.fsc.mastertables.L2;
import com.hcl.fsc.mastertables.L3;
import com.hcl.fsc.mastertables.L4;
import com.hcl.fsc.mastertables.Lob;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmployeeRecruitmentDetails {
	@Id
	private String email;
	private Long sapId;
	private String projectSkills;
	// master table
	@OneToOne
	private Lob lob;
	private String team_Rdu;
	private String hrPanel;
	// master table
	@OneToOne
	private L1 l1;
	// master table
	@OneToOne
	private L2 l2;
	// master table
	@OneToOne
	private L3 l3;
	// master table
	@OneToOne
	private L4 l4;
	private String demandOwner;
	private String demandOwnerSAP;
	private String candidateMapping;
	private String srMapping;
	private String srStatus;
	private String remappedProfile;
	private String remappedLocation;
	private String srNumber;
	private String remarks;
	private String sheetCode;

}
