package com.somecompany.model;

import java.util.Objects;

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

/**
 * The entity model of a row in the "POSTCODE" table.
 * 
 * @author patrick
 */
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
	@Size(min = 4, max = 4, message = "Postcode must be of 4 characters!")
	private String postcode;

	@Column(name = "suburb")
	@NotEmpty(message = "Suburb cannot be null nor empty!")
	@Size(min = 3, max = 100, message = "Suburb must have length between 3 and 100!")
	private String suburb;

	@Column(name = "suburbinfo")
	@Size(min = 0, max = 100, message = "Suburb information must have length between 0 and 100!")
	private String suburbinfo;

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		if (postcode == null || suburb == null) {
			return false;
		}

		Postcode other = (Postcode) obj;
		if (postcode.equals(other.getPostcode()) && suburb.toLowerCase().equals(other.getSuburb().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
}
