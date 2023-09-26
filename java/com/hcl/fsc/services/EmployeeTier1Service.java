package com.hcl.fsc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.entities.EmployeeDetails;
import com.hcl.fsc.entities.EmployeeEducationalDetails;
import com.hcl.fsc.entities.EmployeeOnboardingDetails;
import com.hcl.fsc.entities.EmployeeRecruitmentDetails;
import com.hcl.fsc.entities.Task;
import com.hcl.fsc.excel.vo.Tier1;
import com.hcl.fsc.helpers.Constraints;
import com.hcl.fsc.helpers.EmployeeHelper;
import com.hcl.fsc.mastertables.Lob;
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
import com.hcl.fsc.repositories.OfferedBandRepository;
import com.hcl.fsc.repositories.OfferedDesignationRepository;
import com.hcl.fsc.repositories.OfferedSubBandRepository;
import com.hcl.fsc.repositories.RegionRepository;
import com.hcl.fsc.repositories.StateRepository;
import com.hcl.fsc.repositories.TaskRepository;
import com.hcl.fsc.repositories.UGOrPGRepository;
import com.hcl.fsc.repositories.UgDegreeRepository;
import com.hcl.fsc.response.ResponseList;

@Service
public class EmployeeTier1Service {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private EmployeeEducationalDetailsRepository employeeEducationDetailsRepository;

	@Autowired
	private EmployeeOnboardingDetailsRepository employeeOnboardingDetailsRepository;

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
	private OfferedDesignationRepository offeredDesignationRepository;

	@Autowired
	private OfferedBandRepository offeredBandRepository;

	@Autowired
	private OfferedSubBandRepository offeredSubBandRepository;

//	@Autowired
//	private LocationRepository locationRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	int rowNumber = 2;
	int duplicateCount = 0;

	public ResponseList employeeTier1ListSave(MultipartFile file) {

		List<String> errorsList = new ArrayList<>();
		ResponseList responseList = new ResponseList();
		List<String> duplicateEmailIdList = new ArrayList<>();

		try {

			List<Tier1> employeeTier1List = EmployeeHelper.convertExcelToListOfTier1(file.getInputStream());
			// System.out.println(employeeTier1List);
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

			List<String> ugDegreeList = ugOrPGRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("UGDegree", ugDegreeList);
			List<String> ugOrPgList = ugOrPGRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("UgOrPg", ugOrPgList);
			List<String> graduationSpecializationList = graduationSpecializationRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("GraduationSpecialization", graduationSpecializationList);

			List<String> collegeTieringList = collegeTieringRepository.findAll().stream()
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toList());
			masterTableDatabase.put("CollegeTiering", collegeTieringList);
//			List<String> locationList = locationRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
//					.collect(Collectors.toList());
//			masterTableDatabase.put("Location", locationList);
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
			List<String> lobList = lobRepository.findAll().stream().map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toList());
			masterTableDatabase.put("Lob", lobList);

//getting the data from Tier1 and mapping it to employee details entity after validating the data from related master tables
			rowNumber = 2;
			duplicateCount = 0;
			employeeTier1List.stream().forEach(e -> {

				boolean flag = true;
				if (e.getEmail() != null) {

					if (employeeDetailsRepository.findById(e.getEmail()).equals(Optional.empty())) {
						if (!Constraints.nameValidate(e.getName())) {
							errorsList.add("name is not Correct at row" + rowNumber + " in tier1 excel sheet");
							flag = true;
						}
						if (!Constraints.mobileNumberValidate(e.getContactNo())) {
							errorsList
									.add("Contact number is not Correct at row" + rowNumber + " in tier1 excel sheet");
							flag = true;
						}
						if (!Constraints.emailValidate(e.getEmail())) {
							errorsList.add("Email Id is not Correct at row" + rowNumber + " in tier1 excel sheet");
							flag = true;
						}
						if (e.getGender() != null) {
							boolean gender = masterTableDatabase.get("Gender").contains(e.getGender().toLowerCase());
							if (!gender) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in gender column of tier1 excel sheet");
								flag = false;

							}
						}
//							Region region = regionRepository.findByValue(e.getRegion());
//							if (region == null) {
						if (e.getRegion() != null) {
							boolean region = masterTableDatabase.get("Region").contains(e.getRegion().toLowerCase());
							if (!region) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in region column of tier1 excel sheet");
								flag = false;
							}
						}
//							State state = stateRepository.findByValue(e.getState());
//							if (state == null) {
						if (e.getState() != null) {
							boolean state = masterTableDatabase.get("State").contains(e.getState().toLowerCase());
							if (!state) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in state column of tier1 excel sheet");
								flag = false;
							}
						}
//							if (ugDegreeRepository.findByValue(e.getUgDegree()));
//							if(ugOrPg==null){
						if (e.getUgDegree() != null) {
							boolean ugDegree = masterTableDatabase.get("UGDegree")
									.contains(e.getUgDegree().toLowerCase());
							if (!ugDegree) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in ug-degree column of tier1 excel sheet");
								flag = false;
							}
						}
//							UGOrPG ugOrPg = ugOrPGRepository.findByValue(e.getUgOrPg());
//							if (ugOrPg == null) {
						if (e.getUgOrPg() != null) {
							boolean ugOrPg = masterTableDatabase.get("UgOrPg").contains(e.getUgOrPg().toLowerCase());
							if (!ugOrPg) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in ug-or-pg column of tier1 excel sheet");
								flag = false;
							}
						}

//							GraduationSpecialization graduationSpecialization = graduationSpecializationRepository.findByValue(e.getGraduationSpecialization());
//							if (graduationSpecialization == null) {
						if (e.getGraduationSpecialization() != null) {
							boolean graduationSpecialization = masterTableDatabase.get("GraduationSpecialization")
									.contains(e.getGraduationSpecialization().toLowerCase());
							if (!graduationSpecialization) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in graduation-specialization column of tier1 excel sheet");
								flag = false;
							}
						}
//							Location location = locationRepository.findByValue(e.getLocation());
//							if (location == null) {
//							if (e.getLocation() != null) {
//								boolean location = masterTableDatabase.get("Location").contains(e.getLocation().toLowerCase());
//								if (!location) {
//								errorsList.add("values are null or improper in row " + rowNumber
//										+ " in location column of non-tier1 excel sheet");
//								flag = false;
//							}
//							}

//							CollegeTiering collegeTiering = collegeTieringRepository.findByValue(e.getCollegeTiering());
//							if (collegeTiering == null) {
						if (e.getCollegeTiering() != null) {
							boolean collegeTiering = masterTableDatabase.get("CollegeTiering")
									.contains(e.getCollegeTiering().toLowerCase());
							if (!collegeTiering) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in college-tiering column of tier1 excel sheet");
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
										+ " in offered-designation column of tier1 excel sheet");
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
										+ " in offered-band column of tier1 excel sheet");
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
										+ " in offered-sub-band column of tier1 excel sheet");
								flag = false;
							}
						}
//							Lob lob = lobRepository.findByValue(e.getLob());
//							if (l1 == null) {
						if (e.getLob() != null) {
							boolean lob = masterTableDatabase.get("Lob").contains(e.getLob().toLowerCase());
							if (!lob) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in Lob column of tier1 excel sheet");
								flag = false;
							}
						}
//							L1 l1 = l1Repository.findByValue(e.getL1());
//							if (l1 == null) {
						if (e.getL1() != null) {
							boolean l1 = masterTableDatabase.get("L1").contains(e.getL1().toLowerCase());
							if (!l1) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L1 column of tier1 excel sheet");
								flag = false;
							}
						}
//							L2 l2 = l2Repository.findByValue(e.getL2());
//							if (l2 == null) {
						if (e.getL2() != null) {
							boolean l2 = masterTableDatabase.get("L2").contains(e.getL2().toLowerCase());
							if (!l2) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L2 column of tier1 excel sheet");
								flag = false;
							}
						}
//							L3 l3 = l3Repository.findByValue(e.getL3());
//							if (l3 == null) {
						if (e.getL3() != null) {
							boolean l3 = masterTableDatabase.get("L3").contains(e.getL3().toLowerCase());
							if (!l3) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L3 column of tier1 excel sheet");
								flag = false;
							}
						}
//							L4 l4 = l4Repository.findByValue(e.getL4());
//							if (l4 == null) {
						if (e.getL4() != null) {
							boolean l4 = masterTableDatabase.get("L4").contains(e.getL4().toLowerCase());
							if (!l4) {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L4 column of tier1 excel sheet");
								flag = false;
							}
						}
						if (flag == true) {
							EmployeeDetails employeeDetails = new EmployeeDetails();
							employeeDetails = modelMapper.map(e, EmployeeDetails.class);
							employeeDetails.setGender(genderRepository.findByValue(e.getGender()));
							employeeDetails.setRegion(regionRepository.findByValue(e.getRegion()));
							employeeDetails.setState(stateRepository.findByValue(e.getState()));
							employeeDetails.setSheetCode("Tier1");
							employeeDetailsList.add(employeeDetails);

							EmployeeEducationalDetails employeeEducationalDetails = new EmployeeEducationalDetails();
							employeeEducationalDetails = modelMapper.map(e, EmployeeEducationalDetails.class);
							employeeEducationalDetails.setUgDegree(ugDegreeRepository.findByValue(e.getUgDegree()));
							employeeEducationalDetails.setUgOrPg(ugOrPGRepository.findByValue(e.getUgOrPg()));
							employeeEducationalDetails.setGraduationSpecialization(
									graduationSpecializationRepository.findByValue(e.getGraduationSpecialization()));
							employeeEducationalDetails.setSheetCode("Tier1");
							employeeEducationalDetailsList.add(employeeEducationalDetails);

							EmployeeOnboardingDetails employeeOnboardingDetails = new EmployeeOnboardingDetails();
							employeeOnboardingDetails = modelMapper.map(e, EmployeeOnboardingDetails.class);
							employeeOnboardingDetails
									.setCollegeTiering(collegeTieringRepository.findByValue(e.getCollegeTiering()));

							employeeOnboardingDetails.setOfferedDesignation(
									offeredDesignationRepository.findByValue(e.getOfferedDesignation()));
							employeeOnboardingDetails
									.setOfferedBand(offeredBandRepository.findByValue(e.getOfferedBand()));
							employeeOnboardingDetails
									.setOfferedSubBand(offeredSubBandRepository.findByValue(e.getOfferedSubBand()));
							employeeOnboardingDetails.setSheetCode("Tier1");
							employeeOnboardingDetailsList.add(employeeOnboardingDetails);

							EmployeeRecruitmentDetails employeeRecruitmentDetails = new EmployeeRecruitmentDetails();
							employeeRecruitmentDetails = modelMapper.map(e, EmployeeRecruitmentDetails.class);
							employeeRecruitmentDetails.setL1(l1Repository.findByValue(e.getL1()));
							employeeRecruitmentDetails.setL2(l2Repository.findByValue(e.getL2()));
							employeeRecruitmentDetails.setL3(l3Repository.findByValue(e.getL3()));
							employeeRecruitmentDetails.setL4(l4Repository.findByValue(e.getL4()));
							employeeRecruitmentDetails.setSheetCode("Tier1");
							employeeRecruitmentDetailsList.add(employeeRecruitmentDetails);

							if (e.getSapId() != null) {
								Task task = new Task();
								if (e.getGraduationSpecialization() != null && e.getCollegeTiering() != null) {

									if (e.getCollegeTiering().toUpperCase().equals("Tier-1")) {
										if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
											task.setUserId(e.getSapId());
											taskRepository.save(task);
											employeeDetails.setCategory(categoryRepository.getById((long) 1));
										} else {
											task.setUserId(e.getSapId());
											employeeDetails.setCategory(categoryRepository.getById((long) 2));
										}
									} else {
										if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
											task.setUserId(e.getSapId());
											taskRepository.save(task);
											employeeDetails.setCategory(categoryRepository.getById((long) 3));

										} else {
											task.setUserId(e.getSapId());
											taskRepository.save(task);
											employeeDetails.setCategory(categoryRepository.getById((long) 4));
										}
									}
								} else {
									task.setUserId(e.getSapId());
									taskRepository.save(task);
									employeeDetails.setCategory(categoryRepository.getById((long) 5));
								}
							}

						}
					} else {
						errorsList.add("duplicate entry at row no " + rowNumber
								+ " in tier1 excel sheet this sap-id is already present in the database");
						duplicateEmailIdList.add(e.getEmail());
						duplicateCount++;
						flag = false;
					}
				} else {
					errorsList.add("sap-id is null or improper in row no " + rowNumber + " in the tier1 excel sheet");
				}
				rowNumber++;

			});

			// System.out.println(employeeDetailsList);
			employeeDetailsRepository.saveAll(employeeDetailsList);
			// System.out.println(employeeEducationalDetailsList);
			employeeEducationDetailsRepository.saveAll(employeeEducationalDetailsList);
			// System.out.println(employeeOnboardingDetailsList);
			employeeOnboardingDetailsRepository.saveAll(employeeOnboardingDetailsList);
			// System.out.println(employeeRecruitmentDetailsList);
			employeeRecruitmentDetailsRepository.saveAll(employeeRecruitmentDetailsList);

			responseList.setTotal_No_Records(employeeTier1List.size());
			responseList.setSucessful_Records(employeeDetailsList.size());
			responseList.setFailed_Records(employeeTier1List.size() - employeeDetailsList.size());
			responseList.setDuplicate_Records(duplicateCount);
			responseList.setDuplicate_Email_List(duplicateEmailIdList);
			Map<String, List<String>> failed_Records_List = new HashMap<>();
			failed_Records_List.put("Tier1", errorsList);
			responseList.setFailed_Records_List(failed_Records_List);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return responseList;
	}

}
