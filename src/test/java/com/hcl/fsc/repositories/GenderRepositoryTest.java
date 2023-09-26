package com.hcl.fsc.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hcl.fsc.mastertables.Gender;

@SpringBootTest
public class GenderRepositoryTest {
	
	@Autowired
	private GenderRepository genderRepository;
	
	@Test
	void test() {
		String m="Male";
		Gender gender=genderRepository.findByValue(m);
		System.out.println(m);
		assertEquals(gender.getKey(), "MALE");
		
	}

}
