package model;

/**
 * This is a helper class that holds the data related to the stocks and shares in a portfolio.
 * The values are made public intentionally in-order to access them directly.
 * This class has the following members.
 * <ul>
 *   <li>numberOfUniqueStocks</li>
 *   <li>stockName - name of all the stocks in a portfolio</li>
 *   <li>stockSymbol - symbol of all the stocks in a portfolio</li>
 *   <li>stockQuantity - number of shares for each stock in a portfolio</li>
 *   <li>totalValue - total value of each stock in a portfolio</li>
 *   <li>totalPortFolioValue - total value of the complete portfolio</li>
 *   <li>valueOfSingleStock - value of each share of a stock in the portfolio</li>
 *   <li>createdTimeStamp - timestamp on which the portfolio was created</li>
 * </ul>
 */
public class StockPortFolioDataImpl implements StockPortFolioData {
  public int numberOfUniqueStocks;
  public String[] stockName;
  public String[] stockSymbol;
  public double[] stockQuantity;
  public double[] valueOfSingleStock;
  public String createdTimeStamp;
  public String[] stockLastKnownValueDate;
  public int numberOfTransactions;
}