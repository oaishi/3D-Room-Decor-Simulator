/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import static termproject.Room.path;

/**
 *
 * @author Faria huq
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      ImageView  shelfpic = new ImageView();
      shelfpic.setFitWidth(80);
      shelfpic.setFitHeight(80);
      shelfpic.setLayoutY(30+10);
      shelfpic.setLayoutX(30+10);
      Image img= new Image("/shelf2.jpg");
      shelfpic.setImage( img);
        
      StackPane root = new StackPane();
      root.getChildren().add(shelfpic);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        String path = System.getProperty("user.dir")+"/src";
        System.out.println(path);  
        launch(args);
    }
    
}
