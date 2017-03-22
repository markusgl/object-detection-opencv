import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.*;
import org.opencv.core.MatOfPoint;
import java.util.*;

class DetectFaceDemo {
	  public void run() {
	    System.out.println("\nRunning DetectFaceDemo");

	    // Create a face detector from the cascade file in the resources
	    // directory.
	    CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
	    Mat image = Highgui.imread(getClass().getResource("/lena.png").getPath());

	    // Detect faces in the image.
	    // MatOfRect is a special container class for Rect.
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(image, faceDetections);

	    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

	    // Draw a bounding box around each face.
	    for (Rect rect : faceDetections.toArray()) {
	        Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	    }

	    // Save the visualized detection.
	    String filename = "faceDetection.png";
	    System.out.println(String.format("Writing %s", filename));
	    Highgui.imwrite(filename, image);
	  }
	}

class DetectColor{
	public void run(){
		
		// Blau Grün Rot
		Scalar lower = new Scalar(0,0,160);
		Scalar upper = new Scalar(100,100,255);
		
		Mat srcImg = Highgui.imread(getClass().getResource("/complex.png").getPath());
		Mat destImg = srcImg.clone();
		
		Core.inRange(srcImg, lower, upper, destImg);
		Mat contourImg = destImg.clone();
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();

	    Imgproc.findContours(contourImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	    //System.out.println(contours.toString());
	    
	    
	    double largest_area = 0;
	    Rect bounding_rect = null;
	    
	    for (Mat contour: contours) {
	        //System.out.println(contour);
	        
	        
	        double a = Imgproc.contourArea(contour, false);
	        
	        if (a > largest_area){
	        	largest_area = a;
	        	bounding_rect = Imgproc.boundingRect((MatOfPoint) contour);
	        }
	 
	    }
	    
	    System.out.println(bounding_rect.toString());
	    
	    double x = bounding_rect.x;
	    double y = bounding_rect.y;
	    double width = bounding_rect.width;
	    double height = bounding_rect.height;
	    
	    System.out.println("X:" + x + " Y:" + y + " W:" + width + " H: " + height);
	    
	    Core.rectangle(srcImg, new Point(x, y), new Point(x + width, y + height), new Scalar(0, 255, 0), 15);
	    
	    Core.circle(srcImg, new Point(x + (width/2), y + (height/2)), 30, new Scalar(0, 255, 0), 10);


	    Highgui.imwrite("complexTest.png", destImg);
	    Highgui.imwrite("complexTe.png", srcImg);
	    
	    
	    
	    //for( int i = 0; i< contours.size(); i++ ){
	    //   double a = contourArea(contours[i],false); 
	    //   if(a>largest_area){
	    //   largest_area=a;
	    //   largest_contour_index=i;                //Store the index of largest contour
	    //   bounding_rect=boundingRect(contours[i]); // Find the bounding rectangle for biggest contour
	    //}
	    
	    
	}
}

public class Main {
	public static void main( String[] args )
	   {
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
	      System.out.println( "mat = " + mat.dump() );
	      
	      System.out.println("Welcome to OpenCV " + Core.VERSION);
	      Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
	      System.out.println("OpenCV Mat: " + m);
	      Mat mr1 = m.row(1);
	      mr1.setTo(new Scalar(1));
	      Mat mc5 = m.col(5);
	      mc5.setTo(new Scalar(5));
	      System.out.println("OpenCV Mat data:\n" + m.dump());
	      
	      new DetectFaceDemo().run();
	      new DetectColor().run();
	   }

}
