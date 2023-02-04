package view;

/**
 * This interface represents the bar chart and the ways to implement the bar chart.
 */
public interface BarChart {
  /**
   * This method helps in creating the data set for the graph.
   *
   * @param xAxisValue the x-axis value for the graph
   * @param yAxisValue the y-axis value for the graph
   * @param pfName     the name of the portfolio
   */
  void createDataset(String xAxisValue, double yAxisValue, String pfName);

  /**
   * This method draws the graph that is asked for.
   */
  void drawGraph();
}
