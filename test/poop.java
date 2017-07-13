/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class poop extends Application {

    Group root = new Group();
    Group AnotherRoot = new Group();
    AnchorPane pane;
    Xform room = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform axisGroup = new Xform();
    private static final double AXIS_LENGTH = 6250.0;
    double CAMERA_INITIAL_DISTANCE = 0;
    double CAMERA_INITIAL_Y_DISTANCE = 0;
    double CAMERA_INITIAL_X_DISTANCE = 0;
    private static final double CAMERA_INITIAL_X_ANGLE = 0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
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
    int wallno;
    double mapX,mapY,mapZ;
    double l,w,h;
    double jx,jy,jz , boundx,boundy,boundz;
    Image image[] = new Image[6];
    PhongMaterial barmat[] = new PhongMaterial[6];
    Box barcube[] = new Box[6];
    public void MATERIAL()
    {
       
        barmat[0] = new PhongMaterial(Color.ALICEBLUE);
        image[0] = new Image("file:C:\\Users\\Faria huq\\Desktop\\termproject\\src\\wallpaper\\" + "7.jpg");
        image[0].isPreserveRatio();
        barmat[0].setDiffuseMap(image[0]);

        barmat[1] = new PhongMaterial(Color.GHOSTWHITE);
        
        barmat[2] = new PhongMaterial(Color.SNOW);
        //back
        barmat[3] = new PhongMaterial(Color.ANTIQUEWHITE);
        image[3] =new Image("file:C:\\Users\\Faria huq\\Desktop\\termproject\\src\\wallpaper\\" +4 + ".jpg");
        barmat[3].setDiffuseMap(image[3]);
        //top
        barmat[4] = new PhongMaterial(Color.SKYBLUE);
        //floor
        barmat[5] = new PhongMaterial(Color.CORNSILK);
        image[5] =new Image("file:C:\\Users\\Faria huq\\Desktop\\termproject\\src\\tiles\\" +8 + ".jpg");
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

   
    @Override
    public void start(Stage primaryStage)
    {
        
        System.out.println("start()");
        MATERIAL();
        root.getChildren().add(room);
        root.setDepthTest(DepthTest.ENABLE);
        createContent(1000,5656,9000);
        buildCamera();
        buildAxes();
        Scene scene = new Scene(root, 824, 550, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, room);
        handleMouse(scene, room);
        primaryStage.setTitle("Home decor app");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

   
    
}
