package com.via.exceptions;

@SuppressWarnings("serial")
public class NotEnoughSeatsException extends Exception{
	public NotEnoughSeatsException(String msg){
		super(msg);
	}
}
