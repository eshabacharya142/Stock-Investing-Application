package model;

/**
 * This class holds the data that are used while buying stocks using dollar-value strategy.
 */
public class DollarValueDataImpl implements DollarValueData {
  public String startDate;
  public int pfIndex;
  public int recurIndex;
  public int onGoingIndex;
  public String endDate;
  public String frequencyStr;
  public int numberOfStocks;
  public boolean isCreateDollarValue;
  public int remainder;
}
