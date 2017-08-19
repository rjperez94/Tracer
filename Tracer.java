import ecs100.*;
import java.awt.Color;



/** Tracer
 *  The program displays an image on the screen consisting of a tangle of
 *  microtubules.  
 *  It will enable the user to measure the lengths of the microtubles 
 *  by tracing out polylines (sequences of connected straight line segments)
 *  over the image.
 *  After the user clicks the "Start Line" button, the program will clear all lines,
 *   redisplay the image, and get ready to start a new line
 *  As the user clicks on points on the image, it will build up a "polyline" connecting
 *   the points.  For the first point on the line, it just draws a dot.
 *   For each remaining point the user clicks on, the program will draw
 *   a new line segment from the previous point.
 *  It also keeps track of the total length of the line, adding the length of the
 *   new segment to the total on each click.
 *  When the user clicks the "Line Length" button, it will print out the total length of the line.
 *  When the user clicks the "Choose Image" button, it will allow the user to select a different
 *   image, and will restart the line.
 *
 *  You will need
 *  - fields to record the previous point on the polyline, and the length so far.
 *  - a constructor to set up the GUI (including the mouse listener)
 *  - methods to respond to events (buttons and mouse)
 *  - possibly additional "helper" methods.
 */
public class Tracer implements UIButtonListener, UIMouseListener {
    // Fields
    String imageName = "image01.jpg";     // the current image that will be displayed

    // Other fields to record where the previous point on the polyline was.
    // (negative if there was no previous point), and the length of the line
    
    private double linelength;
    private double lastpointX;
    private double lastpointY;
    private double newX;
    private double newY;
    private int Release;

    // Constructor
    /** 
     *  Construct a new Tracer object and set up the GUI
     */
    public Tracer(){
        UI.setMouseListener(this);
        UI.addButton("Choose File...", this);
        UI.addButton("Start Line", this);
        UI.addButton("Line Length", this);
        
    }

    // GUI Methods
    /** Respond to button presses */
    public void buttonPerformed(String button){
        if (button.equals("Start Line")) {
            UI.clearText();
            UI.println ("This is picture '"+imageName+"'");
            startLine();
        } else if (button.equals("Line Length")) {
            UI.println ("The length of the line is " + linelength);
        } else if (button.equals("Choose File...")) {
            UIFileChooser.open();
            imageName= UIFileChooser.open ();
        }
    }

    /** Respond to mouse events, particularly to "released" */
    public void mousePerformed(String action, double x, double y) {
        UI.setColor(Color.yellow);
        if (action.equals("released")){
            Release = Release + 1;
            if (Release >= 2) {
                newX = x;
                newY = y;
                UI.drawLine(lastpointX, lastpointY, newX, newY);
                lastpointX = x;
                lastpointY = y;
                linelength = linelength + (Math.hypot (lastpointX - newX, lastpointY  - newX));
            } else {
                UI.drawOval (x, y, 5, 5);
                lastpointX = x;
                lastpointY = y;
            }
        }
    }
    
    public static String open() {
        return UIFileChooser.open();
    }
    // other methods: you don't have to define this method, but it may be useful

    /**
     * Clear the screen, redisplay the image, and get ready to start a new line.
     * Sets the values of the fields storing the current point to -1
     */
    private void startLine(){
        UI.clearGraphics();
        UI.drawImage(imageName, 1, 1);        
        lastpointX = -1;
        lastpointY = -1;
        Release = 0;
    }

    // Main
    public static void main(String[] arguments){
        new Tracer();
    }        


}
