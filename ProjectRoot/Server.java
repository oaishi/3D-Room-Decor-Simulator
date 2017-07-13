/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Faria huq
 */

import javax.swing.text.html.parser.ParserDelegator;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.*;

public class Server {
    
     public static void main(String args[]) throws Exception {
        int wp=0 ;
        int tl=0;
        int i=0;
        String filePath = new File("").getAbsolutePath();
        String sush = filePath.replace("\\", "\\\\");
        BufferedReader bir = new BufferedReader(new FileReader(sush + "\\TextContent\\sources.txt"));
         while(true){
                     String s = bir.readLine(); 
                     if(s== null){
                       break;
                     }
                     String []nembee=s.split("//-1-1-//");
                     String source = nembee[0];
                     String webUrl = nembee[1];
                     if(source.equals("wallpaper"))
                     {
                         wp++;
                         i=wp;
                         BufferedWriter biw = new BufferedWriter(new FileWriter(sush + "\\TextContent\\file_version.txt"));
                         biw.write( wp + "-" + tl );
                         biw.close();
                     }
                     else if(source.equals("tiles"))
                     {
                        BufferedWriter biw = new BufferedWriter(new FileWriter(sush + "\\TextContent\\file_version.txt"));
                         tl++;
                         biw.write( wp + "-" + tl );
                         i=tl;
                         biw.close();
                     }
                     
       try {
        URL url = new URL(webUrl);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        HTMLEditorKit htmlKit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
        HTMLEditorKit.Parser parser = new ParserDelegator();
        HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
        parser.parse(br, callback, true);

        for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.IMG); iterator.isValid(); iterator.next()) {
            AttributeSet attributes = iterator.getAttributes();
            String imgSrc = (String) attributes.getAttribute(HTML.Attribute.SRC);

            if (imgSrc != null && imgSrc.endsWith(".jpg") && (imgSrc.startsWith("http") )) {
                try {
                    downloadImage(imgSrc ,source , i);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
         }
          catch (IOException ex) {
                         ex.printStackTrace();
                     }
        }
    }
    private static void downloadImage(String imgSrc , String demo ,int i) throws IOException {
        BufferedImage image = null;
        String filePath = new File("").getAbsolutePath();
        String s = filePath.replace("\\", "/");
        try {
            String imageFormat = null;
            imageFormat = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
            String imgPath = null;
            String url = null;
            url = imgSrc;
            imgPath = s + "/BinaryContent/" + demo + "/" + i + "." + imageFormat + "" ; 
            URL imageUrl = new URL(url);
            image = ImageIO.read(imageUrl);
            if (image != null) {
                File file = new File(imgPath);
                ImageIO.write(image, imageFormat, file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}