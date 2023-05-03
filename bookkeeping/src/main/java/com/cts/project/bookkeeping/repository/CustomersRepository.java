package com.cts.project.bookkeeping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.bookkeeping.entities.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, String>{

	
}
