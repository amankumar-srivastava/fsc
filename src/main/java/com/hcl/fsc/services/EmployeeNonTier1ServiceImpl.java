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
import com.hcl.fsc.excel.vo.NonTier1;
import com.hcl.fsc.helpers.EmployeeHelper;
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
import com.hcl.fsc.repositories.LocationRepository;
import com.hcl.fsc.repositories.MasterTablePossibleValuesRepository;
import com.hcl.fsc.repositories.OfferedBandRepository;
import com.hcl.fsc.repositories.OfferedDesignationRepository;
import com.hcl.fsc.repositories.OfferedSubBandRepository;
import com.hcl.fsc.repositories.RegionRepository;
import com.hcl.fsc.repositories.StateRepository;
import com.hcl.fsc.repositories.TaskRepository;
import com.hcl.fsc.repositories.UGOrPGRepository;
import com.hcl.fsc.response.ResponseList;

@Service
public class EmployeeNonTier1ServiceImpl {

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

	@Autowired
	private MasterTablePossibleValuesRepository masterTablePossibleValuesRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	int rowNumber = 2;
	int duplicateRow = 0;

//	ResponseList responseList = new ResponseList();
//	List<String> duplicateSapIdList = new ArrayList<>();

	public ResponseList employeeNonTier1ListSave(MultipartFile file) {
		log.info("Validating and Saving data from Non-Tier1 Sheet");
		ResponseList responseList = new ResponseList();
		List<String> duplicateEmailIdList = new ArrayList<>();
		List<String> errorsList = new ArrayList<>();
		List<String> emailList = new ArrayList<>();
		List<String> duplicateEmailList = new ArrayList<>();
		List<String> d1 = new ArrayList<>();

		try {

			List<NonTier1> employeeNonTier1List = EmployeeHelper.convertExcelToListOfNonTier1(file.getInputStream());

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

			Set<String> ugOrPgSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("ugorpg")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());

			Set<String> gradSpecializationSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("graduationspecialization"))
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toSet());

			Set<String> collegeTieringSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("collegetiering"))
					.map(e -> e.getValue().toLowerCase()).collect(Collectors.toSet());

			Set<String> locationSet = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("location"))
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
			Set<String> l1Set = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("l1")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());
			Set<String> l2Set = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("l2")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());
			Set<String> l3Set = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("l3")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());
			Set<String> l4Set = masterTablePossibleValuesRepository.findAll().stream()
					.filter(e -> e.getMasterTable().toLowerCase().equals("l4")).map(e -> e.getValue().toLowerCase())
					.collect(Collectors.toSet());
			

//getting the data from NonTier1 and mapping it to employee details entity after validating the data from related master tables
			rowNumber = 2;
			duplicateRow = 0;
			employeeNonTier1List.stream().forEach(e -> {
				boolean flag = true;
				if (e.getEmail() != null) {
					if (!emailList.contains(e.getEmail().toLowerCase())) {
						if (employeeDetailsRepository.findById(e.getEmail()).equals(Optional.empty())) {
//						if (!Constraints.nameValidate(e.getName())) {
//							errorsList.add("name is not Correct at row" + rowNumber + " in non-tier1 excel sheet");
//							flag = true;
//						} 

//						if (!Constraints.mobileNumberValidate(e.getContactNo())) {
//							errorsList.add(
//									"Contact number is not Correct at row" + rowNumber + " in non-tier1 excel sheet");
//							flag = true;
//						}
//						if (!Constraints.emailValidate(e.getEmail())) {
//							errorsList.add("Email Id is not Correct at row" + rowNumber + "  in non-tier1 excel sheet");
//							flag = true;
//						}  

							if (e.getGender() != null) {
								boolean gender = genderSet.contains(e.getGender().toLowerCase());
								if (!gender) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in gender column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in gender column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getRegion() != null) {
								boolean region = regionSet.contains(e.getRegion().toLowerCase());
								if (!region) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in region column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in region column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getState() != null) {
								boolean state = stateSet.contains(e.getState().toLowerCase());
								if (!state) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in state column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in state column of non-tier1 excel sheet");
								flag = false;
							}
							if (e.getUgOrPg() != null) {
								boolean ugOrPg = ugOrPgSet.contains(e.getUgOrPg().toLowerCase());
								if (!ugOrPg) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in ug-or-pg column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in ug-or-pg column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getGraduationSpecialization() != null) {
								boolean graduationSpecialization = gradSpecializationSet
										.contains(e.getGraduationSpecialization().toLowerCase());
								if (!graduationSpecialization) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in graduation-specialization column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in graduation-specialization column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getLocation() != null) {
								boolean location = locationSet.contains(e.getLocation().toLowerCase());
								if (!location) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in location column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in location column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getCollegeTiering() != null) {
								boolean collegeTiering = collegeTieringSet
										.contains(e.getCollegeTiering().toLowerCase());
								if (!collegeTiering) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in college-tiering column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in college-tiering column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getOfferedDesignation() != null) {
								boolean offeredDesignation = offeredDesignationSet
										.contains(e.getOfferedDesignation().toLowerCase());
								if (!offeredDesignation) {
									errorsList.add("values are null or improper or improper in row " + rowNumber
											+ " in offered-designation column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper or improper in row " + rowNumber
										+ " in offered-designation column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getOfferedBand() != null) {
								boolean offeredBand = offeredBandSet.contains(e.getOfferedBand().toLowerCase());
								if (!offeredBand) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in offered-band column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-band column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getOfferedSubBand() != null) {
								boolean offeredSubBand = offeredSubBandSet
										.contains(e.getOfferedSubBand().toLowerCase());
								if (!offeredSubBand) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in offered-sub-band column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in offered-sub-band column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getL1() != null) {
								boolean l1 = l1Set.contains(e.getL1().toLowerCase());
								if (!l1) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in L1 column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L1 column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getL2() != null) {
								boolean l2 = l2Set.contains(e.getL2().toLowerCase());
								if (!l2) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in L2 column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L2 column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getL3() != null) {
								boolean l3 = l3Set.contains(e.getL3().toLowerCase());
								if (!l3) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in L3 column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L3 column of non-tier1 excel sheet");
								flag = false;
							}

							if (e.getL4() != null) {
								boolean l4 = l4Set.contains(e.getL4().toLowerCase());
								if (!l4) {
									errorsList.add("values are null or improper in row " + rowNumber
											+ " in L4 column of non-tier1 excel sheet");
									flag = false;
								}
							} else {
								errorsList.add("values are null or improper in row " + rowNumber
										+ " in L4 column of non-tier1 excel sheet");
								flag = false;
							}

							if (flag == true) {
								EmployeeDetails employeeDetails = new EmployeeDetails();
								employeeDetails = modelMapper.map(e, EmployeeDetails.class);
								employeeDetails.setGender(genderRepository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getGender(),"Gender").getKey()));
								employeeDetails.setRegion(regionRepository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getRegion(),"Region").getKey()));
								employeeDetails.setState(stateRepository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getState(),"State").getKey()));
								employeeDetails.setSheetCode("Non-Tier1");
								employeeDetailsList.add(employeeDetails);

								EmployeeEducationalDetails employeeEducationalDetails = new EmployeeEducationalDetails();
								employeeEducationalDetails = modelMapper.map(e, EmployeeEducationalDetails.class);
								employeeEducationalDetails.setUgOrPg(ugOrPGRepository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getUgOrPg(),"UgOrPg").getKey()));
								employeeEducationalDetails.setGraduationSpecialization(
										graduationSpecializationRepository.getByKey(masterTablePossibleValuesRepository
												.getByValueAndMasterTable(e.getGraduationSpecialization(),"GraduationSpecialization").getKey()));
								employeeEducationalDetails.setSheetCode("Non-Tier1");
								employeeEducationalDetailsList.add(employeeEducationalDetails);

								EmployeeOnboardingDetails employeeOnboardingDetails = new EmployeeOnboardingDetails();
								employeeOnboardingDetails = modelMapper.map(e, EmployeeOnboardingDetails.class);
								employeeOnboardingDetails.setCollegeTiering(collegeTieringRepository.getByKey(
										masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getCollegeTiering(),"CollegeTiering").getKey()));
								employeeOnboardingDetails.setLocation(locationRepository.getByKey(
										masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getLocation(),"Location").getKey()));
								employeeOnboardingDetails.setOfferedDesignation(
										offeredDesignationRepository.getByKey(masterTablePossibleValuesRepository
												.getByValueAndMasterTable(e.getOfferedDesignation(),"OfferedDesignation").getKey()));
								employeeOnboardingDetails.setOfferedBand(offeredBandRepository.getByKey(
										masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getOfferedBand(),"Band").getKey()));
								employeeOnboardingDetails.setOfferedSubBand(offeredSubBandRepository.getByKey(
										masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getOfferedSubBand(),"SubBand").getKey()));
								employeeOnboardingDetails.setSheetCode("Non-Tier1");
								employeeOnboardingDetailsList.add(employeeOnboardingDetails);
								EmployeeRecruitmentDetails employeeRecruitmentDetails = new EmployeeRecruitmentDetails();
								employeeRecruitmentDetails = modelMapper.map(e, EmployeeRecruitmentDetails.class);

								employeeRecruitmentDetails.setL1(l1Repository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getL1(),"L1").getKey()));
								employeeRecruitmentDetails.setL2(l2Repository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getL2(), "L2").getKey()));
								employeeRecruitmentDetails.setL3(l3Repository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getL3(),"L3").getKey()));
								employeeRecruitmentDetails.setL4(l4Repository
										.getByKey(masterTablePossibleValuesRepository.getByValueAndMasterTable(e.getL4(),"L4").getKey()));
								employeeRecruitmentDetails.setSheetCode("Non-Tier1");
								employeeRecruitmentDetailsList.add(employeeRecruitmentDetails);

								if (e.getSapId() != null) {
									if (e.getGraduationSpecialization() != null && e.getCollegeTiering() != null) {
										if (e.getCollegeTiering().toUpperCase().equals("Tier-1")) {
											if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
												employeeDetails.setCategory(categoryRepository.getById((long) 1));
											} else {
												employeeDetails.setCategory(categoryRepository.getById((long) 2));
											}
										} else {
											if (e.getGraduationSpecialization().toUpperCase().equals("CSE")) {
												employeeDetails.setCategory(categoryRepository.getById((long) 3));

											} else {
												employeeDetails.setCategory(categoryRepository.getById((long) 4));
											}
										}
									} else {
										employeeDetails.setCategory(categoryRepository.getById((long) 5));
									}
								}
							}
						} else {
							d1.add("duplicate entry at row no " + rowNumber
									+ " in non-tier1 excel sheet this email-id is already present in the database");
							duplicateEmailIdList.add(e.getEmail());
							duplicateRow++;
							flag = false;
						}
					} else {
						duplicateEmailList.add(e.getEmail());
						errorsList.add("email-id is repating in row no " + rowNumber + " in the non-tier1 excel sheet");
					}
				} else {
					errorsList.add(
							"email-id is null or improper in row no " + rowNumber + " in the non-tier1 excel sheet");
				}
				emailList.add(e.getEmail().toLowerCase());
				rowNumber++;
				System.out.println(rowNumber + "------>>>>> row done--------->>>>>>> " + e.getName());

			});

			employeeDetailsRepository.saveAll(employeeDetailsList);
			employeeEducationDetailsRepository.saveAll(employeeEducationalDetailsList);
			employeeOnboardingDetailsRepository.saveAll(employeeOnboardingDetailsList);
			employeeRecruitmentDetailsRepository.saveAll(employeeRecruitmentDetailsList);

			responseList.setTotal_No_Records(employeeNonTier1List.size());
			responseList.setSucessful_Records(employeeDetailsList.size());
			responseList.setFailed_Records(employeeNonTier1List.size() - duplicateRow);
			responseList.setDuplicate_Records(duplicateRow);
			responseList.setDuplicate_Email_List(duplicateEmailIdList);

			Map<String, List<String>> duplicate_Records_List = new HashMap<>();
			duplicate_Records_List.put("Non-Tier1", d1);
			responseList.setDuplicate_Records_List(duplicate_Records_List);

			Map<String, List<String>> failed_Records_List = new HashMap<>();
			failed_Records_List.put("Non-Tier1", errorsList);
			responseList.setFailed_Records_List(failed_Records_List);
			

		} catch (Exception e) {
			e.printStackTrace();

		}

		return responseList;
	}

}
