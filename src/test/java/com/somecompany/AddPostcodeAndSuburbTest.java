package com.somecompany;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.error.ApiError;
import com.somecompany.model.Postcode;
import com.somecompany.service.PostcodeService;

/**
 * Test cases for "add new suburb and postcode combination".
 * 
 * @author patrick
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "50000")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AddPostcodeAndSuburbTest {

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private PostcodeRepository postcodeRepository;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void shouldBeAbleToAddPostcodeAndSuburb() {

		// Actual result

		Postcode postcode = new Postcode();
		postcode.setPostcode("1234");
		postcode.setSuburb("Test");

		postcodeService.addPostcodeAndSuburb(postcode);

		// Assertions

		assertEquals(7, postcodeRepository.count());

		assertEquals("1234", postcodeRepository.findBySuburbContainingIgnoreCase("Test").get(0).getPostcode());
		assertEquals("Test", postcodeRepository.findByPostcodeContaining("1234").get(0).getSuburb());
	}

	@Test
	public void shouldBeAbleToSkipAddIfCombinationAlreadyExistsAsExactMatchOnAddPostcodeAndSuburb() {

		// Actual result

		Postcode postcode = new Postcode();
		postcode.setPostcode("3121");
		postcode.setSuburb("RICHMOND, VIC");

		postcodeService.addPostcodeAndSuburb(postcode);

		// Assertions

		assertEquals(6, postcodeRepository.count());
	}
	
	@Test
	public void shouldBeAbleToSkipAddIfCombinationAlreadyExistsAsDifferntCaseOnAddPostcodeAndSuburb() {

		// Actual result

		Postcode postcode = new Postcode();
		postcode.setPostcode("3121");
		postcode.setSuburb("richmond, VIC");

		postcodeService.addPostcodeAndSuburb(postcode);

		// Assertions

		assertEquals(6, postcodeRepository.count());
	}

	@Test
	public void shouldBeAbleToAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"1234\", \"suburb\": \"Test\"}").exchange().expectStatus().isOk()
				.expectBody(String.class).value(result -> {
					// Assertions

					assertEquals(7, postcodeRepository.count());

					assertEquals("1234", postcodeRepository.findBySuburbContainingIgnoreCase("Test").get(0).getPostcode());
					assertEquals("Test", postcodeRepository.findByPostcodeContaining("1234").get(0).getSuburb());
				});
	}

	@Test
	public void shouldBeAbleToSkipAddIfCombinationAlreadyExistsAsExactMatchOnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"3121\", \"suburb\": \"RICHMOND, VIC\"}").exchange().expectStatus().isOk()
				.expectBody(String.class).value(result -> {
					// Assertions

					assertEquals(6, postcodeRepository.count());
				});
	}
	
	@Test
	public void shouldBeAbleToSkipAddIfCombinationAlreadyExistsAsDifferentCaseOnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"3121\", \"suburb\": \"richmond, VIC\"}").exchange().expectStatus().isOk()
				.expectBody(String.class).value(result -> {
					// Assertions

					assertEquals(6, postcodeRepository.count());
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeIsMissingOnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"suburb\": \"Test\"}").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString("Postcode cannot be null nor empty!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeLengthIsLessThan4OnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"123\", \"suburb\": \"Test\"}").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString("Postcode must be of 4 characters!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeLengthIsMoreThan4OnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"12345\", \"suburb\": \"Test\"}").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString("Postcode must be of 4 characters!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbIsMissingOnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"1234\"}").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString("Suburb cannot be null nor empty!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbLengthIsLessThan3OnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"postcode\": \"1234\", \"suburb\": \"12\"}").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(),
							CoreMatchers.containsString("Suburb must have length between 3 and 100!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbLengthIsMoreThan100OnAddPostcodeAndSuburbThroughAPICall() {

		webTestClient.put().uri("/api/postcode").contentType(MediaType.APPLICATION_JSON).bodyValue(
				"{ \"postcode\": \"1234\", \"suburb\": \"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890\"}")
				.exchange().expectStatus().isBadRequest().expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(),
							CoreMatchers.containsString("Suburb must have length between 3 and 100!"));
				});
	}
}
