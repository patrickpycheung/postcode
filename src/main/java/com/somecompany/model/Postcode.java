package com.somecompany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Postcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "postcode")
	@NotEmpty(message = "Postcode cannot be null nor empty!")
	@Pattern(regexp = "^[0-9]+$", message = "Postcode must be a number!")
	private String postcode;

	@Column(name = "suburb")
	@NotEmpty(message = "Suburb cannot be null nor empty!")
	@Size(min = 1, max = 100, message = "Suburb must have length between 1 and 100!")
	private String suburb;

	@Column(name = "suburbinfo")
	@Size(min = 0, max = 100, message = "Suburb information must have length between 0 and 100!")
	private String suburbinfo;
}
