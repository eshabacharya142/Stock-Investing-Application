import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import controller.ControllerViewInteract;
import controller.ControllerViewInteractImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class contains tests for the Controller.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest extends TestParentClass {

  // 1

  /**
   * This test checks the creation of portfolio.
   */
  @Test
  public void testAController_CreatePortfolio() throws IOException {
    deleteDirectory();
    deleteFileInDirectory("pf_controllerTest1.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest1" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-11" + "\n" + "2" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.setup();
    obj.start();

    String expectedOutput = getSetupScreen() + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST1 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest1", 1,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest1", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST1 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");
    input.close();
    bytes.close();
    output.close();
    assertEquals(expectedOutput, result);
  }

  // 2

  /**
   * This test checks the output for view portfolio on certain date.
   */
  @Test
  public void testBController_ViewPortfolio() throws IOException {
    String userInput = "2" + "\n" + "1" + "\n" + "2022-11-11" + "\n"
            + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST1 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on " + "2022-11-11" + "\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 2.0\t $"
            + Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest1",
            1, 6, false)) * 100) / 100
            + "\t $"
            + 2 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest1",
            1, 6, false)) * 100) / 100 + "\n"
            + "\nTotal Portfolio Value is on 2022-11-11: $"
            + 2 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest1",
            1, 6, false)) * 100) / 100 + "\n"
            + "\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 3

  /**
   * This test checks that portfolio is not created, if user doesn't buy any stock.
   */
  @Test
  public void testCController_WithoutBuyingAnythingNoPortfolioCreated() {
    String userInput = "1" + "\n" + "1" + "\n" + "controller2" + "\n" + "1" + "\n" + "1" + "\n"
            + "2022-11-11" + "\n" + "m" + "\n" + "2" + "\n" + "e" + "\n" + "b" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLER2 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)"
            + "\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: " + readStockDateFromStockDataCsv() + "\n"
            + "Price: $" + readStockPriceFromStockDataCsv() + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 4

  /**
   * This test checks two stocks of same ticker symbol that are bought on same day
   * and is seen combined in the view portfolio.
   */
  @Test
  public void testDController_ClubTwoStocksOfSameTickerComposition() throws IOException {
    deleteFileInDirectory("pf_controllerTest2.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest2" + "\n" + "1" + "\n" + "1" + "\n"
            + "2022-11-11" + "\n" + "2" + "\n" + "y" + "\n" + "1" + "\n" + "2022-11-11"
            + "\n" + "3" + "\n" + "n" + "\n"
            + "2" + "\n" + "2" + "\n" + "2022-11-11" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST2 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest2", 1,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest2", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest2", 2,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest2", 2,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST2 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST2 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-11\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 5.0\t $"
            + Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest2",
            1, 6, false)) * 100) / 100
            + "\t $"
            + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest2",
            1, 6, false)) * 100) / 100
            + "\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-11: $"
            + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest2",
            1, 6, false)) * 100) / 100
            + "\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 5

  /**
   * This test checks two stocks of same ticker symbol that are bought on different day
   * and is seen combined in the view portfolio.
   */
  @Test
  public void testEController_ClubTwoStocksOfSameTickerComposition2() throws IOException {
    deleteFileInDirectory("pf_controllerTest3.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest3" + "\n" + "1" + "\n" + "1" + "\n"
            + "2022-11-11" + "\n" + "2" + "\n" + "y" + "\n" + "1" + "\n" + "2022-11-12" + "\n" + "3"
            + "\n" + "n" + "\n" + "2" + "\n" + "3" + "\n" + "2022-11-12" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST3 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-11"
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest3", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12"
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest3", 2,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST3 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST3 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-12\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 5.0\t $"

            + Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            2, 6, false)) * 100) / 100
            + "\t $" + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            1, 6, false)) * 100) / 100
            + "\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-12: $"
            + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            1, 6, false)) * 100) / 100
            + "\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //6

  /**
   * This test checks how the same name portfolio gets overridden.
   */
  @Test
  public void testFController_OverrideSamePortfolio() throws IOException {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest3" + "\n" + "y" + "\n"
            + "1" + "\n" + "1" + "\n" + "2022-11-11" + "\n" + "5" + "\n" + "n"
            + "\n" + "2" + "\n" + "3" + "\n" + "2022-11-11" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "This portfolio already exists.\n"
            + "If you want to override this portfolio, then press 'y'. "
            + "If you want to enter another name, press 'n'. "
            + "If you want to go main screen, press 'b'.\n"
            + "\n"
            + "\nCREATE PORTFOLIO MENU\n"
            + "\nCONTROLLERTEST3 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest3", 1,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest3", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST3 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2022-11-11\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 5.0\t $"
            + Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            1, 6, false)) * 100) / 100
            + "\t $"
            + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            1, 6, false)) * 100) / 100 + "\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-11: $"
            + 5 * Math.floor(Double.parseDouble(readStockDataFromPortfolioCsv(
            "controllerTest3",
            1, 6, false)) * 100) / 100 + "\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 7

  /**
   * This test checks portfolio stocks after inputting some previous date.
   */
  @Test
  public void testGController_InputDate() throws IOException {
    String userInput = "3" + "\n" + "3" + "\n" + "2022-11-12" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2022-11-12\t Total Value\n"
            + "\nMicrosoft (MSFT) \t 5.0\t $"
            + Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100) / 100
            + "\t $"
            + 5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100) / 100)
            + "\n\nTotal Portfolio Value is on 2022-11-12: $"
            + 5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100) / 100)
            + "\n\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 8

  /**
   * This test checks portfolio stocks after inputting some previous date.
   */
  @Test
  public void testHController_InputDate2() {
    String userInput = "3" + "\n" + "3" + "\n" + "2019-12-12" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2019-12-12\t Total Value\n"
            + "\nMicrosoft (MSFT) \t 5.0\t $"
            + Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100) / 100
            + "\t $"
            + (5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100)) / 100)
            + "\n\nTotal Portfolio Value is on 2019-12-12: $"
            + (5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100)) / 100)
            + "\n\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 9

  /**
   * This test displays message when wrong input date is given by the user.
   */
  @Test
  public void testIController_InvalidDate() {
    String userInput = "3" + "\n" + "3" + "\n" + "9999-98-88" + "\n" + "2022-02-02"
            + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): "
            + "\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "Value of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2022-02-02\t Total Value\n"
            + "\nMicrosoft (MSFT) \t 5.0\t $"
            + Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100) / 100
            + "\t $"
            + (5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100)) / 100)
            + "\n\nTotal Portfolio Value is on 2022-02-02: $"
            + (5 * (Math.floor(Double.parseDouble(readStockPriceFromStockDataCsv()) * 100)) / 100)
            + "\n"
            + "\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 10

  /**
   * This test displays message when wrong input date is given by the user.
   */
  @Test
  public void testJController_InvalidDate2() {
    String userInput = "3" + "\n" + "3" + "\n" + "123445" + "\n" + "2022-02-02"
            + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): "
            + "\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\nValue of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2022-02-02\t Total Value\n"
            + "\nMicrosoft (MSFT) \t 5.0\t $" + Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100 + "\t $"
            + 5 * Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100 + "\n"
            + "\n"
            + "Total Portfolio Value is on 2022-02-02: $" + 5 * Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100 + "\n"
            + "\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 11

  /**
   * This test displays message when a future date is given by the user.
   */
  @Test
  public void testKController_InvalidDate3FutureDate() {
    String userInput = "3" + "\n" + "3" + "\n" + "2024-10-10" + "\n" + "2022-02-02"
            + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): "
            + "\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\nValue of CONTROLLERTEST3 PORTFOLIO\n"
            + "\nName (Symbol) \t Quantity\t Share Value on 2022-02-02\t Total Value\n"
            + "\nMicrosoft (MSFT) \t 5.0\t $" + Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100
            + "\t $" + 5 * Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100 + "\n"
            + "\n"
            + "Total Portfolio Value is on 2022-02-02: $" + 5 * Math.floor(Double.parseDouble(
            readStockPriceFromStockDataCsv()) * 100) / 100
            + "\n"
            + "\nPress 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 12

  /**
   * This test displays an invalid message when the user inputs
   * wrong option for selecting portfolio.
   */
  @Test
  public void testLController_EnterInvalidOptionForSelectingPortfolio() {
    String userInput = "2" + "\n" + "89" + "\n" + "b" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 13

  /**
   * This test displays an invalid message when the user inputs
   * future date on purchase.
   */

  @Test
  public void testMController_EnterFutureDateForPurchase() {
    String userInput = "1" + "\n" + "1" + "\n" + "test" + "\n" + "1" + "\n" + "9" + "\n"
            + "2023-10-01" + "\n" + "b" + "\n" + "e" + "\n";
    // 1 1 name 1 9 2023-10-01 b e
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back"
            + "\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 14

  /**
   * This test displays an invalid message when the user inputs
   * invalid date on purchase.
   */

  @Test
  public void testNController_EnterInvalidDateForPurchase() {
    String userInput = "1" + "\n" + "1" + "\n" + "test" + "\n" + "1" + "\n" + "9" + "\n"
            + "2021-13-13" + "\n" + "b" + "\n" + "e" + "\n";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back"
            + "\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 15

  /**
   * This test displays an invalid message when the user inputs
   * invalid date on adding to existing portfolio.
   */

  @Test
  public void testOController_EnterInvalidDateForAdd() {
    String userInput = "4" + "\n" + "1" + "\n" + "4" + "\n" + "2011-20-32" + "\n" + "b" + "\n"
            + "\n" + "e";
    // 4 1 4 20111-20-32 b e
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back"
            + "\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 16

  /**
   * This test displays an invalid message when the user inputs
   * future date while selling.
   */

  @Test
  public void testPController_EnterFutureDateForSell() {
    String userInput = "5" + "\n" + "1" + "\n" + "2023-01-01" + "\n" + "b"
            + "\n" + "m" + "\n" + "b" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 17

  /**
   * This test displays an invalid message when the user inputs
   * invalid date while selling.
   */

  @Test
  public void testQController_EnterInvalidDateForSell() {
    String userInput = "5" + "\n" + "2" + "\n" + "2680-34-13" + "\n" + "b"
            + "\n" + "m" + "\n" + "b" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 18

  /**
   * This test displays an invalid message when the user inputs
   * wrong option for initial choice of inputs.
   */
  @Test
  public void testRController_InvalidInitialChoice() {
    String userInput = "10" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 19

  /**
   * This test tests set of all invalid inputs.
   */
  @Test
  public void testSController_InvalidInputs() {
    String userInput = "11" + "\n" + "1" + "\n" + "1" + "\n" + "" + "\n" + "controllerTest4"
            + "\n" + "6" + "\n" + "1" + "\n" + "11" + "\n" + "10" + "\n" + "9900-72-00" + "\n"
            + "2022-01-01" + "\n" + "-4" + "\n" + "4.3" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "Cannot create a portfolio with empty name. Enter a valid name.\n"
            + "If you want to go back to main menu, press '0'.\n"
            + "\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST4 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST4 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\nEnter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back"
            + "\n"
            + "\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: " + readStockDateFromStockDataCsv() + "\n"
            + "Price: $" + readStockPriceFromStockDataCsv() + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 20

  /**
   * This test displays no portfolio creation if the number of stocks inputted by the user is zero.
   */
  @Test
  public void testSOController_HowManyStocksIsZeroSoPortfolioNotCreated() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest4" + "\n" + "1" + "\n" + "1" + "\n"
            + "2022-08-08" + "\n" + "0" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST4 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: " + readStockDateFromStockDataCsv() + "\n"
            + "Price: $" + readStockPriceFromStockDataCsv() + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 21

  /**
   * This test does not display the entry inside the portfolio when
   * the number of stocks inputted by the user is zero.
   */
  @Test
  public void testTController_HowManyStocksIsZeroSoStockNotDisplayedInPortfolio()
          throws IOException {
    deleteFileInDirectory("pf_controllerTest4.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest4" + "\n" + "1" + "\n" + "4" + "\n"
            + "2022-08-09" + "3" + "\n" + "y" + "\n" + "1" + "\n" + "2022-09-08" + "\n"
            + "0" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST4 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?"
            + "\nEnter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\nNot a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back"
            + "\n"
            + "\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Apple\n"
            + "Symbol: AAPL\n"
            + "Time: "
            + readStockDateFromStockDataCsv()
            + "\n"
            + "Price: $"
            + readStockPriceFromStockDataCsv()
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 22

  /**
   * This test displays 4 stocks bought by user.
   */
  @Test
  public void testUController_4StocksBoughtByTheUser() throws IOException {
    deleteFileInDirectory("pf_controllerTest4.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest4" + "\n" + "1" + "\n"
            + "1" + "\n" + "2022-11-11" + "\n" + "10" + "\n" + "Y" + "\n"
            + "2" + "\n" + "2022-11-11" + "\n" + "10" + "\n" + "y" + "\n"
            + "3" + "\n" + "2022-11-11" + "\n" + "10" + "\n" + "y" + "\n"
            + "4" + "\n" + "2022-11-11" + "\n" + "10" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST4 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest4", 1,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest4", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest4", 2,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest4", 2,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Google\n"
            + "Symbol: GOOG\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest4", 3,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest4", 3,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Apple\n"
            + "Symbol: AAPL\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest4", 4,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest4", 4,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST4 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 23

  /**
   * This test displays 5 stocks bought by user on different dates.
   */
  @Test
  public void testVController_5ByTheUser() throws IOException {
    deleteFileInDirectory("pf_controllerTest5.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest5" + "\n" + "1" + "\n"
            + "1" + "\n" + "2022-10-01" + "\n" + "10" + "\n" + "Y" + "\n"
            + "2" + "\n" + "2022-10-02" + "\n" + "10" + "\n" + "y" + "\n"
            + "3" + "\n" + "2022-10-03" + "\n" + "10" + "\n" + "y" + "\n"
            + "4" + "\n" + "2022-10-04" + "\n" + "10" + "\n" + "y" + "\n"
            + "5" + "\n" + "2022-10-05" + "\n" + "10" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST5 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest5", 1,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest5", 1,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest5", 2,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest5", 2,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Google\n"
            + "Symbol: GOOG\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest5", 3,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest5", 3,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Apple\n"
            + "Symbol: AAPL\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest5", 4,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest5", 4,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Tesla\n"
            + "Symbol: TSLA\n"
            + "Time: "
            + readStockDataFromPortfolioCsv("controllerTest5", 5,
            2, false)
            + "\n"
            + "Price: $"
            + readStockDataFromPortfolioCsv("controllerTest5", 5,
            6, false)
            + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST5 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 24

  /**
   * This test displays 10 stocks bought by user.
   */
  @Test
  public void testWController_10StocksBoughtByUser() throws IOException {
    deleteFileInDirectory("pf_controllerTest6.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest6" + "\n" + "1" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "Y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "y" + "\n"
            + "2" + "\n" + "2022-10-10" + "\n" + "1" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST6 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            1, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            1,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            2, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            2,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            3, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            3,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            4, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            4,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            5, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            5,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            6, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            6,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            7, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            7,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            8, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            8,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            9, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            9,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: " + readStockDataFromPortfolioCsv("controllerTest6",
            10, 2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv("controllerTest6",
            10,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST6 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 25

  /**
   * This test takes initial input as exit.
   */
  @Test
  public void testXController_InitialChoiceExit() {
    String userInput = "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 26

  /**
   * This test tests for large input for the number of stocks.
   */
  @Test
  public void testYController_InvalidHowMany() throws IOException {
    deleteFileInDirectory("pf_controllerTest7.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest7" + "\n" + "2"
            + "1" + "\n" + "controllerTest7" + "\n"
            + "1" + "\n" + "10" + "\n" + "2022-08-07" + "\n" + "14000000000000000000"
            + "\n" + "10000000" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST7 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST7 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST7 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: " + readStockDataFromPortfolioCsv(
            "controllerTest7", 1,
            2, false) + "\n"
            + "Price: $" + readStockDataFromPortfolioCsv(
            "controllerTest7", 1,
            6, false) + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST7 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";
    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 27

  /**
   * This test throws a message when portfolio name is an empty string.
   */
  @Test
  public void testZController_StringEmptyForName() {
    String userInput = "1" + "\n" + "1" + "\n" + "" + "\n" + "0" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "Cannot create a portfolio with empty name. Enter a valid name.\n"
            + "If you want to go back to main menu, press '0'.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 28

  /**
   * This test throws error message when user inputs wrong option for displaying portfolio
   * from a list of portfolio.
   */
  @Test
  public void testZAController_ViewPortfolioAndEnterWrongOption() {
    String userInput = "2" + "\n" + "-52" + "\n" + "b" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 29

  /**
   * This test throws error message when user inputs wrong option for displaying portfolio
   * from a list of portfolio.
   */
  @Test
  public void testZBController_ValueOfPortfolioEnterWrongPortfolioNumber() {
    String userInput = "3" + "\n" + "-9" + "\n" + "b" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 30

  /**
   * This test displays an invalid input message when user enters invalid entry while buying stock.
   */
  @Test
  public void testZCController_BuyStockEnterWrongStockOption() {
    String userInput = "1" + "\n" + "1" + "\n" + "test" + "\n" + "1" + "\n"
            + "0" + "\n" + "90" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 31

  /**
   * This test displays an invalid input message when user enters invalid entry while
   * entering number of stocks.
   */
  @Test
  public void testZDController_BuyStockEnterInvalidHowManyStocks() {
    String userInput = "1" + "\n" + "1" + "\n" + "e" + "\n" + "1" + "\n" + "9" + "\n"
            + "2022-11-11" + "\n" + "0" + "\n" + "-85" + "\n" + "8.90" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "E Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\nSTOCK DETAILS\n"
            + "StockName: UnitedHealth\n"
            + "Symbol: UNH\n"
            + "Time: 2022-11-11\n"
            + "Price: $" + readStockPriceFromStockDataCsv() + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Not a valid input. Reenter.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 32

  /**
   * This test checks the operations of back and main menu.
   */
  @Test
  public void testZEController_ToggleBetweenMainMenuAndBack() {
    String userInput = "1" + "\n" + "1" + "\n" + "test" + "\n" + " 6" + "\n" + "3" + "\n"
            + "-1" + "\n" + "b" + "\n" + "3" + "\n" + "1" + "\n" + "-1" + "\n" + "b"
            + "\n" + "-1" + "\n" + "b" + "\n" + "1" + "\n" + "1" + "\n"
            + "test" + "\n" + "1" + "\n" + "-1" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Invalid command. Enter the right option number.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Not a valid input. Please enter the correct portfolio.\n"
            + "Press 'b' to go back to the previous menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 33

  /**
   * This test checks when stock purchased on a specifc date
   * and sold the same stock at a later date.
   */
  @Test
  public void testZFController_BuyAndSell1() {
    deleteFileInDirectory("pf_controllerTest8.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest8" + "\n" + "1" + "\n"
            + "2" + "\n" + "2022-02-02" + "\n" + "100" + "\n" + "n" + "\n"
            + "5" + "\n" + "8" + "\n" + "2022-07-07" + "\n" + "1" + "\n" + "1" + "\n" + "m" + "\n"
            + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST8 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-02-02\n"
            + "Price: $327.8200\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST8 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-07-07\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-07-07\n"
            + "Price: $169.4500\n"
            + "\n"
            + "You can sell only 100.0 shares of this stock on 2022-07-07\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 34

  /**
   * This test throws a message when stock purchased on a specific date
   * and sold the same stock at a previous date than that of the purchase.
   */
  @Test
  public void testZGController_BuyAndSell2() {
    deleteFileInDirectory("pf_controllerTest9.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest9" + "\n" + "1" + "\n"
            + "2" + "\n" + "2022-02-02" + "\n" + "100" + "\n" + "n" + "\n"
            + "5" + "\n" + "2" + "\n" + "2022-01-01" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST9 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-02-02\n"
            + "Price: $327.8200\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST9 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "You don't own any stocks before this date\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //35

  /**
   * This test checks the combinations of stocks purchased while creating portfolio,
   * stocks added and sold.
   */

  @Test
  public void testZHController_BuyAndSell3() {
    deleteFileInDirectory("pf_controllerTest90.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest90" + "\n" + "1" + "\n" + "5"
            + "\n" + "2022-03-03" + "\n" + "100" + "\n" + "y" + "\n" + "6" + "\n" + "2022-01-01"
            + "\n" + "50" + "\n" + "y" + "\n" + "10" + "\n" + "2022-02-02" + "\n" + "150"
            + "\n" + "n" + "\n" + "5" + "\n" + "10" + "\n" + "2022-01-01" + "\n" + "1" + "\n"
            + "20" + "\n" + "m" + "\n" + "5" + "\n" + "10" + "\n" + "2022-03-03" + "\n"
            + "3" + "\n" + "30" + "\n" + "m" + "\n" + "4" + "\n" + "10" + "\n" + "10"
            + "\n" + "2022-02-14" + "\n" + "7" + "\n" + "n" + "\n" + "5" + "\n" + "10" + "\n"
            + "2022-02-15" + "\n" + "2" + "\n" + "6" + "\n" + "m" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST90 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Tesla\n"
            + "Symbol: TSLA\n"
            + "Time: 2022-03-03\n"
            + "Price: $878.7700\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: JPMorgan Chase\n"
            + "Symbol: JPM\n"
            + "Time: 2022-01-01\n"
            + "Price: $158.4500\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-02\n"
            + "Price: $141.0000\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST90 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-01-01\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tJPMorgan Chase (JPM) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: JPMorgan Chase\n"
            + "Symbol: JPM\n"
            + "Time: 2022-01-01\n"
            + "Price: $158.4500\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-01-01\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-03-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tTesla (TSLA) \n"
            + "2.\tJPMorgan Chase (JPM) \n"
            + "3.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-03-03\n"
            + "Price: $137.2900\n"
            + "\n"
            + "You can sell only 150.0 shares of this stock on 2022-03-03\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-14\n"
            + "Price: $135.3300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-02-15\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tJPMorgan Chase (JPM) \n"
            + "2.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-15\n"
            + "Price: $134.7400\n"
            + "\n"
            + "You can sell only 127.0 shares of this stock on 2022-02-15\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //36

  /**
   * This test checks value of portfolio (option 2) on various dates
   * for portfolio controllerTest90.
   */
  @Test
  public void testZIController_AddtoExistingPortfolioAndSell1() {
    String userInput = "2" + "\n" + "10" + "\n" + "2022-01-01" + "\n" + "m" + "\n" + "2" + "\n"
            + "10" + "\n" + "2022-02-02" + "\n" + "m" + "\n" + "2" + "\n" + "10" + "\n"
            + "2022-03-03" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-01-01\t Total Value\n"
            + "\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $158.44\t $4753.2\n"
            + "\n"
            + "Total Portfolio Value is on 2022-01-01: $4753.2\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-02-02\t Total Value\n"
            + "\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $150.5\t $4515.0\n"
            + "Walmart (WMT) \t 150.0\t $141.0\t $21150.0\n"
            + "\n"
            + "Total Portfolio Value is on 2022-02-02: $25665.0\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-03-03\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $878.77\t $87877.0\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $139.84\t $4195.2\n"
            + "Walmart (WMT) \t 121.0\t $137.29\t $16612.09\n"
            + "\n"
            + "Total Portfolio Value is on 2022-03-03: $108684.29\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //37

  /**
   * This test checks value of portfolio on full composition (option 3) on various dates
   * for portfolio controllerTest90.
   */
  @Test
  public void testZKController_AddtoExistingPortfolioAndSell2() {
    String userInput = "3" + "\n" + "10" + "\n" + "2022-01-01" + "\n" + "m" + "\n" + "3" + "\n"
            + "10" + "\n" + "2022-02-02" + "\n" + "m" + "\n" + "3" + "\n" + "10" + "\n"
            + "2022-03-03" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-01-01\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $1073.44\t $107344.0\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $158.44\t $4753.2\n"
            + "Walmart (WMT) \t 121.0\t $143.19\t $17325.98\n"
            + "\n"
            + "Total Portfolio Value is on 2022-01-01: $129423.18\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-02-02\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $928.18\t $92818.0\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $150.5\t $4515.0\n"
            + "Walmart (WMT) \t 121.0\t $141.0\t $17061.0\n"
            + "\n"
            + "Total Portfolio Value is on 2022-02-02: $114394.0\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-03-03\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $878.77\t $87877.0\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $139.84\t $4195.2\n"
            + "Walmart (WMT) \t 121.0\t $137.29\t $16612.09\n"
            + "\n"
            + "Total Portfolio Value is on 2022-03-03: $108684.29\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //38

  /**
   * This test adds stock to the current portfolio.
   */
  @Test
  public void testZLController_AddStockToCurrentPortfolio() {
    String userInput = "4" + "\n" + "10" + "\n" + "6" + "\n" + "2022-11-11" + "\n" + "500" + "\n"
            + "y" + "\n" + "4" + "\n" + "10" + "\n" + "5" + "\n" + "2022-11-12" + "\n" + "40" + "\n"
            + "n" + "\n" + "2" + "\n" + "10" + "\n" + "2022-11-11" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: JPMorgan Chase\n"
            + "Symbol: JPM\n"
            + "Time: 2022-11-11\n"
            + "Price: $135.1900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Apple\n"
            + "Symbol: AAPL\n"
            + "Time: 2022-11-12\n"
            + "Price: $145.8200\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST90 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-11\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $186.0\t $18600.0\n"
            + "JPMorgan Chase (JPM) \t 530.0\t $135.19\t $71650.7\n"
            + "Walmart (WMT) \t 121.0\t $142.66\t $17261.86\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-11: $107512.56\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
    deleteFileInDirectory("pf_controllerTest90.csv");
  }

  // 39

  /**
   * This test sells stocks from current portfolio.
   */
  @Test
  public void testZMController_SellStockFromCurrentPortfolio() {
    deleteFileInDirectory("pf_controllerTest90.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest90" + "\n" + "1" + "\n" + "5"
            + "\n" + "2022-03-03" + "\n" + "100" + "\n" + "y" + "\n" + "6" + "\n" + "2022-01-01"
            + "\n" + "50" + "\n" + "y" + "\n" + "10" + "\n" + "2022-02-02" + "\n" + "150"
            + "\n" + "n" + "\n" + "5" + "\n" + "10" + "\n" + "2022-01-01" + "\n" + "1" + "\n"
            + "20" + "\n" + "m" + "\n" + "5" + "\n" + "10" + "\n" + "2022-03-03" + "\n" + "3"
            + "\n" + "30" + "\n" + "m" + "\n" + "4" + "\n" + "10" + "\n" + "10" + "\n"
            + "2022-02-14" + "\n" + "7" + "\n" + "n" + "\n" + "5" + "\n" + "10" + "\n"
            + "2022-02-15" + "\n" + "2" + "\n" + "6" + "\n" + "m" + "\n" + "5" + "\n"
            + "10" + "\n" + "2022-09-09" + "\n" + "1" + "\n" + "100" + "\n" + "m"
            + "\n" + "2" + "\n" + "10" + "\n" + "2022-09-09" + "\n" + "m"
            + "\n" + "2" + "\n" + "10" + "\n"
            + "2022-09-08" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST90 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Tesla\n"
            + "Symbol: TSLA\n"
            + "Time: 2022-03-03\n"
            + "Price: $878.7700\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: JPMorgan Chase\n"
            + "Symbol: JPM\n"
            + "Time: 2022-01-01\n"
            + "Price: $158.4500\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-02\n"
            + "Price: $141.0000\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST90 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-01-01\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tJPMorgan Chase (JPM) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: JPMorgan Chase\n"
            + "Symbol: JPM\n"
            + "Time: 2022-01-01\n"
            + "Price: $158.4500\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-01-01\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-03-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tTesla (TSLA) \n"
            + "2.\tJPMorgan Chase (JPM) \n"
            + "3.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-03-03\n"
            + "Price: $137.2900\n"
            + "\n"
            + "You can sell only 150.0 shares of this stock on 2022-03-03\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-14\n"
            + "Price: $135.3300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-02-15\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tJPMorgan Chase (JPM) \n"
            + "2.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-15\n"
            + "Price: $134.7400\n"
            + "\n"
            + "You can sell only 127.0 shares of this stock on 2022-02-15\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-09-09\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tTesla (TSLA) \n"
            + "2.\tJPMorgan Chase (JPM) \n"
            + "3.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Tesla\n"
            + "Symbol: TSLA\n"
            + "Time: 2022-09-09\n"
            + "Price: $291.6700\n"
            + "\n"
            + "You can sell only 100.0 shares of this stock on 2022-09-09\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-09-09\t Total Value\n"
            + "\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $119.14\t $3574.2\n"
            + "Walmart (WMT) \t 121.0\t $136.3\t $16492.3\n"
            + "\n"
            + "Total Portfolio Value is on 2022-09-09: $20066.5\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST90 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-09-08\t Total Value\n"
            + "\n"
            + "Tesla (TSLA) \t 100.0\t $281.3\t $28130.0\n"
            + "JPMorgan Chase (JPM) \t 30.0\t $115.28\t $3458.4\n"
            + "Walmart (WMT) \t 121.0\t $135.4\t $16383.4\n"
            + "\n"
            + "Total Portfolio Value is on 2022-09-08: $47971.8\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 40

  /**
   * This test checks value portfolio on certain date for past date.
   */
  @Test
  public void testZNController_ValueOfPortfolioOnCertainDate() {
    String userInput = "2" + "\n" + "10" + "\n" + "2014-01-01" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "The value of portfolio on 2014-01-01 is $0.00\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  //41

  /**
   * This test creates portfolio with combination of purchases and sells in different dates.
   */
  @Test
  public void testZOController_PurchasesAndSells() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest91" + "\n" + "1" + "\n" + "8"
            + "\n" + "2014-03-03" + "\n" + "100" + "\n" + "y" + "\n" + "10" + "\n" + "2018-01-01"
            + "\n" + "50" + "\n" + "y" + "\n" + "1" + "\n" + "2000-02-02" + "\n" + "150"
            + "\n" + "n" + "\n" + "5" + "\n" + "11" + "\n" + "2019-01-01" + "\n" + "1" + "\n"
            + "20" + "\n" + "m" + "\n" + "5" + "\n" + "11" + "\n" + "2022-03-03" + "\n" + "3"
            + "\n" + "30" + "\n" + "m" + "\n" + "4" + "\n" + "10" + "\n" + "10" + "\n"
            + "2022-02-14" + "\n" + "7" + "\n" + "n" + "\n" + "5" + "\n" + "11" + "\n"
            + "2022-02-15" + "\n" + "2" + "\n" + "6" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST91 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2014-03-03\n"
            + "Price: $358.7350\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2018-01-01\n"
            + "Price: $99.4000\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2000-02-02\n"
            + "Price: $102.4400\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST91 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2019-01-01\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2019-01-01\n"
            + "Price: $1510.8000\n"
            + "\n"
            + "You can sell only 100.0 shares of this stock on 2019-01-01\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-03-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-03-03\n"
            + "Price: $302.8900\n"
            + "\n"
            + "You can sell only 150.0 shares of this stock on 2022-03-03\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-14\n"
            + "Price: $135.3300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-02-15\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2022-02-15\n"
            + "Price: $134.7400\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-02-15\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //42

  /**
   * This test checks performance by year.
   */
  @Test
  public void testZPController_PerformanceByYear() {
    String userInput = "6" + "\n" + "11" + "\n" + "1" + "\n" + "2010" + "\n" + "10"
            + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start year in format (YYYY) from year 2000 to "
            + today.minusYears(4).getYear() + ":\n"
            + "Enter the number of years (5 to 13): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST91 from 2010 to 2019\n"
            + "\n"
            + "2010: *\n"
            + "2011: *\n"
            + "2012: *\n"
            + "2013: *\n"
            + "2014: **********\n"
            + "2015: *********************\n"
            + "2016: ************************\n"
            + "2017: *************************************\n"
            + "2018: ************************************************\n"
            + "2019: **************************************************\n"
            + "\n"
            + "Scale: * = $3537\n"
            + "# - either no stocks or 0 value in portfolio.\n\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //43

  /**
   * This test creates portfolio.
   */
  @Test
  public void testZQController_CreationOfPortfolioFlexible() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest92" + "\n" + "1" + "\n" + "8"
            + "\n" + "2014-03-03" + "\n" + "100" + "\n" + "y" + "\n" + "10" + "\n" + "2014-01-01"
            + "\n" + "50" + "\n" + "y" + "\n" + "1" + "\n" + "2014-02-02" + "\n" + "150"
            + "\n" + "n" + "\n" + "5" + "\n" + "12" + "\n" + "2014-01-01" + "\n" + "1" + "\n"
            + "20" + "\n" + "m" + "\n" + "5" + "\n" + "12" + "\n" + "2014-03-03" + "\n" + "3"
            + "\n" + "30" + "\n" + "m" + "\n" + "4" + "\n" + "10" + "\n" + "10" + "\n"
            + "2014-02-14" + "\n" + "7" + "\n" + "n"
            + "\n" + "5" + "\n" + "12" + "\n" + "2014-02-15" + "\n" + "2" + "\n" + "6" + "\n"
            + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST92 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2014-03-03\n"
            + "Price: $358.7350\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2014-01-01\n"
            + "Price: $78.6600\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2014-02-02\n"
            + "Price: $36.9500\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST92 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2014-01-01\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2014-01-01\n"
            + "Price: $78.6600\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2014-01-01\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2014-03-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2014-03-03\n"
            + "Price: $37.9200\n"
            + "\n"
            + "You can sell only 150.0 shares of this stock on 2014-03-03\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2014-02-14\n"
            + "Price: $75.4000\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2014-02-15\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tWalmart (WMT) \n"
            + "2.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2014-02-15\n"
            + "Price: $37.3900\n"
            + "\n"
            + "You can sell only 120.0 shares of this stock on 2014-02-15\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //44

  /**
   * This test checks by month.
   */
  @Test
  public void testZRController_PerformanceByMonth() {
    String userInput = "6" + "\n" + "12" + "\n" + "2" + "\n" + "2014-01"
            + "\n" + "5" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start month in format (YYYY-MM) from year 2000 to AUGUST 2022:\n"
            + "Enter the number of months (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST92 from JANUARY 2014 to MAY 2014\n"
            + "\n"
            + "JAN 2014: **\n"
            + "FEB 2014: *********\n"
            + "MAR 2014: **************************************************\n"
            + "APR 2014: ********************************************\n"
            + "MAY 2014: **********************************************\n"
            + "\n"
            + "Scale: * = $822\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //45

  /**
   * This test checks by day.
   */
  @Test
  public void testZSController_PerformanceByDay() {
    String userInput = "6" + "\n" + "12" + "\n" + "3" + "\n" + "2014-01-01" + "\n"
            + "5" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start date in format (YYYY-MM-DD) from year 2000 to "
            + today.minusDays(4).getYear() + "-"
            + today.minusDays(4).getMonth() + "-"
            + today.minusDays(4).getDayOfMonth() + ":\n"
            + "Enter the number of days (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST92 from 1 JANUARY 2014 to 5 JANUARY 2014\n"
            + "\n"
            + "JAN 01 2014: **************************************************\n"
            + "JAN 02 2014: **************************************************\n"
            + "JAN 03 2014: **************************************************\n"
            + "JAN 04 2014: **************************************************\n"
            + "JAN 05 2014: **************************************************\n"
            + "\n"
            + "Scale: * = $47\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //46

  /**
   * This test creates portfolio where the buying and selling takes place within days.
   * This testcase is written for showing performance for DAY.
   */
  @Test
  public void testZTController_CreationOfPortfolioForShowingPerformanceForDay() {
    deleteFileInDirectory("controllerTest93.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest93" + "\n" + "1" + "\n" + "8"
            + "\n" + "2015-03-03" + "\n" + "100" + "\n" + "y" + "\n" + "10" + "\n" + "2015-03-01"
            + "\n" + "50" + "\n" + "y" + "\n" + "1" + "\n" + "2015-03-02" + "\n" + "150"
            + "\n" + "n" + "\n" + "5" + "\n" + "13" + "\n" + "2015-03-01" + "\n" + "1" + "\n"
            + "20" + "\n" + "m" + "\n" + "5" + "\n" + "13" + "\n" + "2015-03-03" + "\n" + "3"
            + "\n" + "30" + "\n" + "m" + "\n" + "4" + "\n" + "10" + "\n" + "10" + "\n"
            + "2015-03-14" + "\n" + "7" + "\n" + "n" + "\n" + "5" + "\n" + "13" + "\n"
            + "2015-03-15" + "\n" + "2" + "\n" + "6" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST93 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2015-03-03\n"
            + "Price: $383.9500\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2015-03-01\n"
            + "Price: $83.7200\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2015-03-02\n"
            + "Price: $43.6700\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST93 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2015-03-01\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tWalmart (WMT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2015-03-01\n"
            + "Price: $83.7200\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2015-03-01\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2015-03-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2015-03-03\n"
            + "Price: $43.5600\n"
            + "\n"
            + "You can sell only 150.0 shares of this stock on 2015-03-03\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2015-03-14\n"
            + "Price: $81.9500\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2015-03-15\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "2.\tWalmart (WMT) \n"
            + "3.\tMicrosoft (MSFT) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: 2015-03-15\n"
            + "Price: $81.9500\n"
            + "\n"
            + "You can sell only 30.0 shares of this stock on 2015-03-15\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //47

  /**
   * This test checks by day.
   */
  @Test
  public void testZUController_PerformanceByDay2() {
    String userInput = "6" + "\n" + "12" + "\n" + "3" + "\n" + "2015-03-01"
            + "\n" + "30" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start date in format (YYYY-MM-DD) from year 2000 to "
            + today.minusDays(4).getYear() + "-"
            + today.minusDays(4).getMonth() + "-"
            + today.minusDays(4).getDayOfMonth() + ":\n"
            + "Enter the number of days (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST92 from 1 MARCH 2015 to 30 MARCH 2015\n"
            + "\n"
            + "MAR 01 2015: *************************************************\n"
            + "MAR 02 2015: *************************************************\n"
            + "MAR 03 2015: *************************************************\n"
            + "MAR 04 2015: **************************************************\n"
            + "MAR 05 2015: **************************************************\n"
            + "MAR 06 2015: *************************************************\n"
            + "MAR 07 2015: *************************************************\n"
            + "MAR 08 2015: *************************************************\n"
            + "MAR 09 2015: *************************************************\n"
            + "MAR 10 2015: *************************************************\n"
            + "MAR 11 2015: ************************************************\n"
            + "MAR 12 2015: ***********************************************\n"
            + "MAR 13 2015: ************************************************\n"
            + "MAR 14 2015: ************************************************\n"
            + "MAR 15 2015: ************************************************\n"
            + "MAR 16 2015: ************************************************\n"
            + "MAR 17 2015: ************************************************\n"
            + "MAR 18 2015: ************************************************\n"
            + "MAR 19 2015: ************************************************\n"
            + "MAR 20 2015: ************************************************\n"
            + "MAR 21 2015: ************************************************\n"
            + "MAR 22 2015: ************************************************\n"
            + "MAR 23 2015: *************************************************\n"
            + "MAR 24 2015: ************************************************\n"
            + "MAR 25 2015: ************************************************\n"
            + "MAR 26 2015: ***********************************************\n"
            + "MAR 27 2015: ***********************************************\n"
            + "MAR 28 2015: ***********************************************\n"
            + "MAR 29 2015: ***********************************************\n"
            + "MAR 30 2015: ************************************************\n"
            + "\n"
            + "Scale: * = $919\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //48

  /**
   * This test checks performance invalid year,or month or day or invalid range.
   */
  @Test
  public void testZVController_ChecksInvalidInputForPerformanceByYearMonthDay() {
    String userInput = "6" + "\n" + "10" + "\n" + "1" + "\n" + "2045" + "\n" + "b" + "\n"
            + "10" + "\n" + "2" + "\n" + "2023-07" + "\n" + "b" + "\n" + "10" + "\n" + "2024-11-10"
            + "\n" + "b" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start year in format (YYYY) from year 2000 to "
            + today.minusYears(4).getYear() + ":\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start month in format (YYYY-MM) from year 2000 to AUGUST 2022:\n"
            + "Not a valid input. Please enter the correct date.\n"
            + "Press 'b' to go back\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Not a valid input. Please enter the correct option.\n"
            + "Press 'm' to go back to main menu.\n"
            + "\n"
            + "Not a valid input. Please enter the correct option.\n"
            + "Press 'm' to go back to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //49

  /**
   * This test checks performance with # when no value.
   */
  @Test
  public void testZWController_PerformanceToShowHash_noValue() {
    String userInput = "6" + "\n" + "2" + "\n" + "1" + "\n" + "2000" + "\n" + "5" + "\n" + "m"
            + "\n" + "6" + "\n" + "2" + "\n" + "2" + "\n" + "2000-02" + "\n" + "30" + "\n"
            + "m" + "\n" + "6" + "\n" + "2" + "\n" + "2" + "\n" + "2000-02" + "\n" + "30"
            + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start year in format (YYYY) from year 2000 to "
            + today.minusYears(4).getYear() + ":\n"
            + "Enter the number of years (5 to 23): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST2 from 2000 to 2004\n"
            + "\n"
            + "2000: #\n"
            + "2001: #\n"
            + "2002: #\n"
            + "2003: #\n"
            + "2004: #\n"
            + "\n"
            + "Scale: * = $0\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start month in format (YYYY-MM) from year 2000 to AUGUST 2022:\n"
            + "Enter the number of months (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST2 from FEBRUARY 2000 to JULY 2002\n"
            + "\n"
            + "FEB 2000: #\n"
            + "MAR 2000: #\n"
            + "APR 2000: #\n"
            + "MAY 2000: #\n"
            + "JUN 2000: #\n"
            + "JUL 2000: #\n"
            + "AUG 2000: #\n"
            + "SEP 2000: #\n"
            + "OCT 2000: #\n"
            + "NOV 2000: #\n"
            + "DEC 2000: #\n"
            + "JAN 2001: #\n"
            + "FEB 2001: #\n"
            + "MAR 2001: #\n"
            + "APR 2001: #\n"
            + "MAY 2001: #\n"
            + "JUN 2001: #\n"
            + "JUL 2001: #\n"
            + "AUG 2001: #\n"
            + "SEP 2001: #\n"
            + "OCT 2001: #\n"
            + "NOV 2001: #\n"
            + "DEC 2001: #\n"
            + "JAN 2002: #\n"
            + "FEB 2002: #\n"
            + "MAR 2002: #\n"
            + "APR 2002: #\n"
            + "MAY 2002: #\n"
            + "JUN 2002: #\n"
            + "JUL 2002: #\n"
            + "\n"
            + "Scale: * = $0\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start month in format (YYYY-MM) from year 2000 to AUGUST 2022:\n"
            + "Enter the number of months (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST2 from FEBRUARY 2000 to JULY 2002\n"
            + "\n"
            + "FEB 2000: #\n"
            + "MAR 2000: #\n"
            + "APR 2000: #\n"
            + "MAY 2000: #\n"
            + "JUN 2000: #\n"
            + "JUL 2000: #\n"
            + "AUG 2000: #\n"
            + "SEP 2000: #\n"
            + "OCT 2000: #\n"
            + "NOV 2000: #\n"
            + "DEC 2000: #\n"
            + "JAN 2001: #\n"
            + "FEB 2001: #\n"
            + "MAR 2001: #\n"
            + "APR 2001: #\n"
            + "MAY 2001: #\n"
            + "JUN 2001: #\n"
            + "JUL 2001: #\n"
            + "AUG 2001: #\n"
            + "SEP 2001: #\n"
            + "OCT 2001: #\n"
            + "NOV 2001: #\n"
            + "DEC 2001: #\n"
            + "JAN 2002: #\n"
            + "FEB 2002: #\n"
            + "MAR 2002: #\n"
            + "APR 2002: #\n"
            + "MAY 2002: #\n"
            + "JUN 2002: #\n"
            + "JUL 2002: #\n"
            + "\n"
            + "Scale: * = $0\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //50

  /**
   * This test checks performance for a portfolio by month.
   */
  @Test
  public void testZXController_PerformanceByMonth() {
    String userInput = "6" + "\n" + "11" + "\n" + "2" + "\n" + "2022-01"
            + "\n" + "10" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start month in format (YYYY-MM) from year 2000 to AUGUST 2022:\n"
            + "Enter the number of months (5 to 12): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST91 from JANUARY 2022 to OCTOBER 2022\n"
            + "\n"
            + "JAN 2022: *********************************************\n"
            + "FEB 2022: ***********************************************\n"
            + "MAR 2022: **************************************************\n"
            + "APR 2022: ****************************************\n"
            + "MAY 2022: ************************************\n"
            + "JUN 2022: *******\n"
            + "JUL 2022: ********\n"
            + "AUG 2022: *******\n"
            + "SEP 2022: *******\n"
            + "OCT 2022: ******\n"
            + "\n"
            + "Scale: * = $6210\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //51

  /**
   * This test checks performance by year.
   */
  @Test
  public void testZYController_PerformanceByYear() {
    String userInput = "6" + "\n" + "11" + "\n" + "1" + "\n" + "2010"
            + "\n" + "7" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start year in format (YYYY) from year 2000 to "
            + today.minusYears(4).getYear() + ":\n"
            + "Enter the number of years (5 to 13): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST91 from 2010 to 2016\n"
            + "\n"
            + "2010: **\n"
            + "2011: **\n"
            + "2012: **\n"
            + "2013: ***\n"
            + "2014: **********************\n"
            + "2015: ********************************************\n"
            + "2016: **************************************************\n"
            + "\n"
            + "Scale: * = $1721\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //52

  /**
   * This test checks performance by day.
   */
  @Test
  public void testZZController_PerformanceByDay3() {
    String userInput = "6" + "\n" + "11" + "\n" + "3" + "\n"
            + "2010-01-01" + "\n" + "30" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    LocalDate today = LocalDate.now();
    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start date in format (YYYY-MM-DD) from year 2000 to "
            + today.minusDays(4).getYear() + "-"
            + today.minusDays(4).getMonth() + "-"
            + today.minusDays(4).getDayOfMonth() + ":\n"
            + "Enter the number of days (5 to 30): \n"
            + "\n"
            + "Performance of portfolio CONTROLLERTEST91 from 1 JANUARY 2010 to 30 JANUARY 2010\n"
            + "\n"
            + "JAN 01 2010: *************************************************\n"
            + "JAN 02 2010: *************************************************\n"
            + "JAN 03 2010: *************************************************\n"
            + "JAN 04 2010: *************************************************\n"
            + "JAN 05 2010: *************************************************\n"
            + "JAN 06 2010: *************************************************\n"
            + "JAN 07 2010: *************************************************\n"
            + "JAN 08 2010: ************************************************\n"
            + "JAN 09 2010: ************************************************\n"
            + "JAN 10 2010: ************************************************\n"
            + "JAN 11 2010: *************************************************\n"
            + "JAN 12 2010: ************************************************\n"
            + "JAN 13 2010: ************************************************\n"
            + "JAN 14 2010: ************************************************\n"
            + "JAN 15 2010: **************************************************\n"
            + "JAN 16 2010: **************************************************\n"
            + "JAN 17 2010: **************************************************\n"
            + "JAN 18 2010: **************************************************\n"
            + "JAN 19 2010: *************************************************\n"
            + "JAN 20 2010: *************************************************\n"
            + "JAN 21 2010: *************************************************\n"
            + "JAN 22 2010: ************************************************\n"
            + "JAN 23 2010: ************************************************\n"
            + "JAN 24 2010: ************************************************\n"
            + "JAN 25 2010: ***********************************************\n"
            + "JAN 26 2010: ***********************************************\n"
            + "JAN 27 2010: ***********************************************\n"
            + "JAN 28 2010: ************************************************\n"
            + "JAN 29 2010: ************************************************\n"
            + "JAN 30 2010: ************************************************\n"
            + "\n"
            + "Scale: * = $93\n"
            + "# - either no stocks or 0 value in portfolio.\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //53

  /**
   * This test checks invalid input for performance.
   */
  @Test
  public void testZZAController_InvalidInputForPerformance() {
    String userInput = "6" + "\n" + "11" + "\n" + "3" + "\n"
            + "2010-01-01" + "\n" + "35" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    LocalDate today = LocalDate.now();

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the choice of timestamps\n"
            + "\n"
            + "1. View by year\n"
            + "2. View by month\n"
            + "3. View by date\n"
            + "Enter the start date in format (YYYY-MM-DD) from year 2000 to "
            + today.minusDays(4).getYear() + "-"
            + today.minusDays(4).getMonth() + "-"
            + today.minusDays(4).getDayOfMonth()
            + ":\n"
            + "Enter the number of days (5 to 30): \n"
            + "Not a valid input. Please enter the correct option.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //54

  /**
   * This test checks the operations of back and main menu.
   */
  @Test
  public void testZZBController_InvalidInput() {
    String userInput = "1" + "\n" + "1" + "\n" + "test1" + "\n" + "1" + "\n"
            + "0" + "\n" + "90" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "TEST1 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\n"
            + "Not a valid input. Please enter the correct number.\n"
            + "If you want to go back to main menu, press 'm'.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //55

  /**
   * This test checks the total investment on various dates.
   */
  @Test
  public void testZZCController_TotalInvestmentOnVariousDates() {
    String userInput = "7" + "\n" + "11" + "\n" + "2022-01-01" + "\n" + "b" + "\n"
            + "11" + "\n" + "2014-03-03" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST91 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $56209.0\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 4\n"
            + "Total commission charges: $18.0\n"
            + "Total Money spent: $56227.0\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST91 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $51239.0\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 2\n"
            + "Total commission charges: $9.0\n"
            + "Total Money spent: $51248.0\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //56

  /**
   * This test checks value portfolio on full composition on various dates.(inflexible)
   */
  @Test
  public void testZZDController_FullCompositionInflexible() {

    deleteFileInDirectory("pf_controllerTest94.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest94" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-12" + "\n" + "50" + "\n" + "y" + "\n" + "2"
            + "\n" + "2022-11-13" + "\n" + "100" + "\n" + "n" + "\n" + "5" + "\n" + "14" + "\n"
            + "2022-11-14" + "\n"
            + "1" + "\n" + "15" + "\n" + "m" + "\n" + "3" + "\n" + "14" + "\n" + "2022-11-13"
            + "\n" + "b" + "\n" + "14" + "\n" + "2022-11-14" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST94 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12\n"
            + "Price: $242.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-11-13\n"
            + "Price: $109.2300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST94 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMicrosoft (MSFT) \n"
            + "2.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-14\n"
            + "Price: $241.9850\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST94 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-13\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 35.0\t $242.99\t $8504.65\n"
            + "Meta (META) \t 100.0\t $109.23\t $10923.0\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-13: $19427.65\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST94 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-14\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 35.0\t $241.98\t $8469.29\n"
            + "Meta (META) \t 100.0\t $110.99\t $11099.0\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-14: $19568.29\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  //57

  /**
   * This test checks the value portfolio (option 2 on various dates).
   */
  @Test
  public void testZZEController_ValueOfPortfolioOption2() {

    deleteFileInDirectory("controllerTest95.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest95" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-12" + "\n" + "50" + "\n" + "y" + "\n" + "2"
            + "\n" + "2022-11-13" + "\n" + "100" + "\n" + "n" + "\n" + "5" + "\n" + "15" + "\n"
            + "2022-11-14" + "\n" + "1" + "\n" + "15" + "\n" + "m" + "\n" + "2" + "\n"
            + "15" + "\n" + "2022-02-14" + "\n" + "b"
            + "\n" + "15" + "\n" + "2022-02-15" + "\n" + "b" + "\n" + "15" + "\n"
            + "2022-11-11" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST95 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12\n"
            + "Price: $242.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-11-13\n"
            + "Price: $109.2300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST95 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMicrosoft (MSFT) \n"
            + "2.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-14\n"
            + "Price: $241.9850\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "The value of portfolio on 2022-02-14 is $0.00\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "The value of portfolio on 2022-02-15 is $0.00\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "The value of portfolio on 2022-11-11 is $0.00\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 58

  /**
   * This test checks Value of portfolio on full composition on certain date for past date.
   */
  @Test
  public void testZZFController_ValueOfPortfolioOnCertainPastDate() {
    deleteFileInDirectory("pf_controllerTest96.csv");
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest96" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-12" + "\n" + "50" + "\n" + "y" + "\n" + "2"
            + "\n" + "2022-11-13" + "\n" + "100" + "\n" + "n" + "\n" + "5" + "\n" + "16" + "\n"
            + "2022-11-14" + "\n" + "1" + "\n" + "16" + "\n" + "m" + "\n" + "3" + "\n"
            + "16" + "\n" + "2010-01-31" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST96 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12\n"
            + "Price: $242.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-11-13\n"
            + "Price: $109.2300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST96 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMicrosoft (MSFT) \n"
            + "2.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-14\n"
            + "Price: $241.9850\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "16. CONTROLLERTEST96 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST96 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2010-01-31\t Total Value\n"
            + "\n"
            + "Microsoft (MSFT) \t 34.0\t $29.9\t $1016.59\n"
            + "Meta (META) \t 100.0\t $42.05\t $4205.0\n"
            + "\n"
            + "Total Portfolio Value is on 2010-01-31: $5221.59\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 59

  /**
   * This test checks total investments on previous dates.
   */
  @Test
  public void testZZGController_ChecksInvestmentsOnPreviousDates() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest97" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-12" + "\n" + "50" + "\n" + "y" + "\n" + "2"
            + "\n" + "2022-11-13" + "\n" + "100" + "\n" + "n" + "\n" + "5" + "\n"
            + "17" + "\n" + "2022-11-14" + "\n"
            + "1" + "\n" + "10" + "\n" + "m" + "\n" + "7" + "\n" + "17"
            + "\n" + "2022-01-01" + "\n" + "b" + "\n" + "17" + "\n" + "2022-02-02"
            + "\n" + "b" + "\n" + "17" + "\n" + "2022-03-03"
            + "\n" + "b" + "\n" + "17" + "\n" + "2022-11-13" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST97 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12\n"
            + "Price: $242.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-11-13\n"
            + "Price: $109.2300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST97 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMicrosoft (MSFT) \n"
            + "2.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-14\n"
            + "Price: $241.9850\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "There are no investments until 2022-01-01\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "There are no investments until 2022-02-02\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "There are no investments until 2022-03-03\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST97 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $23072.5\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 2\n"
            + "Total commission charges: $9.0\n"
            + "Total Money spent: $23081.5\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 60

  /**
   * This test checks total amount invested on certain date.
   */
  @Test
  public void testZZHController_InvestmentsOnCertainDate() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest98" + "\n" + "1" + "\n" + "1"
            + "\n" + "2022-11-12" + "\n" + "50" + "\n" + "y" + "\n" + "2"
            + "\n" + "2022-11-13" + "\n" + "100" + "\n" + "n" + "\n" + "5" + "\n" + "18" + "\n"
            + "2022-11-14" + "\n" + "1" + "\n" + "20" + "\n" + "m" + "\n"
            + "7" + "\n" + "9" + "\n" + "2022-11-12" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST98 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-12\n"
            + "Price: $242.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Meta\n"
            + "Symbol: META\n"
            + "Time: 2022-11-13\n"
            + "Price: $109.2300\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST98 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tMicrosoft (MSFT) \n"
            + "2.\tMeta (META) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Microsoft\n"
            + "Symbol: MSFT\n"
            + "Time: 2022-11-14\n"
            + "Price: $241.9850\n"
            + "\n"
            + "You can sell only 50.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST9 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $32782.0\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 1\n"
            + "Total commission charges: $4.5\n"
            + "Total Money spent: $32786.5\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 61

  /**
   * This test checks when a purchase and sell takes place of a particular stock
   * and there are no stocks left to be sold. This test prints a message
   * when no stocks are left.
   */
  @Test
  public void testZZIController_PurchasedAndSoldAlreadySoldStocks() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest99" + "\n"
            + "1" + "\n" + "8" + "\n" + "2022-11-01" + "\n"
            + "600" + "\n" + "n" + "\n" + "5" + "\n" + "19" + "\n"
            + "2022-11-11" + "\n" + "1" + "\n" + "600" + "\n" + "m" + "\n"
            + "5" + "\n" + "19" + "\n" + "2022-11-03" + "\n" + "1" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST99 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-01\n"
            + "Price: $103.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST99 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-11\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-11\n"
            + "Price: $97.8800\n"
            + "\n"
            + "You can sell only 600.0 shares of this stock on 2022-11-11\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-03\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "You cannot sell any shares of this stock on this date as they are already sold.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);

  }

  // 62

  /**
   * This test checks when a purchase and sell takes place of a particular stock
   * where the number of stocks sold is greater
   * and there are no stocks left to be sold. This test prints a message
   * when no stocks are left.
   */
  @Test
  public void testZZJController_SellStocksHigherThanPurchased() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest990" + "\n"
            + "1" + "\n" + "8" + "\n" + "2022-11-01" + "\n"
            + "600" + "\n" + "n" + "\n" + "5" + "\n" + "20" + "\n"
            + "2022-11-11" + "\n" + "1" + "\n" + "700" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST990 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-01\n"
            + "Price: $103.9900\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST990 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-11\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-11\n"
            + "Price: $97.8800\n"
            + "\n"
            + "You can sell only 600.0 shares of this stock on 2022-11-11\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Not a valid input. You can only sell until600.0 shares. Also please enter number "
            + "of shares as natural numbers.\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);

  }

  // 63

  /**
   * This test checks if after selling 8 stocks for available 10 stocks
   * , correct quantity is being displayed in the view option.
   */
  @Test
  public void testZZLController_InflexiblePortfolio() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest991" + "\n" + "1" + "\n"
            + "8" + "\n" + "2022-11-13" + "\n" + "10" + "\n" + "n" + "\n" + "5" + "\n" + "21"
            + "\n" + "2022-11-14" + "\n" + "1" + "\n" + "8" + "\n" + "m" + "\n" + "2" + "\n" + "21"
            + "\n" + "2022-11-15" + "\n" + "m" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST991 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD)\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-13\n"
            + "Price: $97.8800\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST991 PORTFOLIO CREATED...!!!"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "\n"
            + "Select the portfolio to sell stocks from.\n"
            + "Enter the date on which you would like to sell the stock (YYYY-MM-DD)\n"
            + "\n"
            + "List of stocks available on date: 2022-11-14\n"
            + "\n"
            + "S.No\tName (Symbol) \n"
            + "\n"
            + "1.\tAmazon (AMZN) \n"
            + "\n"
            + "Which stock would you like to sell?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Amazon\n"
            + "Symbol: AMZN\n"
            + "Time: 2022-11-14\n"
            + "Price: $98.7700\n"
            + "\n"
            + "You can sell only 10.0 shares of this stock on 2022-11-14\n"
            + "How many share would you like to sell?\n"
            + "\n"
            + "Shares successfully sold.\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "16. CONTROLLERTEST96 (FLEXIBLE)\n"
            + "17. CONTROLLERTEST97 (FLEXIBLE)\n"
            + "18. CONTROLLERTEST98 (FLEXIBLE)\n"
            + "19. CONTROLLERTEST99 (FLEXIBLE)\n"
            + "20. CONTROLLERTEST990 (FLEXIBLE)\n"
            + "21. CONTROLLERTEST991 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST991 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-15\t Total Value\n"
            + "\n"
            + "Amazon (AMZN) \t 2.0\t $103.21\t $206.42\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-15: $206.42\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu."
            + "\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  // 64

  /**
   * This test takes commission fee as input from user
   * and displays the total amount invested based on the input entered.
   */
  @Test
  public void testZZLController_UserInputtingCommissionFeesAndDisplayingOutput() {
    String userInput = "8" + "\n" + "3" + "\n" + "7" + "\n" + "1" + "\n"
            + "2022-11-12" + "\n" + "m" + "\n" + "e";


    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "CONFIGURE COMMISSION COST\n"
            + "\n"
            + "Current commission cost is: $4.5\n"
            + "Enter the commission cost per transaction:\n"
            + "Commission cost set to 3\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST1 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $485.98\n"
            + "Commission cost per transaction is: $3.0\n"
            + "Total number of transactions till date is: 1\n"
            + "Total commission charges: $3.0\n"
            + "Total Money spent: $488.98\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 65

  /**
   * This test takes commission fee as input from user and displays
   * the total amount invested based on the input entered.
   */
  @Test
  public void testZZMController_UserInputtingCommissionFeesAndDisplayingOutput2() {
    String userInput = "8" + "\n" + "4.5" + "\n" + "7" + "\n" + "2" + "\n"
            + "2022-11-12" + "\n" + "m" + "\n" + "e";


    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "CONFIGURE COMMISSION COST\n"
            + "\n"
            + "Current commission cost is: $3.0\n"
            + "Enter the commission cost per transaction:\n"
            + "Commission cost set to 4.5\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST2 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $1214.95\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 2\n"
            + "Total commission charges: $9.0\n"
            + "Total Money spent: $1223.95\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 66

  /**
   * This test takes input as invalid commission fee from the user and throws message
   * as the fee must be greater than 0.
   */
  @Test
  public void testZZNController_UserInputtingInvalidCommissionFeesAndDisplayingOutput() {
    String userInput = "8" + "\n" + "-5" + "\n" + "m" + "\n" + "8" + "\n"
            + "0" + "\n" + "m" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "CONFIGURE COMMISSION COST\n"
            + "\n"
            + "Current commission cost is: $4.5\n"
            + "Enter the commission cost per transaction:\n"
            + "Not a valid input. Please enter the correct option.\n"
            + "Commission cost should be greater than 0. Enter 'm' for main menu.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "CONFIGURE COMMISSION COST\n"
            + "\n"
            + "Current commission cost is: $4.5\n"
            + "Enter the commission cost per transaction:\n"
            + "Not a valid input. Please enter the correct option.\n"
            + "Commission cost should be greater than 0. Enter 'm' for main menu.\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  // 67

  /**
   * This test checks inflexible portfolio.
   */
  @Test
  public void testZZOController_InflexiblePortfolio2() {
    String userInput = "1" + "\n" + "2" + "\n" + "controllerTest992" + "\n" + "1" + "\n"
            + "10" + "\n" + "80" + "\n" + "n" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST992 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Which stock would you like to buy?\n"
            + "\n"
            + "STOCK DETAILS\n"
            + "StockName: Walmart\n"
            + "Symbol: WMT\n"
            + "Time: " + readStockDateFromStockDataCsv() + "\n"
            + "Price: $" + readStockPriceFromStockDataCsv() + "\n"
            + "\n"
            + "How many shares would you like to buy?\n"
            + "Press 'b' to go back to the previous menu, 'm' to main menu.\n"
            + "\n"
            + "Would you like to buy another stock? (Y|N)\n"
            + "\n"
            + "CONTROLLERTEST992 PORTFOLIO CREATED...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  @Test
  public void testZZPInvestByDollarCostAveraging() {
    String userInput = "1" + "\n" + "1" + "\n" + "controllerTest993" + "\n" + "2" + "\n"
            + "2022-11-01" + "\n" + "y" + "\n" + "n" + "\n" + "2022-11-29" + "\n"
            + "2" + "\n" + "2" + "\n" + "2" + "\n" + "4" + "\n" + "50" + "\n" + "50" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n" + "ENTER YOUR CHOICE: \n"
            + "\n"
            + getFlexibleInflexibleScreen()
            + "\n"
            + "Enter your choice:\n"
            + "Enter the name for this portfolio.\n"
            + "\n"
            + "CREATE PORTFOLIO MENU\n"
            + "\n"
            + "CONTROLLERTEST993 Portfolio\n"
            + "\n"
            + getBuyStockScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD) "
            + "(from year 2000 to current day)\n"
            + "Do you want to investment to be recurring? (Y|N)\n"
            + "Is this an ongoing strategy? (Y|N)\n"
            + "Enter the end date for the strategy from 2022-11-01 (YYYY-MM-DD)\n"
            + "Enter the recurring frequency (1 to 28 days)\n"
            + "How many stocks would you like to buy? (1 to 10)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Enter the stock option (1 out of 2)\n"
            + "\n"
            + "Enter the stock option (2 out of 2)\n"
            + "How much money you would like to invest?\n"
            + "Enter the proportion percent for Meta (out of 100.0%)\n"
            + "The remaining 50.0 percentage will be automatically applied to Apple stock.\n"
            + "Buying shares, please wait...\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  @Test
  public void testZZQAddStockstoPortfolioUsingDollarStrategy() {
    String userInput = "9" + "\n" + "22" + "\n" + "2022-01-01" + "\n" + "y" + "\n" + "n" + "\n"
            + "2022-11-30" + "\n" + "15" + "\n" + "3" + "\n" + "1" + "\n" + "9" + "\n" + "10"
            + "\n" + "100" + "\n" + "33" + "\n" + "34" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "22. CONTROLLERTEST993\n"
            + "\n"
            + "Select the portfolio to which you would like to add the stock.\n"
            + "Enter the date on which you would like to purchase the stock (YYYY-MM-DD) "
            + "(from year 2000 to current day)\n"
            + "Do you want to investment to be recurring? (Y|N)\n"
            + "Is this an ongoing strategy? (Y|N)\n"
            + "Enter the end date for the strategy from 2022-01-01 (YYYY-MM-DD)\n"
            + "Enter the recurring frequency (1 to 333 days)\n"
            + "How many stocks would you like to buy? (1 to 10)\n"
            + "\n"
            + "\n"
            + getSupportedStocks()
            + "\n"
            + "Enter the stock option (1 out of 3)\n"
            + "\n"
            + "Enter the stock option (2 out of 3)\n"
            + "\n"
            + "Enter the stock option (3 out of 3)\n"
            + "How much money you would like to invest?\n"
            + "Enter the proportion percent for Microsoft (out of 100.0%)\n"
            + "Enter the proportion percent for UnitedHealth (out of 67.0%)\n"
            + "The remaining 33.0 percentage will be automatically applied to Walmart stock.\n"
            + "Buying shares, please wait...\n"
            + "\n"
            + "Stock successfully added to the portfolio...!!!\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";


    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  /**
   * This test checks value of dollar value portfolio on multiple dates.
   */
  @Test
  public void testZZRValueOfPortfolioAfterExecutingStrategy() {
    String userInput = "3" + "\n" + "23" + "\n" + "2022-11-01" + "\n" + "b"
            + "\n" + "23" + "\n" + "2022-12-01" + "\n" + "m" + "\n" + "e";

    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "16. CONTROLLERTEST96 (FLEXIBLE)\n"
            + "17. CONTROLLERTEST97 (FLEXIBLE)\n"
            + "18. CONTROLLERTEST98 (FLEXIBLE)\n"
            + "19. CONTROLLERTEST99 (FLEXIBLE)\n"
            + "20. CONTROLLERTEST990 (FLEXIBLE)\n"
            + "21. CONTROLLERTEST991 (FLEXIBLE)\n"
            + "22. CONTROLLERTEST992 (INFLEXIBLE)\n"
            + "23. CONTROLLERTEST993 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST993 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-11-01\t Total Value\n"
            + "\n"
            + "Meta (META) \t 3.48\t $94.33\t $328.26\n"
            + "Apple (AAPL) \t 2.49\t $155.08\t $387.7\n"
            + "Microsoft (MSFT) \t 2.68\t $234.6\t $628.72\n"
            + "UnitedHealth (UNH) \t 1.44\t $555.0\t $799.2\n"
            + "Walmart (WMT) \t 5.45\t $142.97\t $779.18\n"
            + "\n"
            + "Total Portfolio Value is on 2022-11-01: $2923.06\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\nLIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1 (FLEXIBLE)\n"
            + "2. CONTROLLERTEST2 (FLEXIBLE)\n"
            + "3. CONTROLLERTEST3 (FLEXIBLE)\n"
            + "4. CONTROLLERTEST4 (FLEXIBLE)\n"
            + "5. CONTROLLERTEST5 (FLEXIBLE)\n"
            + "6. CONTROLLERTEST6 (FLEXIBLE)\n"
            + "7. CONTROLLERTEST7 (FLEXIBLE)\n"
            + "8. CONTROLLERTEST8 (FLEXIBLE)\n"
            + "9. CONTROLLERTEST9 (FLEXIBLE)\n"
            + "10. CONTROLLERTEST90 (FLEXIBLE)\n"
            + "11. CONTROLLERTEST91 (FLEXIBLE)\n"
            + "12. CONTROLLERTEST92 (FLEXIBLE)\n"
            + "13. CONTROLLERTEST93 (FLEXIBLE)\n"
            + "14. CONTROLLERTEST94 (FLEXIBLE)\n"
            + "15. CONTROLLERTEST95 (FLEXIBLE)\n"
            + "16. CONTROLLERTEST96 (FLEXIBLE)\n"
            + "17. CONTROLLERTEST97 (FLEXIBLE)\n"
            + "18. CONTROLLERTEST98 (FLEXIBLE)\n"
            + "19. CONTROLLERTEST99 (FLEXIBLE)\n"
            + "20. CONTROLLERTEST990 (FLEXIBLE)\n"
            + "21. CONTROLLERTEST991 (FLEXIBLE)\n"
            + "22. CONTROLLERTEST992 (INFLEXIBLE)\n"
            + "23. CONTROLLERTEST993 (FLEXIBLE)\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "Value of CONTROLLERTEST993 PORTFOLIO\n"
            + "\n"
            + "Name (Symbol) \t Quantity\t Share Value on 2022-12-01\t Total Value\n"
            + "\n"
            + "Meta (META) \t 3.48\t $119.19\t $414.78\n"
            + "Apple (AAPL) \t 2.49\t $148.21\t $370.52\n"
            + "Microsoft (MSFT) \t 2.68\t $253.87\t $680.37\n"
            + "UnitedHealth (UNH) \t 1.44\t $552.36\t $795.39\n"
            + "Walmart (WMT) \t 5.45\t $152.05\t $828.67\n"
            + "\n"
            + "Total Portfolio Value is on 2022-12-01: $3089.73\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }


  /**
   * This test checks the total investment on specific dates for dollar value.
   */
  @Test
  public void testZZZAController_TotalInvestmentOnSpecificDateDollarValue() {
    String userInput = "7" + "\n" + "22" + "\n" + "2022-01-01" + "\n"
            + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "22. CONTROLLERTEST993\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST993 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $93.63\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 3\n"
            + "Total commission charges: $13.5\n"
            + "Total Money spent: $107.13\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
  }

  /**
   * This test checks the total investment for more than one date.
   */
  @Test
  public void testZZZBController_TotalInvestmentMoreThanOneDateDateDollarValue() {
    String userInput = "7" + "\n" + "22" + "\n" + "2022-01-01" + "\n" + "b" + "\n"
            + "22" + "\n" + "2022-11-11" + "\n" + "m" + "\n" + "e";
    InputStream input = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ControllerViewInteract obj = new ControllerViewInteractImpl(input, output);
    obj.start();

    String expectedOutput = getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "LIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "22. CONTROLLERTEST993\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST993 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $93.63\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 3\n"
            + "Total commission charges: $13.5\n"
            + "Total Money spent: $107.13\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + "\nLIST OF PORTFOLIO\n"
            + "\n"
            + "1. CONTROLLERTEST1\n"
            + "2. CONTROLLERTEST2\n"
            + "3. CONTROLLERTEST3\n"
            + "4. CONTROLLERTEST4\n"
            + "5. CONTROLLERTEST5\n"
            + "6. CONTROLLERTEST6\n"
            + "7. CONTROLLERTEST7\n"
            + "8. CONTROLLERTEST8\n"
            + "9. CONTROLLERTEST9\n"
            + "10. CONTROLLERTEST90\n"
            + "11. CONTROLLERTEST91\n"
            + "12. CONTROLLERTEST92\n"
            + "13. CONTROLLERTEST93\n"
            + "14. CONTROLLERTEST94\n"
            + "15. CONTROLLERTEST95\n"
            + "16. CONTROLLERTEST96\n"
            + "17. CONTROLLERTEST97\n"
            + "18. CONTROLLERTEST98\n"
            + "19. CONTROLLERTEST99\n"
            + "20. CONTROLLERTEST990\n"
            + "21. CONTROLLERTEST991\n"
            + "22. CONTROLLERTEST993\n"
            + "\n"
            + "Which portfolio would you like to check?\n"
            + "Enter the year in format (YYYY-MM-DD) (2000 to 2022): \n"
            + "\n"
            + "COST BASIS OF CONTROLLERTEST993 PORTFOLIO\n"
            + "\n"
            + "Total Money invested in stocks: $2300.8\n"
            + "Commission cost per transaction is: $4.5\n"
            + "Total number of transactions till date is: 75\n"
            + "Total commission charges: $337.5\n"
            + "Total Money spent: $2638.3\n"
            + "\n"
            + "Press 'b' to go back and 'm' for main menu.\n"
            + "\n"
            + getMainScreen()
            + "\n"
            + "ENTER YOUR CHOICE: \n"
            + "\n"
            + "Exiting...\n";

    String result = bytes.toString();
    result = result.replace("\r\n", "\n");

    assertEquals(expectedOutput, result);
    deleteDirectory();
  }
}