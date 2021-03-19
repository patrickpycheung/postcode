package com.somecompany.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodePK;

@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, PostcodePK> {

}
