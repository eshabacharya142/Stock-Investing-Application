package view;

/**
 * This interface represents the view that gets commands from the controller and the methods that
 * are called up based on the command provided by the controller.
 */
public interface ViewControllerInteract {
  /**
   * This method helps to dispatch the operations to the corresponding methods based on the
   * command received from the controller.
   *
   * @param type   the type of view to be rendered
   * @param args   helper arguments if any provided by the controller
   * @param length the length of the arguments
   */
  void viewControllerInteract(TypeofViews type, String[] args, int length);

  /**
   * This method displays the total cost invested on a certain date.
   *
   * @param title    title for display
   * @param costData data to be displayed
   */
  void displayValueCompForCost(String title, String[] costData);

  /**
   * This method helps to display the value and composition of a portfolio.
   *
   * @param title  title for display
   * @param column column data
   * @param data   data to be displayed
   * @param footer footer to be displayed
   */
  void displayValueCompForOthers(String title, String[] column, String[][] data, String footer);

  /**
   * This method displays the performance of portfolio over time.
   *
   * @param title    title to be displayed
   * @param data     data to be displayed
   * @param scaleStr scale of the graph
   * @param footer   footer to be displayed
   */
  void portfolioPerformanceOverTimeView(String title, String[] data,
                                        String[] scaleStr, String footer);

  /**
   * This method helps to display the current stock data on the screen.
   *
   * @param stockData stock data to be displayed
   */
  void showStockDataScreen(String[] stockData);
}