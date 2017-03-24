import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.*;
import org.opencv.core.MatOfPoint;
import java.util.*;

class DetectColor{
	public void run(){
		
		// Hue (0-179), Saturation (0-255), Value (0-255)
		//Scalar lower = new Scalar(0, 80, 170);
		//Scalar upper = new Scalar(5, 255, 255);
		Scalar lower = new Scalar(75, 128, 128);
		Scalar upper = new Scalar(98, 255, 255);
		
		Mat srcImg = Highgui.imread(getClass().getResource("/Drecksseil.png").getPath());
		Mat destImg = srcImg.clone();
		
		// Bild ins HSV-Format umwandeln
		Mat hsvImg = srcImg.clone();
		Imgproc.cvtColor(srcImg, hsvImg, Imgproc.COLOR_BGR2HSV);
		Core.inRange(hsvImg, lower, upper, destImg);
		Highgui.imwrite("complexTest.png", destImg);
		
		// Konturen im Bild finden
		Mat contourImg = destImg.clone();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
	    Imgproc.findContours(contourImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	    
	    // Die größte Fläche finden
	    double largest_area = 0;
	    Rect bounding_rect = null;
	    
	    for (Mat contour: contours) {
	        double area = Imgproc.contourArea(contour, false);
	        
	        if (area > largest_area){
	        	largest_area = area;
	        	bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        }
	    }
	    
	    // Grüne Markierungen auf Bild setzen
	    double x = bounding_rect.x;
	    double y = bounding_rect.y;
	    double width = bounding_rect.width;
	    double height = bounding_rect.height;
	    
	    System.out.println("X:" + x + " Y:" + y + " W:" + width + " H: " + height);
	    
	    Core.rectangle(srcImg, new Point(x, y), new Point(x + width, y + height), new Scalar(0, 255, 0), 15);
	    Core.circle(srcImg, new Point(x + (width/2), y + (height/2)), 30, new Scalar(0, 255, 0), 10);

	    // Bilder schreiben
	    Highgui.imwrite("complexTe.png", srcImg);
	}
}

public class Main {
	public static void main( String[] args )
	   {
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      System.out.println(Core.VERSION);
	      new DetectColor().run();
	   }

}
