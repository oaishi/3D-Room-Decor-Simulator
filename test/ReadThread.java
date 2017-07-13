package termproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.image.BufferedImage;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Faria huq
 */
 class ReadThread implements Runnable {
	private Thread thr;
	private NetworkUtil nc;
        int i,j,k;
        String demo,imgSrc,url;
	public ReadThread(NetworkUtil nc) throws IOException {
		this.nc = nc;
                this.thr = new Thread(this);
		thr.start();
                this.i=0;
                this.j=0;
                this.k=0;
                
	}
	
	public void run() {
		try {
                       while(true) {
                                 
				String s=(String)nc.read();
                                if(s!=null)
                                {
                                   // System.out.println(s);  
                                    if(s== "finished")
                                    {
                                        System.out.println("finished");
                                        nc.closeConnection();
                                        break;
                                    }
                                   
                                String []nembee=s.split("//-1-1-//");
                                demo = nembee[0];
                                imgSrc = nembee[1];
                                url = nembee[2];
                                if(demo=="wallpaper")
                                {
                                    i++;
                                    System.out.println("here");
                                    downloadImage(url, imgSrc, demo , i);
                                }
                                else if(demo=="tiles")
                                {
                                    j++;
                                    System.out.println("here t");
                                    downloadImage(url, imgSrc, demo , j);
                                }
                                else if(demo=="wallart")
                                {
                                    k++;
                                    System.out.println("wallart");
                                    downloadImage(url, imgSrc, demo , k);
                                }
                                }
			}
		} catch(Exception e) {
			System.out.println (e);           
		}
		nc.closeConnection();
	}
        
        private void downloadImage(String url, String imgSrc , String demo , int k) throws IOException {
        BufferedImage image = null;
        int i = k;
        try {
            if (!(imgSrc.startsWith("http"))) {
                url = url + imgSrc;
            } else {
                url = imgSrc;
            }
            String imageFormat = null;
            imageFormat = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
            String imgPath = null;
            imgPath = "C:/Users/Faria huq/Desktop/termproject/src/" + demo + i + "";
            URL imageUrl = new URL(url);
            image = ImageIO.read(imageUrl);
            if (image != null) {
                File file = new File(imgPath);
                ImageIO.write(image, imageFormat, file);
                System.out.println("downloaded");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
}
 }
