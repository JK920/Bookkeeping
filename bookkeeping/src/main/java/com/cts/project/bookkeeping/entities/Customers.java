package com.cts.project.bookkeeping.entities;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Customers {

	@Id
	@GenericGenerator(name = "customer_id", strategy = "com.cts.project.bookkeeping.generator.CustomerIdGenerator")
    @GeneratedValue(generator = "customer_id")  
	@Size(max=8)
	private String customerId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@NotNull
	private Users user;
	
	@NotNull
	@Size(min = 4, max = 25)
	private String name;
	
	@Size(max = 50)
	private String address;
	
	@NotNull
	@Size(min = 4, max = 20)
	private String phoneNo;
	
	@Email
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Journal> journalList;
	
	public Customers(Users user, String name, String address, String phoneNo, String email) {
		super();
		this.user = user;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.email = email;
	}



}
