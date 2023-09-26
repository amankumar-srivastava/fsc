package com.hcl.fsc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hcl.fsc.controllers.MasterTableController;
import com.hcl.fsc.services.EmployeeNonTier1ServiceImpl;
import com.hcl.fsc.services.MasterTableServiceImpl;
import com.hcl.fsc.mastertables.MasterTables;
import com.hcl.fsc.repositories.GenderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AllControllerTest {

	@Autowired
	public MasterTableController controller;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	public MasterTableServiceImpl MTservice;
	
	@Autowired
	private EmployeeNonTier1ServiceImpl Eservice;
	
	@Autowired
	public GenderRepository repo;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

//	@Test
//	@Order(1)
//	public void getRecordAllTables() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables", "UGOrPG");
//		for (String s : tablesList) {
//			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/master/" + s.toLowerCase())).andReturn();
//			String jsonResponse = result.getResponse().getContentAsString();
//
//			System.out.println(s + " table JSON response: " + jsonResponse);
//			JSONArray jsonArray = new JSONArray(jsonResponse);
//			int objectCount = jsonArray.length();
//
//			int recordCountFromService = MTservice.getRecord(s).size();
//			assertEquals(objectCount, recordCountFromService);
//		}
//	}

	@Test
	@Order(2)
	public void getRecordByKey() throws Exception {
		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
				"GraduationSpecialization", "MasterTables");

		for (String tableName : tablesList) {
			String className = "com.hcl.fsc.mastertables." + tableName;
			Class<?> pojoClass = Class.forName(className);

			// Get records from master table
			List<?> records = MTservice.getRecord(tableName.toLowerCase());

			// Add records' key into keyList
			for (Object record : records) {
				try {
					// get Key from each mastertable's record.
					Method getKeyMethod = pojoClass.getMethod("getKey");
					String key = (String) getKeyMethod.invoke(record);

					// get response from API
					MvcResult result = mockMvc
							.perform(MockMvcRequestBuilders.get("/master/?mastertable=" + tableName + "&key=" + key))
							.andReturn();
					if (result.getResponse().getStatus() == HttpStatus.OK.value()) {
						String jsonResponse = result.getResponse().getContentAsString();

						JSONObject apiObject = new JSONObject(jsonResponse);

						Optional<?> serviceObject = MTservice.getRecordbyKey(tableName, key);

						if (serviceObject.isPresent()) {
							Object object = serviceObject.get();
							if (pojoClass.isInstance(object)) {
								Object pojoObject = pojoClass.cast(object);

								Method getUid = pojoClass.getMethod("getUid");
								Method getKey = pojoClass.getMethod("getKey");
								Method getValue = pojoClass.getMethod("getValue");

								int tempUid = (int) getUid.invoke(pojoObject);
								String tempKey = (String) getKey.invoke(pojoObject);
								String tempValue = (String) getValue.invoke(pojoObject);

								JSONObject jsonObject = new JSONObject();
								jsonObject.put("uid", tempUid);
								jsonObject.put("key", tempKey);
								jsonObject.put("value", tempValue);

								JSONObject optionalObject = new JSONObject(jsonObject.toString());
								assertEquals(apiObject.toString(), optionalObject.toString());
							}

						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

//	@Test
//	@Order(3)
//	public void testCreateRecordWithValidValue() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables");
//
//		for (String table : tablesList) {
//			String requestBodyJson = "{\"key\":\"TEST\", \"value\":\"TEST\"}";
//			ObjectMapper objectMapper = new ObjectMapper();
//			MasterTables master = objectMapper.readValue(requestBodyJson, MasterTables.class);
//
//			String masterJson = objectMapper.writeValueAsString(master);
//
//			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/master/" + table)
//					.contentType(MediaType.APPLICATION_JSON).content(masterJson))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//			String responseContent = result.getResponse().getContentAsString();
//			assertEquals("Data saved successfully!", responseContent);
//		}
//	}
//
//	@Test
//	@Order(4)
//	public void testCreateRecordWithInvalidValue() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables");
//
//		for (String table : tablesList) {
//			String requestBodyJson = "{\"key\":\"\", \"value\":\"\"}";
//			ObjectMapper objectMapper = new ObjectMapper();
//			MasterTables master = objectMapper.readValue(requestBodyJson, MasterTables.class);
//
//			String masterJson = objectMapper.writeValueAsString(master);
//
//			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/master/" + table)
//					.contentType(MediaType.APPLICATION_JSON).content(masterJson))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//			String responseContent = result.getResponse().getContentAsString();
//			assertEquals("KEY is null or empty", responseContent);
//		}
//	}
//
//	@Test
//	@Order(5)
//	public void testUpdateRecordWithValidValue() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables");
//
//		for (String table : tablesList) {
//			String requestBodyJson = "{\"value\":\"TEST1\"}";
//			ObjectMapper objectMapper = new ObjectMapper();
//			MasterTables master = objectMapper.readValue(requestBodyJson, MasterTables.class);
//
//			String masterJson = objectMapper.writeValueAsString(master);
//
//			MvcResult result = mockMvc
//					.perform(MockMvcRequestBuilders.put("/master/" + table + "/TEST")
//							.contentType(MediaType.APPLICATION_JSON).content(masterJson))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//			String responseContent = result.getResponse().getContentAsString();
//			assertEquals("Data Updated successfully!", responseContent);
//		}
//	}
//
//	@Test
//	@Order(6)
//	public void testUpdateRecordWithInvalidValue() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables");
//
//		for (String table : tablesList) {
//			String requestBodyJson = "{\"key\":\"\", \"value\":\"\"}";
//			ObjectMapper objectMapper = new ObjectMapper();
//			MasterTables master = objectMapper.readValue(requestBodyJson, MasterTables.class);
//
//			String masterJson = objectMapper.writeValueAsString(master);
//
//			MvcResult result = mockMvc
//					.perform(MockMvcRequestBuilders.put("/master/" + table + "/TEST")
//							.contentType(MediaType.APPLICATION_JSON).content(masterJson))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//			String responseContent = result.getResponse().getContentAsString();
//			assertEquals("VALUE is null or empty", responseContent);
//		}
//	}
//
//	@Test
//	@Order(7)
//	public void testDeleteRecord() throws Exception {
//
//		List<String> tablesList = Arrays.asList("Gender", "LOB", "Location", "Region", "CollegeTiering", "State", "L1",
//				"L2", "L3", "L4", "UGDegree", "OfferedBand", "OfferedSubBand", "OfferedDesignation", "OnboardingStatus",
//				"GraduationSpecialization", "MasterTables");
//		for (String table : tablesList) {
//
//			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/master/" + table + "/TEST"))
//					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//			String responseContent = result.getResponse().getContentAsString();
//			assertEquals("Data Deleted successfully!", responseContent);
//		}
//	}
//	
//	@Test
//	public void elchEmployeeUploadValid() throws Exception {
//		Workbook workbook = new XSSFWorkbook();
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		workbook.write(outputStream);
//		
//		workbook.close();
//		MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//				outputStream.toByteArray());
//
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fsc/upload").file(file))
//				  .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("All files are uploaded")).andReturn();
//
//		String responseContent = result.getResponse().getContentAsString();
//		assertEquals("{\"message\":\"All files are uploaded\"}", responseContent);
//
//	}
//	
//	@Test
//	public void employeeNonTier1UploadInvalid() throws Exception {
//		Workbook workbook = new XSSFWorkbook();
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		workbook.write(outputStream);
//		
//		workbook.close();
//		MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel",
//				outputStream.toByteArray());
//
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fsc/upload")
//	            .file(file))
//	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
//	            .andReturn();
//		String responseContent = result.getResponse().getContentAsString();
//		assertEquals("Please upload Excel sheet Only", responseContent);
//
//	}
//	
//	@Test
//	public void uploadTest() throws Exception {
//	    Workbook workbook = new XSSFWorkbook();
//
//	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	    workbook.write(outputStream);
//
//	    workbook.close();
//	    MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//	            outputStream.toByteArray());
//
//	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fsc/uploads")
//	            .file(file))
//	            .andExpect(MockMvcResultMatchers.status().isOk())
//	            .andReturn();
//
//	    String responseContent = result.getResponse().getContentAsString();
//	    assertEquals("Upload", responseContent);
//	}
//	
//	@Test
//	public void uploadTestInvalid() throws Exception {
//		Workbook workbook = new XSSFWorkbook();
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		workbook.write(outputStream);
//		
//		workbook.close();
//		MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel",
//				outputStream.toByteArray());
//
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/fsc/uploads")
//	            .file(file))
//	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
//	            .andReturn();
//		String responseContent = result.getResponse().getContentAsString();
//		assertEquals("Please upload Excel sheet Only", responseContent);
//
//	}

	
//	@Test
//	public void getAllEmps() throws Exception {
//
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employeesList")).andReturn();
//		String jsonResponse = result.getResponse().getContentAsString();
//
//		System.out.println("table JSON response: " + jsonResponse);
//		JSONArray jsonArray = new JSONArray(jsonResponse);
//		int objectCount = jsonArray.length();
//
//		int recordCountFromService = Eservice.getAllEmployees().size();
//		assertEquals(objectCount, recordCountFromService);
//	}
	
}
