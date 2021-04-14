package UIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;

public class IragarpenakController {

    @FXML
    private TextField textLabelId;

    @FXML
    private Label iragarpenaLabelId;


    @FXML
    private Button iragarpenaButton;

    @FXML
    private Button ezabatuButton;



    @FXML
    private Button fitxategiaSartuButton;


    @FXML
    void ezabatuClick(ActionEvent event) {
        textLabelId.setText("");
    }

    @FXML
    void fitxategiaSartuClick(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT fitxategiak", "*.txt")
        );
        File aukeratua = fileChooser.showOpenDialog(fitxategiaSartuButton.getScene().getWindow());
        Reader targetReader = new FileReader(aukeratua);
        BufferedReader reader = new BufferedReader(targetReader);

        Thread taskThread = new Thread(() -> {
            String lineaBerria;
            try {

                lineaBerria= reader.readLine();

                while (lineaBerria != null) {
                    iragarpenaEgin(lineaBerria);
                    Thread.sleep(3000);
                    lineaBerria= reader.readLine();

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }



        });
        taskThread.start();

    }

    @FXML
    void iragarpenaClick(ActionEvent event) {
        String testua = textLabelId.getText();
        iragarpenaEgin(testua);
        String iragarpena = "";
        //Sacar la iragarpena
        iragarpenaLabelId.setText(iragarpena);
    }


    @FXML
    void initialize() {
    }

    void iragarpenaEgin(String s){
        iragarpenaLabelId.setText("Tonto");
    }
}
