package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodePK;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class PostcodeTest {

	@Autowired
	private PostcodeService PostcodeService;

	@Test
	public void shouldBeAbleToGetSuburbByPostCode() {

		// Actual result

		List<Postcode> result = PostcodeService.getSuburbByPostcode(3121);

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

		assertEquals(2, result.size());

		assertEquals(3121, result.get(0).getPostcodePK().getPostcode());
		assertEquals("BURNLEY, VIC", result.get(0).getPostcodePK().getSuburb());

		assertEquals(3121, result.get(1).getPostcodePK().getPostcode());
		assertEquals("RICHMOND, VIC", result.get(1).getPostcodePK().getSuburb());
	}
}
