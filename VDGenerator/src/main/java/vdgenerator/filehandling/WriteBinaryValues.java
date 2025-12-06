package vdgenerator.filehandling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vdgenerator.utility.GeneratorUtils;
import java.util.List;


public class WriteBinaryValues {

    SavedInputs savedInputs = new SavedInputs();

    public void addBinaryValues(Document document, List<Element> analogInputs) {

        Element root = document.getDocumentElement();
        String nameSpace = root.getNamespaceURI();

        String[] nameSuffixes = {"AlarmLL", "AlarmL", "AlarmH", "AlarmHH", "Fault"};
        String[] descriptionSuffixes = {"AlarmLowLow", "AlarmLow", "AlarmHigh", "AlarmHighHigh", "Sensorfehler"};

        for (Element element : analogInputs) {
            String datapointName = savedInputs.getDatapointName(element);
            String datapointDescription = savedInputs.getDatapointDescription(element);
            String checkedValue = GeneratorUtils.valueCheck(datapointName);

            if (checkedValue.contains("MW")) {
                for (int counter = 0; counter < nameSuffixes.length; counter++) {
                    Element parent = document.createElementNS(nameSpace, "object");
                    String baseName = GeneratorUtils.generateBaseName(datapointName);
                    String referenceName = GeneratorUtils.generateReferenceName(document, baseName, nameSuffixes[counter]);

                    parent.setAttribute("classid", "168");
                    parent.setAttribute("bacnetclassid", "5");
                    parent.setAttribute("classVersion", "1.0");
                    parent.setAttribute("ref", referenceName);

                    parent.appendChild(createProperty(document, nameSpace, "6", "enum", "1", "95"));//Alarmwert
                    parent.appendChild(createProperty(document, nameSpace, "17", "unsignedLong", "60", null));//Verzögerungszeit
                    parent.appendChild(createProperty(document, nameSpace, "28", "string", datapointDescription + " " + descriptionSuffixes[counter], null));//Datenpunktbeschreibung
                    parent.appendChild(createProperty(document, nameSpace, "113", "unsignedShort", "80", null));//Meldungsklasse
                    parent.appendChild(createProperty(document, nameSpace, "569", "boolean", "1", null));//Intrinsinc Alarming aktiviert
                    parent.appendChild(createProperty(document, nameSpace, "931", "enum", "95", "801"));
                    parent.appendChild(createProperty(document, nameSpace, "1092", "boolean", "1", null));//Benutzername ist BACnet Obj. Name
                    parent.appendChild(createProperty(document, nameSpace, "2390", "string", baseName + nameSuffixes[counter], null)); //Datenpunktname
                    parent.appendChild(createProperty(document, nameSpace, "32527", "string", referenceName, null));

                    if(nameSuffixes[counter].equals("Fault")){

                    }

                    root.appendChild(parent);

                }
            }
            else  if (checkedValue.contains("RM")){
                   Element parent = document.createElementNS(nameSpace, "object");
                   String baseName = GeneratorUtils.generateBaseName(datapointName);
                   String referenceName = GeneratorUtils.generateReferenceName(document, baseName, "RTF");

                   parent.setAttribute("classid", "168");
                   parent.setAttribute("bacnetclassid", "5");
                   parent.setAttribute("classVersion", "1.0");
                   parent.setAttribute("ref", referenceName);

                   parent.appendChild(createProperty(document, nameSpace, "28", "string", datapointDescription + " " + "Laufzeit", null));    // Beschreibung
                   parent.appendChild(createProperty(document, nameSpace, "931", "enum", "95", "801"));                       // Standardzustand / Enum
                   parent.appendChild(createProperty(document, nameSpace, "2390", "string", baseName + "RTF", null));  // Name
                   parent.appendChild(createProperty(document, nameSpace, "32527", "string", referenceName, null));                // Referenz

                   root.appendChild(parent);

            }
        }
    }
    /**
     * <property id="xxx"><data><type>value</type></data></property>
     */
    private Element createProperty(Document document, String nameSpace,  String id, String type, String value, String set) {
        Element property = document.createElementNS(nameSpace, "property");
        property.setAttribute("id", id);

        Element data = document.createElementNS(nameSpace, "data");
        Element val = document.createElementNS(nameSpace, type);
        val.setTextContent(value);

        if (set != null) {
            val.setAttribute("set", set);
          // val.setTextContent(value);
        }

        data.appendChild(val);
        property.appendChild(data);
        return property;
    }
}

