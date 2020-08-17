import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.creditcards.CreditCard;
import com.creditcards.CreditCardFactory;
import com.parse.file.CSVFileParser;
import com.parse.file.FileParser;
import com.parse.file.FileParserFactory;
import com.parse.file.JSONFileParser;
import com.parse.file.XMLFileParser;

import static org.hamcrest.CoreMatchers.instanceOf;

public class CreditCardTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	public File tempInputCSVFile;
	public File tempOutputCSVFile;
	public File tempOutputXMLFile;
    public File tempOutputJSONFile;

	@Before
	public void setUp() throws Exception {
		tempInputCSVFile = tempFolder.newFile("tempInputFile.csv");
		String CSVString = String.join("\n", "CardNumber,ExpirationDate,NameOfCardholder",
				"5410000000000000,3/20/2030,Alice", "4120000000000,4/20/2030,Bob", "341000000000000,5/20/2030,Eve",
				"6010000000000000,6/20/2030,Richard");
		Files.write(Paths.get(tempInputCSVFile.getPath()), CSVString.getBytes());
		tempOutputCSVFile = tempFolder.newFile("tempOutputFile.csv");
		tempOutputXMLFile = tempFolder.newFile("tempOutputFile.xml");
		tempOutputJSONFile = tempFolder.newFile("tempOutputFile.json");
	}

	@Test
	public void testReadAndWriteXMLFile() throws Exception {
		FileParser inputFileParser = FileParserFactory.GetFileParser(tempInputCSVFile.getPath());
		List<CreditCard> creditCards = inputFileParser.GetAllCards();
		Assert.assertEquals(creditCards.size(), 4);
		FileParser outputFileParser = FileParserFactory.GetFileParser(tempOutputXMLFile.getPath());
		outputFileParser.PutAllCards(creditCards);
		byte[] content = Files.readAllBytes(Paths.get(tempOutputXMLFile.getPath()));
		String stringContent = new String(content);
		stringContent = stringContent.substring(stringContent.indexOf('\n')+1);
		Assert.assertEquals(stringContent.trim(),
				String.join("\n",
				"<root>",
				"  <row>",
				"    <CardNumber>5410000000000000</CardNumber>",
				"    <CardType>Master</CardType>",
				"    <CardHolderName>Alice</CardHolderName>",
				"    <CardExpiryDate>3/20/2030</CardExpiryDate>",
				"  </row>",
				"  <row>",
				"    <CardNumber>4120000000000</CardNumber>",
				"    <CardType>Visa</CardType>",
				"    <CardHolderName>Bob</CardHolderName>",
				"    <CardExpiryDate>4/20/2030</CardExpiryDate>",
				"  </row>",
				"  <row>",
				"    <CardNumber>341000000000000</CardNumber>",
				"    <CardType>AmEx</CardType>",
				"    <CardHolderName>Eve</CardHolderName>",
				"    <CardExpiryDate>5/20/2030</CardExpiryDate>",
				"  </row>",
				"  <row>",
				"    <CardNumber>6010000000000000</CardNumber>",
				"    <CardType>Invalid</CardType>",
				"    <CardHolderName>Richard</CardHolderName>",
				"    <CardExpiryDate>6/20/2030</CardExpiryDate>",
				"  </row>",
				"</root>"
				));
	}

	@Test
	public void testReadAndWriteCSVFile() throws Exception {
		FileParser inputFileParser = FileParserFactory.GetFileParser(tempInputCSVFile.getPath());
		List<CreditCard> creditCards = inputFileParser.GetAllCards();
		Assert.assertEquals(creditCards.size(), 4);
		FileParser outputFileParser = FileParserFactory.GetFileParser(tempOutputCSVFile.getPath());
		outputFileParser.PutAllCards(creditCards);
		byte[] content = Files.readAllBytes(Paths.get(tempOutputCSVFile.getPath()));
		String stringContent = new String(content);
		Assert.assertEquals(stringContent.trim(),
				String.join("\n", "CardNumber,CardType,NameOfCardholder,ExpirationDate",
						"5410000000000000,Master,Alice,3/20/2030", "4120000000000,Visa,Bob,4/20/2030",
						"341000000000000,AmEx,Eve,5/20/2030", "6010000000000000,Invalid,Richard,6/20/2030"
				));
	}

	@Test
	public void testReadAndWriteJSONFile() throws Exception {
		FileParser inputFileParser = FileParserFactory.GetFileParser(tempInputCSVFile.getPath());
		List<CreditCard> creditCards = inputFileParser.GetAllCards();
		Assert.assertEquals(creditCards.size(), 4);
		FileParser outputFileParser = FileParserFactory.GetFileParser(tempOutputJSONFile.getPath());
		outputFileParser.PutAllCards(creditCards);
		byte[] content = Files.readAllBytes(Paths.get(tempOutputJSONFile.getPath()));
		String stringContent = new String(content);
		Assert.assertEquals(stringContent.split("},").length, 5);
	}

	@Test
	public void testMasterCCCreated() {
		CreditCard creditCard = CreditCardFactory.CreateCard("5410000000000000", "3/20/2030", "Alice");
		Assert.assertEquals(creditCard.GetCardType(), "Master");
	}

	@Test
	public void testVisaCCCreated() {
		CreditCard creditCard = CreditCardFactory.CreateCard("4120000000000", "3/20/2030", "Alice");
		Assert.assertEquals(creditCard.GetCardType(), "Visa");
	}

	@Test
	public void testAmExCCCreated() {
		CreditCard creditCard = CreditCardFactory.CreateCard("341000000000000", "3/20/2030", "Alice");
		Assert.assertEquals(creditCard.GetCardType(), "AmEx");
	}

	@Test
	public void testInvalidCCCreated() {
		CreditCard creditCard = CreditCardFactory.CreateCard("6010000000000000", "3/20/2030", "Alice");
		Assert.assertEquals(creditCard.GetCardType(), "Invalid");
	}

	@Test
	public void testParserCreationFailure() {
		FileParser fileParser = FileParserFactory.GetFileParser("something");
		Assert.assertNull(fileParser);
	}

	@Test
	public void testCSVFileParserSuccessfulCreation() {
		FileParser fileParser = FileParserFactory.GetFileParser("something.csv");
		Assert.assertThat(fileParser, instanceOf(CSVFileParser.class));
	}

	@Test
	public void testJSONFileParserSuccessfulCreation() {
		FileParser fileParser = FileParserFactory.GetFileParser("something.json");
		Assert.assertThat(fileParser, instanceOf(JSONFileParser.class));
	}

	@Test
	public void testXMLFileParserSuccessfulCreation() {
		FileParser fileParser = FileParserFactory.GetFileParser("something.xml");
		Assert.assertThat(fileParser, instanceOf(XMLFileParser.class));
	}
}
