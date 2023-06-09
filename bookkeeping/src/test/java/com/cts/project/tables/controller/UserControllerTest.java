package com.cts.project.tables.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

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

import com.cts.project.bookkeeping.TablesApplication;
import com.cts.project.bookkeeping.controller.UsersController;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.model.LoginModel;
import com.cts.project.bookkeeping.model.PasswordChange;
import com.cts.project.bookkeeping.model.UserModel;
import com.cts.project.bookkeeping.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

	
	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private UsersService uS;
	
	@InjectMocks
	UsersController uC;
	
	private UserModel um;
	private Users u;
	
	@BeforeEach
	void setUp() throws Exception {
		um = new UserModel("User", "username", "pass", "user@email.com", "comapany");
		u = new Users(um.getName(), um.getUsername(), um.getPassword(), um.getEmail(), um.getCompanyName());
	}

	@Test
	void testRegisterUser() throws Exception {
		
		when(uS.registerUser(any(UserModel.class))).thenReturn(u);
		String exp = objectMapper.writeValueAsString(u);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create/user")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(um))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testLoginUser() throws Exception {
		String exp = objectMapper.writeValueAsString(u);
		LoginModel l = new LoginModel("username","pass");
		when(uS.login(any(LoginModel.class))).thenReturn(u);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/login/user")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(l))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetUserById() throws Exception {
		
		when(uS.getUserById("u1")).thenReturn(u);
		String exp = objectMapper.writeValueAsString(u);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users/u1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetAllUsers() throws Exception {
		
		when(uS.getAllUsers()).thenReturn(List.of(u));
		String exp = objectMapper.writeValueAsString(List.of(u));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	
	
	@Test
	void testUpdateUser() throws Exception {
		
		when(uS.updateUserById(any(UserModel.class),anyString())).thenReturn(u);
		String exp = objectMapper.writeValueAsString(u);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/user/u1")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(um))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testUpdatePassword() throws Exception {
		PasswordChange p = new PasswordChange("pass", "password");
		String exp = "Password Changed";
		when(uS.updatePassword(anyString(),any(PasswordChange.class))).thenReturn(exp);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/user/u1/password")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(p))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
}
