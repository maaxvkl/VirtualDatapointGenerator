package vdgenerator.filehandling;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vdgenerator.utility.GeneratorUtils;
import vdgenerator.values.DefaultValues;
import java.util.List;


public class WriteAnalogValues {

    DefaultValues defaultValues = new DefaultValues();
    SavedInputs savedInputs = new SavedInputs();

    public void addAnalogValues(Document document, List<Element> analogInputs) {

        Element root = document.getDocumentElement();
        String nameSpace = root.getNamespaceURI();

        String[] nameSuffixes = {"LimitLL", "LimitL", "LimitH", "LimitHH"};
        String[] descriptionSuffixes = {"LimitLowLow", "LimitLow", "LimitHigh", "LimitHighHigh"};

        for (Element element : analogInputs) {
             String datapointName = savedInputs.getDatapointName(element);
             String datapointDescription = savedInputs.getDatapointDescription(element);
             String checkedValue = GeneratorUtils.valueCheck(datapointName);
             String[] valueSetpoints = getValueSetpoints(datapointDescription);

               if(checkedValue.contains("MW")) {
                  for (int counter = 0; counter < nameSuffixes.length; counter++) {
                      Element parent = document.createElementNS(nameSpace, "object");
                      String referenceName = GeneratorUtils.generateReferenceName(document, datapointName, nameSuffixes[counter]);
                      String baseName = GeneratorUtils.generateBaseName(datapointName);
                      String valueSetpoint = valueSetpoints[counter];

                      parent.setAttribute("classid", "165");
                      parent.setAttribute("bacnetclassid", "2");
                      parent.setAttribute("classVersion", "1.0");
                      parent.setAttribute("ref", referenceName);

                      parent.appendChild(createProperty(document, nameSpace, "22", "float", "0.2", null));//COV inkrement
                      parent.appendChild(createProperty(document, nameSpace, "28", "string", datapointDescription + " " + descriptionSuffixes[counter], null));
                      parent.appendChild(createProperty(document, nameSpace, "104", "float", valueSetpoint, null));
                      parent.appendChild(createProperty(document, nameSpace, "117", "enum", "62", "507"));
                      parent.appendChild(createProperty(document, nameSpace, "1092", "boolean", "1", null));//Benutzername ist BACnet Obj. Name
                      parent.appendChild(createProperty(document, nameSpace, "2390", "string", baseName + nameSuffixes[counter], null));
                      parent.appendChild(createProperty(document, nameSpace, "3113", "float", valueSetpoint, null));
                      parent.appendChild(createProperty(document, nameSpace, "32527", "string", referenceName, null));

                      root.appendChild(parent);
                  }
               }
            }
        }

    // <property id="xxx"><data><type>value</type></data></property>
    private Element createProperty(Document document, String nameSpace,  String id, String type, String value, String set) {
        Element property = document.createElementNS(nameSpace, "property");
        property.setAttribute("id", id);

        Element data = document.createElementNS(nameSpace, "data");
        Element val = document.createElementNS(nameSpace, type);
        val.setTextContent(value);

        if (set != null) {
            val.setAttribute("set", set);
        }
        data.appendChild(val);
        property.appendChild(data);
        return property;
    }

    private String[] getValueSetpoints (String datapointDescription){
        String descriptionSubstring = datapointDescription.substring(35).replaceAll("\\.", "").replaceAll(" ", "");
        System.out.println(descriptionSubstring);
        String[] fallbackValues = {"0", "0", "0", "0"};
        switch(descriptionSubstring){
            case "ZU-Temp" : return defaultValues.getZUTempValues();
            case "AB-Temp" : return defaultValues.getABTempValues();
            case "AU-Temp" : return defaultValues.getAUTempValues();
            case "ML-Temp" : return defaultValues.getMLTempValues();
            case "Bypass-Temp" : return defaultValues.getBypassTempValues();
            case "FO-Temp" : return defaultValues.getFOTempValues();
            case "ZU-TempnWRG" : return defaultValues.getZUnWRGTempValues();
            case "ZU-TempvorKuehler", "ZU-TempvKuehlregister": return defaultValues.getZUvKuehlerTempValues();
            case "VE-NTVL-Temp", "VE-NTRL-Temp" : return defaultValues.getNTTempValues();
            case "NE-HTVL-Temp", "NE-HTRL-Temp", "Bypass-ErhVL-Temp", "Bypass-ErhRL-Temp" : return defaultValues.getHTTempValues();
            case "KuehlerVL-Temp", "KuehlerRL-Temp" : return defaultValues.getCoolingTempValues();
            default :  return fallbackValues;
        }
    }

}

