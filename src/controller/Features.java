package controller;



import javax.swing.JSpinner;
import javax.swing.JComboBox;

import view.GUIView;

/**
 * This interface represents all the features supported by the controller for the GUI.
 */
public interface Features {
  /**
   * This method is the entry point for the controller to set the view.
   *
   * @param guiView the GUI object from view
   */
  void setView(GUIView guiView);

  /**
   * This method instructs view to display main screen for creating portfolio.
   */
  void createAPortfolioGUI();

  /**
   * This method instructs view to display the value and composition main screen.
   */
  void valueAndCompositionGUIMainScreen();

  /**
   * This method create the portfolio.
   *
   * @param type           type of portfolio
   * @param name           name of portfolio
   * @param optionSelected type of buy
   */
  void createPortfolioScreenSubmit(int type, String name, int optionSelected);

  /**
   * This method instructs view to display the main screen for portfolio performance.
   */
  void performanceOfPortfolioMain();

  /**
   * This method instructs view to display the second screen for portfolio performance.
   *
   * @param pfIndex       index of portfolio selected
   * @param timestampType timestamp selected by user
   */
  void performancePortfolioMainMenu(int pfIndex, int timestampType);

  /**
   * A method to check the current stock price and display.
   *
   * @param type          type of portfolio
   * @param name          name of stock
   * @param stockSelected symbol of stock
   */
  void checkCurrentPrice(int type, String name, int stockSelected);

  /**
   * A method to exit the program from the GUI.
   */
  void exitProgram();

  /**
   * This method helps in calculating the performance of portfolio.
   *
   * @param date          data selected by user
   * @param timestampType timestamp type selected by user
   */
  void performancePortfolioSubmit(String date, int timestampType);

  /**
   * This method calls the graph method to display the performance.
   *
   * @param num number of days selected by user
   */
  void performanceOfPortfolioGetData(String num);

  /**
   * This method buys the stocks selected by user.
   *
   * @param date          date selected by user
   * @param stockSelected stock selected by user
   * @param noOfStocks    number of stocks selected by user
   * @param portfolioName name of portfolio
   */
  void buyStockSubmit(String date, int stockSelected, String noOfStocks, String portfolioName);

  /**
   * This method helps in displaying error message for input while buying shares.
   */
  void checkHowManyShares();

  /**
   * This method instructs view to display the list of portfolios.
   */
  void selectPortfolio();

  /**
   * This method sells the stocks selected by the user.
   *
   * @param date          date selected by the user
   * @param stockSelected stock selected by user
   * @param noOfStocks    number of stocks selected by user
   * @param pfNumber      portfolio number
   */
  void sellStock(String date, int stockSelected, String noOfStocks, int pfNumber);

  /**
   * This method helps controller to decide to tell view whether to display buy or sell screen.
   *
   * @param buyOrSell     option selected by user
   * @param portfolioName name of portfolio
   */
  void selectStockSubmit(int buyOrSell, int portfolioName);

  /**
   * This method instructs view to display the main screen for buying stocks using dollar value.
   */
  void addStocksUsingDollarMain();

  /**
   * This method instructs view to display the second screen for buying stocks using dollar value.
   *
   * @param pfIndex    portfolio index selected by user
   * @param date       date selected by user
   * @param recurIndex recurring option selected by user
   */
  void dollarValueScreenOne(int pfIndex, String date, int recurIndex);

  /**
   * This method instructs view to display the third screen for buying stocks using dollar value.
   *
   * @param onGoingIndex ongoing option selected by the user
   */
  void dollarValueScreenTwo(int onGoingIndex);

  /**
   * This method instructs view to display the fourth screen for buying stocks using dollar value.
   *
   * @param endDate end date selected by user
   */
  void dollarValueScreenThreeEndDate(String endDate);

  /**
   * This method instructs view to display the fifth screen for buying stocks using dollar value.
   *
   * @param frequency frequency selected by user
   */
  void dollarValueScreenFourFrequency(String frequency);

  /**
   * This method instructs view to display the fifth screen for buying stocks using dollar value.
   *
   * @param numberOfStocks number of stocks selected by user
   */
  void dollarValueScreenFiveHowManyShares(String numberOfStocks);

  /**
   * This method calculates the stocks to be bought and buys them.
   *
   * @param price                   price inputted by user
   * @param spinner                 proportion selected by user
   * @param comboSupportStocksArray stocks selected by user
   */
  void dollarValueScreenSixBuyStocks(String price, JSpinner[] spinner,
                                     JComboBox[] comboSupportStocksArray);

  /**
   * This method instructs view to display the second screen for view value and composition.
   *
   * @param pfIndex portfolio selected by user
   */
  void valueAndCompositionScreenOne(int pfIndex);

  /**
   * This method instructs view to reset the screen to main menu.
   */
  void resetMainMenu();

  /**
   * This method gets date for viewing value and composition.
   *
   * @param date date selected by user
   */
  void valueAndCompositionFlexDateScreen(String date);

  /**
   * This method instructs view to display value composition first screen.
   */
  void valueOnFullCompMainScreen();

  /**
   * This method instructs view to display value composition second screen.
   *
   * @param pfIndex portfolio selected by user
   * @param date    date selected by user
   */
  void valueOnFullScreenOne(int pfIndex, String date);

  /**
   * This method instructs view to display total value invested first screen.
   */
  void totalCostInvestedByDateMainMenu();

  /**
   * This method instructs view to display total value invested second screen.
   *
   * @param pfIndex portfolio selected by user
   * @param date    date selected by user
   */
  void totalCostInvestedScreenOne(int pfIndex, String date);

  /**
   * This method helps in getting stock data available for sale.
   *
   * @param date    date selected by user
   * @param pfIndex portfolio selected by user
   */
  void getStocksAvailableForSaleAsPerDate(String date, int pfIndex);

  /**
   * This method instructs view to display the buy stocks screen.
   */
  void buyAnotherSubmitButton();
}