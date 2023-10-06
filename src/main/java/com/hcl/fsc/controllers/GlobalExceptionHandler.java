package com.hcl.fsc.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hcl.fsc.customExpcetion.DuplicateValueException;
import com.hcl.fsc.customExpcetion.NullValueException;
import com.hcl.fsc.customExpcetion.ValueNotPresentException;
import com.hcl.fsc.response.Response;

@RestControllerAdvice
@CrossOrigin("*")
//@CrossOrigin(origins = "*")
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MasterTableController.class);
	
	@ExceptionHandler({DuplicateValueException.class, NullValueException.class, ValueNotPresentException.class})
	public ResponseEntity<?> handleCustomException(Exception e) {
		Response res = new Response(e.getMessage());
		log.error(res.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handleNullPointerException(NoSuchElementException e) {
		Response res = new Response("No Such record is present");
		log.error("No Such record is present");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
	
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<?> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e) {
		log.warn("Issue from RDBMS side or an Entity Side");
		Response res = new Response("Issue from RDBMS side or an Entity Side");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("Internal Server Error");
		Response res = new Response("Something Went Wrong");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
	
	
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String, String>> handleMessage(MethodArgumentNotValidException ex) {
//		Map<String, String> res = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String name = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			res.put(name, message);
//		});
//		return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
//	}
	
}
