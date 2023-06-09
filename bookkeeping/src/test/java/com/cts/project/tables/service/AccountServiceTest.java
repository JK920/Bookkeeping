package com.cts.project.tables.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.cts.project.bookkeeping.TablesApplication;
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
import com.cts.project.bookkeeping.service.AccountService;
import com.cts.project.bookkeeping.service.UsersService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@DirtiesContext
class AccountServiceTest {


	@Mock
	AccountRepository aRepo;
	@Mock
	JournalRepository jRepo;
	@Mock
	LedgerRepository lRepo;
	
	@Mock
	UsersService uS;
	
	@InjectMocks
	AccountService aService;
	
	private AccountModel acc;
	private Account a;
	private Account cash;
	private Account payable;
	private Account sales;
	private Account cogs;
	private Account commonStock;
	private Journal j;
	private List<Account> accL;
	private Users user;
	
	@BeforeEach
	public void setUp() {
		
		acc = new AccountModel("u1", "dummy", AccountType.ASSET, 50);
		a = new Account(acc.getAccountName(), acc.getType(), acc.getBalance());
		a.setAccountId("a1");
		
		cash = new Account("Cash", AccountType.ASSET, 0.0);
		cash.setAccountId("1");
		payable = new Account("Accounts Payable",AccountType.LIABILITY,0);
		payable.setAccountId("2");
		sales = new Account("Sales Revenue",AccountType.REVENUE,0);
		sales.setAccountId("3");
		cogs = new Account("Cost Of Goods Sold",AccountType.EXPENSE,0);
		cogs.setAccountId("4");
		commonStock = new Account("Common Stock", AccountType.EQUITY,0);
		commonStock.setAccountId("5");
		
		j= new Journal(LocalDate.now(), "reference","description",5000, 0);
		accL=List.of(cash,payable,sales,cogs,commonStock);
		user = new Users("John", "john123", "password", "john@gmail.com", "New Company");
		user.setUserId("u1");
		user.setAccountList(accL);
	}
	
	@Test
	 void testCreateAccount() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.save(any(Account.class))).thenReturn(a);
		Account dummy = aService.createAccount(acc);
		assertEquals(acc.getAccountName(), dummy.getAccountName());
		
	}
	
	@Test
	 void testCreateAccount_UserNotFound() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class,() -> aService.createAccount(acc));
	}
	
	@Test
	 void testGetAccountsOfUsers_success() throws UserNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		List<Account> list = aService.getAccountsOfUser("u1");
		assertIterableEquals(accL, list);
	}
	
	@Test
	 void testGetAccountsOfUsers_UserNotFound() throws UserNotFoundException{
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class,() -> aService.getAccountsOfUser("u1"));
	}
	
	@Test
	 void testGetAccountById_Success() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		Account acc = aService.getAccountById("1","u1");
		assertEquals(cash, acc);
	}
	
	@Test
	 void testGetAccountById_AccountNotFound() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(AccountNotFoundException.class,() -> aService.getAccountById("6","u1"));
		
	}
	
	@Test
	 void testGetAccountByName_Success() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		Account acc = aService.getAccountByName("Cash","u1");
		assertEquals(cash, acc);
	}
	
	@Test
	 void testGetAccountByName_AccountNotFound() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(AccountNotFoundException.class,() -> aService.getAccountByName("Receivables","u1"));
		
	}
	
	@Test
	 void testGetAccountByType_asset() throws AccountNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(cash.getAccountName(), aService.getAccountByType(AccountType.ASSET, "u1").get(0).getAccountName());
	}
	
	@Test
	 void testGetAccountByType_AccountNorFound() throws AccountNotFoundException{
		accL = List.of(cash,payable);
		user.setAccountList(accL);
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(AccountNotFoundException.class,() -> aService.getAccountByType(AccountType.EQUITY,"u1"));
		
	}
	
	@Test
	 void testUpdateAccount() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.save(any(Account.class))).thenReturn(cash);
		Account a1 = aService.updateAccount("Dummy1","1", "u1");
		assertEquals("Dummy1", a1.getAccountName());
	}
	
	@Test
	 void testUpdateAccount_AccountNotFound() throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.save(any(Account.class))).thenReturn(cash);
		assertThrows(AccountNotFoundException.class,() -> aService.updateAccount("Dummy1","a1", "u1"));
	}
	
	@Test
	 void testDeleteAccount_deleted()throws AccountNotFoundException{
		cash.setJournalList(List.of());
		when(uS.getUserById("u1")).thenReturn(user);
		String res = aService.deleteAccountById("1","u1");
		assertEquals("Account Cash deleted.", res);
	}
	
	@Test
	 void testDeleteAccount_CaanotBeDeleted()throws AccountNotFoundException{
		j.setAccount(cash);
		cash.setJournalList(List.of(j));
		when(uS.getUserById("u1")).thenReturn(user);
		String res = aService.deleteAccountById("1","u1");
		assertEquals("Account cannot be deleted. It is associated with Journal entries.", res);
	}
	
	@Test
	 void testDeleteAccount_AccountNotFound()throws AccountNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(AccountNotFoundException.class,() ->  aService.deleteAccountById("a1","u1"));
		
	}
	
	@Test
	 void testUpdateBalance_asset() {
		j.setAccount(cash);
		assertEquals(5000, aService.updateAccountBalance(j), 0.01);
		
	}
	
	@Test
	 void testUpdateBalance_revenue() {
		j.setAccount(sales);
		assertEquals(-5000, aService.updateAccountBalance(j), 0.01);
		
	}
	
	@Test
	 void testUpdateBalanceForJournalDeletion_asset() {
		cash.setBalance(5000);
		j.setAccount(cash);
		assertEquals(0, aService.updateAccountBalanceForJournalDeletion(j), 0.01);
		
	}
	
	@Test
	 void testUpdateBalanceForJournalDeletion_revenue() {
		sales.setBalance(-5000);
		j.setAccount(sales);
		assertEquals(0, aService.updateAccountBalanceForJournalDeletion(j), 0.01);
		
	}

	@Test
	 void testTotalBalanceByAccountType_asset() {
		cash.setBalance(200);
		when(uS.getUserById("u1")).thenReturn(user);
		double actual = aService.totalBalanceByAccountType(AccountType.ASSET, "u1");
		assertEquals(200, actual,0.01);
	}
	
	@Test
	 void testGenerateIncome() {
		sales.setBalance(5000);
		cogs.setBalance(2000);
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(3000, aService.generateIncome("u1"),0.01);
	}
	
	@Test
	 void testUpdateOpeningBalances_AssetPositiveBalance() {
		Map<String,Double> map = new HashMap<>();
		map.put("1", 5000.0);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.updateOpeningBalance("u1", map);
		assertEquals(5000, cash.getBalance(),0.1);
		verify(lRepo,times(2)).saveAndFlush(any(Ledger.class));
	}
	
	@Test
	 void testGenerateLedger() {
		user.setAccountList(List.of(cash));
		cash.setJournalList(List.of(j));
		Ledger l = new Ledger();
		l.setBalance(5000);
		j.setLedger(l);
		Map<String,List<AccountLedger>> map = new HashMap<>();
		AccountLedger aL = new AccountLedger(j.getDate(),j.getReference(),j.getDescription(),j.getDebit(),j.getCredit(),l.getBalance());
		map.put("Cash", List.of(aL));
		when(uS.getUserById("u1")).thenReturn(user);
		List<AccountLedger> a = aService.generateLedger("u1").get("Cash");
		assertEquals(aL.getBalance(),a.get(0).getBalance());
	}
	
	@Test
	 void testUpdateOpeningBalances_RevenuePositiveBalance() {
		Map<String,Double> map = new HashMap<>();
		map.put("3", 5000.0);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.updateOpeningBalance("u1", map);
		assertEquals(-5000, sales.getBalance(),0.1);
		verify(lRepo,times(2)).saveAndFlush(any(Ledger.class));
	}
	
	@Test
	 void testUpdateOpeningBalances_AssetNegativeBalance() {
		Map<String,Double> map = new HashMap<>();
		map.put("1", -5000.0);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.updateOpeningBalance("u1", map);
		assertEquals(-5000, cash.getBalance(),0.1);
		verify(lRepo,times(2)).saveAndFlush(any(Ledger.class));
	}
	
	@Test
	 void testUpdateOpeningBalances_RevenueNegativeBalance() {
		Map<String,Double> map = new HashMap<>();
		map.put("3", -5000.0);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.updateOpeningBalance("u1", map);
		assertEquals(5000, sales.getBalance(),0.1);
		verify(lRepo,times(2)).saveAndFlush(any(Ledger.class));
	}
	
	@Test
	 void testUpdateOpeningBalances_ZeroBalance() {
		Map<String,Double> map = new HashMap<>();
		map.put("3", 0.0);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.updateOpeningBalance("u1", map);
		assertEquals(0, sales.getBalance(),0.1);
		verify(lRepo,times(0)).saveAndFlush(any(Ledger.class));
	}
	
	@Test
	 void testYearEndBalances() {
		cash.setBalance(500);
		sales.setBalance(500);
		cogs.setBalance(-500);
		payable.setBalance(-501);
		commonStock.setBalance(-1);
		when(uS.getUserById("u1")).thenReturn(user);
		when(aRepo.saveAndFlush(any(Account.class))).thenReturn(cash);
		when(jRepo.saveAndFlush(any(Journal.class))).thenReturn(j);
		aService.yearEndBalances("u1");
		verify(lRepo,times(5)).saveAndFlush(any(Ledger.class));
		
	}
	
	
}

