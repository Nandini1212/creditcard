package com.creditcards;

import com.creditcards.CreditCard;

class DiscoverCC extends CreditCard {
	public DiscoverCC(String cardNumber, String expiryDate, String cardHolderName) {
		super(cardNumber, expiryDate, cardHolderName);
	}

	public String GetCardType() {
		return "Discover";
	}
}