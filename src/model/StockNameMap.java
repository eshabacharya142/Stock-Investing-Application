package model;

import java.util.Map;

/**
 * This interface represents the supported stocks in this application.
 */
public interface StockNameMap {

  /**
   * This method provides the hashmap of the stock mapping that was created by the constructor.
   *
   * @return the hashmap of the supported stocks data
   */
  Map<String, String> getMap();

  /**
   * This method tell the number of stocks supported by the application.
   *
   * @return the size of the hashmap as an integer
   */
  int getMapSize();
}
