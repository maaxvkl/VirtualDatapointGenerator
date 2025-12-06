package vdgenerator.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class GeneratorUtils {

    public static String generateReferenceName(Document document, String baseName, String nameSuffixes){
        Element firstObject = (Element) document.getElementsByTagName("object").item(0);
        String referenceBase = firstObject.getAttribute("ref");
        String systemDescription = baseName.substring(19,19 + 3); //zb HKR
        String systemNumber = baseName.substring(23, 23 + 3);//zb 64
        String valueType;
        if(baseName.substring(27, 27 + 3).endsWith("_")){
            valueType = baseName.substring(27, 27 + 2);//zb VL
        }
        else {
            valueType = baseName.substring(27, 27 + 3);//zb WRG
        }
        String valueNumber = baseName.substring(31, 31 + 2);// zb 02
        return referenceBase +  "." + systemDescription + systemNumber + "_"  + valueType + "_" + valueNumber + nameSuffixes;
    }

    public static String valueCheck(String datapointName) {
        int start;
        if(datapointName.endsWith("MW")){
            start = datapointName.lastIndexOf("MW");
            return datapointName.substring(start, start +2);
        }
        else if (datapointName.endsWith("RM")){
            start = datapointName.lastIndexOf("RM");
            return datapointName.substring(start, start +2);
        }
        else {
            return " ";
        }
    }

    public static String generateBaseName(String datapointName){
        return datapointName.substring(0, datapointName.length()-2);
    }

}
