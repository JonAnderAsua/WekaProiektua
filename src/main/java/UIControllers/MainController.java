package UIControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane IragarpenakPaneId;

    @FXML
    private AnchorPane EstatistikakPaneId;

    @FXML
    private AnchorPane IragarpenakTextPane;


    @FXML
    void initialize() {
        EstatistikakPaneId.toBack();
        IragarpenakTextPane.toBack();
    }

    @FXML
    void iragarpenakPestañaClick(ActionEvent event){
        EstatistikakPaneId.toBack();
    }
    @FXML
    void datuakPestañaClick(ActionEvent event) {
        EstatistikakPaneId.toFront();
    }

    void testuaErakutsi(){
        System.out.println(IragarpenakTextPane);
        IragarpenakTextPane.toFront();
    }
}
