package com.hcl.fsc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.Gender;
import com.hcl.fsc.mastertables.GraduationSpecialization;
import com.hcl.fsc.mastertables.L1;
import com.hcl.fsc.mastertables.L2;
import com.hcl.fsc.mastertables.L3;
import com.hcl.fsc.mastertables.L4;
import com.hcl.fsc.mastertables.Lob;
import com.hcl.fsc.mastertables.Location;
import com.hcl.fsc.mastertables.MasterTablePossibleValues;
import com.hcl.fsc.mastertables.MasterTables;
import com.hcl.fsc.mastertables.OfferedBand;
import com.hcl.fsc.mastertables.OfferedDesignation;
import com.hcl.fsc.mastertables.OfferedSubBand;
import com.hcl.fsc.mastertables.OnboardingStatus;
import com.hcl.fsc.mastertables.Region;
import com.hcl.fsc.mastertables.State;
import com.hcl.fsc.mastertables.UGOrPG;
import com.hcl.fsc.mastertables.UgDegree;
import com.hcl.fsc.repositories.CollegeTieringRepository;
import com.hcl.fsc.repositories.EmployeeDetailsRepository;
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
import com.hcl.fsc.repositories.UGOrPGRepository;
import com.hcl.fsc.repositories.UgDegreeRepository;

@Service
public class MasterTableServiceImpl {

	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private GenderRepository genderRepository;

	@Autowired
	private LobRepository lobRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private CollegeTieringRepository collegeTieringRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private L1Repository l1Repository;

	@Autowired
	private L2Repository l2Repository;

	@Autowired
	private L3Repository l3Repository;

	@Autowired
	private L4Repository l4Repository;

	@Autowired
	private UgDegreeRepository ugDegreeRepository;

	@Autowired
	private UGOrPGRepository ugOrPgRepository;

	@Autowired
	private GraduationSpecializationRepository ugSpecializationRepository;

	@Autowired
	private OnboardingStatusRepository onboardingStatusRepository;

	@Autowired
	private OfferedBandRepository offeredBandRepository;

	@Autowired
	private OfferedSubBandRepository offeredSubBandRepository;

	@Autowired
	private OfferedDesignationRepository offeredDesignationRepository;
	
	@Autowired
	private MasterTablePossibleValuesRepository masterTablePossibleValuesRepository;

	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);

	public List getRecord(String mastertable) {
		if(repo(mastertable)!=null)
		return repo(mastertable).findAll();
		else
			return null;

	}

	public JpaRepository repo(String mastertable) {

		mastertable = mastertable.toLowerCase();

		if (mastertable.equals("gender")) {
			return this.genderRepository;
		} else if (mastertable.equals("lob")) {

			return this.lobRepository;
		} else if (mastertable.equals("location")) {
			return this.locationRepository;
		} else if (mastertable.equals("region")) {
			return this.regionRepository;
		} else if (mastertable.equals("collegetiering")) {
			return this.collegeTieringRepository;
		} else if (mastertable.equals("state")) {
			return this.stateRepository;
		} else if (mastertable.equals("l1")) {
			return this.l1Repository;
		}

		else if (mastertable.equals("l2")) {
			return this.l2Repository;
		}

		else if (mastertable.equals("l3")) {
			return this.l3Repository;
		}

		else if (mastertable.equals("l4")) {
			return this.l4Repository;
		}

		else if (mastertable.equals("ugdegree")) {
			return this.ugDegreeRepository;
		}

		else if (mastertable.equals("ugpg")) {
			return this.ugOrPgRepository;

		}

		else if (mastertable.equals("specialization")) {
			return this.ugSpecializationRepository;
		}

		else if (mastertable.equals("offeredband")) {
			return this.offeredBandRepository;
		} else if (mastertable.equals("offeredsubband")) {
			return this.offeredSubBandRepository;
		}

		else if (mastertable.equals("offereddesignation")) {
			return this.offeredDesignationRepository;
		} else if (mastertable.equals("onboardingstatus")) {
			return this.onboardingStatusRepository;
		}

		return null;

	}

	public Optional getRecordbyKey(String mastertable, String key) {
		 System.out.println(repo(mastertable.toLowerCase())==null);
		if(repo(mastertable.toLowerCase())!=null)
		return repo(mastertable.toLowerCase()).findById(key.toUpperCase());
		else
			return null;
	}

	public int createData(MasterTables master, String str) {
		int res = 0;
		str = str.toLowerCase();
				if (master.getKey() != null && master.getKey() != "" &&  master.getValue() != null && master.getValue() != "") {
			if (str.equals("gender")) {
				if(genderRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.genderRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.genderRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					genderRepository.save(new Gender(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			}

			else if (str.equals("lob")) {
				if(lobRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.lobRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.lobRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					lobRepository.save(new Lob(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			}

			else if (str.equals("location")) {
				if(locationRepository.findById(master.getKey()).equals(Optional.empty())) {
				Integer uid=0;
				if (this.locationRepository.findAll().size() == 0) {
					uid=1;
				} else {
					uid = this.locationRepository.findTopByOrderByUidDesc().getUid();
					uid=uid++;
				}
				locationRepository.save(new Location(++uid, master.getKey(), master.getValue()));
				}
				else
					return -1;

				
			} else if (str.equals("region")) {
				if(regionRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.regionRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.regionRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					regionRepository.save(new Region(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("collegetiering")) {
				if(collegeTieringRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.collegeTieringRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.collegeTieringRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					collegeTieringRepository.save(new CollegeTiering(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("state")) {
				if(stateRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.stateRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.stateRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					stateRepository.save(new State(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;
			} else if (str.equals("l1")) {
				if(l1Repository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.l1Repository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.l1Repository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					l1Repository.save(new L1(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("l2")) {

				if(l2Repository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.l2Repository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.l2Repository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					l2Repository.save(new L2(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("l3")) {

				if(l3Repository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.l3Repository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.l3Repository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					l3Repository.save(new L3(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("l4")) {

				if(l4Repository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.l4Repository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.l4Repository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					l4Repository.save(new L4(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("ugdegree")) {

				if(ugDegreeRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.ugDegreeRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.ugDegreeRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					ugDegreeRepository.save(new UgDegree(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("ugpg")) {

				if(ugOrPgRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.ugOrPgRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.ugOrPgRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					ugOrPgRepository.save(new UGOrPG(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("specialization")) {

				if(ugSpecializationRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.ugSpecializationRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.ugSpecializationRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					ugSpecializationRepository.save(new GraduationSpecialization(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("offeredband")) {

				if(offeredBandRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.offeredBandRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.offeredBandRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					offeredBandRepository.save(new OfferedBand(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("offeredsubband")) {

				if(offeredSubBandRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.offeredSubBandRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.offeredSubBandRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					offeredSubBandRepository.save(new OfferedSubBand(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("offereddesignation")) {

				if(offeredDesignationRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.offeredDesignationRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.offeredDesignationRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					offeredDesignationRepository.save(new OfferedDesignation(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			} else if (str.equals("onboardingstatus")) {

				if(onboardingStatusRepository.findById(master.getKey()).equals(Optional.empty())) {
					Integer uid=0;
					if (this.onboardingStatusRepository.findAll().size() == 0) {
						uid=1;
					} else {
						uid = this.onboardingStatusRepository.findTopByOrderByUidDesc().getUid();
						uid=uid++;
					}
					onboardingStatusRepository.save(new OnboardingStatus(++uid, master.getKey(), master.getValue()));
					}
					else
						return -1;

			}
			return -2;
		}
		return res;

	}

	public int save(MultipartFile file) {
		int res = 0;

		return res;
	}

	public int updateRecord(String key, MasterTables master, String str) {
		int res = 0;
		str = str.toLowerCase();
		key = key.toUpperCase();
		if ( master.getValue() != null && master.getValue() != "") {
			if (str.equals("gender")) {

				Gender obj1 = genderRepository.getOne(key);
				if(!obj1.equals(Optional.empty())) {
				obj1.setKey(master.getKey());
				obj1.setValue(master.getValue());
				genderRepository.save(obj1);
				res++;
				}
			}

			else if (str.equals("lob")) {
				Lob obj2 = lobRepository.getOne(key);
				if(!obj2.equals(Optional.empty())) {
				obj2.setKey(master.getKey());
				obj2.setValue(master.getValue());
				lobRepository.save(obj2);
				res++;
				}
			}

			else if (str.equals("location")) {
				System.out.println(locationRepository.findById(key).equals(Optional.empty()));
				if(!locationRepository.findById(key).equals(Optional.empty())) {
				System.out.println("---obj3");
				Location obj3 = locationRepository.getById(key);
				System.out.println(obj3+"---obj3");
//				if(!obj3.equals(Optional.empty())) {
//				obj3.setKey(master.getKey());
				obj3.setValue(master.getValue());
				locationRepository.save(obj3);
				res++;
				return res;
//				}
				}
				else 
					return -1;
			
			}

			else if (str.equals("region")) {
				Region obj4 = regionRepository.getOne(key);
				if(!obj4.equals(Optional.empty())) {
				obj4.setKey(master.getKey());
				obj4.setValue(master.getValue());
				regionRepository.save(obj4);
				res++;
				}
			}

			else if (str.equals("collegetiering")) {
				CollegeTiering obj5 = collegeTieringRepository.getOne(key);
				if(!obj5.equals(Optional.empty())) {
				obj5.setKey(master.getKey());
				obj5.setValue(master.getValue());
				collegeTieringRepository.save(obj5);
				res++;
				}
			}

			else if (str.equals("state")) {
				State obj6 = stateRepository.getOne(key);
				if(!obj6.equals(Optional.empty())) {
				obj6.setKey(master.getKey());
				obj6.setValue(master.getValue());
				stateRepository.save(obj6);
				res++;
				}
			} else if (str.equals("l1")) {
				L1 obj7 = l1Repository.getOne(key);
				if(!obj7.equals(Optional.empty())) {
				obj7.setKey(master.getKey());
				obj7.setValue(master.getValue());
				l1Repository.save(obj7);
				res++;
				}
			} else if (str.equals("l2")) {
				L2 obj8 = l2Repository.getOne(key);
				if(!obj8.equals(Optional.empty())) {
				obj8.setKey(master.getKey());
				obj8.setValue(master.getValue());
				l2Repository.save(obj8);
				res++;
				}
			} else if (str.equals("l3")) {
				L3 obj9 = l3Repository.getOne(key);
				if(!obj9.equals(Optional.empty())) {
				obj9.setKey(master.getKey());
				obj9.setValue(master.getValue());
				l3Repository.save(obj9);
				res++;
				}
			} else if (str.equals("l4")) {
				L4 obj10 = l4Repository.getOne(key);
				if(!obj10.equals(Optional.empty())) {
				obj10.setKey(master.getKey());
				obj10.setValue(master.getValue());
				l4Repository.save(obj10);
				res++;
				}
			} else if (str.equals("ugdegree")) {
				UgDegree obj11 = ugDegreeRepository.getOne(key);
				if(!obj11.equals(Optional.empty())) {
				obj11.setKey(master.getKey());
				obj11.setValue(master.getValue());
				ugDegreeRepository.save(obj11);				
				res++;
				}
			} else if (str.equals("ugpg")) {
				UGOrPG obj12 = ugOrPgRepository.getOne(key);
				if(!obj12.equals(Optional.empty())) {
				obj12.setKey(master.getKey());
				obj12.setValue(master.getValue());
				ugOrPgRepository.save(obj12);
				res++;
				}
			} else if (str.equals("specialization")) {
				GraduationSpecialization obj13 = ugSpecializationRepository.getOne(key);
				if(!obj13.equals(Optional.empty())) {
				obj13.setKey(master.getKey());
				obj13.setValue(master.getValue());
				ugSpecializationRepository.save(obj13);
				res++;
				}
			} else if (str.equals("offeredband")) {
				OfferedBand obj14 = offeredBandRepository.getOne(key);
				if(!obj14.equals(Optional.empty())) {
				obj14.setKey(master.getKey());
				obj14.setValue(master.getValue());
				offeredBandRepository.save(obj14);
				res++;
				}
			} else if (str.equals("offeredsubband")) {
				OfferedSubBand obj15 = offeredSubBandRepository.getOne(key);
				if(!obj15.equals(Optional.empty())) {
				obj15.setKey(master.getKey());
				obj15.setValue(master.getValue());
				offeredSubBandRepository.save(obj15);
				res++;
				}
			} else if (str.equals("offereddesignation")) {
				OfferedDesignation obj16 = offeredDesignationRepository.getOne(key);
				if(!obj16.equals(Optional.empty())) {
				obj16.setKey(master.getKey());
				obj16.setValue(master.getValue());
				offeredDesignationRepository.save(obj16);
				res++;
				}
			} else if (str.equals("onboardingstatus")) {
				OnboardingStatus obj17 = onboardingStatusRepository.getOne(key);
				if(!obj17.equals(Optional.empty())) {
				obj17.setKey(master.getKey());
				obj17.setValue(master.getValue());
				onboardingStatusRepository.save(obj17);
				res++;
				}
			}
			
		}
		
		return res;
	}

	public int deleteRecord(String key, String str) {
		str = str.toLowerCase();
		key = key.toUpperCase();
		int res=0;
		{
			if (str.equals("gender")) {
				Optional<Gender> obj1 = genderRepository.findById(key);
				if(!obj1.equals(Optional.empty())) {
				genderRepository.deleteById(obj1.get().getKey());
				res++;}
			}

			else if (str.equals("lob")) {
				Optional<Lob> obj2 = lobRepository.findById(key);
				if(!obj2.equals(Optional.empty())) {
				lobRepository.deleteById(obj2.get().getKey());
				res++;
				}
			} else if (str.equals("location")) {
				Optional<Location> obj3 = locationRepository.findById(key);
				if(!obj3.equals(Optional.empty())) {
				locationRepository.deleteById(obj3.get().getKey());
				res++;
				}
			}

			else if (str.equals("region")) {
				Optional<Region> obj4 = regionRepository.findById(key);
				if(!obj4.equals(Optional.empty())) {
				regionRepository.deleteById(obj4.get().getKey());
				res++;}
			}

			else if (str.equals("collegetiering")) {
				Optional<CollegeTiering> obj5 = collegeTieringRepository.findById(key);
				if(!obj5.equals(Optional.empty())) {
				collegeTieringRepository.deleteById(obj5.get().getKey());
				res++;}
			}

			else if (str.equals("state")) {
				Optional<State> obj6 = stateRepository.findById(key);
				if(!obj6.equals(Optional.empty())) {
				stateRepository.deleteById(obj6.get().getKey());
				res++;}
			}

			else if (str.equals("l1")) {
				Optional<L1> obj7 = l1Repository.findById(key);
				if(!obj7.equals(Optional.empty())) {
				l1Repository.deleteById(obj7.get().getKey());
				res++;}
			}

			else if (str.equals("l2")) {
				Optional<L2> obj8 = l2Repository.findById(key);
				if(!obj8.equals(Optional.empty())) {
				l2Repository.deleteById(obj8.get().getKey());
				res++;}
			}

			else if (str.equals("l3")) {
				Optional<L3> obj9 = l3Repository.findById(key);
				if(!obj9.equals(Optional.empty())) {
				l3Repository.deleteById(obj9.get().getKey());
				res++;}
			}

			else if (str.equals("l4")) {
				Optional<L4> obj10 = l4Repository.findById(key);
				if(!obj10.equals(Optional.empty())) {
				l4Repository.deleteById(obj10.get().getKey());
				res++;}
			} else if (str.equals("ugdegree")) {
				Optional<UgDegree> obj11 = ugDegreeRepository.findById(key);
				if(!obj11.equals(Optional.empty())) {
				ugDegreeRepository.deleteById(obj11.get().getKey());
				res++;}
			}

			else if (str.equals("ugpg")) {
				Optional<UGOrPG> obj12 = ugOrPgRepository.findById(key);
				if(!obj12.equals(Optional.empty())) {
				ugOrPgRepository.deleteById(obj12.get().getKey());
				res++;}
			}

			else if (str.equals("specialization")) {
				Optional<GraduationSpecialization> obj13 = ugSpecializationRepository.findById(key);
				if(!obj13.equals(Optional.empty())) {
				ugSpecializationRepository.deleteById(obj13.get().getKey());
				res++;}
			}

			else if (str.equals("offeredband")) {
				Optional<OfferedBand> obj14 = offeredBandRepository.findById(key);
				if(!obj14.equals(Optional.empty())) {
				offeredBandRepository.deleteById(obj14.get().getKey());
				res++;}
			} else if (str.equals("offeredsubband")) {
				Optional<OfferedSubBand> obj15 = offeredSubBandRepository.findById(key);
				if(!obj15.equals(Optional.empty())) {
				offeredSubBandRepository.deleteById(obj15.get().getKey());
				res++;}
			}

			else if (str.equals("offereddesignation")) {
				Optional<OfferedDesignation> obj16 = offeredDesignationRepository.findById(key);
				if(!obj16.equals(Optional.empty())) {
				offeredDesignationRepository.deleteById(obj16.get().getKey());
				res++;}
			} else if (str.equals("onboardingstatus")) {
				Optional<OnboardingStatus> obj17 = onboardingStatusRepository.findById(key);
				if(!obj17.equals(Optional.empty())) {
				onboardingStatusRepository.deleteById(obj17.get().getKey());
				res++;}
			}
		} return res;
	}
	int res=0;
    public int addmasterTableValues(List<MasterTablePossibleValues> masterTablePossibleValues) {
    	res=0;
    	masterTablePossibleValues.stream().forEach(masterValue->{   		
    			if(masterValue.getValue()!=null && masterValue.getKey()!=null) {
    				System.out.println(masterTablePossibleValuesRepository.findByValueAndMasterTable(masterValue.getValue(),masterValue.getMasterTable()));
    				if(masterTablePossibleValuesRepository.findByValueAndMasterTable(masterValue.getValue(),masterValue.getMasterTable())==null) {
    					Integer uid=0;
    					if (this.masterTablePossibleValuesRepository.findAll().size() == 0) {
    						uid=1;
    					} else {
    						uid = this.masterTablePossibleValuesRepository.findTopByOrderByIdDesc().getId();
    						uid=uid++;
    					}		
//		MasterTablePossibleValues masterObj=new MasterTablePossibleValues();
//		masterObj.setId(uid);
//		masterObj.setValue(masterValue.getValue());
//		masterObj.setKey(masterValue.getKey());
//		masterObj.setMasterTable(masterValue.getMasterTable());
		
    	masterTablePossibleValuesRepository.save(masterValue);
    	res++;
    	}
    		}
    	});
    	return res;
    }

	public int createListData(List<MasterTables> master, String str) {
		int res = 0;
		str = str.toLowerCase();
			if (str.equals("gender")) {
				int i=0;
				List<Gender> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new Gender(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
				genderRepository.saveAll(list);

			}

			else if (str.equals("lob")) {
				int i=0;
				List<Lob> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new Lob(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
				lobRepository.saveAll(list);

			}

			else if (str.equals("location")) {
				int i=0;
				List<Location> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new Location(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                locationRepository.saveAll(list);

				
			} else if (str.equals("region")) {
				int i=0;
				List<Region> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new Region(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                regionRepository.saveAll(list);

			} else if (str.equals("collegetiering")) {
				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);
				

			} else if (str.equals("state")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("l1")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);
			} else if (str.equals("l2")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);
			} else if (str.equals("l3")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("l4")) {

				int i=0;
				List<L4> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new L4(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                l4Repository.saveAll(list);

			} else if (str.equals("ugdegree")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("ugpg")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("specialization")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("offeredband")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("offeredsubband")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("offereddesignation")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			} else if (str.equals("onboardingstatus")) {

				int i=0;
				List<CollegeTiering> list=new ArrayList<>();
                while(i<master.size()) {
                	if (master.get(i).getKey() != null &&master.get(i).getKey() != "") {
                		list.add(new CollegeTiering(0, master.get(i).getKey(), master.get(i).getValue()));
                		res++;
                	}
                }
                collegeTieringRepository.saveAll(list);

			}
			
			return res;
		}

	
}
