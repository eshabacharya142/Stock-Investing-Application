package controller;

import model.TypeofAction;

/**
 * This interface represents the controller that interacts with the model and the set of methods
 * that can be called upon to interact with the model.
 */
public interface ControllerModelInteract {
  /**
   * This method helps in interacting the controller with the model. It tells model what action
   * to perform based on the type of action provided.
   *
   * @param type   the type of action for the model to perform
   * @param args   arguments to be passed to the model
   * @param length length of the arguments
   */
  void controllerModelInteract(TypeofAction type, String[] args, int length);
}
