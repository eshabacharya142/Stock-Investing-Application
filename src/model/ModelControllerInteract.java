package model;

/**
 * This interface represents the model that gets commands from the controller and the methods that
 * are called up based on the command provided by the controller.
 */
public interface ModelControllerInteract {
  /**
   * This method helps to dispatch the operations to the corresponding methods based on the
   * command received from the controller.
   *
   * @param type   the type of action to be performed
   * @param args   helper arguments if any passed by the controller
   * @param length length of the arguments
   */
  void modelControllerInteract(TypeofAction type, String[] args, int length);
}
