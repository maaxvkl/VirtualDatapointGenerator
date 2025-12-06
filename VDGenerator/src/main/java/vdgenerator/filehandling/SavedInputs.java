package vdgenerator.filehandling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;


public class SavedInputs {

    List<Element> analogInputs = new ArrayList<>();

    public void saveAnalogInputs (Document document) {
        NodeList analogInNodes = document.getElementsByTagName("object");
        for (int i = 0; i < analogInNodes.getLength(); i++) {
            Element element = (Element) analogInNodes.item(i);
            String classId = element.getAttribute("classid");
            if (classId.equals("599")) {
                analogInputs.add(element);
            }
        }
    }

    public String getDatapointDescription(Element element) {
        NodeList properties = element.getElementsByTagName("property");
        for(int i = 0; i<properties.getLength(); i++){
            Element propertyElement = (Element) properties.item(i);
            String elementId = propertyElement.getAttribute("id");
            if(elementId.equals("28")){
                Element stringElement = (Element) propertyElement.getElementsByTagName("string").item(0);
                return stringElement.getTextContent();
            }
        }
                return " ";
    }

    public String getDatapointName(Element element) {
        NodeList properties = element.getElementsByTagName("property");
        for(int i = 0; i<properties.getLength(); i++){
            Element propertyElement = (Element) properties.item(i);
            String elementId = propertyElement.getAttribute("id");
            if(elementId.equals("2390")){
                Element stringElement = (Element) propertyElement.getElementsByTagName("string").item(0);
                return stringElement.getTextContent();
            }
        }
               return " ";
    }

    public List<Element> getAnalogInputs() {
        return analogInputs;
    }
}
