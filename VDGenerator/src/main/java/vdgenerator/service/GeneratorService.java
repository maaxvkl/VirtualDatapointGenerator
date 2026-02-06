package vdgenerator.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vdgenerator.filehandling.*;
import java.io.File;
import java.util.List;


public class GeneratorService {

    private final ReadXMLFile xmlReader;
    private final WriteAnalogValues analogWriter;
    private final WriteBinaryValues binaryWriter;
    private final SaveXMLFile xmlSaver;
    private final SavedInputs savedInputs;

    public GeneratorService() {
        this.xmlReader = new ReadXMLFile();
        this.analogWriter = new WriteAnalogValues();
        this.binaryWriter = new WriteBinaryValues();
        this.xmlSaver = new SaveXMLFile();
        this.savedInputs = new SavedInputs();
    }

    
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


