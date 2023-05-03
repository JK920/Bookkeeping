package com.cts.project.bookkeeping.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.project.bookkeeping.entities.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, String>{

	
	@Query(value = "select a from Account a where a.user.userId =:userId")
	public List<Account> findAccountByUserId(@Param("userId") String userId);
}
