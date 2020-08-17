package com.creditcards;

import com.creditcards.CreditCard;

class MasterCC extends CreditCard {
	public MasterCC(String cardNumber, String expiryDate, String cardHolderName) {
		super(cardNumber, expiryDate, cardHolderName);
	}

	public String GetCardType() {
		return "Master";
	}
}