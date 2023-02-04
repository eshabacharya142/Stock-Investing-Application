package controller;

/**
 * This interface represents the controller that interacts with the view and the set of methods
 * that can be called upon to interact with the model.
 */
public interface ControllerViewInteract {
  /**
   * This method starts the application and keeps running in loop. Basically this method is the
   * entry point to the application.
   */
  void start();

  /**
   * This method setups the basic data for the application to run. This is an initial process.
   */
  void setup();
  
}
