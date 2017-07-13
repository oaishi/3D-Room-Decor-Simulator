/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 *
 * @author Faria huq
 */
public class NewFXMain extends Application {
    
    
    public AnchorPane menu() 
    {
       // String path = null;
        
        AnchorPane pane = new AnchorPane();
        pane.setMaxHeight(-Infinity);
        pane.setMaxWidth(-Infinity);
        pane.setMinHeight(-Infinity);
        pane.setMinWidth(-Infinity);
        pane.setPrefHeight(500);
        pane.setPrefWidth(500);
        
        ScrollPane inpane = new ScrollPane();
        inpane.setPrefSize(500, 500);
        AnchorPane.setTopAnchor(inpane, 0.00);
        AnchorPane.setLeftAnchor(inpane, 0.00);
        AnchorPane.setRightAnchor(inpane, 0.00);
        AnchorPane.setBottomAnchor(inpane, 0.00);

        AnchorPane ininpane = new AnchorPane();
        ininpane.setPrefSize(1000, 486);
        
        Label wall2 = new Label("1. press " + "Z " + "to zoom"  + "\t" + "2.press" + " N" + " to unzoom" + "\n" + "3. press " + "B " + "to rotate anticlockwise" +"\t" +"4. press " + "M " + "to rotate clockwise" + "\n" + "5. press " + "C " + "to translate in positive z" +"\t" + "6. press " + "X " + "to translate in negative z"+ "\n" + "7 . press" + " I " + "to bring initial state"  + "\n" + "8 . press" + " Y " + "to transalte in positive y "+"\t" + "9 . press " + "U" + " to translate in negative y" + "\n" + " 10 . press" + " S  and D " + "to rotate in Z axis" + "\n" + "11. press  F and G in rotate in X axis" );
        wall2.setPrefSize(1000,150);
        wall2.setLayoutX(20);
        wall2.setLayoutY(6);
        wall2.setTextFill(Color.CHOCOLATE);
        wall2.setStyle("-fx-font: 18 arial;");
        ininpane.getChildren().add(wall2);
        wall2.setStyle("-fx-background-color : #b3ccff");
                
        ScrollPane in3pane = new ScrollPane();
        in3pane.setPrefSize(402, 228);
        in3pane.setLayoutX(20);
        in3pane.setLayoutY(265+150);
        
        AnchorPane in4pane = new AnchorPane();
        in4pane.setPrefSize(520 , 520);
        in4pane.setMaxHeight(-Infinity);
        in4pane.setMaxWidth(-Infinity);
        in4pane.setMinHeight(-Infinity);
        in4pane.setMinWidth(-Infinity);
        in4pane.setStyle("-fx-background-color : #e6e6cc");
        int wp =4;
        ImageView []wallpic = new ImageView[wp];
        for(int i=0;i<wp;i++)
        {
        wallpic[i] = new ImageView();
        wallpic[i].setFitWidth(80);
        wallpic[i].setFitHeight(80);
        wallpic[i].setLayoutY(30+ (80*(i/4))+4);
        wallpic[i].setLayoutX(30 + (80*(i%4))+4);
        Image img = new Image("/BinaryContent/wallpaper/" + (i+1) + ".jpg");
        wallpic[i].setImage(img);
        in4pane.getChildren().add(wallpic[i]);
        }        
        in3pane.setContent(in4pane);
        
        ScrollPane in7pane = new ScrollPane();
        in7pane.setPrefSize(300, 200);
        in7pane.setLayoutX(20);
        in7pane.setLayoutY(906+150);
      
        AnchorPane in8pane = new AnchorPane();
        in8pane.setPrefSize(300 , 200);
        in8pane.setMaxHeight(-Infinity);
        in8pane.setMaxWidth(-Infinity);
        in8pane.setMinHeight(-Infinity);
        in8pane.setMinWidth(-Infinity);
        in8pane.setStyle("-fx-background-color : #e6b3e6");
        
        ImageView []windowpic = new ImageView[3];
        for(int i=0;i<3;i++)
        {
        windowpic[i] = new ImageView();
        windowpic[i].setFitWidth(80);
        windowpic[i].setFitHeight(80);
        windowpic[i].setLayoutY(30+10);
        windowpic[i].setLayoutX(30+ (80*i)+10);
        Image img= new Image("/BinaryContent/window/" + (i+1) + ".png");
        windowpic[i].setImage( img);
        in8pane.getChildren().add(windowpic[i]);
        }
        
        in7pane.setContent(in8pane);
        
        
        ininpane.getChildren().addAll(in3pane,in7pane);
        inpane.setContent(ininpane);
        pane.getChildren().add(inpane);
        
        return pane;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        String filePath = new File("").getAbsolutePath();
        String sush = filePath.replace("\\", "\\\\");
        System.out.println(sush);
        BufferedReader br = new BufferedReader(new FileReader(sush + "\\src\\TextContent\\file_version.txt"));
        String s = br.readLine();
        String []in = s.split("-");
        int wp = Integer.parseInt(in[0]);
        System.out.println(wp);
        int tl = Integer.parseInt(in[1]);
        System.out.println(tl);
        Scene scene = new Scene(menu(), 300, 250);
       AnchorPane root = new AnchorPane();
       ImageView img = new ImageView();
       
       img.setImage(new Image("/BinaryContent/wallpaper/1.jpg"));
       root.getChildren().add(img);
       // Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          launch(args);
    }
    
}
