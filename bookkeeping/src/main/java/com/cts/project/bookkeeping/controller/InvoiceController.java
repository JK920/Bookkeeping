package com.cts.project.bookkeeping.controller;

import java.util.List;

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

import com.cts.project.bookkeeping.entities.Invoice;
import com.cts.project.bookkeeping.exception.InvoiceNotFoundException;
import com.cts.project.bookkeeping.model.InvoiceModel;
import com.cts.project.bookkeeping.service.InvoiceService;

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class InvoiceController {

	@Autowired
	InvoiceService iService;
	
	@PostMapping(value="/create/invoice")
	public ResponseEntity<Object> addInvoice(@Valid @RequestBody InvoiceModel invoice) {
		Invoice inv = iService.addInvoice(invoice);
		return new ResponseEntity<>(inv,HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/user/{userId}/invoices")
	public List<Invoice> getInvoicesofUser(@PathVariable("userId") String userId){
		return iService.getInvoicesofUser(userId);
	}
	
	@GetMapping(value = "/get/user/{userId}/invoices/{invId}")
	public Invoice getInvoiceById(@PathVariable("invId") String invId, @PathVariable("userId") String userId) throws InvoiceNotFoundException{
		return iService.getInvoiceById(invId, userId);
	}
	
	@GetMapping(value = "/get/user/{userId}/invoice/reminder")
	public List<Invoice> getRemainderForDues( @PathVariable("userId") String userId){
		return iService.invoiceRemaiderForUser(userId);
	}
	@PutMapping(value="/update/user/{userId}/invoice/{invoiceId}/paid")
	public ResponseEntity<Object> updatePaidInvocie(@PathVariable("invoiceId") String invoiceId, @PathVariable("userId")String userId){
		iService.updateJournalWhenInvoicePaid(invoiceId, userId);
		return new ResponseEntity<> ("Invoice paid, Journal updated", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/user/{userId}/invoice/{invId}")
	public  ResponseEntity<Object> deleteInvoice(@PathVariable("invId") String invId, @PathVariable("userId") String userId) {
		iService.deleteInvoice(invId, userId);
		return new ResponseEntity<>("Invoice Deleted",HttpStatus.OK);
	}
	
	
}
