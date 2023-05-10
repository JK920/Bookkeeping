package com.cts.project.bookkeeping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.entities.Vendors;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.exception.VendorNotFoundException;
import com.cts.project.bookkeeping.model.VendorModel;
import com.cts.project.bookkeeping.repository.VendorsRepository;

import jakarta.transaction.Transactional;

@Service
public class VendorsService {

	@Autowired
	VendorsRepository vRepo;
	
	@Autowired
	UsersService uService;
	
	
	@Transactional
	public Vendors createVendor(VendorModel vendor) throws UserNotFoundException{
			Users user = uService.getUserById(vendor.getUserId());
			//String name, String address, String phoneNo, String email
			Vendors ven = new Vendors(user, vendor.getName(),vendor.getAddress(),vendor.getPhoneNo(),vendor.getEmail());
			ven = vRepo.save(ven);

		return ven;
	}

	//read
	@Transactional
	public List<Vendors> getVendorsOfUser(String userId) throws UserNotFoundException{

		Users user = uService.getUserById(userId);
		return user.getVendorList();
	}
	
	@Transactional
	public Vendors getVendorById(String venId, String userId) throws VendorNotFoundException{
		List<Vendors> vendorList = getVendorsOfUser(userId);
		Vendors vendor = null;
		for(Vendors v:vendorList) {
			if(v.getVendorId().equals(venId)) {
				vendor = v;
			}
		}
		if(vendor!=null) {
			return vendor;
		}
		else {
			throw new VendorNotFoundException("Vendor Not Found!!");
		}
	}
	
	@Transactional
	public Vendors getVendorByName(String name, String userId) throws VendorNotFoundException{
		List<Vendors> vendorList = getVendorsOfUser(userId);
		Vendors vendor = null;
		for(Vendors v:vendorList) {
			if(v.getName().equals(name)) {
				vendor = v;
			}
		}
		if(vendor!=null) {
			return vendor;
		}
		else {
			throw new VendorNotFoundException("Vendor Not Found!!");
		}
	}
	//update
	@Transactional
	public Vendors updateVendorDetails(VendorModel vendor, String id) throws VendorNotFoundException{
		Vendors ven = getVendorById(id, vendor.getUserId());
		ven.setName(vendor.getName());
		ven.setEmail(vendor.getEmail());
		ven.setPhoneNo(vendor.getPhoneNo());
		ven.setAddress(vendor.getAddress());
		ven = vRepo.save(ven);

		return ven;
	}
	//delete
	@Transactional
	public String deleteVendorsById(String venId, String userId) throws VendorNotFoundException{
		String res="";
		Vendors v = getVendorById(venId, userId);
		String name=v.getName();
		
		if(v.getJournalList()==null) {
			vRepo.delete(v);
			res= "Vendor "+name+" deleted";
		}
		else {
			res= "Vendor is associated with journals";
		}
		return res;
	}
}
