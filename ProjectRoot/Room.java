/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package termproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static jdk.nashorn.internal.objects.Global.Infinity;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

 class Xform extends Group {

    public Translate t = new Translate();
    
    public Rotate rx = new Rotate();
    {
        rx.setAxis(Rotate.X_AXIS);
    }
    
    public Rotate ry = new Rotate();
    {
        ry.setAxis(Rotate.Y_AXIS);
    }
    
    public Rotate rz = new Rotate();
    {
        rz.setAxis(Rotate.Z_AXIS);
    }
    
    public Scale s = new Scale();

    public Xform() {
        super();
        getTransforms().addAll(t, rz, ry, rx, s);
    }

    public void setTx(double x) {
        t.setX(x);
    }

    public void setTy(double y) {
        t.setY(y);
    }

    public void setTz(double z) {
        t.setZ(z);
    }

    public void setRotateX(double x) {
        rx.setAngle(x);
    }

    public void setRotateY(double y) {
        ry.setAngle(y);
    }

    public void setRotateZ(double z) {
        rz.setAngle(z);
    }
    
    public void setScale(double x, double y, double z) {
        s.setX(x);
        s.setY(y);
        s.setZ(z);
    }

    @Override
    public String toString() {
        return "Xform[t = ("
                + t.getX() + ", "
                + t.getY() + ", "
                + t.getZ() + ")  "
                + "r = ("
                + rx.getAngle() + ", "
                + ry.getAngle() + ", "
                + rz.getAngle() + ")  ";
    }
}
/**
 *
 * @author faria huq
 */
public class Room extends Application {

    Group root = new Group();
    Group AnotherRoot = new Group();
    AnchorPane pane;
    Xform room = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform axisGroup = new Xform();
    private static final double AXIS_LENGTH = 6250.0;
    double CAMERA_INITIAL_DISTANCE = 0;//-675;
    double CAMERA_INITIAL_Y_DISTANCE = 0;
    double CAMERA_INITIAL_X_DISTANCE = 0;
    private static final double CAMERA_INITIAL_X_ANGLE = 0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double MOUSE_SPEED = 0.1;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    double length2;
    double width2;
    double height2;
    double negx , posx , negy , posy , onlyz ;
    double speed = 5;
    double modifier = 1.0;
    int wallno ;
    int wp , tl;
    double mapX,mapY,mapZ;
    double l,w,h;
    double jx,jy,jz , boundx,boundy,boundz;
    Image image[] = new Image[6];
    PhongMaterial barmat[] = new PhongMaterial[6];
    Box barcube[] = new Box[6];
    public void MATERIAL()
    {
       
        barmat[0] = new PhongMaterial(Color.ALICEBLUE);
        image[0] = new Image("/BinaryContent/wallpaper/" + "1.jpg");
        image[0].isPreserveRatio();
        barmat[0].setDiffuseMap(image[0]);

        barmat[1] = new PhongMaterial(Color.GHOSTWHITE);
        
        barmat[2] = new PhongMaterial(Color.SNOW);
        //back
        barmat[3] = new PhongMaterial(Color.ANTIQUEWHITE);
        image[3] =new Image("/BinaryContent/wallpaper/" +2 + ".jpg");
        barmat[3].setDiffuseMap(image[3]);
        //top
        barmat[4] = new PhongMaterial(Color.SKYBLUE);
        //floor
        barmat[5] = new PhongMaterial(Color.CORNSILK);
        image[5] =new Image("/BinaryContent/tiles/"+1 + ".jpg");
        image[5].isPreserveRatio();
        barmat[5].setDiffuseMap(image[5]);
    }
    
 private void createContent(double Height1, double Width1, double Length1) {
      
        double barWidth = Width1;
        double barDepth = 1.0;
        double barHeight = Height1;
        double barLength = Length1;
       
        int setpostionX,setpostionY,setpostionZ;
        setpostionX=0;
        setpostionY=0;
        setpostionZ=0;
        this.height2 = Height1;
        this.length2 = Length1;
        this.width2 = Width1;
  
        //FRONT ONE
        barcube[0] = new Box( barLength,  barHeight,  barDepth);
        barcube[0].setMaterial(barmat[0]);
        barcube[0].setTranslateX(0+setpostionX);
        barcube[0].setTranslateZ(0+setpostionZ);
        barcube[0].setTranslateY(0+setpostionY);
       
        //RIGHT ONE
        barcube[1] = new Box(barDepth, barHeight, barWidth);
        barcube[1].setMaterial(new PhongMaterial(Color.BLUE));
        barcube[1].setTranslateX(-(barLength/2)-(barDepth/2)+setpostionX); // negx
        barcube[1].setTranslateZ(-(barWidth/2)+(barDepth/2)+setpostionZ); //z
        barcube[1].setTranslateY(0+setpostionY);
        
       //LEFT ONE   
       barcube[2] = new Box(barDepth, barHeight, barWidth);
       barcube[2].setMaterial(barmat[2]);
       barcube[2].setTranslateX((barLength/2)+(barDepth/2)+setpostionX);  // posx
       barcube[2].setTranslateZ(-(barWidth/2)+(barDepth/2)+setpostionZ); // z
       barcube[2].setTranslateY(0+setpostionY);
       
       //back one
        barcube[3]=new Box(barLength,  barHeight,  barDepth);
        barcube[3].setTranslateX(0+setpostionX);
        barcube[3].setTranslateZ(-(barWidth));
        barcube[3].setTranslateY(0+setpostionY); 
        barcube[3].setMaterial(barmat[3]);
        
        //CEILING       
        barcube[4] = new Box(barLength+(barDepth*2), barDepth, barWidth);
        barcube[4].setMaterial(barmat[4]);
        barcube[4].setTranslateY((barHeight/2)+(barDepth/2)+setpostionY); // posy
        barcube[4].setTranslateZ(-(barWidth/2)+(barDepth/2)+setpostionZ); // z
        barcube[4].setTranslateX(setpostionX);
       
        //FLOOR       
        barcube[5] = new Box(barLength+(barDepth*2), barDepth, barWidth);
        barcube[5].setMaterial(barmat[5]);
        barcube[5].setTranslateY(-(barHeight/2)-(barDepth/2)+setpostionY); // negy
        barcube[5].setTranslateZ(-(barWidth/2)+(barDepth/2)+setpostionZ); //z
        barcube[5].setTranslateX(setpostionX);
        
        this.posx = (barLength/2)+(barDepth/2)+setpostionX ;
        this.negx = -(barLength/2)-(barDepth/2)+setpostionX ;
        this.posy = (barHeight/2)+(barDepth/2)+setpostionY ;
        this.negy = -(barHeight/2)-(barDepth/2)+setpostionY;
        this.onlyz= -(barWidth/2)+(barDepth/2)+setpostionZ;
      
        room.getChildren().addAll(barcube[0],barcube[1],barcube[2],barcube[3],barcube[4],barcube[5]);
    }
    
    private void buildCamera() {
        System.out.println("buildCamera()");
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        cameraXform.setRotateZ(180.0);
        CAMERA_INITIAL_DISTANCE = - (width2/2);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        camera.setTranslateY(CAMERA_INITIAL_Y_DISTANCE);
        camera.setTranslateX(CAMERA_INITIAL_X_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
        cameraXform.t.setZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.t.setY(CAMERA_INITIAL_Y_DISTANCE);
        cameraXform.t.setX(CAMERA_INITIAL_X_DISTANCE);
    }
    
    private void buildAxes() {
        
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
 
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
 
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
 
        final Box xAxis = new Box(AXIS_LENGTH, 10, 10);
        final Box yAxis = new Box(10, AXIS_LENGTH, 10);
        final Box zAxis = new Box(10, 10, AXIS_LENGTH);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(false);
        room.getChildren().addAll(axisGroup);
        
    }
    
    private void handleMouse(Scene scene, final Node root) {
        
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            if(me.isSecondaryButtonDown())
                System.out.println("here");  
        });
        
    }
    
    private void handleKeyboard(Scene scene, final Node root) {
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            double x,newx,y,newy,z,newz;
            switch (event.getCode()) {
                case I:
                    camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                    camera.setTranslateY(CAMERA_INITIAL_Y_DISTANCE);
                    camera.setTranslateX(CAMERA_INITIAL_X_DISTANCE);
                    cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                    cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                    cameraXform.t.setZ(CAMERA_INITIAL_DISTANCE);
                    cameraXform.t.setY(CAMERA_INITIAL_Y_DISTANCE);
                    cameraXform.t.setX(CAMERA_INITIAL_X_DISTANCE);
                    break;
                    
                 case A:
                    axisGroup.setVisible(!axisGroup.isVisible());
                    break;    
                case V:
                    room.setVisible(!room.isVisible());
                    break;
                    
                case X:
                      x = camera.getTranslateX();
                      newx = x - speed*modifier; 
                      System.out.println("   Transx in neg -" + newx);
                      camera.setTranslateX(newx);
                      break;
                    
                case C:
                       x = camera.getTranslateX();
                       newx = x + speed*modifier; 
                       System.out.println("   TRANSX   in pos -" + newx);
                      camera.setTranslateX(newx);
                    break;
                    
                case Y:
                    //translating in y
                    y = camera.getTranslateY();
                    newy = y + speed*modifier; 
                    System.out.println("   TRANSY in pos -" + newy);
                    camera.setTranslateY(newy);
                    break;
                    
                     case U:
                    y = camera.getTranslateY();
                    newy = y -speed*modifier; 
                    System.out.println("   TRANSY in neg -" + newy);
                    camera.setTranslateY(newy);
                    break;
                    
                    case N:
                    //zooming 
                    z = camera.getTranslateZ();
                    newz = z +speed*modifier;
                    camera.setTranslateZ(newz);
                    System.out.println(newz);
                    break;
                    
                    case Z:
                        //unzooming
                    z = camera.getTranslateZ();
                    newz = z -speed*modifier; 
                    camera.setTranslateZ(newz);
                    System.out.println(newz);
                    break;
       
                    case B:
                    y= cameraXform.ry.getAngle();
                    cameraXform.ry.setAngle(y-10);
                    System.out.println(cameraXform.ry.getAngle());
                    break;
                    
                    case M:
                    y= cameraXform.ry.getAngle();
                    cameraXform.ry.setAngle(y+10);
                    System.out.println(cameraXform.ry.getAngle());
                    break;
                    
                    case S:
                    z= cameraXform.rz.getAngle();
                     if((z-5)>-90)
                    cameraXform.rz.setAngle(z-5);
                    break;
                    
                    case D:
                    z= cameraXform.rz.getAngle();
                     if((z+5)<90)
                    cameraXform.rz.setAngle(z+5);
                    break;
                    
                     case F:
                    x= cameraXform.rx.getAngle();
                    if((x-5)>-90)
                    cameraXform.rx.setAngle(x-5);
                    break;
                    
                    case G:
                    x= cameraXform.rx.getAngle();
                    if((x+5)<90)
                    cameraXform.rx.setAngle(x+5);
                    break;
                    
            }
        });
    }
    public AnchorPane menu() 
    {
        pane = new AnchorPane();
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
                
        Label wall = new Label("PICK A WALL");
        wall.setPrefSize(350,34);
        wall.setLayoutX(20);
        wall.setLayoutY(15+150);
        wall.setTextFill(Color.CRIMSON);
        wall.setStyle("-fx-font: 24 arial;");
        
       RadioButton[] walls= new RadioButton[4];
        for(int move=0;move<4;move++)
        {
            walls[move]= new RadioButton();
            walls[move].setPrefSize(69, 10);
            walls[move].setLayoutY(52+150);
            walls[move].setTranslateX(20+(move*87));
            walls[move].setText("wall" + (move+1));
            ininpane.getChildren().add(walls[move]);
            walls[move].setOnMouseClicked((MouseEvent event) -> {  
                    RadioButton pressedBtn = (RadioButton)event.getSource();
                    
                    for(int mm=0;mm<4;mm++)
                    {
                        if (pressedBtn == walls[mm])
                        {
                           wallno = mm;
                            System.out.println(wallno);
                            break;
                        }
                        
                    }
                }
            );
            }

        ImageView[] wals= new ImageView[4];
        for(int i=0;i<4;i++)
        {
            wals[i]= new  ImageView();
            wals[i].setFitWidth(69);
            wals[i].setFitHeight(69);
            wals[i].setLayoutY(72+150);
            wals[i].setTranslateX(20+(i*87));
            wals[i].setOpacity(0.5);
            ininpane.getChildren().add(wals[i]);
        }
        
        Label til = new Label("PICK A COLOR");
        til.setPrefSize(200,36);
        til.setLayoutX(17);
        til.setLayoutY(145+150);
        til.setTextFill(Color.DEEPPINK);
        til.setStyle("-fx-font: 24 arial;");
        
        ColorPicker color = new ColorPicker();
        color.setPrefSize(90, 25);
        color.setLayoutX(20);
        color.setLayoutY(188+150);
        color.setPromptText("pick a color");
        color.setOnAction((ActionEvent t) -> {
            Color newColor = color.getValue();
            System.out.println(newColor);
            barcube[wallno].setMaterial(new PhongMaterial(newColor));
            walls[wallno].setStyle("-fx-background-color : " + "rgb(" + (newColor.getRed()*255) + "," + (newColor.getGreen()*255) + "," + (newColor.getBlue()*255) + ") ; ");
            wals[wallno].setImage(null);
        });
          
        Label wallp = new Label("PICK A WALLPAPER");
        wallp.setPrefSize(350, 35);
        wallp.setLayoutX(20);
        wallp.setLayoutY(219+150);
        wallp.setTextFill(Color.DODGERBLUE);
        wallp.setStyle("-fx-font: 24 arial;");
    
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
        wallpic[i].setOnMouseClicked((MouseEvent event) -> {
                     barmat[wallno].setDiffuseMap(img);
                     barcube[wallno].setMaterial(barmat[wallno]);
                     wals[wallno].setImage(img);
                      });
        }
       
 
 
        in3pane.setContent(in4pane);
       
        Label til1 = new Label("PICK A TILE");
        til1.setPrefSize(300,36);
        til1.setLayoutX(17);
        til1.setLayoutY(561+150);
        til1.setTextFill(Color.LIMEGREEN);
        til1.setStyle("-fx-font: 24 arial;");
                   
        ScrollPane in5pane = new ScrollPane();
        in5pane.setPrefSize(402, 228);
        in5pane.setLayoutX(20);
        in5pane.setLayoutY(608+150);
      
        AnchorPane in6pane = new AnchorPane();
        in6pane.setPrefSize(520 , 520);
        in6pane.setMaxHeight(-Infinity);
        in6pane.setMaxWidth(-Infinity);
        in6pane.setMinHeight(-Infinity);
        in6pane.setMinWidth(-Infinity);
        in6pane.setStyle("-fx-background-color : #ffe6b3");

        ImageView []tilepic = new ImageView[tl];
        for(int i=0;i<tl;i++)
        {
        tilepic[i] = new ImageView();
        tilepic[i].setFitWidth(80);
        tilepic[i].setFitHeight(80);
        tilepic[i].setLayoutY(30+ (80*(i/4))+10);
        tilepic[i].setLayoutX(30+ (80*(i%4))+10);
        Image img= new Image("/BinaryContent/tiles/"+ (i+1) + ".jpg");
        tilepic[i].setImage( img);
        in6pane.getChildren().add(tilepic[i]);
        tilepic[i].setOnMouseClicked((MouseEvent event) -> {
            System.out.println(img);
                    barmat[5].setDiffuseMap( img);
                     barcube[5].setMaterial(barmat[5]);
                      });
        }
        
        in5pane.setContent(in6pane);
        
        //others
        
        Label other = new Label("ADD WINDOW");
        other.setPrefSize(300,36);
        other.setLayoutX(17);
        other.setLayoutY(850+150);
        other.setTextFill(Color.TOMATO);
        other.setStyle("-fx-font: 24 arial;");
        
        
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
        Image img= new Image("/BinaryContent/window/"  + (i+1) + ".png");
        windowpic[i].setImage( img);
        in8pane.getChildren().add(windowpic[i]);
        windowpic[i].setOnMouseClicked((MouseEvent event) -> {
             System.out.println("here");
             PhongMaterial ph = new PhongMaterial(Color.GHOSTWHITE);
             ph.setDiffuseMap(img);
             switch (wallno) {
                case 0:
                    l=length2/4;
                    w=height2/3;
                    h=30;
                    jx=0;
                    jz=-15;
                    boundx= l/2 ;
                    boundz=0;
                    mapX=1;
                    mapZ=0;
                    break;
                case 1:
                    l=30;
                    w=height2/3;
                    h=width2/3;
                    jx=(negx+15);
                    jz=onlyz;
                    boundz =  h/2 ;
                    boundx=0;
                    mapX=0;
                    mapZ=1;
                   break;
                case 2:
                    l=30;
                    w=height2/3;
                    h=width2/3;
                    jx = (posx-15);
                    jz=onlyz;
                    boundz = h/2 ;
                    boundx=0;
                    mapX=0;
                    mapZ=1;
                    break;
                case 3:
                    l=length2/4;
                    w=height2/3;
                    h=30;
                    jx=0;
                    jz=onlyz+15;
                    boundx= w/2;
                    boundz=0;
                    mapX=1;
                    mapZ=0;
                      break;
            }
               Box box = new Box(l,w,h);
               box.setTranslateX(jx);
               box.setTranslateZ(jz);
               box.setMaterial(ph);
               room.getChildren().add(box);
               System.out.println("box created");
               box.setOnMousePressed((MouseEvent me) -> {
               mousePosX = me.getSceneX();
               mousePosY = me.getSceneY();
                if(me.isSecondaryButtonDown())
                {
                box.mouseTransparentProperty().set(true);
                }
               });
                    box.setOnMouseDragged((MouseEvent me) -> {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        if (me.isPrimaryButtonDown()) {
                        double Y = box.getTranslateY() - mouseDeltaY;
                        if(Y>=(negy+(height2/6)) && Y<=(posy-(height2/6)))
                                box.setTranslateY(Y);
                        double Z = box.getTranslateZ() - (mapZ*mouseDeltaX);
                             if(Z>=(onlyz +boundz) && Z<=(-boundz))
                             box.setTranslateZ(Z);
                        double X = box.getTranslateX() - (mapX*mouseDeltaX);
                             if(X>=(negx + boundx) && X<(posx-boundx))
                             box.setTranslateX(X); 
                        }   
                    }); 
            });
        }
        
        in7pane.setContent(in8pane);
        
        Label fur = new Label("ADD FURNITURE");
        fur.setPrefSize(300,36);
        fur.setLayoutX(17);
        fur.setLayoutY(1148+40+150);
        fur.setTextFill(Color.TOMATO);
        fur.setStyle("-fx-font: 24 arial;");
        
        ScrollPane in9pane = new ScrollPane();
        in9pane.setPrefSize(300, 180);
        in9pane.setLayoutX(20);
        in9pane.setLayoutY(1148+80+150);
      
        AnchorPane in10pane = new AnchorPane();
        in10pane.setPrefSize(300 , 200);
        in10pane.setMaxHeight(-Infinity);
        in10pane.setMaxWidth(-Infinity);
        in10pane.setMinHeight(-Infinity);
        in10pane.setMinWidth(-Infinity);
        in10pane.setStyle("-fx-background-color : #ccffff");
        
        ImageView []furniturepic = new ImageView[2];
        for(int i=0;i<2;i++)
        {
        furniturepic[i] = new ImageView();
        furniturepic[i].setFitWidth(80);
        furniturepic[i].setFitHeight(80);
        furniturepic[i].setLayoutY(30+10);
        furniturepic[i].setLayoutX(30+ (80*i)+10);
        Image img= new Image("/BinaryContent/furniture_" + (i+1) + ".jpg");
        furniturepic[i].setImage( img);
        in10pane.getChildren().add(furniturepic[i]);
        final int m = (i+1);
        furniturepic[i].setOnMouseClicked((MouseEvent event) -> {
            System.out.println(img);
            System.out.println("here");
            PhongMaterial ph = new PhongMaterial(Color.GHOSTWHITE);
            ph.setDiffuseMap(new Image("/BinaryContent/" + "furniture" + m + ".png"));
            switch (wallno) {
                case 0:
                    l=width2/3;
                    w=height2/2;
                    h=10;
                    jx=0;
                    jz=-5;
                    break;
                case 1:
                    l=10;
                    w=height2/2;
                    h=width2/3;
                    jx=(negx+5);
                    jz=onlyz;
                     break;
                case 2:
                    l=10;
                    w=height2/2;
                    h=width2/3;
                    jx = (posx-5);
                    jz=onlyz;
                    break;
                case 3:
                    l=width2/3;
                    w=height2/2;
                    h=10;
                    jx=0;
                    jz=onlyz+5;
                    break;
            }
               Box box = new Box(l,w,h);
               box.setTranslateX(jx);
               box.setTranslateZ(jz);
               box.setLayoutY(- w/2);
               box.setMaterial(ph);
               room.getChildren().add(box);
               System.out.println("box created");
                   box.setOnMousePressed((MouseEvent me) -> {
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        if(me.isSecondaryButtonDown())
                {
                box.mouseTransparentProperty().set(true);
                }
                       });
                    box.setOnMouseDragged((MouseEvent me) -> {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        if (me.isPrimaryButtonDown()) {
                            double oasX = box.getTranslateX() - mouseDeltaX*MOUSE_SPEED*modifier;
                            double oasZ = box.getTranslateZ() - mouseDeltaY*MOUSE_SPEED*modifier;
                            double Y = box.getTranslateY() - mouseDeltaY;
                            if(Y>=(negy) && Y<=(posy))
                            if(oasX>negx && oasX<posx)
                                box.setTranslateX(oasX);
                            if(oasZ>onlyz && oasZ<0)
                                box.setTranslateZ(oasZ);
                        }
                       });
                     });
        }
        
        in9pane.setContent(in10pane);
        
        
        Label vas = new Label("ADD VASE");
        vas.setPrefSize(300,36);
        vas.setLayoutX(17);
        vas.setLayoutY(1148+80 + 220+150);
        vas.setTextFill(Color.TOMATO);
        vas.setStyle("-fx-font: 24 arial;");
        ininpane.getChildren().add(vas);
        
        ScrollPane in11pane = new ScrollPane();
        in11pane.setPrefSize(300, 180);
        in11pane.setLayoutX(20);
        in11pane.setLayoutY(1148+80+ 280+150);
      
        AnchorPane in12pane = new AnchorPane();
        in12pane.setPrefSize(300 , 200);
        in12pane.setMaxHeight(-Infinity);
        in12pane.setMaxWidth(-Infinity);
        in12pane.setMinHeight(-Infinity);
        in12pane.setMinWidth(-Infinity);
        in12pane.setStyle("-fx-background-color : #b399ff");
        
        ImageView []vasepic = new ImageView[3];
        for(int i=0;i<3;i++)
        {
        vasepic[i] = new ImageView();
        vasepic[i].setFitWidth(80);
        vasepic[i].setFitHeight(80);
        vasepic[i].setLayoutY(30+10);
        vasepic[i].setLayoutX(30+ (80*i)+10);
        Image img= new Image("/BinaryContent/"  + "vase" + (i+1) + ".jpg");
        vasepic[i].setImage( img);
        in12pane.getChildren().add(vasepic[i]);
        final int m = (i+1);
        vasepic[i].setOnMouseClicked((MouseEvent event) -> {
            System.out.println(img);
            System.out.println("here");
            PhongMaterial ph = new PhongMaterial(Color.GHOSTWHITE);
            ph.setDiffuseMap(new Image("/BinaryContent/" + "vase" + m + ".png"));
            switch (wallno) {
                case 0:
                    l=500;
                    w=1000;
                    h=10;
                    jx=0;
                    jz=-5;
                    break;
                case 1:
                    l=10;
                    w=height2/2;
                    h=500;
                    jx=(negx+5);
                    jz=onlyz;
                     break;
                case 2:
                    l=10;
                    w=height2/2;
                    h=500;
                    jx = (posx-5);
                    jz=onlyz;
                    break;
                case 3:
                    l=500;
                    w=height2/2;
                    h=10;
                    jx=0;
                    jz=onlyz+5;
                    break;
            }
               Box box = new Box(l,1000,h);
               box.setTranslateX(jx);
               box.setTranslateZ(jz);
               box.setLayoutY(- w/2);
               box.setMaterial(ph);
               room.getChildren().add(box);
               System.out.println("box created");
                   box.setOnMousePressed((MouseEvent me) -> {
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        if(me.isSecondaryButtonDown())
                {
                box.mouseTransparentProperty().set(true);
                }
                       });
                    box.setOnMouseDragged((MouseEvent me) -> {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        if (me.isPrimaryButtonDown()) {
                            double oasX = box.getTranslateX() - mouseDeltaX*MOUSE_SPEED*modifier;
                            double oasZ = box.getTranslateZ() - mouseDeltaY*MOUSE_SPEED*modifier;
                            if(oasX>negx && oasX<posx)
                                box.setTranslateX(oasX);
                            if(oasZ>onlyz && oasZ<0)
                                box.setTranslateZ(oasZ);
                        }
                       });
                     });
        }
        
        in11pane.setContent(in12pane);
        
        Label shel = new Label("ADD SHELF");
        shel.setPrefSize(300,36);
        shel.setLayoutX(17);
        shel.setLayoutY(1148+80 + 2*220 + 50+150);
        shel.setTextFill(Color.TOMATO);
        shel.setStyle("-fx-font: 24 arial;");
        ininpane.getChildren().add( shel);
        
        ScrollPane in13pane = new ScrollPane();
        in13pane.setPrefSize(300, 180);
        in13pane.setLayoutX(20);
        in13pane.setLayoutY(1148+80+ 2*280+150);
      
        AnchorPane in14pane = new AnchorPane();
        in14pane.setPrefSize(600 , 200);
        in14pane.setMaxHeight(-Infinity);
        in14pane.setMaxWidth(-Infinity);
        in14pane.setMinHeight(-Infinity);
        in14pane.setMinWidth(-Infinity);
        in14pane.setStyle("-fx-background-color : #ffe6cc");
        
        ImageView []shelfpic = new ImageView[4];
        for(int i=0;i<4;i++)
        {
        shelfpic[i] = new ImageView();
        shelfpic[i].setFitWidth(80);
        shelfpic[i].setFitHeight(80);
        shelfpic[i].setLayoutY(30+10);
        shelfpic[i].setLayoutX(30+ (80*i)+10);
        Image img= new Image("/BinaryContent/" + "shelf" + (i+1) + ".jpg");
        shelfpic[i].setImage( img);
        in14pane.getChildren().add(shelfpic[i]);
        final int m = (i+1);
        shelfpic[i].setOnMouseClicked((MouseEvent event) -> {
            System.out.println(img);
            System.out.println("here");
            PhongMaterial ph = new PhongMaterial(Color.GHOSTWHITE);
            ph.setDiffuseMap(new Image( "/BinaryContent/" + "shelf" + m + ".png"));
            switch (wallno) {
                case 0:
                    l=1000;
                    w=height2/2;
                    h=10;
                    jx=0;
                    jz=-5;
                    break;
                case 1:
                    l=10;
                    w=height2/2;
                    h=1000;
                    jx=(negx+5);
                    jz=onlyz;
                     break;
                case 2:
                    l=10;
                    w=height2/2;
                    h=1000;
                    jx = (posx-5);
                    jz=onlyz;
                    break;
                case 3:
                    l=1000;
                    w=height2/2;
                    h=10;
                    jx=0;
                    jz=onlyz+5;
                    break;
            }
               Box box = new Box(l,1000,h);
               box.setTranslateX(jx);
               box.setTranslateZ(jz);
               box.setLayoutY(- w/2);
               box.setMaterial(ph);
               room.getChildren().add(box);
               System.out.println("box created");
                   box.setOnMousePressed((MouseEvent me) -> {
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        if(me.isSecondaryButtonDown())
                {
                box.mouseTransparentProperty().set(true);
                }
                       });
                    box.setOnMouseDragged((MouseEvent me) -> {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        if (me.isPrimaryButtonDown()) {
                            double oasX = box.getTranslateX() - mouseDeltaX*MOUSE_SPEED*modifier;
                            double oasZ = box.getTranslateZ() - mouseDeltaY*MOUSE_SPEED*modifier;
                            double Y = box.getTranslateY() - mouseDeltaY;
                            if(Y>=(negy) && Y<=(posy))
                                box.setTranslateY(Y);
                            if(oasX>negx && oasX<posx)
                                box.setTranslateX(oasX);
                            if(oasZ>onlyz && oasZ<0)
                                box.setTranslateZ(oasZ);
                        }
                       });
                     });
        }
        
        in13pane.setContent(in14pane);
        
        Label lmp = new Label("ADD LAMPSHADE");
        lmp.setPrefSize(300,36);
        lmp.setLayoutX(17);
        lmp.setLayoutY(1148+80 + 3*220 + 100+150);
        lmp.setTextFill(Color.TOMATO);
        lmp.setStyle("-fx-font: 24 arial;");
        ininpane.getChildren().add( lmp);
        
        ScrollPane in15pane = new ScrollPane();
        in15pane.setPrefSize(300, 180);
        in15pane.setLayoutX(20);
        in15pane.setLayoutY(1148+80+ 3*280+150 );
      
        AnchorPane in16pane = new AnchorPane();
        in16pane.setPrefSize(300 , 200);
        in16pane.setMaxHeight(-Infinity);
        in16pane.setMaxWidth(-Infinity);
        in16pane.setMinHeight(-Infinity);
        in16pane.setMinWidth(-Infinity);
        in16pane.setStyle("-fx-background-color : #ffb366");
        
        ImageView []lamppic = new ImageView[3];
        for(int i=0;i<3;i++)
        {
        lamppic[i] = new ImageView();
        lamppic[i].setFitWidth(80);
        lamppic[i].setFitHeight(80);
        lamppic[i].setLayoutY(30+10);
        lamppic[i].setLayoutX(30+ (80*i)+10);
        Image img= new Image("/BinaryContent/" + "lamp" + (i+1) + ".jpg");
       // Image img= new Image("file:" + "lamp" + (i+1) + ".jpg");
        lamppic[i].setImage(img);
        in16pane.getChildren().add(lamppic[i]);
        final int m = (i+1);
        lamppic[i].setOnMouseClicked((MouseEvent event) -> {
            System.out.println(img);
            System.out.println("here");
            PhongMaterial ph = new PhongMaterial(Color.GHOSTWHITE);
            ph.setDiffuseMap(new Image("/BinaryContent/" + "lamp" + m + ".png"));
             switch (wallno) {
                case 0:
                    l=300;
                    w=500;
                    h=10;
                    jx=0;
                    jz=-5;
                    break;
                case 1:
                    l=10;
                    w=500;
                    h=300;
                    jx=(negx+5);
                    jz=onlyz;
                     break;
                case 2:
                    l=10;
                    w=500;
                    h=300;
                    jx = (posx-5);
                    jz=onlyz;
                    break;
                case 3:
                    l=300;
                    w=500;
                    h=10;
                    jx=0;
                    jz=onlyz+5;
                    break;
            }
               Box box = new Box(l,w,h);
               box.setTranslateX(jx);
               box.setTranslateZ(jz);
               box.setLayoutY(- w/2);
               box.setMaterial(ph);
               room.getChildren().add(box);
               System.out.println("box created");
                   box.setOnMousePressed((MouseEvent me) -> {
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        if(me.isSecondaryButtonDown())
                {
                box.mouseTransparentProperty().set(true);
                }
                       });
                    box.setOnMouseDragged((MouseEvent me) -> {
                        mouseOldX = mousePosX;
                        mouseOldY = mousePosY;
                        mousePosX = me.getSceneX();
                        mousePosY = me.getSceneY();
                        mouseDeltaX = (mousePosX - mouseOldX);
                        mouseDeltaY = (mousePosY - mouseOldY);
                        if (me.isPrimaryButtonDown()) {
                            double oasX = box.getTranslateX() - mouseDeltaX*MOUSE_SPEED*modifier;
                            double oasZ = box.getTranslateZ() - mouseDeltaY*MOUSE_SPEED*modifier;
                            double Y = box.getTranslateY() - mouseDeltaY;
                            if(Y>=(negy) && Y<=(posy))
                                box.setTranslateY(Y);
                            if(oasX>negx && oasX<posx)
                                box.setTranslateX(oasX);
                            if(oasZ>onlyz && oasZ<0)
                                box.setTranslateZ(oasZ);
                        }
                       });
                     });
        }
        
        in15pane.setContent(in16pane);
        
        ininpane.getChildren().addAll(color,wall,wallp,in3pane,til,til1,in5pane,other,fur,in7pane,in9pane,in11pane,in13pane,in15pane);
        inpane.setContent(ininpane);
        pane.getChildren().add(inpane);
        
        return pane;
    }
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException 
    {
         
        String filePath = new File("").getAbsolutePath();
        String sush = filePath.replace("\\", "\\\\");
        System.out.println(sush);
        BufferedReader br = new BufferedReader(new FileReader(sush + "\\TextContent\\file_version.txt"));
        String s = br.readLine();
        String []in = s.split("-");
        wp = Integer.parseInt(in[0]);
        System.out.println(wp);
        tl = Integer.parseInt(in[1]);
        System.out.println(tl);
        System.out.println("start()");
        Stage anotherStage = new Stage();
        MATERIAL();
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the height : ");
        double Height1 = sc.nextDouble();
        System.out.println("enter the length : ");
        double Length1= sc.nextDouble();
        System.out.println("enter the width : ");
        double Width1 = sc.nextDouble();
        root.getChildren().add(room);
        root.setDepthTest(DepthTest.ENABLE);
  //     createContent(1000,5656,9000);
        createContent(Height1*100,Width1*565,Length1*750);
        buildCamera();
        buildAxes();
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, room);
    //    handleMouse(scene, room);
        primaryStage.setTitle("Home decor app");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
        
        Scene anotherscene = new Scene(menu(), 500, 500);  
        anotherStage.setTitle("MENU OPTION");
        anotherStage.setScene(anotherscene);
        anotherStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        launch(args);
    }
}
