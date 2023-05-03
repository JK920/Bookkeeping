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
public class InvoiceModel {
	
	private String userId;
	private String invoiceId;
	private LocalDate invoiceDate;
	private LocalDate dueDate;
	private String billedBy;
	private String billedTo;
	private double taxPercentage;
	private double totalAmount;
	private double costPrice;
	private boolean paid;
}
