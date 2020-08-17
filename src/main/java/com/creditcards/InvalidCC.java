package com.creditcards;

import com.creditcards.CreditCard;

class InvalidCC extends CreditCard {
	public InvalidCC(String cardNumber, String expiryDate, String cardHolderName) {
		super(cardNumber, expiryDate, cardHolderName);
	}

	public String GetCardType() {
		return "Invalid";
	}
}