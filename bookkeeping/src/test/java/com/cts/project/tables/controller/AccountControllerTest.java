package com.cts.project.tables.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cts.project.bookkeeping.TablesApplication;
import com.cts.project.bookkeeping.controller.AccountController;
import com.cts.project.bookkeeping.entities.Account;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.model.AccountLedger;
import com.cts.project.bookkeeping.model.AccountModel;
import com.cts.project.bookkeeping.model.AccountType;
import com.cts.project.bookkeeping.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private AccountService aS;
	
	@InjectMocks
	AccountController aC;
	
	private AccountModel am;
	private Account a;
	private Account a1;
	private Users u;
	private List<Account> aL = new ArrayList<>();
	
	@BeforeEach
	void setUp() {
		u = new Users("User", "username", "pass", "email", "comapany");
		am = new AccountModel("u1", "account", AccountType.ASSET, 0);
		a = new Account("account", AccountType.ASSET, 0);
		a1= new Account("a1",AccountType.LIABILITY,0);
		aL.add(a);
		aL.add(a1);
		u.setAccountList(aL);
		
	}


	@Test
	void testCreateAccount() throws Exception {
		
		when(aS.createAccount(any(AccountModel.class))).thenReturn(a);
		String exp = objectMapper.writeValueAsString(a);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create/account")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(am))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);

	}
	
	@Test
	void testGetAccountsOfUsers() throws Exception {

		when(aS.getAccountsOfUser("u1")).thenReturn(aL);
		String exp = objectMapper.writeValueAsString(aL);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/accounts")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testGetAccountsByType() throws Exception {

		when(aS.getAccountByType(AccountType.ASSET, "u1")).thenReturn(List.of(a));
		String exp = objectMapper.writeValueAsString(List.of(a));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/account/ASSET")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testGetAccountById() throws Exception {

		when(aS.getAccountById("a", "u1")).thenReturn(a);
		String exp = objectMapper.writeValueAsString(a);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/accountsbyid/a")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testGetAccountByName() throws Exception {

		when(aS.getAccountByName("account", "u1")).thenReturn(a);
		String exp = objectMapper.writeValueAsString(a);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/accountsbyname/account")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testUpdateAccount() throws Exception {
		a.setAccountName("a");
		when(aS.updateAccount("a","account", "u1")).thenReturn(a);
		String exp = objectMapper.writeValueAsString(a);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/users/u1/accounts/account/a")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testDeleteAccount() throws Exception {
		String exp = "Account Deleted";
		when(aS.deleteAccountById("a1", "u1")).thenReturn(exp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/delete/users/u1/accounts/a1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	


	@Test
	void testTotalBalanceByAccountType() throws Exception {
		String exp = "500.0";
		when(aS.totalBalanceByAccountType(AccountType.ASSET, "u1")).thenReturn(500.0);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/accountbalance/ASSET")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	
	@Test
	void testGenerateLedger() throws Exception {
		
		Map<String,List<AccountLedger>> map = new HashMap<>();
		map.put("Account", List.of(new AccountLedger()));
		String exp = objectMapper.writeValueAsString(map);
		when(aS.generateLedger("u1")).thenReturn(map);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/ledger")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testGenerateIncome() throws Exception {
		String exp = "500.0";
		when(aS.generateIncome("u1")).thenReturn(500.0);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1/income")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);

	}
	
	@Test
	void testUpdateOpeningBalance() throws Exception{
		String exp = "Opening Balances Updated For User";
		Map<String,Double> map = new HashMap<>();
		map.put("a1", 500.0);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/users/u1/openingbalances")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		verify(aS,times(1)).updateOpeningBalance(anyString(), anyMap());
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);
	}
	
	@Test
	void testNewYearBalance() throws Exception{
		String exp = "Opening Balances Updated in journal for new Financial Year";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/users/u1/newyearbalances")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String act = response.getContentAsString();
		verify(aS,times(1)).yearEndBalances("u1");
		assertEquals(200, response.getStatus());
		assertEquals(exp, act);
	}
}
