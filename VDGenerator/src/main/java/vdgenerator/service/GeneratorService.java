package vdgenerator.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vdgenerator.filehandling.*;
import java.io.File;
import java.util.List;


public class GeneratorService {

    public GeneratorService() {
    }

    private final ReadXMLFile xmlReader = new ReadXMLFile();
    private final WriteAnalogValues analogWriter = new WriteAnalogValues();
    private final WriteBinaryValues binaryWriter = new WriteBinaryValues();
    private final SaveXMLFile xmlSaver = new SaveXMLFile();
    private final SavedInputs savedInputs = new SavedInputs();

    public void loadInputFile(File inputFile) throws Exception {
        Document document = xmlReader.loadXmlFromZip(inputFile);
        savedInputs.saveAnalogInputs(document);
    }

    public byte[] generateOutputFile(File outputFile) throws Exception {
        Document document = xmlReader.loadXmlFromZip(outputFile);
        List<Element> analogInputs = savedInputs.getAnalogInputs();
        analogWriter.addAnalogValues(document, analogInputs);
        binaryWriter.addBinaryValues(document, analogInputs);
        return xmlSaver.saveXML(document);
    }
}


