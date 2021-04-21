package UIControllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Nagusia extends Application {
    private Parent root;
    private Stage stage;

    private Parent iragarpenakUI;

    private MainController mCont;
    private IragarpenakController iraCont;

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
        iraCont.setMainController(mCont);

    }

    private void stageSetup(){
        stage.setScene(new Scene(root));
        stage.setTitle("Pantaila nagusia");
        stage.setResizable(false);
    }

}
