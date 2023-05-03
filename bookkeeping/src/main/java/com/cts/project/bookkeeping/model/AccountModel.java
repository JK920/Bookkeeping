package com.cts.project.bookkeeping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {

	
	private String userId;
	private String accountName;
	private AccountType type;
	private double balance;
	
	
}
