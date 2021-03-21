package com.somecompany.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somecompany.model.Postcode;

/**
 * The data-access-object (DAO) used to perform CRUD operations with the DB.
 * 
 * @author patrick
 *
 */
@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {

	List<Postcode> findByPostcode(String postcode);

	List<Postcode> findByPostcodeContaining(String postcode);

	List<Postcode> findBySuburbContainingIgnoreCase(String suburb);
}
