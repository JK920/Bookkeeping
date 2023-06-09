package com.cts.project.bookkeeping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.bookkeeping.entities.Account;
import com.cts.project.bookkeeping.entities.Journal;
import com.cts.project.bookkeeping.entities.Ledger;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.AccountNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.AccountLedger;
import com.cts.project.bookkeeping.model.AccountModel;
import com.cts.project.bookkeeping.model.AccountType;
import com.cts.project.bookkeeping.repository.AccountRepository;
import com.cts.project.bookkeeping.repository.JournalRepository;
import com.cts.project.bookkeeping.repository.LedgerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {
	
	@Autowired
	AccountRepository aRepo;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	JournalRepository jRepo;
	
	@Autowired
	LedgerRepository lRepo;
	
	@Transactional
	public Account createAccount(AccountModel account) throws UserNotFoundException{
		Account acc = null;
		
		Users user = uService.getUserById(account.getUserId());
		acc= new Account(account.getAccountName(),account.getType(),account.getBalance());
		acc.setUser(user);
		acc = aRepo.save(acc);
		
		return acc;
	}
	
	
	@Transactional
	public List<Account> getAccountsOfUser(String userId) throws UserNotFoundException{
		

		Users user = uService.getUserById(userId);
		return user.getAccountList();

	}
	
	@Transactional
	public Account getAccountById(String accId, String userId) throws AccountNotFoundException{
		Account account = null;
		List<Account> accountList = getAccountsOfUser(userId);
		for(Account acc : accountList) {
			if(acc.getAccountId().equals(accId)) {
				account =acc;
			}
		}
		if(account!= null) {
			return account;
		}
		else {
			throw new AccountNotFoundException("Account not found!!");
		}
	}
	
	@Transactional
	public Account getAccountByName(String name, String userId) throws AccountNotFoundException{
		Account account = null;
		List<Account> accountList = getAccountsOfUser(userId);
		for(Account acc : accountList) {
			if(acc.getAccountName().equals(name)) {
				account = acc;
			}
		}
		if(account!= null) {
			return account;
		}
		else {
			throw new AccountNotFoundException("Account not found!!");
		}

	}
	
	@Transactional
	public List<Account> getAccountByType(AccountType type, String userId) throws AccountNotFoundException{
		List<Account> accounts = new ArrayList<>();
		List<Account> accountList = getAccountsOfUser(userId);
		for(Account acc : accountList) {
			if(acc.getType()== type) {
				accounts.add(acc);
			}
		}
		if(accounts.isEmpty()) {
			throw new AccountNotFoundException("Account not found!!");
		}
		else {
			return accounts;
		}

	}
	
	
	@Transactional
	public Account updateAccount(String name, String id,String userId) throws AccountNotFoundException{
		
		Account acc  = getAccountById(id, userId);
		acc.setAccountName(name);
		acc = aRepo.save(acc);
		return acc;

	}
	
	
	@Transactional
	public String deleteAccountById(String id, String userId) throws AccountNotFoundException{
		String res="";
		Account a = getAccountById(id, userId);
		String name = a.getAccountName();
		if(a.getJournalList().isEmpty()) {
			aRepo.delete(a);
			res = "Account "+name+" deleted.";
		}
		else {
			res = "Account cannot be deleted. It is associated with Journal entries.";
		}
		return res;
	}
	
	@Transactional
	public double updateAccountBalance(Journal j) throws  AccountNotFoundException{
		Account acc = j.getAccount();
		double debit = j.getDebit();
		double credit = j.getCredit();
		double balance = acc.getBalance();
		if(acc.getType()==AccountType.ASSET || acc.getType()==AccountType.EXPENSE) {
			
				balance +=( debit-credit);
			
		}
		else if(acc.getType()==AccountType.LIABILITY || acc.getType()==AccountType.EQUITY || acc.getType()==AccountType.REVENUE) {
				balance += (credit-debit);
		}
		acc.setBalance(balance);
		aRepo.save(acc);
		return balance;
	}
	
	public double updateAccountBalanceForJournalDeletion(Journal j) {
		Account acc = j.getAccount();
		double debit = j.getDebit();
		double credit = j.getCredit();
		double balance = acc.getBalance();
		if(acc.getType()==AccountType.ASSET || acc.getType()==AccountType.EXPENSE) {
			
				balance -=( debit-credit);
			
		}
		else if(acc.getType()==AccountType.LIABILITY || acc.getType()==AccountType.EQUITY || acc.getType()==AccountType.REVENUE) {
				balance -= (credit-debit);
		}
		acc.setBalance(balance);
		aRepo.save(acc);
		return balance;
	}
	
	public double totalBalanceByAccountType(AccountType type ,String userId) {
		double total=0;
		for(Account a :getAccountByType(type, userId)) {
			total+=a.getBalance();
		}
		return total;
	}
	
	public Map<String, List<AccountLedger>> generateLedger(String userId) {
		Map<String, List<AccountLedger>> ledger = new HashMap<>();
		for(Account a:getAccountsOfUser(userId)) {
			String s = a.getAccountName(); 
			List<AccountLedger> list = new ArrayList<>();
			for(Journal j:a.getJournalList()) {
				AccountLedger aL = new AccountLedger(j.getDate(), j.getReference(), j.getDescription(), j.getDebit(), j.getCredit(), j.getLedger().getBalance());
				list.add(aL);
			}
			ledger.put(s, list);					
		}
		return ledger;

	}
	
	public double generateIncome(String userId) {
		return totalBalanceByAccountType(AccountType.REVENUE, userId) - totalBalanceByAccountType(AccountType.EXPENSE, userId);
	}
	
	
	public void journalAndLedgerEntry(Journal j, Account a) {
		j.setAccount(a);
		j.setUser(a.getUser());
		j=jRepo.saveAndFlush(j);
		Ledger l = new Ledger();
		l.setAccount(j.getAccount());
		l.setBalance(a.getBalance());
		l.setJournal(j);
		lRepo.saveAndFlush(l);
	}
	
	
	public void updateOpeningBalance(String userId, Map<String,Double> balanceMap) {
		LocalDate date = LocalDate.now();
		double total = 0;
		for(String accId :balanceMap.keySet()) {
			double balance = balanceMap.get(accId);
			total+=balance;
			Account a = getAccountById(accId, userId);
			log.info(accId,balance);
			if(a.getType()==AccountType.ASSET || a.getType()==AccountType.EXPENSE) {
				a.setBalance(balance);
				a=aRepo.saveAndFlush(a);
				if(balance>0) {
					Journal j = new Journal(date, "Opening Balance", "", balance, 0);
					journalAndLedgerEntry(j, a);
				}
				else if(balance<0) {
					balance=0-balance;
					Journal j = new Journal(date, "Opening Balance", "", 0, balance);
					journalAndLedgerEntry(j, a);
				}	
			}
			else {
				balance=0-balance;
				a.setBalance((balance));
				a=aRepo.saveAndFlush(a);
				if(balance<0) {
					balance=0-balance;
					Journal j = new Journal(date, "Opening Balance", "", balance, 0);
					journalAndLedgerEntry(j, a);
				}
				else if(balance>0) {
					
					Journal j = new Journal(date, "Opening Balance", "", 0, balance);
					journalAndLedgerEntry(j, a);
				}	
				
			}
		}
		if(total<0) {
				
			Account a = getAccountByName("Common Stock", userId);
			Journal j = new Journal(date, "Retained Earnings", "", total, 0);
			journalAndLedgerEntry(j, a);
			
		}
		else if(total>0) {
			Account a = getAccountByName("Common Stock", userId);
			Journal j = new Journal(date, "Retained Earnings", "", 0, total);
			journalAndLedgerEntry(j, a);
		}
		
	}
	
	
	public void yearEndBalances(String userId) {
		LocalDate date = LocalDate.now();
		for(Account a :getAccountsOfUser(userId)) {
			
			double balance = a.getBalance();
			if(a.getType()==AccountType.ASSET || a.getType()==AccountType.EXPENSE) {
				if(balance>0) {
					Journal j = new Journal(date, "Opening Balances", "New Financial Year", balance, 0);
					journalAndLedgerEntry(j, a);
				}
				else if(balance<0){
					balance=0-balance;
					Journal j = new Journal(date, "Opening Balances", "New Financial Year", 0,balance);
					journalAndLedgerEntry(j, a);
				}
			}else {
				if(balance<0) {
					balance=0-balance;
					Journal j = new Journal(date, "Opening Balances", "New Financial Year", balance, 0);
					journalAndLedgerEntry(j, a);
				}
				else if(balance>0){
					
					Journal j = new Journal(date, "Opening Balances", "New Financial Year", 0,balance);
					journalAndLedgerEntry(j, a);
				}
			}
			
		}

	}


}
