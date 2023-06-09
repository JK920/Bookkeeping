package com.cts.project.bookkeeping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.bookkeeping.entities.Account;
import com.cts.project.bookkeeping.entities.Customers;
import com.cts.project.bookkeeping.entities.Invoice;
import com.cts.project.bookkeeping.entities.Journal;
import com.cts.project.bookkeeping.entities.Ledger;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.entities.Vendors;
import com.cts.project.bookkeeping.exception.InvoiceNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.InvoiceModel;
import com.cts.project.bookkeeping.repository.InvoiceRepository;
import com.cts.project.bookkeeping.repository.JournalRepository;
import com.cts.project.bookkeeping.repository.LedgerRepository;

import jakarta.transaction.Transactional;


@Service
public class InvoiceService {

	@Autowired
	UsersService uService;
	@Autowired
	AccountService aService;
	@Autowired
	CustomersService cService;
	@Autowired
	VendorsService vService;
	
	@Autowired
	InvoiceRepository iRepo;
	@Autowired
	JournalRepository jRepo;
	@Autowired
	LedgerRepository lRepo;
	
	
	public Invoice addInvoice(InvoiceModel invoice) throws RuntimeException {
		Invoice inv = null;

		String userId =invoice.getUserId();
		Users user = uService.getUserById(userId);
		String billedBy = invoice.getBilledBy();
		String billedTo = invoice.getBilledTo();
		LocalDate date = invoice.getInvoiceDate();
		double tax = invoice.getTaxPercentage();
		double totalAmt = invoice.getTotalAmount();
		boolean paid = invoice.isPaid();
		
		inv = new Invoice(invoice.getInvoiceId(),user,invoice.getInvoiceDate(),invoice.getDueDate(),billedBy,billedTo,
				tax,totalAmt,paid);
		
		String reference = "";
		String desc="";
		// when product is sold or bought inventory is affected
		Account inventory = aService.getAccountByName("Inventory", userId);
		
		List<Journal> journalList = new ArrayList<>();
		//find amount without tax
		double amt = Math.round((totalAmt*100/(100+tax))*100)/(double)100;
		double taxAmt =Math.round((totalAmt - amt)*100)/(double)100;
		
		//invoice generated for item sold
		if(billedBy.equals(user.getCompanyName())) {
			
			Customers c = cService.getCustomerByName(billedTo, userId);
			inv.setBilledTo(c.getCustomerId());
			inv = iRepo.saveAndFlush(inv);
			
			//affected when product is sold
			Account salesRev = aService.getAccountByName("Sales Revenue", userId);
			Account cogs = aService.getAccountByName("Cost Of Goods Sold", userId);
			
			reference = "Product sold";
			desc="Payment to be recived from "+billedTo+" for invoice "+invoice.getInvoiceId()+".";
			
			//if invoice is paid on generation
			if(inv.isPaid()) {
				desc = "Recieved cash from "+billedTo+" for invoice "+invoice.getInvoiceId()+" on "+LocalDate.now()+".";
				//affected when invoice is paid
				Account cash = aService.getAccountByName("Cash", userId);//debit
				//sales recording in journal
				
				//cash account 
				Journal cashJ = new Journal(date,reference ,desc,totalAmt, 0);
				cashJ.setAccount(cash);
				cashJ.setCustomer(c);
				cashJ.setUser(user);
				journalList.add(cashJ);
			}
			else {
				//if not paid receivable account is affected
				Account receivable = aService.getAccountByName("Accounts Receivable", userId);
				Journal recJ = new Journal(date,reference ,desc,totalAmt, 0);
				recJ.setAccount(receivable);
				recJ.setCustomer(c);
				recJ.setUser(user);
				journalList.add(recJ);
				
			}
			//tax or no tax
			if(tax==0) {
				//tax = 0 => journal is updated for sales revenue account
				Journal saleJ = new Journal(date, reference, desc, 0, totalAmt);
				saleJ.setAccount(salesRev);
				saleJ.setCustomer(c);
				saleJ.setUser(user);
				journalList.add(saleJ);
			}
			else {
				
				//journal is updated for sales revenue account and sales tax account
				//sales revenue
				Journal saleJ = new Journal(date, reference, desc, 0, amt);
				saleJ.setAccount(salesRev);
				saleJ.setCustomer(c);
				saleJ.setUser(user);
				journalList.add(saleJ);
				
				//sales tax affected when there is tax
				Account saleTax = aService.getAccountByName("Sales Tax Payable", userId);//credit
				Journal taxJ = new Journal(date, reference, desc, 0, taxAmt);
				taxJ.setAccount(saleTax);
				taxJ.setCustomer(c);
				taxJ.setUser(user);
				journalList.add(taxJ);
				
			}
			double costPrice = invoice.getCostPrice();
			
			Journal cogsJ = new Journal(date, reference, desc, costPrice,0);
			cogsJ.setAccount(cogs);
			cogsJ.setCustomer(c);
			cogsJ.setUser(user);
			journalList.add(cogsJ);
			
			Journal inventoryJ = new Journal(date, reference, desc, 0, costPrice);
			inventoryJ.setAccount(inventory);
			inventoryJ.setCustomer(c);
			inventoryJ.setUser(user);
			journalList.add(inventoryJ);

		}
		else if(billedTo.equals(user.getCompanyName())){
			
			Vendors v = vService.getVendorByName(billedBy, userId);
			inv.setBilledBy(v.getVendorId());
			inv = iRepo.saveAndFlush(inv);
			reference = "Product bought";
			desc = "Cash to be payed to "+billedBy+" for invoice "+invoice.getInvoiceId()+".";
			
			Journal inventoryJ = new Journal(date, reference, desc, amt, 0);
			inventoryJ.setAccount(inventory);
			inventoryJ.setVendor(v);
			inventoryJ.setUser(user);
			journalList.add(inventoryJ);
			
			if(tax!=0) {
				//tax account
				Account saleTax = aService.getAccountByName("Sales Tax Payable", userId);
				Journal taxJ = new Journal(date, reference, desc,taxAmt,0);
				taxJ.setAccount(saleTax);
				taxJ.setVendor(v);
				taxJ.setUser(user);
				journalList.add(taxJ);
			}
			
			if(inv.isPaid()) {
				//cash account 
				desc = "Cash payed to "+billedBy+" for invoice "+invoice.getInvoiceId()+" on "+LocalDate.now()+".";
				
				Account cash = aService.getAccountByName("Cash", userId);
				
				//purchase recording in journal
				Journal cashJ = new Journal(date,reference ,desc,0,totalAmt);
				cashJ.setAccount(cash);
				cashJ.setVendor(v);
				cashJ.setUser(user);
				journalList.add(cashJ);
				
			}
			else {
				
				Account payable = aService.getAccountByName("Accounts Payable", userId);
				Journal payableJ = new Journal(date, reference, desc, 0, totalAmt);
				payableJ.setAccount(payable);
				payableJ.setVendor(v);
				payableJ.setUser(user);
				journalList.add(payableJ);
			}
				
		}
		//journal updation
		for(Journal j : journalList) {
			j.setInvoice(inv);
			j=jRepo.saveAndFlush(j);
			//account balance update
			double bal = aService.updateAccountBalance(j);
			//ledger update
			Ledger l = new Ledger();
			l.setAccount(j.getAccount());
			l.setBalance(bal);
			l.setJournal(j);
			lRepo.saveAndFlush(l);

		}
		return inv;
	}
	
	
	public void updateJournalWhenInvoicePaid(String invoiceId, String userId) throws RuntimeException {
		

			Invoice inv = getInvoiceById(invoiceId, userId);
			inv.setPaid(true);
			inv = iRepo.save(inv);
			Users user = uService.getUserById(userId);
			String billedBy = inv.getBilledBy();
			String billedTo = inv.getBilledTo();
			double totalAmt = inv.getTotalAmount();
			LocalDate date = LocalDate.now();
			String desc="";
			String reference="";
			List<Journal> journalList = new ArrayList<>();
			Account cash = aService.getAccountByName("Cash", userId);
			if(billedBy.equals(user.getCompanyName())) {
				Customers c = cService.getCustomerByName(billedTo, userId);
				desc = "Recieved cash from "+billedTo+" for invoice "+invoiceId+" on "+date+".";
				reference="Paid by customer";
				//affected when invoice is paid
				//sales recording in journal
				
				//cash account 
				Journal cashJ = new Journal(date,reference ,desc,totalAmt, 0);
				cashJ.setAccount(cash);
				cashJ.setCustomer(c);
				cashJ.setUser(user);
				journalList.add(cashJ);
				
				Account receivable = aService.getAccountByName("Accounts Receivable", userId);
				Journal recJ = new Journal(date,reference ,desc,0,totalAmt);
				recJ.setAccount(receivable);
				recJ.setCustomer(c);
				recJ.setUser(user);
				journalList.add(recJ);
			}
			else if(billedTo.equals(user.getCompanyName())){
				
				Vendors v = vService.getVendorByName(billedBy, userId);
				reference = "Product bought";
				desc = "Cash payed to "+billedBy+" for invoice "+invoiceId+" on "+date+".";
				
				Account payable = aService.getAccountByName("Accounts Payable", userId);
				Journal payableJ = new Journal(date, reference, desc, totalAmt,0);
				payableJ.setAccount(payable);
				payableJ.setVendor(v);
				payableJ.setUser(user);
				journalList.add(payableJ);
				
				Journal cashJ = new Journal(date,reference ,desc,0,totalAmt);
				cashJ.setAccount(cash);
				cashJ.setVendor(v);
				cashJ.setUser(user);
				journalList.add(cashJ);
			}
			for(Journal j : journalList) {
				j.setInvoice(inv);
				j=jRepo.saveAndFlush(j);
				//account balance update
				double bal = aService.updateAccountBalance(j);
				//ledger update
				Ledger l = new Ledger();
				l.setAccount(j.getAccount());
				l.setBalance(bal);
				l.setJournal(j);
				lRepo.saveAndFlush(l);
			}

				
	}
	
	public List<Invoice> invoiceRemaiderForUser(String userId) {
		LocalDate date = LocalDate.now().minusDays(2);
		List<Invoice> invoiceList = getInvoicesofUser(userId);
		invoiceList.removeIf(Invoice::isPaid);
		invoiceList.removeIf(invoice -> invoice.getDueDate().isBefore(date));
		return invoiceList;
	}
	
	@Transactional
	public List<Invoice> getInvoicesofUser(String userId) throws UserNotFoundException{
		
		Users user = uService.getUserById(userId);
		return user.getInvoiceList();
	}
	
	@Transactional
	public Invoice getInvoiceById(String id, String userId) throws InvoiceNotFoundException{
		List<Invoice> invoiceList = getInvoicesofUser(userId);
		Invoice inv = null;
		for(Invoice invoice : invoiceList) {
			if(invoice.getInvoiceId().equals(id)) {
				inv = invoice;
			}
		}
		if(inv==null) {
			throw new InvoiceNotFoundException("Invoice not found!!");
		}
		else {
			return inv;
		}
	}
	
	
	@Transactional
	public void deleteInvoice(String id, String userId) throws RuntimeException {

		Invoice i = getInvoiceById(id, userId);
		for(Journal j : i.getJournalList()) {
			aService.updateAccountBalanceForJournalDeletion(j);
			Ledger l = j.getLedger();
			lRepo.delete(l);
			jRepo.delete(j);
		}
		iRepo.delete(i);

	}
}
