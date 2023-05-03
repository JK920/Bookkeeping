package com.cts.project.bookkeeping.entities;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Users {
	
	@Id
	@GenericGenerator(name = "user_id", strategy = "com.cts.project.bookkeeping.generator.UserIdGenerator")
    @GeneratedValue(generator = "user_id")
	@Size(max = 8)
	private String userId;
	
	@NotBlank
	@Size(min = 4, max = 25)
	private String name;
	
	@Column(unique = true)
	@NotBlank
	@Size(min = 4, max = 25)
	private String username;
	
	@NotBlank
	@Size(min = 4)
	private String password;
	
	@Column(unique = true)
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 4, max = 30)
	private String companyName;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Account> accountList;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Customers> customerList;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Vendors> vendorList;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user" )
	private List<Invoice> invoiceList;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Journal> journalList;
	
	
	public Users(String name,String username, String password, String email, String companyName) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.companyName = companyName;
	}
	
}
