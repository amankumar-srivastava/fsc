package com.hcl.fsc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.entities.EmployeeDetails;
import com.hcl.fsc.entities.EmployeeEducationalDetails;
import com.hcl.fsc.entities.EmployeeOnboardingDetails;
import com.hcl.fsc.entities.EmployeeRecruitmentDetails;
import com.hcl.fsc.entities.Task;
import com.hcl.fsc.excel.vo.CDAC;
import com.hcl.fsc.helpers.Constraints;
import com.hcl.fsc.helpers.EmployeeHelper;
import com.hcl.fsc.mastertables.Gender;
import com.hcl.fsc.repositories.CategoryRepository;
import com.hcl.fsc.repositories.CollegeTieringRepository;
import com.hcl.fsc.repositories.EmployeeDetailsRepository;
import com.hcl.fsc.repositories.EmployeeEducationalDetailsRepository;
import com.hcl.fsc.repositories.EmployeeOnboardingDetailsRepository;
import com.hcl.fsc.repositories.EmployeeRecruitmentDetailsRepository;
import com.hcl.fsc.repositories.GenderRepository;
import com.hcl.fsc.repositories.GraduationSpecializationRepository;
import com.hcl.fsc.repositories.L1Repository;
import com.hcl.fsc.repositories.L2Repository;
import com.hcl.fsc.repositories.L3Repository;
import com.hcl.fsc.repositories.L4Repository;
import com.hcl.fsc.repositories.LobRepository;
import com.hcl.fsc.repositories.LocationRepository;
import com.hcl.fsc.repositories.MasterTablePossibleValuesRepository;
import com.hcl.fsc.repositories.OfferedBandRepository;
import com.hcl.fsc.repositories.OfferedDesignationRepository;
import com.hcl.fsc.repositories.OfferedSubBandRepository;
import com.hcl.fsc.repositories.OnboardingStatusRepository;
import com.hcl.fsc.repositories.RegionRepository;
import com.hcl.fsc.repositories.StateRepository;
import com.hcl.fsc.repositories.TaskRepository;
import com.hcl.fsc.repositories.UGOrPGRepository;
import com.hcl.fsc.repositories.UgDegreeRepository;
import com.hcl.fsc.response.ResponseList;

@Service
public class EmployeeCDACServiceImpl {
	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private EmployeeEducationalDetailsRepository employeeEducationDetailsRepository;

	@Autowired
	private EmployeeOnboardingDetailsRepository employeeOnboardingRepository;

	@Autowired
	private EmployeeRecruitmentDetailsRepository employeeRecruitmentDetailsRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GenderRepository genderRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private UgDegreeRepository ugDegreeRepository;

	@Autowired
	private L2Repository l2Repository;

	@Autowired
	private OfferedDesignationRepository offeredDesignationRepository;

	@Autowired
	private OfferedBandRepository offeredBandRepository;

	@Autowired
	private OfferedSubBandRepository offeredSubBandRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private MasterTablePossibleValuesRepository masterTablePossibleValuesRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	int rowNumber = 2;
	int duplicateRow = 0;

	public ResponseList employeeCDACListSave(MultipartFile file) {
		log.info("Validating and Saving data from CDAC Sheet");
		List<String> errorsList = new ArrayList<>();
		ResponseList responseList = new ResponseList();
		List<String> duplicateEmailIdList = new ArrayList<>();
		List<String> duplicateRecords = new ArrayList<>();
		try {
			List<CDAC> employeeCDACList = EmployeeHelper.convertExcelToListOfCDAC(file.getInputStream());

			List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
			List<EmployeeEducationalDetails> employeeEducationalDetailsList = new ArrayList<>();
			List<EmployeeOnboardingDetails> employeeOnboardingDetailsList = new ArrayList<>();
			List<EmployeeRecruitmentDetails> employeeRecruitmentDetailsList = new ArrayList<>();
			Set<String> genderSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("gender")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());

			Set<String> regionSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("region")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());

			Set<String> stateSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("state")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());

			Set<String> ugDegreeSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("graduationdegree"))
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toSet());

			Set<String> offeredDesignationSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("offereddesignation"))
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toSet());

			Set<String> offeredBandSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("band")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());
			Set<String> offeredSubBandSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("subband"))
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toSet());

			Set<String> l2Set = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("l2")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());

			rowNumber = 2;
			duplicateRow = 0;

			employeeCDACList.stream().forEach(e -> {
				boolean flag = true;
				if (e.getEmail() != null) {
					if (employeeDetailsRepository.findById(e.getEmail()).equals(Optional.empty())) {
//						if (!Constraints.nameValidate(e.getName())) {
//							errorsList.add("name is not Correct at " + rowNumber + " in CDAC excel sheet");
//							flag = true;
//						}
//						if (!Constraints.mobileNumberValidate(e.getContactNo())) {
//							errorsList.add("Contact number is not Correct at " + rowNumber + " in CDAC excel sheet");
//							flag = true;
//						}
//						if (!Constraints.emailValidate(e.getEmail())) {
//							errorsList.add("Email Id is not Correct at " + rowNumber + " in CDAC excel sheet");
//							flag = true;
//						}
						if (e.getGender() != null) {
							boolean gender = genderSet.contains(e.getGender().toLowerCase());
							if (!gender) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in gender column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in gender column of CDAC excel sheet");
							flag = false;
						}

						if (e.getRegion() != null) {
							boolean region = regionSet.contains(e.getRegion().toLowerCase());
							if (!region) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in region column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in region column of CDAC excel sheet");
							flag = false;
						}

						if (e.getState() != null) {
							boolean state = stateSet.contains(e.getState().toLowerCase());
							if (!state) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in state column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in state column of CDAC excel sheet");
							flag = false;
						}

						if (e.getUgDegree() != null) {
							System.out.println(ugDegreeSet.contains(e.getUgDegree().toLowerCase()));
							boolean ugDegree = ugDegreeSet.contains(e.getUgDegree().toLowerCase());
							if (!ugDegree) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in ug-degree column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in ug-degree column of CDAC excel sheet");
							flag = false;
						}

						if (e.getOfferedDesignation() != null) {
							boolean offeredDesignation = offeredDesignationSet
									.contains(e.getOfferedDesignation().toLowerCase());
							if (!offeredDesignation) {
								errorsList.add("values are null or improper or improper in row " + rowNumber
										+ " in offered-designation column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper or improper in row " + rowNumber
									+ " in offered-designation column of CDAC excel sheet");
							flag = false;
						}

						if (e.getOfferedBand() != null) {
							boolean offeredBand = offeredBandSet.contains(e.getOfferedBand().toLowerCase());
							if (!offeredBand) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-band column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in offered-band column of CDAC excel sheet");
							flag = false;
						}

						if (e.getOfferedSubBand() != null) {
							boolean offeredSubBand = offeredSubBandSet.contains(e.getOfferedSubBand().toLowerCase());
							if (!offeredSubBand) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-sub-band column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in offered-sub-band column of CDAC excel sheet");
							flag = false;
						}

						if (e.getL2() != null) {
							boolean l2 = l2Set.contains(e.getL2().toLowerCase());
							if (!l2) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L2 column of CDAC excel sheet");
								flag = false;
							}
						} else {
							errorsList.add("values are null or improper in row " + rowNumber
									+ " in L2 column of CDAC excel sheet");
							flag = false;
						}

						if (flag == true) {
							EmployeeDetails employeeDetails = new EmployeeDetails();
							employeeDetails = modelMapper.map(e, EmployeeDetails.class);
							employeeDetails.setGender(genderRepository.getByKey(masterTablePossibleValuesRepository
									.getByValueAndMasterTable(e.getGender(), "Gender").getKey()));
							employeeDetails.setRegion(regionRepository.getByKey(masterTablePossibleValuesRepository
									.getByValueAndMasterTable(e.getRegion(), "Region").getKey()));
//							System.out.println(stateRepository
//									.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getState(),"State").getKey()));
							employeeDetails.setState(stateRepository.getByKey(masterTablePossibleValuesRepository
									.getByValueAndMasterTable(e.getState(), "State").getKey()));
							employeeDetails.setSheetCode("CDAC");
							employeeDetailsList.add(employeeDetails);

							EmployeeEducationalDetails employeeEducationalDetails = new EmployeeEducationalDetails();
							employeeEducationalDetails = modelMapper.map(e, EmployeeEducationalDetails.class);
							System.out.println(e.getUgDegree() + "  -----degree");
							System.out.println(masterTablePossibleValuesRepository
									.getByValueAndMasterTable(e.getUgDegree(), "GraduationDegree").getKey());
							employeeEducationalDetails
									.setUgDegree(ugDegreeRepository.getByKey(masterTablePossibleValuesRepository
											.getByValueAndMasterTable(e.getUgDegree(), "GraduationDegree").getKey()));
							employeeEducationalDetails.setSheetCode("CDAC");
							employeeEducationalDetailsList.add(employeeEducationalDetails);

							EmployeeOnboardingDetails employeeOnboardingDetails = new EmployeeOnboardingDetails();
							employeeOnboardingDetails = modelMapper.map(e, EmployeeOnboardingDetails.class);
							employeeOnboardingDetails.setOfferedDesignation(
									offeredDesignationRepository.getByKey(masterTablePossibleValuesRepository
											.getByValueAndMasterTable(e.getOfferedDesignation(), "OfferedDesignation")
											.getKey()));
							employeeOnboardingDetails
									.setOfferedBand(offeredBandRepository.getByKey(masterTablePossibleValuesRepository
											.getByValueAndMasterTable(e.getOfferedBand(), "Band").getKey()));
							employeeOnboardingDetails.setOfferedSubBand(
									offeredSubBandRepository.getByKey(masterTablePossibleValuesRepository
											.getByValueAndMasterTable(e.getOfferedSubBand(), "SubBand").getKey()));
							employeeOnboardingDetails.setSheetCode("CDAC");
							employeeOnboardingDetailsList.add(employeeOnboardingDetails);

							EmployeeRecruitmentDetails employeeRecruitmentDetails = new EmployeeRecruitmentDetails();
							employeeRecruitmentDetails = modelMapper.map(e, EmployeeRecruitmentDetails.class);
							employeeRecruitmentDetails.setL2(l2Repository.getByKey(masterTablePossibleValuesRepository
									.getByValueAndMasterTable(e.getL2(), "L2").getKey()));
							employeeRecruitmentDetails.setSheetCode("CDAC");
							employeeRecruitmentDetailsList.add(employeeRecruitmentDetails);

//							if (e.getSapId() != null) {
//								Task task = new Task();
//								if (e.getGraduationSpecialization() != null && e.getCollegeTiering() != null) {
//
//									if (e.getCollegeTiering().toUpperCase().equals("Tier-1")) {
//										if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
//											task.setUserId(e.getSapId());
//											taskRepository.save(task);
//											employeeDetails.setCategory(categoryRepository.getById((long) 1));
//										} else {
//											task.setUserId(e.getSapId());
//											employeeDetails.setCategory(categoryRepository.getById((long) 2));
//										}
//									} else {
//										if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
//											task.setUserId(e.getSapId());
//											taskRepository.save(task);
//											employeeDetails.setCategory(categoryRepository.getById((long) 3));
//
//										} else {
//											task.setUserId(e.getSapId());
//											taskRepository.save(task);
//											employeeDetails.setCategory(categoryRepository.getById((long) 4));
//										}
//									}
//								} else {
//									task.setUserId(e.getSapId());
//									taskRepository.save(task);
//									employeeDetails.setCategory(categoryRepository.getById((long) 5));
//								}
//							}

						}
					} else {
						duplicateRecords.add("duplicate entry at row no " + rowNumber
								+ " in CDAC excel sheet this email-id is already present in the database");
						duplicateEmailIdList.add(e.getEmail());
						duplicateRow++;
						flag = false;
					}
				} else {
					errorsList.add("email-id is null or improper in row no " + rowNumber + " in the CDAC excel sheet");
				}
				rowNumber++;
				System.out.println(rowNumber + "row done--");
			});

			// System.out.println(employeeDetailsList);
			employeeDetailsRepository.saveAll(employeeDetailsList);

			// System.out.println(employeeEducationalDetailsList);
			employeeEducationDetailsRepository.saveAll(employeeEducationalDetailsList);

			// System.out.println(employeeRecruitmentDetailsList);
			employeeRecruitmentDetailsRepository.saveAll(employeeRecruitmentDetailsList);

			// System.out.println(employeeOnboardingDetailsList);
			employeeOnboardingRepository.saveAll(employeeOnboardingDetailsList);

			responseList.setTotal_No_Records(employeeCDACList.size());
			responseList.setSucessful_Records(employeeDetailsList.size());
			responseList.setFailed_Records(employeeCDACList.size() - duplicateRow);
			responseList.setDuplicate_Records(duplicateRow);
			responseList.setDuplicate_Email_List(duplicateEmailIdList);

			Map<String, List<String>> duplicate_Records_List = new HashMap<>();
			duplicate_Records_List.put("CDAC", duplicateRecords);
			responseList.setDuplicate_Records_List(duplicate_Records_List);

			Map<String, List<String>> failed_Records_List = new HashMap<>();
			failed_Records_List.put("CDAC", errorsList);
			responseList.setFailed_Records_List(failed_Records_List);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
}
