package com.cts.project.bookkeeping.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ledger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ledgerId;
	
	private double balance;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account account;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="journalId")
	private Journal journal;

}
