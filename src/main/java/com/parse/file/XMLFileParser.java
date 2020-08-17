package com.parse.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.creditcards.CreditCard;
import com.creditcards.CreditCardFactory;


public class XMLFileParser extends FileParser {
	public XMLFileParser(String fileName) {
		super(fileName);
	}

	public List<CreditCard> GetAllCards() {
		List<CreditCard> creditCardList = new ArrayList<CreditCard>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fileName));
			document.getDocumentElement().normalize();
			Element root = document.getDocumentElement();
			NodeList cardNodes = document.getElementsByTagName("row");
			for (int nodeIndex = 0; nodeIndex < cardNodes.getLength(); nodeIndex++) {
				Node cardNode = cardNodes.item(nodeIndex);
				Element cardElement = (Element) cardNode;
				CreditCard creditCard = CreditCardFactory.CreateCard(
						cardElement.getElementsByTagName("CardNumber").item(0).getTextContent(),
						cardElement.getElementsByTagName("ExpirationDate").item(0).getTextContent(),
						cardElement.getElementsByTagName("NameOfCardholder").item(0).getTextContent());
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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("root");
			document.appendChild(root);
			for(CreditCard cc : creditCards) {
				Element row = document.createElement("row");
				root.appendChild(row);
				Element cardNumber = document.createElement("CardNumber");
				cardNumber.appendChild(document.createTextNode(cc.GetCardNumber()));
				row.appendChild(cardNumber);
				Element cardType = document.createElement("CardType");
				cardType.appendChild(document.createTextNode(cc.GetCardType()));
				row.appendChild(cardType);
				Element cardHolderName = document.createElement("CardHolderName");
				cardHolderName.appendChild(document.createTextNode(cc.GetCardHolderName()));
				row.appendChild(cardHolderName);
				Element cardExpiryDate = document.createElement("CardExpiryDate");
				cardExpiryDate.appendChild(document.createTextNode(cc.GetExpiryDate()));
				row.appendChild(cardExpiryDate);	
				
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(fileName));
			transformer.transform(source, result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}


