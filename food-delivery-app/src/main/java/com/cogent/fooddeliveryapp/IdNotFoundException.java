package com.cogent.fooddeliveryapp;

public class IdNotFoundException extends RuntimeException {
	
	public IdNotFoundException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
