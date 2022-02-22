package com.cogent.fooddeliveryapp.exception;


public class NameAlreadyExistsException extends Exception {
	
	public NameAlreadyExistsException(String e) {
		// TODO Auto-generated constructor stub
		super(e);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
