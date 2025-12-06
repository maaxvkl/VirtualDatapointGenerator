package vdgenerator.filehandling;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ReadXMLFile {

    public ReadXMLFile() {
    }

    public Document loadXmlFromZip(File zipFile) throws Exception {
    try (FileInputStream fis = new FileInputStream(zipFile);
         ZipInputStream zis = new ZipInputStream(fis)) {

        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            if (name.endsWith("export.xml")) {
                return parseXmlFromStream(zis);
            }
            zis.closeEntry();
        }
    }

    throw new IllegalArgumentException("Keine export.xml in ZIP-Datei gefunden: " + zipFile.getName());
}

    private Document parseXmlFromStream(InputStream inputStream) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(inputStream);
        document.getDocumentElement().normalize();
        return document;
    }
}


