package com.cts.project.bookkeeping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

	private String name;
	private String username;
	private String password;
	private String email;
	private String companyName;
	
}
