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
	private String accountName;
	private String reference;
	private String description;
	private String invoiceId;
	private String vendorName;
	private String customerName;
	private double debit;
	private double credit;
	
}
