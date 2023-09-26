package com.hcl.fsc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	private UGOrPGRepository ugOrPGRepository;

	@Autowired
	private UgDegreeRepository ugDegreeRepository;

	@Autowired
	private GraduationSpecializationRepository graduationSpecializationRepository;

	@Autowired
	private CollegeTieringRepository collegeTieringRepository;

	@Autowired
	private L1Repository l1Repository;

	@Autowired
	private L2Repository l2Repository;

	@Autowired
	private L3Repository l3Repository;

	@Autowired
	private L4Repository l4Repository;

	@Autowired
	private LobRepository lobRepository;

	@Autowired
	private OnboardingStatusRepository onboardingStatusRepository;

	@Autowired
	private OfferedDesignationRepository offeredDesignationRepository;

	@Autowired
	private OfferedBandRepository offeredBandRepository;

	@Autowired
	private OfferedSubBandRepository offeredSubBandRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	int rowNumber = 2;
	int duplicateRow = 0;

	public ResponseList employeeCDACListSave(MultipartFile file) {
        log.info("Validating and Saving data from CDAC Sheet");
        List<String> errorsList = new ArrayList<>();
		ResponseList responseList = new ResponseList();
		List<String> duplicateEmailIdList = new ArrayList<>();
		try {
			List<CDAC> employeeCDACList = EmployeeHelper.convertExcelToListOfCDAC(file.getInputStream());

			List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
			List<EmployeeEducationalDetails> employeeEducationalDetailsList = new ArrayList<>();
			List<EmployeeOnboardingDetails> employeeOnboardingDetailsList = new ArrayList<>();
			List<EmployeeRecruitmentDetails> employeeRecruitmentDetailsList = new ArrayList<>();

			Map<String, List<String>> masterTableDatabase = new HashMap<>();
			List<String> genderList = genderRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("Gender", genderList);
			List<String> regionList = regionRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("Region", regionList);
			List<String> stateList = stateRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("State", stateList);

			List<String> ugOrPgList = ugOrPGRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("UgOrPg", ugOrPgList);
			List<String> graduationSpecializationList = graduationSpecializationRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("GraduationSpecialization", graduationSpecializationList);

			List<String> collegeTieringList = collegeTieringRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("CollegeTiering", collegeTieringList);
			List<String> locationList = locationRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("Location", locationList);
			List<String> offeredDesignationList = offeredDesignationRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("OfferedDesignation", offeredDesignationList);
			List<String> offeredBandList = offeredBandRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("OfferedBand", offeredBandList);
			List<String> offeredSubBandList = offeredSubBandRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("OfferedSubBand", offeredSubBandList);

			List<String> l1List = l1Repository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("L1", l1List);
			List<String> l2List = l2Repository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("L2", l2List);
			List<String> l3List = l3Repository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("L3", l3List);
			List<String> l4List = l4Repository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("L4", l4List);
			rowNumber = 2;
			duplicateRow = 0;

			employeeCDACList.stream().forEach(e -> {
				boolean flag = true;
				if (e.getEmail() != null) {
//					System.out.println(
//							e.getSapId() + " checking sap-id " + employeeDetailsRepository.findById(e.getSapId()));
					if (employeeDetailsRepository.findById(e.getEmail()).equals(Optional.empty())) {
						if (!Constraints.nameValidate(e.getName())) {
							errorsList.add("name is not Correct at " + rowNumber + " in CDAC excel sheet");
							flag = true;
						}
						if (!Constraints.mobileNumberValidate(e.getContactNo())) {
							errorsList.add("Contact number is not Correct at " + rowNumber + " in CDAC excel sheet");
							flag = true;
						}
						if (!Constraints.emailValidate(e.getEmail())) {
							errorsList.add("Email Id is not Correct at " + rowNumber + " in CDAC excel sheet");
							flag = true;
						}
//						 Gender gender=genderRepository.findByValue(e.getGender());
//							if (gender==null) {
						if (e.getGender() != null) {
							boolean gender = masterTableDatabase.get("Gender").contains(e.getGender().toLowerCase());
							if (!gender) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in gender column of CDAC excel sheet");
								flag = false;

							}
						}
//							Region region = regionRepository.findByValue(e.getRegion());
//							if (region == null) {
						if (e.getRegion() != null) {
							boolean region = masterTableDatabase.get("Region").contains(e.getRegion().toLowerCase());
							if (!region) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in region column of CDAC excel sheet");
								flag = false;
							}
						}
//							State state = stateRepository.findByValue(e.getState());
//							if (state == null) {
						if (e.getState() != null) {
							boolean state = masterTableDatabase.get("State").contains(e.getState().toLowerCase());
							if (!state) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in state column of CDAC excel sheet");
								flag = false;
							}
						}

//							if (ugDegreeRepository.findByValue(e.getUgDegree()) == null) {
						if (e.getUgDegree() != null) {
							boolean ugDegree = masterTableDatabase.get("State").contains(e.getState().toLowerCase());
							if (!ugDegree) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in ug-degree column of CDAC excel sheet");
								flag = false;
							}
						}

//							OfferedDesignation offeredDesignation = offeredDesignationRepository
//									.findByValue(e.getOfferedDesignation());
//							if (offeredDesignation == null) {
						if (e.getOfferedDesignation() != null) {
							boolean offeredDesignation = masterTableDatabase.get("OfferedDesignation")
									.contains(e.getOfferedDesignation().toLowerCase());
							if (!offeredDesignation) {
								errorsList.add("values are null or improper or improper in row " + rowNumber
										+ " in offered-designation column of CDAC excel sheet");
								flag = false;
							}
						}
//							OfferedBand offeredBand = offeredBandRepository.findByValue(e.getOfferedBand());
//							if (offeredBand == null) {
						if (e.getOfferedBand() != null) {
							boolean offeredBand = masterTableDatabase.get("OfferedBand")
									.contains(e.getOfferedBand().toLowerCase());
							if (!offeredBand) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-band column of CDAC excel sheet");
								flag = false;
							}
						}
//							OfferedSubBand offeredSubBand = offeredSubBandRepository.findByValue(e.getOfferedSubBand());
//							if (offeredSubBand == null) {
						if (e.getOfferedSubBand() != null) {
							boolean offeredSubBand = masterTableDatabase.get("OfferedSubBand")
									.contains(e.getOfferedSubBand().toLowerCase());
							if (!offeredSubBand) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-sub-band column of CDAC excel sheet");
								flag = false;
							}
						}

//							L2 l2 = l2Repository.findByValue(e.getL2());
//							if (l2 == null) {
						if (e.getL2() != null) {
							boolean l2 = masterTableDatabase.get("L2").contains(e.getL2().toLowerCase());
							if (!l2) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L2 column of CDAC excel sheet");
								flag = false;
							}
						}

						if (flag == true) {
							EmployeeDetails employeeDetails = new EmployeeDetails();
							employeeDetails = modelMapper.map(e, EmployeeDetails.class);
							employeeDetails.setGender(genderRepository.findByValue(e.getGender().toLowerCase()));
							employeeDetails.setRegion(regionRepository.findByValue(e.getRegion().toLowerCase()));
							employeeDetails.setState(stateRepository.findByValue(e.getState().toLowerCase()));
							employeeDetails.setSheetCode("CDAC");
							employeeDetailsList.add(employeeDetails);

							EmployeeEducationalDetails employeeEducationalDetails = new EmployeeEducationalDetails();
							employeeEducationalDetails = modelMapper.map(e, EmployeeEducationalDetails.class);
							// employeeEducationalDetails.setUgOrPG(ugOrPGRepository.findByValue(e.getUGOrPG()));
							// employeeEducationalDetails.setGraduationSpecialization(graduationSpecializationRepository.findByValue(e.getGraduationSpecialization()));
							employeeEducationalDetails.setUgDegree(ugDegreeRepository.findByValue(e.getUgDegree()));
							employeeEducationalDetails.setSheetCode("CDAC");
							employeeEducationalDetailsList.add(employeeEducationalDetails);

							EmployeeOnboardingDetails employeeOnboardingDetails = new EmployeeOnboardingDetails();
							employeeOnboardingDetails = modelMapper.map(e, EmployeeOnboardingDetails.class);
//		    		employeeOnboardingDetails.setCollegeTiering(collegeTieringRepository.findByValue(e.getCollegeTiering()));
//		    		employeeOnboardingDetails.setLocation(locationRepository.findByValue(e.getLocation()));
							employeeOnboardingDetails.setOnboardingStatus(
									onboardingStatusRepository.findByValue(e.getOnboardingStatus()));
							employeeOnboardingDetails.setOfferedDesignation(
									offeredDesignationRepository.findByValue(e.getOfferedDesignation()));
							employeeOnboardingDetails
									.setOfferedBand(offeredBandRepository.findByValue(e.getOfferedBand()));
							employeeOnboardingDetails
									.setOfferedSubBand(offeredSubBandRepository.findByValue(e.getOfferedSubBand()));
							employeeOnboardingDetails.setSheetCode("CDAC");
							employeeOnboardingDetailsList.add(employeeOnboardingDetails);

							EmployeeRecruitmentDetails employeeRecruitmentDetails = new EmployeeRecruitmentDetails();
							employeeRecruitmentDetails = modelMapper.map(e, EmployeeRecruitmentDetails.class);
							employeeRecruitmentDetails.setLob(lobRepository.findByValue(e.getLob()));
							employeeRecruitmentDetails.setL2(l2Repository.findByValue(e.getL2()));
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
						errorsList.add("duplicate entry at row no " + rowNumber
								+ " in CDAC excel sheet this email-id is already present in the database");
						duplicateEmailIdList.add(e.getEmail());
						duplicateRow++;
						flag = false;
					}
				} else {
					errorsList.add("email-id is null or improper in row no " + rowNumber + " in the CDAC excel sheet");
				}
				rowNumber++;
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
			responseList.setFailed_Records(employeeCDACList.size() - employeeDetailsList.size());
			responseList.setDuplicate_Records(duplicateRow);
			responseList.setDuplicate_Email_List(duplicateEmailIdList);
			Map<String, List<String>> failed_Records_List = new HashMap<>();
			failed_Records_List.put("CDAC", errorsList);
			responseList.setFailed_Records_List(failed_Records_List);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
}
