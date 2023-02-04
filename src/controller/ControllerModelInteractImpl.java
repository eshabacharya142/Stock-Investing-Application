package controller;

import model.ModelControllerInteract;
import model.ModelControllerInteractImpl;
import model.TypeofAction;

/**
 * This class represents the controller that interacts with the model and provides implementation
 * that helps controller to interact with the model. This class has the following variables.
 * <ul>
 *   <li> mciObj - an object for the model </li>
 * </ul>
 */
public class ControllerModelInteractImpl implements ControllerModelInteract {
  private final ModelControllerInteract mciObj = new ModelControllerInteractImpl();

  @Override
  public void controllerModelInteract(TypeofAction type, String[] args, int length) {
    switch (type) {
      case BUY_STOCKS:
      case GET_STOCK_DATA:
      case CREATE_PORTFOLIO:
      case DELETE_EMPTY_PORTFOLIO:
      case INITIAL_SETUP:
      case SELL_STOCKS:
      case GET_PORTFOLIO_PERFORMANCE:
      case STORE_COMMISSION_COST: {
        mciObj.modelControllerInteract(type, args, length);
        break;
      }
      default: {
        break;
      }
    }
  }
}
