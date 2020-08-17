package com.utility;

import java.io.FileNotFoundException;
import java.util.List;

import com.creditcards.CreditCard;
import com.parse.file.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {

		if (args.length != 2) {
			System.out.println("Invalid number of arguments");
			System.out.println("Usage : CreditCardValidator <source file path> <destination file path>");
		} /*
			 * FileParser inputFileParser = FileParserFactory.GetFileParser(args[0]);
			 * if(inputFileParser == null) {
			 * System.out.println("There is no file name given"); } List<CreditCard>
			 * creditCards = inputFileParser.GetAllCards(); FileParser outputFileParser =
			 * FileParserFactory.GetFileParser(args[1]); if(outputFileParser == null) {
			 * System.out.println("There is no file name given"); } try {
			 * outputFileParser.PutAllCards(creditCards); }catch(Exception e) {
			 * e.printStackTrace(); }
			 */
		FileParser inputFileParser = FileParserFactory.GetFileParser(args[0]);
		
		if (inputFileParser == null) {
			
			try {
				throw new Exception("Invalid file");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<CreditCard> creditCards = inputFileParser.GetAllCards();
		if (creditCards.size() == 0) {
			return;
		}
		FileParser outputFileParser = FileParserFactory.GetFileParser(args[1]);
		if (outputFileParser == null) {
			try {
				throw new Exception("Invalid file");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		outputFileParser.PutAllCards(creditCards);
	}
}
