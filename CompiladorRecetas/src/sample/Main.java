package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import sample.controllers.Splash;


public class Main extends Application {
    public  static int duracion=10000;
    public  static int steps=1;

    @Override
    public void init() throws Exception {
        for(int i=0;i<duracion;i++){
            double progress=(100*i)/duracion;
            LauncherImpl.notifyPreloader(this,
                    new Splash.ProgressNotification(progress));
            System.out.println((100*i)/duracion);
        }//llave for
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/principal.fxml"));
        primaryStage.setTitle("Repsy");
        primaryStage.setScene(new Scene(root, 970, 600));
      //  primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
    }


    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class,Splash.class,args);
        //launch(args);
    }
}
