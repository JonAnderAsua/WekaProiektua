package UIControllers;

import EntregaHiru.Predictions;
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
    private Nagusia nagusia = new Nagusia();
    private MainController mc = new MainController();


    @FXML
    void ezabatuClick(ActionEvent event) {
        textLabelId.setText("");
    }

    @FXML
    void iragarpenaClick(ActionEvent event) throws Exception {
        String testua = textLabelId.getText();
        String iragarpena = iragarpenaEgin(testua);
        iragarpenaLabelId.setText(iragarpena);
    }


    @FXML
    void initialize() {

    }

    private String iragarpenaEgin(String s) throws Exception {
        Predictions iragarpenak = new Predictions();
        String emaitza = iragarpenak.iragarpenakAtera(s);
        return emaitza;
    }

    public void setNagusia(Nagusia nag) {
        nagusia = nag;
    }

    public void setMainController(MainController mCont) {
        mc = mCont;
    }
}
