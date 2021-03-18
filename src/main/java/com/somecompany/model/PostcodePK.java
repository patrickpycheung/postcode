package com.somecompany.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PostcodePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "postcode")
	@NotEmpty(message = "Postcode cannot be null nor empty!")
	@Pattern(regexp = "^[0-9]+$", message = "Postcode must be a number!")
	private Integer postcode;

	@Column(name = "suburb")
	@NotEmpty(message = "Suburb cannot be null nor empty!")
	@Size(min = 1, max = 100, message = "Suburb must have length between 1 and 100!")
	private String suburb;
}
