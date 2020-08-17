package com.parse.file;

import com.creditcards.CreditCard;
import com.creditcards.CreditCardFactory;
import com.creditcards.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CSVFileParser extends FileParser {
	public CSVFileParser(String fileName) {
		super(fileName);
	}

	public List<CreditCard> GetAllCards() {
		List<CreditCard> creditCardList = new ArrayList<CreditCard>();

		try {
			CSVReader csvFileReader = new CSVReader(new FileReader(fileName));
			String[] creditCardInfo = null;

			creditCardInfo = csvFileReader.readNext();

			while ((creditCardInfo = csvFileReader.readNext()) != null) {
				if (creditCardInfo.length == 3) {
					CreditCard creditCard = CreditCardFactory.CreateCard(creditCardInfo[0], creditCardInfo[1],
							creditCardInfo[2]);
					if (creditCard != null) {
						creditCardList.add(creditCard);
					}
				}
			}
			csvFileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditCardList;
	}

	public void PutAllCards(List<CreditCard> creditCards) {
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(fileName));
			CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			String[] headerRecord = { "CardNumber", "CardType", "NameOfCardholder", "ExpirationDate" };
			csvWriter.writeNext(headerRecord);
			for (CreditCard cc : creditCards) {
				csvWriter.writeNext(new String[] {cc.GetCardNumber(), cc.GetCardType(), cc.GetCardHolderName(),
						cc.GetExpiryDate() });
				csvWriter.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}