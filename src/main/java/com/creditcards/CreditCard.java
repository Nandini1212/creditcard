package com.creditcards;

public abstract class CreditCard {
	String cardNumber;
	String expiryDate;
	String cardHolderName;

	public CreditCard(String cardNumber, String expiryDate, String cardHolderName) {
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cardHolderName = cardHolderName;
	}

	public abstract String GetCardType();

	public String GetCardNumber() {
		return cardNumber;
	}

	public String GetExpiryDate() {
		return expiryDate;
	}

	public String GetCardHolderName() {
		return cardHolderName;
	}
}
