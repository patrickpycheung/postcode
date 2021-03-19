package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.SuburbResponse;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class PostcodeTest {

	@Autowired
	private PostcodeService PostcodeService;

	@Test
	public void shouldBeAbleToGetSuburbByPostCode() {

		// Actual result

		SuburbResponse result = PostcodeService.getSuburbByPostcode("3121");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}
}
