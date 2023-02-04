import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is the parent class for all other test classes.
 */
public class TestParentClass {

  // A helper function to read date from StockData.csv
  protected String readStockDateFromStockDataCsv() {
    String line;
    String splitBy = ",";
    BufferedReader stockData;
    String[] splitStockData;
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      throw new RuntimeException();
    }

    try {
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
    } catch (Exception e) {
      throw new RuntimeException();
    }
    try {
      stockData.close();
    } catch (Exception e) {
      //
    }
    return splitStockData[3];
  }

  // A helper function to read price from StockData.csv
  protected String readStockPriceFromStockDataCsv() {
    String line;
    String splitBy = ",";
    BufferedReader stockData;
    String[] splitStockData;
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      throw new RuntimeException();
    }

    try {
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
    } catch (Exception e) {
      throw new RuntimeException();
    }

    try {
      stockData.close();
    } catch (Exception e) {
      //
    }
    return splitStockData[1];
  }

  // A helper function to delete a file

  protected void deleteDirectory() {
    File directory = new File("userdata/user1/Portfolio");
    if (directory.exists()) {
      File[] filename = directory.listFiles();
      assert filename != null;
      for (File fName : filename) {
        if (fName.delete()) {
          // Do nothing
        } else {
          System.out.println("Unable to delete file: " + fName.getName());
        }
      }
    }
    directory.delete();
  }

  // A helper function to get the stock data from the portfolio

  protected String readStockDataFromPortfolioCsv(String portfolioName, int lineNumber,
                                                 int colNumber, boolean needSplit)
          throws IOException {
    String filename = "userdata/user1/Portfolio/" + "pf_" + portfolioName + ".csv";
    String[] cols;
    FileReader fr = new FileReader(filename);
    BufferedReader br = new BufferedReader(fr);
    String line;
    String value = null;
    int l = 4 + lineNumber;

    while ((line = br.readLine()) != null && l >= 0) {
      cols = line.split(",");
      if (l == 1) {
        value = cols[colNumber];
      }
      l--;
    }

    if (needSplit) {
      assert value != null;
      return value.substring(0, 10);
    }

    try {
      br.close();
      fr.close();
    } catch (Exception e) {
      //
    }
    return value;
  }

  protected int readPortfolioCsvIndex(String portfolioName) throws IOException {
    String filename = "userdata/user1/Portfolio/" + "pf_" + portfolioName + ".csv";
    String[] cols;
    FileReader fr = new FileReader(filename);
    BufferedReader br = new BufferedReader(fr);
    String line;
    int value = -1;
    int l = 0;
    while ((line = br.readLine()) != null && l < 2) {
      cols = line.split(",");
      if (l == 1) {
        value = Integer.parseInt(cols[1]);
      }
      l++;
    }
    try {
      br.close();
      fr.close();
    } catch (Exception e) {
      //
    }
    return value;
  }

  protected void deleteFileInDirectory(String fileName) {
    File directory = new File("userdata/user1/Portfolio/");
    if (directory.exists()) {
      File[] filename = directory.listFiles();
      assert filename != null;
      for (File fName : filename) {
        if (Objects.equals(fileName, fName.getName())) {
          if (fName.delete()) {
            // Do nothing
          } else {
            System.out.println("Unable to delete");
          }
          break;
        }
      }
    }
  }

  protected String getSupportedStocks() {
    return "1. Microsoft (MSFT)\n"
            + "2. Meta (META)\n"
            + "3. Google (GOOG)\n"
            + "4. Apple (AAPL)\n"
            + "5. Tesla (TSLA)\n"
            + "6. JPMorgan Chase (JPM)\n"
            + "7. Johnson (JNJ)\n"
            + "8. Amazon (AMZN)\n"
            + "9. UnitedHealth (UNH)\n"
            + "10. Walmart (WMT)\n";
  }

  protected String getSetupScreen() {
    return "\n"
            + "Setting up the application. Please wait...\n"
            + "Setup complete...!!!\n\n";
  }

  protected String getMainScreen() {
    return "\nMENU\n"
            + "\n"
            + "1. Create a portfolio\n"
            + "2. Value and Composition of portfolio\n"
            + "3. Value of portfolio on full composition\n"
            + "4. Add a stock to portfolio\n"
            + "5. Sell a stock from portfolio\n"
            + "6. Performance of portfolio\n"
            + "7. Total amount invested on certain date\n"
            + "8. Configure the commission cost\n"
            + "9. Add stocks to portfolio using Dollar-Cost strategy\n"
            + "e. Exit\n";
  }


  protected String getBuyStockScreen() {
    return "1. Buy a stock\n"
            + "2. Invest by dollar-cost averaging\n"
            + "3. Main Menu\n"
            + "e. Exit\n";
  }

  protected String getFlexibleInflexibleScreen() {
    return "What type of portfolio would you like to create?\n"
            + "\n"
            + "1. Flexible / Customizable Portfolio\n"
            + "2. Inflexible / Non Customizable Portfolio\n";
  }


  protected String[] readStockDataToShow() {
    String line;
    String splitBy = ",";
    BufferedReader stockData = null;
    String[] splitStockData = new String[4];
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      // Do nothing
    }

    try {
      assert stockData != null;
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
      stockData.close();
    } catch (Exception e) {
      try {
        stockData.close();
      } catch (Exception ex) {
        // Nothing
      }
      return null;
    }
    return splitStockData;
  }
}
