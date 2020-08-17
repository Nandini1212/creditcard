package com.parse.file;

import java.util.List;

import com.creditcards.*;

public abstract class FileParser {
	String fileName;

	public FileParser(String fileName) {
		this.fileName = fileName;
	}

// For each line of the file create a card.
	public abstract List<CreditCard> GetAllCards();

	public abstract void PutAllCards(List<CreditCard> creditCards);
}
