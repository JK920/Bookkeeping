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

import com.cts.project.bookkeeping.entities.Vendors;
import com.cts.project.bookkeeping.exception.VendorNotFoundException;
import com.cts.project.bookkeeping.model.VendorModel;
import com.cts.project.bookkeeping.service.VendorsService;

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class VendorsController {

	@Autowired
	VendorsService vService;
	
	@PostMapping(value = "/create/vendor")
	public ResponseEntity<Object> createVendor(@Valid @RequestBody VendorModel vendor) {
		Vendors ven = vService.createVendor(vendor);
		return new ResponseEntity<>(ven, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/get/user/{userId}/vendors")
	public List<Vendors> getVendorsOfUser(@PathVariable("userId") String userId){
		return vService.getVendorsOfUser(userId);
	}
	@GetMapping(value = "/get/user/{userId}/vendorsbyid/{venId}")
	public Vendors getVendorById(@PathVariable("venId") String venId,@PathVariable("userId") String userId) throws VendorNotFoundException{
		return vService.getVendorById(venId, userId);
	}
	
	@GetMapping(value = "/get/user/{userId}/vendorsbyname/{name}")
	public Vendors getVendorByName(@PathVariable("name") String name,@PathVariable("userId") String userId) throws VendorNotFoundException{
		return vService.getVendorByName(name, userId);
	}
	
	@PutMapping(value = "update/user/vendors/{venId}")
	public ResponseEntity<Object> updateVendorDetails(@Valid @RequestBody VendorModel vendor,@PathVariable("venId") String venId) {
		Vendors ven = vService.updateVendorDetails(vendor, venId);
		return new ResponseEntity<>(ven, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "delete/user/{userId}/vendors/{venId}")
	public String deleteVendorsById(@PathVariable("venId") String venId,@PathVariable("userId") String userId){
		return vService.deleteVendorsById(venId, userId);
		
	}
}
