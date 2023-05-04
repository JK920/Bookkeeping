package com.cts.project.bookkeeping.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.DuplicateUserException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.LoginModel;
import com.cts.project.bookkeeping.model.PasswordChange;
import com.cts.project.bookkeeping.model.UserModel;
import com.cts.project.bookkeeping.service.UsersService;

import jakarta.validation.Valid;

@RestController
public class UsersController {

	@Autowired
	UsersService uService;
	
	
	@PostMapping(value="/create/user")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody UserModel user) throws DuplicateUserException{
		Users savedUser = uService.registerUser(user);
		return new ResponseEntity<>(savedUser,HttpStatus.OK);
	}
	
	@PostMapping(value="/login/user")
	public String loginUser(@Valid @RequestBody LoginModel login) throws UserNotFoundException{
		return uService.login(login);
	}
	
	@GetMapping(value="/get/users/{userId}")
	public Users getUserById(@PathVariable("userId") String id) throws UserNotFoundException {
		return uService.getUserById(id);
	}
	
	
	@GetMapping(value="/get/users")
	public List<Users> getAllUsers() {
		return uService.getAllUsers();
	}
	
	@PutMapping(value = "/update/user/{userId}")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user,@PathVariable("userId") String userId) throws UserNotFoundException{
		Users u = uService.updateUserById(user, userId);
		return new ResponseEntity<>(u,HttpStatus.OK);
	}
	@PutMapping(value="/update/user/{userId}/password")
	public String updatePass(@PathVariable("userId") String userId,@RequestBody PasswordChange pass){
		return uService.updatePassword(userId, pass);
	}
	
	
}
