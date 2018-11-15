
package com.adv.java.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XML {

    private Document document;
    private NodeList nodes;

    public XML(String xml) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("Called: com.adv.java.xml.XML(String)");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));

        document = builder.parse(source);
        nodes = document.getElementsByTagName("result");

    }

    public void nodePrinter() {

        int[] intArray = new int[20];

        for (int i = 0; i < nodes.getLength(); i++) {

            Element e = (Element) nodes.item(i);

            System.out.println("uuid: " + e.getElementsByTagName("uuid").item(0).getTextContent());
            System.out.println("title: " + e.getElementsByTagName("title").item(0).getTextContent());
            System.out.println("itemLink: " + e.getElementsByTagName("itemLink").item(0).getTextContent());

        }

    }

    public String[] getUuids() {
        System.out.println("Called: com.adv.java.xml.XML.getUuids()");

        String[] uuids = new String[nodes.getLength()];

        // Search for results and save the UUID of the result
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            uuids[i] = e.getElementsByTagName("uuid").item(0).getTextContent();
            System.out.println("Found uuid: " + uuids[i]);
        }

        return uuids;
    }

    public String getTitle(String uuid) {
        System.out.println("Called: com.adv.java.xml.XML.getTitle(String)");

        String title = null;
        boolean isCorrectResult = false;

        // Searches for a result with a matchin UUID and stores the title
        for (int i = 0; i < nodes.getLength(); i++) {

            Element e = (Element) nodes.item(i);

            // Find out if the UUID matches the result
            if (uuid.equalsIgnoreCase(e.getElementsByTagName("uuid").item(0).getTextContent()))
                isCorrectResult = true;
            else
                isCorrectResult = false;

            // Only gather titles if the UUID matches
            if (isCorrectResult) {
                title = e.getElementsByTagName("title").item(0).getTextContent();
                System.out.println("Found title: " + title);
            }

        }

        return title;
    }

    public String getItemLink(String uuid) {
        System.out.println("Called: com.adv.java.xml.XML.getItemLink(String)");

        String itemLink = null;
        boolean isCorrectResult = false;

        // Searches for a result with a matchin UUID and stores the itemLink
        for (int i = 0; i < nodes.getLength(); i++) {

            Element e = (Element) nodes.item(i);

            // Find out if the UUID matches the result
            if (uuid.equalsIgnoreCase(e.getElementsByTagName("uuid").item(0).getTextContent()))
                isCorrectResult = true;
            else
                isCorrectResult = false;

            // Only gather itemLinks if the UUID matches
            if (isCorrectResult) {
                itemLink = e.getElementsByTagName("itemLink").item(0).getTextContent();
                System.out.println("Found itemLink: " + itemLink);
            }

        }

        return itemLink;
    }

}
