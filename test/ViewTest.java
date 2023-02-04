import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Arrays;

import model.ModelControllerInteract;
import model.ModelControllerInteractImpl;
import model.TypeofAction;
import view.TypeofViews;
import view.ViewControllerInteract;
import view.ViewControllerInteractImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class contains tests for the View.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewTest extends TestParentClass {

  /**
   * This test displays main screen with the list of all options such as
   * create portfolio, value of portfolio on certain date,
   * value of portfolio on full composition, add stock, sell stock,
   * performance of portfolio, total amount invested on certain date and exit.
   */

  @Test
  public void testAMainScreen() {
    deleteDirectory();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.MAIN, null, 0);

    String expected = "\n"
            + "MENU\n"
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
            + "e. Exit\n"
            + "\n"
            + "ENTER YOUR CHOICE: \n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");
    assertEquals(expected, result);
  }

  /**
   * This test displays a message when the user doesn't have a portfolio.
   */

  @Test
  public void testBNoPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);

    String expected = "You dont have any portfolio.\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays a message to notify the user that the portfolio already exists.
   */
  @Test
  public void testCThisPortfolioExists() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_ALREADY_EXISTS, null, 0);

    String expected = "This portfolio already exists.\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message when the user attempts to input incorrect option.
   */
  @Test
  public void testDNotValidInputScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);

    String expected = "Not a valid input. Please enter the correct option.\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }


  /**
   * This test displays a message for the user to write the name of the portfolio.
   */
  @Test
  public void testECreatePortfolioNameScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.CREATE_PORTFOLIO_NAME_SCREEN, null, 0);

    String expected = "Enter the name for this portfolio.\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays a message to prompt the user to write the number of shares
   * that are needed to be bought.
   */
  @Test
  public void testFShowBuyStockValueScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.BUY_STOCKS_VALUE, null, 0);

    String expected = "\nHow many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays a message when the user wants to buy another stock or not.
   */
  @Test
  public void testGWouldYouLikeToBuyAnotherStockScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.BUY_ANOTHER_STOCK, null, 0);

    String expected = "Would you like to buy another stock? (Y|N)\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message when the user doesn't enter any name
   * at the time of portfolio creation.
   */
  @Test
  public void testHShowPortfolioNameReenter() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_NAME_REENTER, null, 0);

    String expected = "Cannot create a portfolio with empty name. Enter a valid name.\n"
            + "If you want to go back to main menu, press '0'.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message if the user enters invalid option while buying a stock.
   */
  @Test
  public void testIShowStockBuyReenter() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.STOCK_BUY_REENTER, null, 0);

    String expected = "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message if the user enters 0 or negative numbers
   * while buying the number of shares.
   */
  @Test
  public void testJShowStockBuyInvalidRetryScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.BUY_STOCKS_INVALID_RETRY, null, 0);

    String expected = "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays a message if the user wishes to go back to the main menu or back.
   */
  @Test
  public void testKShowPortfolioReviewScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.GOBACK_MAINMENU_OPTION, null, 0);

    String expected = "Press 'b' to go back and 'm' for main menu.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message if the user enters
   * invalid input for viewing portfolio.
   */
  @Test
  public void testLPortfolioInvalidEntryScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);

    String expected = "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays a message to override if the user
   * enters same name that already exists for portfolio creation.
   */
  @Test
  public void testMPfReenterDuplicateName() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.PF_REENTER_DUPLICATE_NAME, null, 0);

    String expected = "If you want to override this portfolio, then press 'y'. "
            + "If you want to enter another name, press 'n'. If you want to go main screen, "
            + "press 'b'.\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays an invalid message if user inputs incorrect date.
   */
  @Test
  public void testNCorrectDateScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);

    String expected = "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n" + "\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays screen after create portfolio with the list of all options such as
   * buy stock, main menu, exit.
   */
  @Test
  public void testOCreatePortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    String[] name = {"esha"};
    String s = Arrays.toString(name).replaceAll("\\[|\\]|,", "");
    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.CREATE_PORTFOLIO, name, 0);

    String expected = "\nCREATE PORTFOLIO MENU\n" + "\n"
            + s.toUpperCase() + " Portfolio\n" + "\n"
            + "1. Buy a stock\n"
            + "2. Invest by dollar-cost averaging\n"
            + "3. Main Menu\n"
            + "e. Exit\n" + "\n"
            + "ENTER YOUR CHOICE: \n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
    deleteFileInDirectory("pf_esha.csv");
  }

  /**
   * This test displays a message when portfolio has been successfully created.
   */
  @Test
  public void testPShowDisplayPortFolioCreated() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    String[] name = {"esha"};
    String s = Arrays.toString(name).replaceAll("\\[|\\]|,", "");
    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.DISPLAY_PORTFOLIO_CREATED, name, 0);

    String expected = "\n" + s.toUpperCase() + " PORTFOLIO CREATED...!!!\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }


  /**
   * This test displays a list of supported stocks.
   */
  @Test
  public void testQListOfSupportedStocksScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.LIST_OF_STOCKS, null, 0);

    String expected = "\n" + "\n" + getSupportedStocks();


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }


  /**
   * This test displays stock deatils.
   */
  @Test
  public void testRShowStockDataScreen() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    String[] stockDataView = super.readStockDataToShow();
    if (stockDataView == null) {
      return;
    }
    vciObj.showStockDataScreen(stockDataView);

    String line;
    String splitBy = ",";
    BufferedReader stockData = null;
    String[] splitStockData = new String[4];
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      output.println("Supported stocks file not found " + e.getMessage());
    }

    try {
      assert stockData != null;
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
    } catch (Exception e) {
      output.println("Error in reading Supported stocks csv file.");
    }


    String expected = "\nSTOCK DETAILS"
            + "\nStockName: " + splitStockData[0]
            + "\nSymbol: " + splitStockData[2]
            + "\nTime: " + splitStockData[3]
            + "\nPrice: $" + splitStockData[1] + "\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test asks the user to input the portfolio that needs to be displayed.
   */
  @Test
  public void testSWhichPortfolioCheck() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.WHICH_PORTFOLIO_CHECK, null, 0);

    String expected = "\nWhich portfolio would you like to check?\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
  }

  /**
   * This test displays the list of portfolios on the screen as options for the user.
   */
  @Test
  public void testTShowPortFolioCompositionScreen() {
    deleteFileInDirectory("pf_view1.csv");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    String[] name = {"view1", "1"};
    String[] stocks = {"MSFT", "2022-11-11"};

    String[] portfolioNames = {"view1", "ALL"};
    int length = 2;

    ModelControllerInteract obj = new ModelControllerInteractImpl();
    obj.modelControllerInteract(TypeofAction.CREATE_PORTFOLIO, name, 0);
    obj.modelControllerInteract(TypeofAction.GET_STOCK_DATA, stocks, 0);

    ViewControllerInteract vciObj = new ViewControllerInteractImpl(output);
    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, portfolioNames, length);

    String expected = "\nLIST OF PORTFOLIO\n" + "\n"
            + "1. VIEW1 (FLEXIBLE)\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expected, result);
    deleteFileInDirectory("pf_view1.csv");
    deleteDirectory();
  }

}
