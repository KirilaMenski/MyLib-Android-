package com.ansgar.mylib.fbreader;

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

public class DescriptionImpl implements Description {

    private String path;
    private InputStream mInputStream;

    private Document mDocument;

    public DescriptionImpl(String path) {
        this.path = path;
    }

    public DescriptionImpl(InputStream inputStream) {
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

    public String getTitle() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String title = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                title = element.getElementsByTagName("book-title").item(0)
                        .getChildNodes().item(0).getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return title;
    }

    public String getGenre() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String genre = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                genre = element.getElementsByTagName("genre").item(0)
                        .getChildNodes().item(0).getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return genre;
    }


    //TODO
    @Override
    public List<String> getCover() {
        NodeList nodeList = mDocument.getElementsByTagName("binary");
        List<String> covers = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
//                cover = element.getElementsByTagName("image").item(0)
//                        .getAttributes().getNamedItem("l:href").getNodeValue();
                covers.add(element.getChildNodes().item(0).getNodeValue());
            } catch (Exception e) {
                continue;
            }
        }
        return covers;
    }

    @Override
    public String getAuthor() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String firstname = "";
        String lastname = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                firstname = element.getElementsByTagName("first-name").item(0)
                        .getChildNodes().item(0).getNodeValue();
                lastname = element.getElementsByTagName("last-name").item(0)
                        .getChildNodes().item(0).getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return firstname + " " + lastname;

    }

    @Override
    public String getSeries() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String series = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                series = element.getElementsByTagName("sequence").item(0)
                        .getAttributes().getNamedItem("name").getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return series;

    }

    @Override
    public String getNumOfSer() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String number = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                number = element.getElementsByTagName("sequence").item(0)
                        .getAttributes().getNamedItem("number").getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return number;
    }

    @Override
    public String getLang() {
        NodeList nodeList = mDocument.getElementsByTagName("title-info");
        String lang = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                lang = element.getElementsByTagName("lang").item(0)
                        .getChildNodes().item(0).getNodeValue();
            } catch (Exception e) {
                continue;
            }
        }
        return lang;
    }

    @Override
    public List<String> getAnnotation() {
        NodeList nodeList = mDocument.getElementsByTagName("annotation");
        List<String> anotation = new ArrayList<String>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                anotation.add(element.getElementsByTagName("p").item(0)
                        .getChildNodes().item(0).getNodeValue() + " ");
            } catch (Exception e) {
                continue;
            }
        }
        return anotation;
    }

}
