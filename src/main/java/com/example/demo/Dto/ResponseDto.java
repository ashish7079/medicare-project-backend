package com.example.demo.Dto;


public class ResponseDto<T> {

	private String message;
	private int Status;
	private T data;
	
	public ResponseDto(String message, int status, T data) {
		super();
		this.message = message;
		Status = status;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
