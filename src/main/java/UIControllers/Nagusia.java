package UIControllers;

import UIControllers.IragarpenakController;
import UIControllers.IragarpenakTextController;
import UIControllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;

public class Nagusia extends Application {
    private Parent root;
    private Stage stage;
    private Parent root2;

    private Parent iragarpenakUI;

    private MainController mCont;
    private IragarpenakController iraCont;
    private IragarpenakTextController iTextContr;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadUI(primaryStage);
        stageSetup();
        stage.show();
    }

    private void loadUI(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        root = loader.load();
        mCont = loader.getController();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Iragarpenak.fxml"));
        iragarpenakUI = loader2.load();
        iraCont = loader2.getController();
        iraCont.setNagusia(this);

        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/IragarpenakTestua.fxml"));
        root2 = loader3.load();
        iTextContr = loader3.getController();
    }

    private void stageSetup(){
        stage.setScene(new Scene(root));
        stage.setTitle("Pantaila nagusia");
        stage.setResizable(false);
    }


    public void erakutsiTestua() {
        mCont.testuaErakutsi();
    }
}
