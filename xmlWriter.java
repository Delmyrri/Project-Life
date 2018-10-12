package xmlRandomizer;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.Date;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class xmlWriter {
	
	static String[] patients = new String[] {"John Smith", "Jane Doe", "Alex Benz", "Eric Jefferson", "Hans Gustaffson", "Ravi Patel", "Rebecca Johnson", "Jeremy Jenkins", "Brenda Sanchez", "Ashley Smith"};
	static String[] doctors = new String[] {"Dr. Patricia Jones", "Dr. Ben Watson", "Dr. Jamie Wallace", "Dr. Thomas Grey", "Dr. Darryl Williams"};
	static String[] conditions = new String[] {"asthma", "eczema", "cancer", "obesity", "diabetes", "N/A"};
	static String[] scripts = new String[] {"albuterol", "warfarin", "metformin", "apresoline", "erythromycin", "omeprazole", "none"};
	static String[] events = new String[] {"physical", "check-up", "follow-up", "emergency"};
	static String[] famHist = new String[] {"Heart attack - Grandfather", "Diabetes - Mother", "Asthma - Father", "Colon Cancer - Father", "Breast Cancer - Grandmother", "Leukemia - Brother"};
	
	static int idCount = 0;
	static int xmlCount = 0;

	public static void main(String[] args) {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		// arbitrary number can be changed to match number of xml files needed to be generated
		while (xmlCount <= 19) {   
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();

				// Adds root element to document
				Element rootElement = doc.createElement("LIFE_File");
			
				// append root element to document
				doc.appendChild(rootElement);
			
				// append randomly generated transaction to document
				rootElement.appendChild(generateRandTransaction(doc));
			
				// Outputs to file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
			
				// formats output
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
				DOMSource source = new DOMSource(doc);
			
				// writes to console for debugging purposes
				// file path used in "outFile" for testing purposes only, change file path 
				StreamResult console = new StreamResult(System.out);
				StreamResult outFile = new StreamResult(new File("/Users/Marvin/Desktop/testXML/testFile" + Integer.toString(xmlCount) + ".xml"));
				
			
				transformer.transform(source, outFile);
				System.out.println("Xml file generated!\n");
			
				System.out.println("Xml example:\n---------------------------------------------");
				transformer.transform(source, console);
			} catch(Exception e) {
				e.printStackTrace();
			}
			xmlCount++;
		}
	}

	// helper function used to generate random patient transactions calls getTransElements() 
	// to generate elements to be appended to xml
	private static Node generateRandTransaction(Document doc) {
		
		Random rand = new Random();
		
		int patient = rand.nextInt(9);
		int doctor = rand.nextInt(4);
		int operDoc = rand.nextInt(4);
		int condition1 = rand.nextInt(5);
		int condition2 = rand.nextInt(5);
		
		while (condition1 == condition2) {
			condition2 = rand.nextInt(5);
		}
		
		int script1 = rand.nextInt(6);
		int script2 = rand.nextInt(6);
		
		while (script1 == script2) { 
			script2 = rand.nextInt(6);
		}
		
		int currscript1 = rand.nextInt(6);
		int currscript2 = rand.nextInt(6);
		
		while (currscript1 == currscript2) {
			currscript2 = rand.nextInt(6);
		}
		
		int eventName = rand.nextInt(1);
		int family1 = rand.nextInt(5);
		int family2 = rand.nextInt(5);
		
		while (family1 == family2) {
			family2 = rand.nextInt(5);
		}
		
		// determine if multiple instances of each appended child will be added
		int condMult = rand.nextInt(1);
		int scriptMult = rand.nextInt(1);
		int currScriptMult = rand.nextInt(1);
		int famMult = rand.nextInt(1);
		

		Element transaction = doc.createElement("Transaction");
		
		transaction.appendChild(getTransElements(doc, transaction, "TransactionID", String.format("%04d", idCount)));
		transaction.appendChild(getTransElements(doc, transaction, "PatientName", patients[patient]));
		transaction.appendChild(getTransElements(doc, transaction, "PatientPCP", doctors[doctor]));
		transaction.appendChild(getTransElements(doc, transaction, "EventName", events[eventName]));
		transaction.appendChild(getTransElements(doc, transaction, "PerformedBy", doctors[operDoc]));
		transaction.appendChild(getTransElements(doc, transaction, "EventDate", generateRandDate()));
		
		if (scriptMult == 0) {
			transaction.appendChild(getTransElements(doc, transaction, "PrescriptionsAdded", scripts[script1]));
		} else {
			transaction.appendChild(getTransElements(doc, transaction, "PrescriptionsAdded", scripts[script1] + ", " + scripts[script2]));
		}
		
		if (currScriptMult == 0) {
			transaction.appendChild(getTransElements(doc, transaction, "CurrentPrescriptions", scripts[currscript1]));
		} else {
			transaction.appendChild(getTransElements(doc, transaction, "CurrentPrescriptions", scripts[currscript1] + ", " + scripts[currscript2]));
		}
		
		if (condMult == 0) {
			transaction.appendChild(getTransElements(doc, transaction, "PatientConditions", conditions[condition1]));
		} else { 
			transaction.appendChild(getTransElements(doc, transaction, "PatientConditions", conditions[condition1] + ", " + conditions[condition2]));
		}
		
		if (famMult % 2 == 0) {
			transaction.appendChild(getTransElements(doc, transaction, "PatientFamilyHistory", famHist[family1] + ", " + famHist[family2]));
		} else {
			transaction.appendChild(getTransElements(doc, transaction, "PatientFamilyHistory", famHist[family1]));
		}
		
		idCount ++;
		
		return transaction;
	}
	
	// Helper function called by generateRandTransaction() to generate random patient event date
	// Formats date to match string "MM/dd/yyyy"
	private static String generateRandDate() {
		
		String strDate;
		// sets and formats date of event between January 1, 2016 and September 30, 2018
		long offset = Timestamp.valueOf("2016-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2018-09-30 00:00:00").getTime();
		long diff = end - offset + 1;
				
		Timestamp randTime = new Timestamp(offset + (long)(Math.random() * diff));
		SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy");
		strDate = dateForm.format(randTime);
		
		return strDate;
	}
	
	// creates elements to be appended to xml document
	private static Node getTransElements(Document doc, Element element, String name, String value) {
        
		Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        
        return node;
    }

}