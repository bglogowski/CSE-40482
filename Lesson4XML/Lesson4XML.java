/**
 * Lesson4XML.java
 */


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Lesson4XML {

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        String filename = null;

        long beforeParse, afterParse, domTime, saxTime, xpathTime;

        // Get the file name from the command line...
        for (String s : args) {
            filename = s;
        }

        // ...or use a default name.
        if (filename == null)
            filename = "JobResult_UCSDExt.xml";

        System.out.println("Results of XML Parsing using DOM Parser:");
        beforeParse = System.currentTimeMillis();
        domReader(filename);
        afterParse = System.currentTimeMillis();
        domTime = afterParse - beforeParse;
        // System.out.println("--> Time to parse XML: " + domTime + " milliseconds");

        System.out.println("Results of XML Parsing using SAX Parser:");
        beforeParse = System.currentTimeMillis();
        saxReader(filename);
        afterParse = System.currentTimeMillis();
        saxTime = afterParse - beforeParse;
        // System.out.println("--> Time to parse XML: " + saxTime + " milliseconds");

        System.out.println("Results of XML Parsing using XPath:");
        beforeParse = System.currentTimeMillis();
        xpathReader(filename);
        afterParse = System.currentTimeMillis();
        xpathTime = afterParse - beforeParse;
        // System.out.println("--> Time to parse XML: " + xpathTime + " milliseconds");

        System.out.println("All Done!");
        
        // Uncomment this to print the results of a comparison between DOM and SAX
        /*
        if(saxTime < domTime) {
            System.out.println();
            System.out.println("Parsing XML with SAX is faster than with DOM!");
            
        }
        */

    }

    // Parse the XML file with DOM
    private static void domReader(String filename) throws ParserConfigurationException, SAXException, IOException {

        File file = new File(filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NodeList nodes = doc.getElementsByTagName("jobresult");
        nodePrinter(nodes);

    }

    // Parse the XML file with SAX
    private static void saxReader(String filename) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean serial = false;
            boolean visibleString = false;
            boolean unsigned = false;

            public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {

                if (qName.equalsIgnoreCase("serial"))
                    serial = true;

                if (qName.equalsIgnoreCase("visible-string"))
                    visibleString = true;

                if (qName.equalsIgnoreCase("unsigned"))
                    unsigned = true;
            }

            public void characters(char ch[], int start, int length) throws SAXException {

                if (serial) {
                    System.out.println("serial: " + new String(ch, start, length));
                    serial = false;
                }

                if (visibleString) {
                    System.out.println("visible-string: " + new String(ch, start, length));
                    visibleString = false;
                }

                if (unsigned) {
                    System.out.println("unsigned: " + new String(ch, start, length));
                    unsigned = false;
                }

            }

        };

        saxParser.parse(filename, handler);

    }

    // Parse the XML file with XPath
    private static void xpathReader(String filename)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        File file = new File(filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodes = (NodeList) xPath.compile("jobresult").evaluate(doc, XPathConstants.NODESET);
        nodePrinter(nodes);

    }

    // Iterate through a NodeList and print the details
    private static void nodePrinter(NodeList nodes) {

        for (int i = 0; i < nodes.getLength(); i++) {

            Element e = (Element) nodes.item(i);

            System.out.println("serial: " + e.getElementsByTagName("serial").item(0).getTextContent());
            System.out.println("visible-string: " + e.getElementsByTagName("visible-string").item(0).getTextContent());
            System.out.println("unsigned: " + e.getElementsByTagName("unsigned").item(0).getTextContent());

        }

    }

}
