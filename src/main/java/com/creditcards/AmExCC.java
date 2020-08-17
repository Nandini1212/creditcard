package com.creditcards;

import com.creditcards.CreditCard;

class AmExCC extends CreditCard {
	public AmExCC(String cardNumber, String expiryDate, String cardHolderName) {
		super(cardNumber, expiryDate, cardHolderName);
	}

	public String GetCardType() {
		return "AmEx";
	}
}