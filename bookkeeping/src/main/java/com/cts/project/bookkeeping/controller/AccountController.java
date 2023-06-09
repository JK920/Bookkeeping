package com.cts.project.bookkeeping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.bookkeeping.entities.Account;
import com.cts.project.bookkeeping.exception.AccountNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.AccountLedger;
import com.cts.project.bookkeeping.model.AccountModel;
import com.cts.project.bookkeeping.model.AccountType;
import com.cts.project.bookkeeping.service.AccountService;

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AccountController {

	@Autowired
	AccountService aService;
	
	@PostMapping(value="/create/account")
	public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountModel account) throws UserNotFoundException{
		Account savedAccount= aService.createAccount(account);
		return new ResponseEntity<>(savedAccount,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/users/{userId}/accounts")
	public List<Account> getAccountsOfUser(@PathVariable("userId") String userId)throws UserNotFoundException{
		return aService.getAccountsOfUser(userId);
		
	}
	
	@GetMapping(value="/get/users/{userId}/account/{type}")
	public List<Account> getAccountByType(@PathVariable("type") AccountType type ,@PathVariable("userId") String userId) {
		return aService.getAccountByType(type, userId);
	}
	
	@GetMapping(value="/get/users/{userId}/accountsbyid/{accId}")
	public Account getAccountById(@PathVariable("accId") String accId, @PathVariable("userId") String userId) throws AccountNotFoundException{
		return aService.getAccountById(accId, userId);
	}
	
	@GetMapping(value="/get/users/{userId}/accountsbyname/{name}")
	public Account getAccountByName(@PathVariable("name") String name, @PathVariable("userId") String userId) throws AccountNotFoundException{
		return aService.getAccountByName(name, userId);
	}
	
	@PutMapping(value="/update/users/{userId}/accounts/{name}/{newName}")
	public ResponseEntity<Object> updateAccount(@PathVariable("newName") String newName, @PathVariable("name") String name, @PathVariable("userId") String userId)throws AccountNotFoundException {
		Account acc = aService.updateAccount(newName, name, userId);
		return new ResponseEntity<>(acc,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/users/{userId}/accounts/{accId}")
	public ResponseEntity<Object> deleteAccountById(@PathVariable("accId") String id, @PathVariable("userId") String userId) throws AccountNotFoundException{
		String s = aService.deleteAccountById(id, userId);
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/users/{userId}/accountbalance/{type}")
	public double totalBalanceByAccountType(@PathVariable("type") AccountType type ,@PathVariable("userId") String userId) {
		return aService.totalBalanceByAccountType(type, userId);
	}
	
	@GetMapping(value="/get/users/{userId}/ledger")
	public Map<String, List<AccountLedger>> generateLedger(@PathVariable("userId") String userId) {
		return aService.generateLedger(userId);
	}
	
	@GetMapping(value="/get/users/{userId}/income")
	public double generateIncome(@PathVariable("userId") String userId) {
		return aService.generateIncome(userId);
	}
	
	@PutMapping(value="/update/users/{userId}/openingbalances")
	public ResponseEntity<Object> updateOpeningBalance(@PathVariable("userId") String userId,@RequestBody Map<String, Double> balMap){
		aService.updateOpeningBalance(userId, balMap);
		return new ResponseEntity<>("Opening Balances Updated For User",HttpStatus.OK);
	}
	
	@PutMapping(value="/update/users/{userId}/newyearbalances")
	public ResponseEntity<Object> newYearBalances(@PathVariable("userId") String userId){
		aService.yearEndBalances(userId);
		return new ResponseEntity<>("Opening Balances Updated in journal for new Financial Year",HttpStatus.OK);	
	}
}
