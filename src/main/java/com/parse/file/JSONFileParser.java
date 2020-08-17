package com.parse.file;

import java.io.FileReader;
import java.io.FileWriter;

import com.creditcards.CreditCard;
import com.creditcards.CreditCardFactory;
import com.creditcards.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONFileParser extends FileParser {

	public JSONFileParser(String fileName) {
		super(fileName);
	}

	public List<CreditCard> GetAllCards() {

		ArrayList<CreditCard> creditCardList = new ArrayList();
		try {
			FileReader reader = new FileReader(fileName);
			JSONParser jsonParser = new JSONParser();
			Object object = jsonParser.parse(reader);
			JSONArray cardObjects = (JSONArray) object;
			for (int cardObjectIndex = 0; cardObjectIndex < cardObjects.size(); cardObjectIndex++) {
				JSONObject cardObject = (JSONObject) cardObjects.get(cardObjectIndex);
				CreditCard creditCard = CreditCardFactory.CreateCard((String) cardObject.get("CardNumber"),
						(String) cardObject.get("ExpirationDate"), (String) cardObject.get("NameOfCardholder"));
				if (creditCard != null) {
					creditCardList.add(creditCard);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditCardList;
	}

	public void PutAllCards(List<CreditCard> creditCards) {

		try {
			JSONObject creditCardObject = new JSONObject();
			FileWriter file = new FileWriter(fileName);
			file.write("[\n");

			for (CreditCard cc : creditCards) {
				creditCardObject.put("CreditCardNumber", cc.GetCardNumber());
				creditCardObject.put("CardType", cc.GetCardType());
				creditCardObject.put("CardHolderName", cc.GetCardHolderName());
				creditCardObject.put("CardExpiryDated", cc.GetExpiryDate());
				String jsonString = creditCardObject.toJSONString().replace(",", ",\n        ");
				jsonString = jsonString.replace("{", "    {\n        ");
				jsonString = jsonString.replace("}", "\n    }");
				file.write(jsonString);
				file.write(",\n");
			}
			file.write("]");
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
