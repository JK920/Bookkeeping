package com.cts.project.bookkeeping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.bookkeeping.entities.Vendors;

@Repository
public interface VendorsRepository extends JpaRepository<Vendors, String>{

}
