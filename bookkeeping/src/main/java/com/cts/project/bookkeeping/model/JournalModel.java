package com.cts.project.bookkeeping.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalModel {

	
	private String userId;
	private LocalDate date;
	private String accountId;
	private String reference;
	private String description;
	private String invoiceId;
	private String vendorId;
	private String customerId;
	private double debit;
	private double credit;
	
}
