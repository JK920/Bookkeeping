package com.cts.project.bookkeeping.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountLedger {

	
	private LocalDate date;
	private String reference;
	private String desc;
	private double debit;
	private double credit;
	private double balance;
}
