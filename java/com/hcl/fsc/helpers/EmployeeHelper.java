package com.hcl.fsc.helpers;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hcl.fsc.excel.vo.CDAC;
import com.hcl.fsc.excel.vo.DigiBee;
import com.hcl.fsc.excel.vo.MoU;
import com.hcl.fsc.excel.vo.NonTier1;
import com.hcl.fsc.excel.vo.ProjectTypeExcel;
import com.hcl.fsc.excel.vo.SkilledHiring;
import com.hcl.fsc.excel.vo.Tier1;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;

public class EmployeeHelper {

	public static boolean checkExcelFormate(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			return true;
		else
			return false;
	}

	public static List<NonTier1> convertExcelToListOfNonTier1(InputStream inputStream) {
		List<NonTier1> employeeNonTier1List = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, NonTier1.class);
		return employeeNonTier1List;
	}

	public static List<DigiBee> convertExcelToListOfDigibee(InputStream inputStream) {
		List<DigiBee> employeeDigibeeList = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, DigiBee.class);
		return employeeDigibeeList;
	}

	public static List<CDAC> convertExcelToListOfCDAC(InputStream inputStream) {
		List<CDAC> employeeCDACList = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, CDAC.class);
		return employeeCDACList;
	}

	public static List<Tier1> convertExcelToListOfTier1(InputStream inputStream) {
		List<Tier1> list = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, Tier1.class);
		return list;
	}

	public static List<SkilledHiring> convertExcelToListOfSkilledHiring(InputStream inputStream) {
		List<SkilledHiring> list = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, SkilledHiring.class);
		return list;
	}

	public static List<MoU> convertExcelToListOfMoU(InputStream inputStream) {
		List<MoU> list = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, MoU.class);
		return list;
	}
	
	public static List<ProjectTypeExcel> convertExcelToListOfProjectType(InputStream inputStream){
		List<ProjectTypeExcel> list = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, ProjectTypeExcel.class);
		return list;
	}
	
	
	
}