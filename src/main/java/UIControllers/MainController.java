package UIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField textLabelId;

    @FXML
    private Label ehunekoSPAMId;

    @FXML
    private Label ehunekoEzSPAMId;

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
    void fitxategiaSartuClick(ActionEvent event) {

    }

    @FXML
    void iragarpenaClick(ActionEvent event) {

    }


    @FXML
    void initialize() {
       ehunekoEzSPAMId.setText("");
       ehunekoSPAMId.setText("");
    }
}
