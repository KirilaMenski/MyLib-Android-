package com.ansgar.mylib.reader.fbreader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class TextImpl implements Text {

	private String path;
	private InputStream mInputStream;

	private Document mDocument;

	public TextImpl(String path) {
		this.path = path;
	}

	public TextImpl(InputStream inputStream) {
		this.mInputStream = inputStream;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			mDocument = builder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getText() {
		NodeList nodeList = mDocument.getElementsByTagName("section");
		List<String> text = new ArrayList<String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			NodeList nl = mDocument.getElementsByTagName("p");
			for (int j = 0; j < nl.getLength(); j++) {
				try {
					text.add(element.getElementsByTagName("p").item(j)
							.getTextContent() + " ");
				} catch (Exception e) {
					continue;
				}
				text.add("\n");
			}
		}
		return text;
	}

}
