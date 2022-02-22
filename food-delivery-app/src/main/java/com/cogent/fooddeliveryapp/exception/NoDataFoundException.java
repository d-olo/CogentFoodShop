package com.cogent.fooddeliveryapp.exception;

public class NoDataFoundException extends RuntimeException {

	public NoDataFoundException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}
