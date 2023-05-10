package com.cts.project.bookkeeping.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.bookkeeping.entities.Account;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.DuplicateUserException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.AccountType;
import com.cts.project.bookkeeping.model.LoginModel;
import com.cts.project.bookkeeping.model.PasswordChange;
import com.cts.project.bookkeeping.model.UserModel;
import com.cts.project.bookkeeping.repository.AccountRepository;
import com.cts.project.bookkeeping.repository.UsersRepository;
import com.cts.project.bookkeeping.tools.PasswordHashing;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersService {
	
	@Autowired
	UsersRepository uRepo;
	
	@Autowired
	AccountRepository aRepo;
	

	@Autowired
	PasswordHashing pH;
	
	public Users registerUser(UserModel user) throws DuplicateUserException{
		Users savedUser = new Users();
		if(uRepo.findByEmail(user.getEmail()).isEmpty()) {
			savedUser.setName(user.getName());
			savedUser.setUsername(user.getUsername());
			savedUser.setPassword(pH.hashPassword(user.getPassword()));
			savedUser.setEmail(user.getEmail());
			savedUser.setCompanyName(user.getCompanyName());
			savedUser= uRepo.save(savedUser);
			log.info("User created");
			Account cash = new Account("Cash",AccountType.ASSET,0);
			Account receivable = new Account("Accounts Receivable",AccountType.ASSET,0);
			Account payable = new Account("Accounts Payable",AccountType.LIABILITY,0);
			Account inventory = new Account("Inventory",AccountType.ASSET,0);
			Account sales = new Account("Sales Revenue",AccountType.REVENUE,0);
			Account costOfGoodsSold = new Account("Cost Of Goods Sold",AccountType.EXPENSE,0);
			Account rent = new Account("Rent",AccountType.EXPENSE,0);
			Account insurance = new Account("Insuarance",AccountType.EXPENSE,0);
			Account intrest = new Account("Intrest Expense",AccountType.EXPENSE,0);
			Account salaries = new Account("Wages Expense",AccountType.EXPENSE,0);
			Account commonStock = new Account("Common Stock", AccountType.EQUITY,0);
			Account salesTaxPayable = new Account("Sales Tax Payable", AccountType.LIABILITY,0);
			

			List<Account> defaultAccounts = List.of(cash,receivable,payable,inventory,sales,costOfGoodsSold,
					 rent,insurance,salaries,commonStock,intrest,salesTaxPayable);
			for(Account a : defaultAccounts) {
				Account acc = new Account();
				acc.setUser(savedUser);
				acc.setAccountName(a.getAccountName());
				acc.setType(a.getType());
				acc.setBalance(a.getBalance());
				aRepo.saveAndFlush(acc);
			}
			log.info("Default Accounts Created");
			return savedUser;
		}
		else {
			log.info("Duplicate User Found");
			throw new DuplicateUserException("You are already registred");
		}
	}
	
	public Users login(LoginModel userLogin) throws UserNotFoundException{
		
		String userName = userLogin.getUsername();
		String pass = userLogin.getPassword();
		List<Users> user = uRepo.findByUsername(userName);
		if(user.isEmpty()) {
			log.info("User not found");
			throw new UserNotFoundException("User Not Found!!!");
		}
		else {
			String hash = pH.hashPassword(pass);
			if(!hash.equals(user.get(0).getPassword())) {
				log.info("Username and Password does not match");
				throw new UserNotFoundException("Invalid Credentials");
			}
			log.info("logged in");
			return user.get(0);
		}
	}
	
	
	public List<Users> getAllUsers(){
		return uRepo.findAll();
	}
	
	public Users getUserById(String id) throws UserNotFoundException{
		Users user = uRepo.findById(id).orElse(null);
		if(user==null) {
			throw new UserNotFoundException("User Not Found!!!");
		}
		else {
			return user;
		}
		
	}
	
	@Transactional
	public String updatePassword(String email,PasswordChange pass)throws UserNotFoundException {
		List<Users> userL = uRepo.findByEmail(email);
		if(userL.isEmpty()) {
			throw new UserNotFoundException("User was not found");
		}
		else {
			Users u=userL.get(0);
		
		if(u.getPassword().equals(pass.getOldPass())) {
			u.setPassword(pass.getNewPass());
			uRepo.save(u);
			return "Password Changed";
		}
		return "Password do not match";
		}
	}
	
	@Transactional
	public Users updateUserById(UserModel user, String userId) throws UserNotFoundException {
		Users u = null;

		u = getUserById(userId);
		u.setName(user.getName());
		u.setUsername(user.getUsername());
		u.setEmail(user.getEmail());
		u.setCompanyName(user.getCompanyName());
		u = uRepo.save(u);

		return u;
	}
	
}
