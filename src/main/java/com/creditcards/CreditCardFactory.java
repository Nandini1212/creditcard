package com.creditcards;

public class CreditCardFactory {
	public static CreditCard CreateCard(String cardNumber, String expiryDate, String cardHolderName) {
		if (cardNumber.length() == 16 && cardNumber.substring(0, 4) == "6011") {
			return new DiscoverCC(cardNumber, expiryDate, cardHolderName);
		} else if ((cardNumber.length() == 13 || cardNumber.length() == 16) && cardNumber.charAt(0) == '4') {
			return new VisaCC(cardNumber, expiryDate, cardHolderName);
		} else if (cardNumber.length() == 15 && cardNumber.charAt(0) == '3'
				&& (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7')) {
			return new AmExCC(cardNumber, expiryDate, cardHolderName);
		} else if (cardNumber.length() == 16 && cardNumber.charAt(0) == '5'
				&& (cardNumber.charAt(1) == '1' || cardNumber.charAt(1) == '2' || cardNumber.charAt(1) == '3'
						|| cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '5')) {
			return new MasterCC(cardNumber, expiryDate, cardHolderName);
		}
		return new InvalidCC(cardNumber, expiryDate, cardHolderName);
	}
}
