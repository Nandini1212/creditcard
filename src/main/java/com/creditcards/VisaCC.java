package com.creditcards;

import com.creditcards.CreditCard;

class VisaCC extends CreditCard {
	public VisaCC(String cardNumber, String expiryDate, String cardHolderName) {
		super(cardNumber, expiryDate, cardHolderName);
	}

	public String GetCardType() {
		return "Visa";
	}
}
