package com.hcl.fsc.helpers;

import java.util.*;
import lombok.Data;

@Data
public class ResponseList {

	private Integer total_No_Records;

	private Integer sucessful_Records;

	private Integer failed_Records;

	private Integer duplicate_Records;

	private List<String> duplicate_Email_List;

	private Map<String,List<String>> failed_Records_List;

}
