import controller.ControllerViewInteract;
import controller.ControllerViewInteractImpl;
import controller.GUIController;
import view.GUIView;
import view.JFrameViewImpl;

/**
 * This class represents the main method for the application.
 * This class is the basic entry point for the application as well.
 */
public class Main {
  /**
   * The main method is called with arguments. This application doesn't require any arguments
   * for the main method.
   * Main creates an object for the controller and passes the input stream and output steam to
   * the constructor. It then calls the start to start the application.
   *
   * @param args arguments for the main method if any
   */
  public static void main(String[] args) {
    ControllerViewInteract obj = new ControllerViewInteractImpl(System.in, System.out);
    obj.setup();
    GUIView guiView = new JFrameViewImpl("STOCKS GUI APPLICATION");
    GUIController guiController = new GUIController(System.in, System.out);
    guiController.setView(guiView);
    obj.start();
  }
}
