package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodePK;
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

		SuburbResponse result = PostcodeService.getSuburbByPostcode(3121);

		// Expected result

		PostcodePK postcodePK01 = new PostcodePK();
		postcodePK01.setPostcode(3121);
		postcodePK01.setSuburb("BURNLEY, VIC");

		Postcode postcode01 = new Postcode();
		postcode01.setPostcodePK(postcodePK01);

		PostcodePK postcodePK02 = new PostcodePK();
		postcodePK02.setPostcode(3121);
		postcodePK02.setSuburb("RICHMOND, VIC");

		Postcode postcode02 = new Postcode();
		postcode02.setPostcodePK(postcodePK02);

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}
}
