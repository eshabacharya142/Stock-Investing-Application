package view;

import java.util.Map;

import controller.Features;

/**
 * This interface represents the controller that interacts with the model and the set of methods
 * that can be called upon to interact with the model.
 */
public interface GUIView {

  /**
   * This method adds features to the components of the panel such as buttons and text fields.
   * It consists of collection of actionListeners of all the components and
   * performs action based on the type of action provided.
   *
   * @param feature type of feature provided as per the component.
   */
  void addFeatures(Features feature);

  /**
   * This method gets the type of portfolio(Flexible or Inflexible), name of the portfolio
   * from the user and asks the user to opt for buying or investing in the portfolio.
   * When the user selects to create portfolio, this is the first screen that gets displayed
   * by the view.
   */
  void displayTypeOfPortfolioFlexibleOrInFlexibleScreen();

  /**
   * This method pops up a dialog box to get input from the user if the user wants
   * to override the portfolio or not.
   *
   * @return the override option selected by user
   */
  int jOptionPortfolioAlreadyExists();

  /**
   * This method views the user to select stocks from the supported list of stocks and date
   * of purchase.
   *
   * @param type            type of portfolio
   * @param supportedStocks list of available stocks supported by the application
   * @param name            name of the portfolio
   */
  void flexiblePortfolioScreenWithDateInput(int type, String[] supportedStocks, String name);

  /**
   * This method prompts the user to select stocks from the supported list of stocks.
   *
   * @param supportedStocks list of strings of supported stocks
   */
  void inflexiblePortfolioScreen(String[] supportedStocks);

  /**
   * This method prompts the user to select portfolio from the list of available
   * flexible portfolios received from the controller and asks the user to select date as input.
   *
   * @param displayString list of available flexible portfolios.
   */
  void displayPerformanceOfPortfolio(String[] displayString);

  /**
   * This method clears all the options of the main menu screen.
   */
  void resetMainMenu();

  /**
   * This method views the date that needs to be selected by the user.
   *
   * @param timestampType timestamp selected by the user
   */
  void performanceDateEnter(int timestampType);

  /**
   * This method helps in displaying the error message if the user enters invalid input.
   *
   * @param error the error message received from the controller
   */
  void displayErrorMessage(String error);

  /**
   * This method helps in displaying informational message to the user.
   *
   * @param info the informational message received from the controller.
   */
  void displayInformationalMessage(String info);

  /**
   * This method displays the number of days/months/years received from the controller
   * and prompts the user to enter on the basis of the available range.
   *
   * @param str the valid range of date received from the controller
   */
  void displayPerformanceNumberOfDaysInput(String str);

  /**
   * This method helps in displaying the graph of the performance.
   *
   * @param pfPerformance the map of performance received from the controller.
   * @param pfName        the portfolio name.
   */
  void getGraph(Map<String, Double> pfPerformance, String pfName);

  /**
   * This method resets all the components of the Create Portfolio view.
   */
  void resetCreatePortfolioScreen();

  /**
   * This method resets all the components of the Flexible Portfolio View.
   */
  void resetFlexiblePortfolioScreen();

  /**
   * This method displays the message when the stocks are successfully bought.
   *
   * @param showSuccessMsg the message is sent from the controller.
   */
  void displayBoughtSuccessfulAndWouldLikeToBuyAgainButtonWindow(String showSuccessMsg);

  /**
   * This method clears the text field of number of shares. The reset of the text field takes place
   * if the user tries to input wrong input. The instruction to rest the
   * text field is given by the controller.
   */
  void resetHowManyShares();

  /**
   * This method displays the option to choose date and prompts the user to select the date
   * of selling a stock.
   */
  void displaySellScreen();

  /**
   * This method simply displays the list of portfolios to the user.
   *
   * @param getListOfPortfolioNames the list of portfolio names is made available
   *                                to the view by the controller
   */
  void displayListOfPortfolioScreen(String[] getListOfPortfolioNames);

  /**
   * This method displays list of portfolios, date to be selected, recurring or non-recurring
   * option that needs to be selected by the user.
   *
   * @param displayString list of portfolios available sent by the controller
   */
  void displayAddStocksUsingDollarStrategyMain(String[] displayString);

  /**
   * This method clears all the options of dollar value main screen.
   */
  void resetDollarValueMainScreen();

  /**
   * This method prompts the user to select if the strategy is ongoing or not.
   */

  void dollarValueOnGoingScreen();

  /**
   * This method prompts the user to select end date for the strategy.
   *
   * @param str the range of date received by the controller
   */
  void dollarValueEndDateScreen(String str);

  /**
   * This method helps in displaying the option to enter recurring frequency by the user.
   *
   * @param str the frequency range is determined by model and is sent
   *            by the controller to the view.
   */
  void dollarValueFrequencyEnterScreen(String str);

  /**
   * This method asks the user to enter the number of stocks that needs to be bought.
   * If the frequency is invalid, the view displays an error message.
   *
   * @param buyStr the type of str to be displayed comes from the controller
   */
  void dollarValueHowManyStocksScreen(String buyStr);

  /**
   * This method propts the user to select stocks and enter proportion.
   *
   * @param numberOfStocks  the number of stocks
   * @param supportedStocks list of supported stocks
   */
  void dollarValueStockProportionScreen(int numberOfStocks, String[] supportedStocks);

  /**
   * This method prompts the user to select portfolio.
   *
   * @param displayString list of available portfolios.
   */
  void valueAndCompScreenOne(String[] displayString);

  /**
   * This method displays the composition of inflexible portfolio.
   *
   * @param title   name of portfolio
   * @param column  columns determining name, symbol, quantity, DOP, Price on DOP
   * @param data    values of the portfolio coming from the controller
   * @param subText DOP value
   */
  void valueAndCompScreenInflexibleResult(String title, String[] column,
                                          String[][] data, String subText);

  /**
   * This method prompts the user to select date for displaying value and composition of
   * flexible portfolio.
   */
  void valueAndCompFlexDateScreen();

  /**
   * This method displays the total money invested and the total number of transactions.
   *
   * @param title    cost basis of portfolio
   * @param costData the cost data value of the portfolio
   */
  void displayValueCompForCost(String title, String[] costData);

  /**
   * This method displays the total composition of the portfolio.
   *
   * @param title  name of portfolio
   * @param column columns such as name, symbol, quantity, share value, total value
   * @param data   the share values
   * @param footer total portfolio value
   */
  void displayValueCompForOthers(String title, String[] column, String[][] data, String footer);

  /**
   * This method prompts the user to select date and portfolio from the list of portfolios
   * for Full composition.
   *
   * @param displayString list of portfolios
   */
  void valueOnFullCompScreenOne(String[] displayString);

  /**
   * This method prompts the user to select date and portfolio from the list of portfolios
   * for COST Basis.
   *
   * @param listOfPortfolioNames list of portfolios
   */
  void totalCostInvestedByDateScreenOne(String[] listOfPortfolioNames);

  /**
   * This method views the list of options of stocks available for sale that are received
   * from the controller and prompts the user to input the number of shares that
   * the user wants to sell.
   *
   * @param listOfAvailableStocksForSale list of stocks available for sale
   */
  void displaySellScreen2(String[] listOfAvailableStocksForSale);

  /**
   * This method displays the previous view of buying stock.
   */
  void buyAnotherReset();

  /**
   * This method resets the list of stocks available for sale and the text field.
   */
  void resetStockSellScreenAfterSell();
}
