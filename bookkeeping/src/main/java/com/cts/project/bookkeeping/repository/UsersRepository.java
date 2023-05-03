package com.cts.project.bookkeeping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.bookkeeping.entities.Users;



@Repository
public interface UsersRepository extends JpaRepository<Users, String>{

	
	List<Users> findByEmail(String email);
    List<Users> findByUsernameOrEmail(String username, String email);
    List<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    

}
