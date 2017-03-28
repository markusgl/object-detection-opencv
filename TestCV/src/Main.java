import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.utils.Converters;
import org.opencv.imgproc.*;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;

import java.util.*;

class DetectColor{
	public void run(){
		
		// Hue (0-179), Saturation (0-255), Value (0-255)
		//Scalar lower = new Scalar(0, 80, 170);
		//Scalar upper = new Scalar(5, 255, 255);
		Scalar lower = new Scalar(75, 128, 128);
		Scalar upper = new Scalar(98, 255, 255);
		
		Mat srcImg = Highgui.imread(getClass().getResource("/Seil3.png").getPath());
		Mat destImg = srcImg.clone();
		
		// Bild ins HSV-Format umwandeln
		Mat hsvImg = srcImg.clone();
		Imgproc.cvtColor(srcImg, hsvImg, Imgproc.COLOR_BGR2HSV);
		Core.inRange(hsvImg, lower, upper, destImg);
		
		int dilation_size = 30;
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
		Imgproc.dilate(destImg, destImg, kernel);
		Imgproc.erode(destImg, destImg, kernel);
		
		
		
		
		
		Highgui.imwrite("complexTest.png", destImg);
		
		
		
		// Konturen im Bild finden
		Mat contourImg = destImg.clone();
		//Imgproc.blur(contourImg, contourImg, new Size(10, 10));
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
	    Imgproc.findContours(contourImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	    
	   System.out.println(contours.size());
	   
	   // Die größte Fläche finden
	    double largest_area = 0;
	    Mat largest_contour = new Mat();
	    //Rect bounding_rect = null;
	    
	    for (Mat contour: contours) {
	        double area = Imgproc.contourArea(contour, false);
	        //Rect bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        
	        //double x = bounding_rect.x + (bounding_rect.width/2);
	        //double y = bounding_rect.y + (bounding_rect.height/2);
	        //mittelpunkte.add(new Point(x, y));
	        
	        
	        if (area > largest_area){
	        	largest_area = area;
	        	largest_contour = contour;
	        	//bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        }
	        
	    }
	    
	    
	   
	   
	   
	  
	    
	    //for (Mat contour: contours){
	    //	MatOfPoint2f curve = new MatOfPoint2f();
	    //	MatOfPoint2f mat = new MatOfPoint2f();
	    //	contour.convertTo(mat, CvType.CV_32F);
	  	//    Imgproc.approxPolyDP(mat, curve, (double) 1, false);
	  	//    
	    //	
	    //}
	    
	    //List<MatOfPoint2f> kurven = new ArrayList<MatOfPoint2f>();
	    //Imgproc.drawContours(srcImg, kurven, -1, new Scalar(0, 255, 0));
	    
	    
	    //List<MatOfInt> ConvexHullPoints = new ArrayList<MatOfInt>();
	    //for (Mat)
	    //Imgproc.convexHull(contours, ConvexHullPoints);

	    //polylines( drawing, ConvexHullPoints, true, Scalar(0,0,255), 2 );
	    //imshow("Contours", drawing);

	    //polylines( src, ConvexHullPoints, true, Scalar(0,0,255), 2 );
	    //imshow("contoursConvexHull", src);
	    //waitKey();
	    //return 0;
	    
	    
	    
	    // Die größte Fläche finden
	    List<Point> mittelpunkte = new ArrayList<Point>();
	    //double largest_area = 0;
	    //Rect bounding_rect = null;
	    
	    for (Mat contour: contours) {
	        //double area = Imgproc.contourArea(contour, false);
	        Rect bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        
	        double x = bounding_rect.x + (bounding_rect.width/2);
	        double y = bounding_rect.y + (bounding_rect.height/2);
	        mittelpunkte.add(new Point(x, y));
	        
	        
	        //if (area > largest_area){
	        //	largest_area = area;
	        //	bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        //}
	        
	    }
	    if (mittelpunkte.size() > 1){
	    	
	    	for (int i = 0; i < mittelpunkte.size() - 1; i++) {
				//System.out.println(mittelpunkte.get(i));
				Point a = mittelpunkte.get(i);
				Point b = mittelpunkte.get(i+1);
				
				Core.line(srcImg, a, b, new Scalar(0, 0, 255), 15);
				
				
			}
	    	
	    	// Bilder schreiben
		    Highgui.imwrite("complexTe.png", srcImg);
	    	
	    	
	    }
	    
	    
	    
	    // Grüne Markierungen auf Bild setzen
	    //double x = bounding_rect.x;
	    //double y = bounding_rect.y;
	    //double width = bounding_rect.width;
	    //double height = bounding_rect.height;
	    
	    //System.out.println("X:" + x + " Y:" + y + " W:" + width + " H: " + height);
	    
	    //Core.rectangle(srcImg, new Point(x, y), new Point(x + width, y + height), new Scalar(0, 255, 0), 15);
	    //Core.circle(srcImg, new Point(x + (width/2), y + (height/2)), 30, new Scalar(0, 255, 0), 10);

	    // Bilder schreiben
	    //Highgui.imwrite("complexTe.png", srcImg);
	    
	    //int kernelSize = 9;
	    //Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F){
         //   {
         //      put(0,0,-1);
         //      put(0,1,0);
         //      put(0,2,1);
         //
         //      put(1,0-2);
         //      put(1,1,0);
         //      put(1,2,2);
         //
         //      put(2,0,-1);
         //      put(2,1,0);
         //      put(2,2,1);
         //   }
         //};	  
	    
	    //Mat sobelImg = srcImg.clone();
	    //Imgproc.Sobel(srcImg, sobelImg, -1, kernel, 0);
	    
	    
	    
	}
}

public class Main {
	public static void main( String[] args )
	   {
	      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	      System.out.println(Core.VERSION);
	      new DetectColor().run();
	   }

}
