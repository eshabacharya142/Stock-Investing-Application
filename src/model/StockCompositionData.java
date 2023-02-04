package model;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * This interface represents the portfolio and the data available in the portfolio.
 */
public interface StockCompositionData extends StockPortFolioData {
  /**
   * A method that tells the number of portfolio's that are available for the user.
   *
   * @return the number of portfolio's
   */
  int getNumberOfPortFolio();

  /**
   * A helper method that helps to find out the number of stocks that are present in a portfolio.
   *
   * @param filename the filename of the portfolio
   * @return the number of stocks as an integer
   */
  int getNumberOfTransactionsInAPortFolio(String filename);

  /**
   * This method finds out the names of all the portfolio that a user has in the system.
   *
   * @param portfolioType type of portfolio
   * @return the names of the portfolio as a string array
   */
  String[] getPortFolioNames(String portfolioType);

  /**
   * This method gets all the stock related data that is in a portfolio and some data relevant to
   * the portfolio.
   *
   * @param index         the index of the portfolio
   * @param unique        true if the stocks bought of different dates should be obtained uniquely
   * @param dateStr       date until which the stock data is required
   * @param includeSale   true if the data needs to have the stocks that are sold as well
   * @param realValue     true to ignore the data of those stocks with zero shares
   * @param portfolioType type of portfolio
   * @return the object of stock portfolio data class
   */
  StockPortFolioData getAllStockDataInPortFolio(int index, boolean unique, String dateStr,
                                                boolean includeSale, boolean realValue,
                                                String portfolioType);

  /**
   * This method gets all the stocks on a particular date that are available for sale.
   *
   * @param pfIndex       the index of the portfolio
   * @param stockSymbol   symbol of the stock
   * @param dateStr       data on which the stock data needs to be obtained
   * @param portfolioType type of portfolio
   * @return the number of shares available as an integer
   * @throws ParseException if error while parsing the csv data
   */
  double sharesAvailableOnTheDateForSale(int pfIndex,
                                         String stockSymbol,
                                         String dateStr,
                                         String portfolioType) throws ParseException;

  /**
   * This method obtains the complete performance data of the portfolio on the given date.
   *
   * @param args          the arguments required for computing the portfolio performance
   * @param length        the length of the arguments
   * @param portfolioType type of portfolio
   * @return the map of the date and value of the portfolio on that date
   * @throws IOException if there is an error in IO operation
   */
  Map<String, Double> computePerformanceData(String[] args, int length,
                                             String portfolioType) throws IOException;

  /**
   * This method that returns the name of the portfolio by the index that is provided.
   *
   * @param index         the index of the portfolio
   * @param portfolioType type of portfolio
   * @return the name of the portfolio as a string
   */
  String getPortFolioFileNameByIndex(int index, String portfolioType);

  /**
   * This method helps to determine what type of portfolio based on the input.
   *
   * @param filename      name of the portfolio
   * @param portfolioType type of portfolio
   * @return true if portfolio is of the given type
   */
  boolean isPortfolioOfGivenType(String filename, String portfolioType);

  /**
   * This method helps to retrive the commission cost.
   *
   * @return the commission cost as a double
   */
  double getCommissionCost();
}
