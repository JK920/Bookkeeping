package com.cts.project.bookkeeping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.bookkeeping.entities.Journal;



@Repository
public interface JournalRepository extends JpaRepository<Journal, String>{

}
