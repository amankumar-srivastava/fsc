package com.hcl.fsc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.entities.EmployeeDetails;
import com.hcl.fsc.entities.EmployeeEducationalDetails;
import com.hcl.fsc.entities.EmployeeOnboardingDetails;
import com.hcl.fsc.entities.EmployeeRecruitmentDetails;
import com.hcl.fsc.repositories.EmployeeDetailsRepository;
import com.hcl.fsc.repositories.EmployeeEducationalDetailsRepository;
import com.hcl.fsc.repositories.EmployeeOnboardingDetailsRepository;
import com.hcl.fsc.repositories.EmployeeRecruitmentDetailsRepository;
import com.hcl.fsc.response.AvailabelCandidateDetailsResponse;
import com.hcl.fsc.response.LeadershipDashboardResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private EmployeeEducationalDetailsRepository employeeEducationalDetailsRepository;

	@Autowired
	private EmployeeRecruitmentDetailsRepository employeeRecruitmentDetailsRepository;

	@Autowired
	private EmployeeOnboardingDetailsRepository employeeOnboardingDetailsRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	// Get_Mapping
	public List getEmployeesDetails(String details) {
		details = details.toLowerCase();
		log.info("Fetching details of table: " + details);
		if (details.equals("employeeDetails".toLowerCase()))
			return this.employeeDetailsRepository.findAll();
		else if (details.equals("employeeEducationalDetails".toLowerCase()))
			return this.employeeEducationalDetailsRepository.findAll();
		else if (details.equals("employeeRecruitmentDetails".toLowerCase()))
			return this.employeeRecruitmentDetailsRepository.findAll();
		else if (details.equals("employeeOnboardingDetails".toLowerCase()))
			return this.employeeOnboardingDetailsRepository.findAll();
		else
			return new ArrayList<>();
	}

	// Delete_Mapping
	public String deleteEmployeesDetails(String email) {
		log.info("Deleting employee all details for email: " + email);
		int res = 0;
		if (!employeeDetailsRepository.findById(email).equals(Optional.empty())) {
			EmployeeDetails employeeDetails = employeeDetailsRepository.getById(email);
			EmployeeEducationalDetails employeeEduactioanlDetails = employeeEducationalDetailsRepository.getById(email);
			EmployeeOnboardingDetails employeeOnboardingDetails = employeeOnboardingDetailsRepository.getById(email);
			EmployeeRecruitmentDetails employeeRecruitmentDetails = employeeRecruitmentDetailsRepository.getById(email);

			employeeDetailsRepository.delete(employeeDetails);
			employeeEducationalDetailsRepository.delete(employeeEduactioanlDetails);
			employeeOnboardingDetailsRepository.delete(employeeOnboardingDetails);
			employeeRecruitmentDetailsRepository.delete(employeeRecruitmentDetails);
			res = 1;
		}
		if (res == 1) {
			log.info("Employee details deleted sucessfully for email: " + email);
			return "Employee Details deleted Sucessfully";
		} else {
			log.info("not able to delete for email " + email + " incorrect email id");
			return "Incorrect Email-id ";
		}

	}

	public List<AvailabelCandidateDetailsResponse> getAvailableCandidatesDetails() {

		List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
		List<EmployeeEducationalDetails> employeeEducationalDetailsList = employeeEducationalDetailsRepository
				.findAll();
		List<EmployeeOnboardingDetails> employeeOnbaordingDetailsList = employeeOnboardingDetailsRepository.findAll();
		List<EmployeeRecruitmentDetails> employeeRecruitmentDetailsList = employeeRecruitmentDetailsRepository
				.findAll();
		List<AvailabelCandidateDetailsResponse> availabelCandidateDetailsResponselist = new ArrayList();
		AvailabelCandidateDetailsResponse availabelCandidateDetailsResponse = new AvailabelCandidateDetailsResponse();

		int i = 0;
		while (i < employeeDetailsList.size()) {
			if (employeeDetailsList.get(i).getSapId() != null && employeeDetailsList.get(i).getSapId() != 0
					&& employeeOnbaordingDetailsList.get(i).getCollegeTiering() != null
					&& employeeOnbaordingDetailsList.get(i).getOfferedBand() != null
					&& employeeEducationalDetailsList.get(i).getGraduationSpecialization() != null
					&& employeeOnbaordingDetailsList.get(i).getOfferedBand() != null) {
				availabelCandidateDetailsResponse.setEmail(employeeDetailsList.get(i).getEmail());
				availabelCandidateDetailsResponse.setEmployeeName(employeeDetailsList.get(i).getName());
				availabelCandidateDetailsResponse.setSapId(employeeDetailsList.get(i).getSapId());
				availabelCandidateDetailsResponse.setContactNo(employeeDetailsList.get(i).getContactNo());
				if (employeeDetailsList.get(i).getProjAssignedStatus()) {
					availabelCandidateDetailsResponse.setStatus("SCHEDULED");
				} else {
					availabelCandidateDetailsResponse.setStatus("AVAILABLE");
				}
				availabelCandidateDetailsResponse
						.setCollegeTier(employeeOnbaordingDetailsList.get(i).getCollegeTiering().getValue());
				availabelCandidateDetailsResponse.setGradSpecialisation(
						employeeEducationalDetailsList.get(i).getGraduationSpecialization().getValue());
				availabelCandidateDetailsResponse.setSkill(employeeRecruitmentDetailsList.get(i).getProjectSkills());
				availabelCandidateDetailsResponse
						.setLocation(employeeOnbaordingDetailsList.get(i).getLocation().getValue());
				availabelCandidateDetailsResponse
						.setBand(employeeOnbaordingDetailsList.get(i).getOfferedBand().getValue());
//			availabelCandidateDetailsResponse.setActionId("12345678");
//			availabelCandidateDetailsResponse.setTaskOwner("UNKNOWN");

				availabelCandidateDetailsResponselist.add(availabelCandidateDetailsResponse);
				availabelCandidateDetailsResponse = new AvailabelCandidateDetailsResponse();
			}
			i++;
		}
		return availabelCandidateDetailsResponselist;
	}

	public LeadershipDashboardResponse fetchLeadershipDashboardDetails() {
		LeadershipDashboardResponse leadershipDashboardResponse = new LeadershipDashboardResponse();
		leadershipDashboardResponse.setTotalCandidatesDeployed((long) employeeDetailsRepository.findAll().stream()
				.filter(e -> e.getSapId() != null && e.getProjAssignedStatus()).toArray().length);
		leadershipDashboardResponse.setTotalCandidatesAvailable((long) employeeDetailsRepository.findAll().stream()
				.filter(e -> e.getSapId() != null && !e.getProjAssignedStatus()).toArray().length);
		leadershipDashboardResponse.setNoOfProjectsAvailable((long) 100);
		return leadershipDashboardResponse;
	}

}
