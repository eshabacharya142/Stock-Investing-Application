package controller;

import java.io.IOException;
import java.text.ParseException;

/**
 * This interface represents the API calls that helps in fetching the stock related data from the
 * Alpha Vantage.
 */
public interface GetStockData {
  /**
   * This method helps in getting the real-time value of a stock by performing an API call to the
   * Alpha Vantage.
   *
   * @param stock   the ticker symbol of the stock that the data is required for
   * @param dateStr data on which the stock is required for
   * @throws ParseException if there is an error in parsing the data
   * @throws IOException    if there is an error in reading the file
   */
  void getValue(String stock, String[] dateStr) throws ParseException, IOException;

  /**
   * This method helps to initialize and setup all values for a stock data.
   *
   * @param stock the ticker symbol of the stock that the data is required for
   */
  void getAllValue(String stock);
}
