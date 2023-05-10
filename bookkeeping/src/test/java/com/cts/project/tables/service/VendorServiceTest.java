package com.cts.project.tables.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.cts.project.bookkeeping.TablesApplication;
import com.cts.project.bookkeeping.entities.Vendors;
import com.cts.project.bookkeeping.entities.Journal;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.VendorNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.VendorModel;
import com.cts.project.bookkeeping.repository.VendorsRepository;
import com.cts.project.bookkeeping.service.VendorsService;
import com.cts.project.bookkeeping.service.UsersService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@DirtiesContext
class VendorServiceTest {

	@Mock
	VendorsRepository vR;

	@Mock
	UsersService uS;
	
	@InjectMocks
	VendorsService vS;
	
	private Users user;
	private VendorModel vm;
	private Vendors v;
	private Vendors v1;
	private Vendors v2;
	private List<Vendors> vList = new ArrayList<>();
	Journal j;
	
	@BeforeEach
	public void setup() {
		user = new Users("John", "john123", "password", "john@gmail.com", "New Company");
		user.setUserId("u1");
		vm = new VendorModel("u1", "Sam", "Cochin", "987654321","sam@email.com");
		v = new Vendors(user,vm.getName(),vm.getAddress(),vm.getPhoneNo(),vm.getEmail());
		v.setVendorId("v");
		v1 = new Vendors(user,"Vendor1", "address1", "9988776655", "v1@email.com");
		v1.setVendorId("v1");
		v2 = new Vendors(user,"Vendor2", "address2", "9988776655", "v2@email.com");
		v2.setVendorId("v2");
		vList = List.of(v,v1,v2);
		user.setVendorList(vList);
		j= new Journal();
	}
	
	@Test
	 void testCreateVendor() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		when(vR.save(any(Vendors.class))).thenReturn(v);
		Vendors Vendor = vS.createVendor(vm);
		assertEquals(v, Vendor);
		
	}
	
	@Test
	 void testCreateVendor_UserNotFound() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, ()-> vS.createVendor(vm));
		
	}
	
	@Test
	 void testGetVendorsOfUsers() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		assertIterableEquals(vList, vS.getVendorsOfUser("u1"));
	}
	
	@Test
	 void testGetVendorsOfUsers_UserNotFound() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class,()-> vS.getVendorsOfUser("u1"));
	}
	
	@Test
	void testGetVendorById()throws VendorNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(v1, vS.getVendorById("v1", "u1"));
	}
	@Test
	void testGetVendorById_VendorNotFound()throws VendorNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(VendorNotFoundException.class,()-> vS.getVendorById("v0", "u1"));
	}
	
	@Test
	void testGetVendorByName()throws VendorNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(v1, vS.getVendorByName("Vendor1", "u1"));
	}
	
	@Test
	void testGetVendorByName_VendorNotFound()throws VendorNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(VendorNotFoundException.class,()-> vS.getVendorByName("Vendor", "u1"));
	}
	
	@Test
	void testUpdateVendorDetails() throws VendorNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		when(vR.save(any(Vendors.class))).thenReturn(v);
		assertEquals(v, vS.updateVendorDetails(vm, "v"));
	}
	
	@Test
	void testUpdateVendorDetails_VendorNotFound() throws VendorNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(VendorNotFoundException.class,()-> vS.updateVendorDetails(vm, "v3"));
	}
	
	@Test
	void testDeleteVendorsById_success() throws VendorNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals("Vendor Sam deleted", vS.deleteVendorsById("v", "u1"));
	}
	
	@Test
	void testDeleteVendorsById_failed() throws VendorNotFoundException{
		v.setJournalList(List.of(j));
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals("Vendor is associated with journals", vS.deleteVendorsById("v", "u1"));
	}
	
	@Test
	void testDeleteVendorsById_VendorNotFound() throws VendorNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(VendorNotFoundException.class,()-> vS.deleteVendorsById("v3", "u1"));
	}
	
}
