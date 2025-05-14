package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Transaction {
	private String customerId;
	private double amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate transactionDate;

	// Constructor
	public Transaction(String customerId, double amount, LocalDate transactionDate) {
		this.customerId = customerId;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public Transaction() {
	}

	// Getters
	public String getCustomerId() {
		return customerId;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
}
