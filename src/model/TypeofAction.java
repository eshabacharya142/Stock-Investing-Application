package model;

/**
 * This list of enums represents the list of actions that the model can perform. This list is
 * provided to the controller.
 */
public enum TypeofAction {
  BUY_STOCKS,
  GET_STOCK_DATA,
  CREATE_PORTFOLIO,
  INITIAL_SETUP,
  SELL_STOCKS,
  GET_PORTFOLIO_PERFORMANCE, STORE_COMMISSION_COST, DELETE_EMPTY_PORTFOLIO
}
