import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import model.ModelControllerInteract;
import model.ModelControllerInteractImpl;
import model.TypeofAction;

import static org.junit.Assert.assertEquals;

/**
 * This class contains tests for the Model.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelTest extends TestParentClass {

  /**
   * This test assures that flexible portfolio has been created.
   */
  @Test
  public void testAModelCreatePortfolio() {
    deleteDirectory();
    String[] args = {"modelTest5", "1"};
    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, args, 0);

    File dir = new File("userdata/user1/Portfolio");
    File[] directoryListing = dir.listFiles();
    String expectedName = "pf_" + args[0] + ".csv";
    String pfName = null;
    if (directoryListing != null) {
      for (File i : directoryListing) {
        if (i.getName().equals("pf_" + args[0] + ".csv")) {
          pfName = i.getName();
          break;
        }
      }
    }
    assertEquals(expectedName, pfName);
  }


  /**
   * This test assures that inflexible portfolio has been created.
   */
  @Test
  public void testAModelCreatePortfolio2() {
    String[] args = {"modelTest4", "2"};
    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, args, 0);

    File dir = new File("userdata/user1/Portfolio");
    File[] directoryListing = dir.listFiles();
    String expectedName = "pf_" + args[0] + ".csv";
    String pfName = null;
    if (directoryListing != null) {
      for (File i : directoryListing) {
        if (i.getName().equals("pf_" + args[0] + ".csv")) {
          pfName = i.getName();
          break;
        }
      }
    }
    assertEquals(expectedName, pfName);
  }


  /**
   * This test assures that empty portfolio is deleted.
   */
  @Test
  public void testBModelDeleteEmptyPortfolio() {
    String[] args = {"modelTest1","Flexible"};
    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, args, 0);
    obj.modelControllerInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);

    File dir = new File("userdata/user1/");
    File[] directoryListing = dir.listFiles();

    int count = 0;
    if (directoryListing != null) {
      for (File i : directoryListing) {
        if (!i.getName().equals("pf_" + args[0] + ".csv")) {
          count++;
        }
      }
    }
    assertEquals(count, Objects.requireNonNull(dir.listFiles()).length);
  }

  /**
   * This test tests if the stock data is being received properly from the API call.
   */
  @Test
  public void testCModelGetStockData() {
    String[] args = {"TSLA", "2022-01-01"};
    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.GET_STOCK_DATA, args, 0);

    String line;
    String splitBy = ",";
    String expectedName = args[0];
    BufferedReader stockData = null;
    String[] splitStockData = new String[4];
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      System.out.println("Supported stocks file not found " + e.getMessage());
    }
    try {
      assert stockData != null;
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
    } catch (Exception e) {
      System.out.println("Error in reading Supported stocks csv file.");
    }

    assertEquals(expectedName, splitStockData[2]);
  }


  /**
   * This test chekcs if the stock has been bought.
   */
  @Test
  public void testDModelBuyStocks() throws IOException {
    deleteFileInDirectory("modelTest.csv");
    String[] name = {"modelTest", "1"};
    String[] args = {"GOOG", "2022-11-11"};
    String[] args1 = {"1", "modelTest"};


    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, name, 0);
    obj.modelControllerInteract(TypeofAction.GET_STOCK_DATA, args, 0);
    obj.modelControllerInteract(TypeofAction.BUY_STOCKS, args1, 0);

    String expected = super.readStockDataFromPortfolioCsv(name[0],
            1, 1, false);
    String actual = String.valueOf((System.currentTimeMillis() / 1000));
    assertEquals(expected, actual);
    deleteFileInDirectory("modelTest.csv");
  }

  /**
   * This test checks if the stock has been sold.
   */
  @Test
  public void testESellStocks() throws IOException, ParseException {
    deleteFileInDirectory("modelTest1.csv");
    String[] name = {"modelTest1", "1"};
    String[] args = {"GOOG", "2022-11-11"};
    String[] args1 = {"3", "modelTest1"};
    String[] args2 = {"2", "modelTest1"};

    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, name, 0);
    obj.modelControllerInteract(TypeofAction.GET_STOCK_DATA, args, 0);
    obj.modelControllerInteract(TypeofAction.BUY_STOCKS, args1, 0);
    obj.modelControllerInteract(TypeofAction.SELL_STOCKS, args2, 0);

    String expected = super.readStockDataFromPortfolioCsv(name[0],
            1, 1, false);
    String actual = String.valueOf((System.currentTimeMillis() / 1000));
    assertEquals(expected, actual);
    deleteFileInDirectory("modelTest1.csv");
  }

  /**
   * This test checks creation of supported stocks.
   */
  @Test
  public void testFCreateSupportedStocks() throws IOException {
    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.INITIAL_SETUP, null, 0);
    BufferedReader supportedStocksData = null;
    try {
      supportedStocksData = new BufferedReader(new FileReader("data/SupportedStocks.csv"));
    } catch (Exception e) {
      System.out.println("Supported stocks file not found " + e.getMessage());
    }
    String actual = null;
    try {
      StringBuilder sb = new StringBuilder();
      String line = supportedStocksData.readLine();

      while (line != null) {
        sb.append(line);
        sb.append("\n");
        line = supportedStocksData.readLine();
      }
      actual = sb.toString();
    } finally {
      supportedStocksData.close();
    }

    String expected = "StockID,StockName,StockSymbol\n"
            + "1,Apple,AAPL\n"
            + "2,Amazon,AMZN\n"
            + "3,Microsoft,MSFT\n"
            + "4,Tesla,TSLA\n"
            + "5,Meta,META\n"
            + "6,Walmart,WMT\n"
            + "7,Johnson,JNJ\n"
            + "8,JPMorgan Chase,JPM\n"
            + "9,Google,GOOG\n"
            + "10,UnitedHealth,UNH\n";

    assertEquals(expected, actual);
    deleteDirectory();
  }
}



