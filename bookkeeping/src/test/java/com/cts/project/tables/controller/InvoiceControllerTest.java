package com.cts.project.tables.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.cts.project.bookkeeping.controller.InvoiceController;
import com.cts.project.bookkeeping.entities.Invoice;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.model.InvoiceModel;
import com.cts.project.bookkeeping.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@AutoConfigureMockMvc
class InvoiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private InvoiceService iS;
	
	@InjectMocks
	InvoiceController iC;
	
	private InvoiceModel im;
	private Invoice i;
	private Users u;
	@BeforeEach
	void setUp() throws Exception {
		
		im = new InvoiceModel("u1", "i1", LocalDate.now(), LocalDate.now(), "company", "customer", 2.0, 1000, 10, false);
		i = new Invoice(im.getInvoiceId(), u, im.getInvoiceDate(), im.getDueDate(), im.getBilledBy(), im.getBilledTo(), im.getTaxPercentage(), im.getTotalAmount(),false);
		
	}

	@Test
	void testAddInvoice() throws Exception {
		when(iS.addInvoice(any(InvoiceModel.class))).thenReturn(i);
		String exp = objectMapper.writeValueAsString(i);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create/invoice")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(im))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	
	@Test
	void testGetInvoicesOfUser() throws Exception {
		when(iS.getInvoicesofUser(anyString())).thenReturn(List.of(i));
		String exp = objectMapper.writeValueAsString(List.of(i));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/invoices")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetInvoicesById() throws Exception {
		when(iS.getInvoiceById("i1", "u1")).thenReturn(i);
		String exp = objectMapper.writeValueAsString(i);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/invoices/i1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}

	
	

	@Test
	void testGetInvoicesReminderForUser() throws Exception {
		when(iS.invoiceRemaiderForUser("u1")).thenReturn(List.of(i));
		String exp = objectMapper.writeValueAsString(List.of(i));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/invoice/reminder")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testUpdateJournalWhenInvoicePaid() throws Exception {
		
		String exp = "Invoice paid, Journal updated";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/user/u1/invoice/i1/paid")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		verify(iS,times(1)).updateJournalWhenInvoicePaid("i1", "u1");
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testDeleteInvoice() throws Exception {
		String exp = "Invoice Deleted";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/delete/user/u1/invoice/i1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(iS,times(1)).deleteInvoice("i1", "u1");
		
		MockHttpServletResponse response = result.getResponse();
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
}
