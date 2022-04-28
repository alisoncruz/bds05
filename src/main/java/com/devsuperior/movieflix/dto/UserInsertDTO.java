package com.devsuperior.movieflix.dto;

import java.io.Serializable;

import com.devsuperior.movieflix.services.validation.UserInsertValid;


@UserInsertValid
public class UserInsertDTO extends UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public UserInsertDTO() {
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
