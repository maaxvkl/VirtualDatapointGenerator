package vdgenerator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vdgenerator.service.GeneratorService;
import java.io.File;
import java.io.FileOutputStream;

public class GeneratorController {

    GeneratorService generatorService;
    @FXML
    private Button selectInputButton;
    @FXML
    private Label selectedInputLabel;
    @FXML
    private Button selectReferenceButton;
    @FXML
    private Label selectedReferenceLabel;
    @FXML
    private Button processButton;
    private File hardwareFolder;
    private File virtualFolder;

    public GeneratorController(){
    }

    @FXML
    public void handleSelectInputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Hardware Points ZIP Folder");
        hardwareFolder = fileChooser.showOpenDialog(new Stage());
        if (hardwareFolder != null) {
            selectedInputLabel.setText(hardwareFolder.getAbsolutePath());
        }
        updateProcessButtonState();
    }

    @FXML
    public void handleSelectOutputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Virtual Points ZIP Folder");
        virtualFolder = fileChooser.showOpenDialog(new Stage());
        if (virtualFolder != null) {
            selectedReferenceLabel.setText(virtualFolder.getAbsolutePath());
        }
        updateProcessButtonState();
    }

    @FXML
    public void handleProcessFile() {
        try {
            if (hardwareFolder == null || virtualFolder == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Missing files");
                alert.setContentText("Please select both input and reference files.");
                alert.showAndWait();
                return;
            }
            generatorService = new GeneratorService();
            generatorService.loadInputFile(hardwareFolder);
            byte[] processedData = generatorService.generateOutputFile(virtualFolder);

            FileChooser saveFileChooser = new FileChooser();
            saveFileChooser.setTitle("Save Processed File");
            String baseName = virtualFolder.getName();
            String fileName = baseName.replaceFirst("[.][^.]+$", "");
            saveFileChooser.setInitialFileName(fileName +".txt");
            File processedFile = saveFileChooser.showSaveDialog(new Stage());
            if (processedFile != null) {
                try (FileOutputStream fos = new FileOutputStream(processedFile)) {
                    fos.write(processedData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    public void initialize() {
        updateProcessButtonState();
    }

    private void updateProcessButtonState() {
        boolean enabled = hardwareFolder != null && virtualFolder != null;
        processButton.setDisable(!enabled);
    }
}
