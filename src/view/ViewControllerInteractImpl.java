package view;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;

import model.StockCompositionData;
import model.StockCompositionDataImpl;
import model.StockNameMap;
import model.StockNameMapImpl;
import model.StockPortFolioDataImpl;

/**
 * This class represents the view and provides the implementation to dispatch the actions to the
 * corresponding methods based on commands provided by the controller.
 * This class has the following variables.
 * <ul>
 *   <li>output - the output stream which is the print stream</li>
 * </ul>
 */
public class ViewControllerInteractImpl implements ViewControllerInteract {
  private final PrintStream output;

  /**
   * A constructor that assigns the print stream to the global variable.
   *
   * @param out the print stream
   */
  public ViewControllerInteractImpl(PrintStream out) {
    this.output = out;
  }

  @Override
  public void viewControllerInteract(TypeofViews type, String[] args, int length) {
    switch (type) {
      case MAIN: {
        mainScreen();
        break;
      }
      case CREATE_PORTFOLIO_NAME_SCREEN: {
        showPortfolioName();
        break;
      }
      case CREATE_PORTFOLIO: {
        showStockMainScreen(args[0]);
        break;
      }
      case LIST_OF_STOCKS: {
        listOfSupportedStocksScreen();
        break;
      }
      case BUY_STOCKS_VALUE: {
        showBuyStockValueScreen();
        break;
      }
      case BUY_ANOTHER_STOCK: {
        wouldYouLikeToBuyAnotherStockScreen();
        break;
      }
      case DISPLAY_PORTFOLIO_CREATED: {
        showDisplayPortFolioCreated(args[0]);
        break;
      }
      case PORTFOLIO_COMPOSITION: {
        showPortFolioCompositionScreen(args, length);
        break;
      }
      case WHICH_PORTFOLIO_CHECK: {
        whichPortfolioLikeToCheck();
        break;
      }
      case PORTFOLIO_INDIVIDUAL_LIST: {
        String opt = args[0];
        String portfolioType = args[1];
        showPortfolioIndividualScreen(opt, portfolioType);
        break;
      }
      case PORTFOLIO_NAME_REENTER: {
        showPortfolioNameReenter();
        break;
      }
      case STOCK_BUY_REENTER: {
        showStockBuyReenter();
        break;
      }
      case BUY_STOCKS_INVALID_RETRY: {
        showStockBuyInvalidRetryScreen(args);
        break;
      }
      case GOBACK_MAINMENU_OPTION: {
        showPortfolioReviewScreen();
        break;
      }
      case NOT_VALID_INPUT_SCREEN: {
        output.println("Not a valid input. Please enter the correct option.");
        break;
      }
      case NO_PORTFOLIO: {
        output.println("You dont have any portfolio.");
        break;
      }
      case PORTFOLIO_INVALID_ENTRY: {
        portfolioInvalidEntryScreen();
        break;
      }
      case PORTFOLIO_ALREADY_EXISTS: {
        output.println("This portfolio already exists.");
        break;
      }
      case PF_REENTER_DUPLICATE_NAME: {
        pfReenterDuplicateName();
        break;
      }
      case DATE_RENTER: {
        correctDateScreen();
        break;
      }
      case LIST_OF_STOCKS_ON_DATE: {
        showListOfStocksAvailableOnADate(args[0], args[1], args[2]);
        break;
      }
      case NOT_VALID_MAIN_MENU: {
        notAValidMainMenu();
        break;
      }
      case PORTFOLIO_PERFORMANCE_DATE_INPUT: {
        dateInputPortfolioPerformance();
        break;
      }
      case TYPEOF_PORTFOLIO_SCREEN: {
        displayTypesOfPortfolioToCreate();
        break;
      }
      default: {
        break;
      }
    }
  }

  /**
   * A private helper method to display the options while creating the portfolio.
   */
  private void displayTypesOfPortfolioToCreate() {
    output.println("\nWhat type of portfolio would you like to create?\n");
    output.println("1. Flexible / Customizable Portfolio");
    output.println("2. Inflexible / Non Customizable Portfolio\n");
    output.println("Enter your choice:");
  }

  /**
   * This method displays the choice for the user for date input while calculating the performance
   * of the portfolio.
   */
  private void dateInputPortfolioPerformance() {
    output.println("Enter the choice of timestamps\n");
    output.println("1. View by year");
    output.println("2. View by month");
    output.println("3. View by date");
  }

  /**
   * A helper method to display the error and main menu option.
   */
  private void notAValidMainMenu() {
    output.println("Not a valid input. Please enter the correct option.");
    output.println("Press 'm' to go back to main menu.\n");
  }

  @Override
  public void displayValueCompForCost(String title, String[] costData) {
    output.println(title);
    for (String costDatum : costData) {
      output.println(costDatum);
    }
  }

  @Override
  public void displayValueCompForOthers(String title, String[] column,
                                        String[][] data, String footer) {
    output.println(title);

    for (int i = 0; i < column.length; i++) {
      if (i == column.length - 1) {
        output.println(column[i]);
      } else {
        output.print(column[i]);
      }
    }

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (j == data[i].length - 1) {
          output.println(data[i][j]);
        } else {
          output.print(data[i][j]);
        }
      }
    }

    output.println(footer);
  }

  @Override
  public void portfolioPerformanceOverTimeView(String title, String[] data,
                                               String[] scaleStr, String footer) {
    output.println(title);
    for (String datum : data) {
      output.println(datum);
    }
    for (String s : scaleStr) {
      output.println(s);
    }
    output.println(footer);
  }

  /**
   * This method displays the list of stocks in the portfolio on a specific date.
   *
   * @param date          the date which user entered
   * @param pfIndex       the index of the portfolio
   * @param portfolioType type of the portfolio
   */
  private void showListOfStocksAvailableOnADate(String date, String pfIndex, String portfolioType) {
    int portfolioNumber = Integer.parseInt(pfIndex) - 1;
    StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
    StockPortFolioDataImpl stkObj;

    try {
      stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber, true,
              date, true, true, portfolioType);
    } catch (Exception e) {
      output.println("Error in getting stock data " + e.getMessage());
      return;
    }

    if (stkObj == null) {
      output.println("Error in getting stock data");
      return;
    }

    if (stkObj.numberOfUniqueStocks == 0) {
      noStockBeforeDate();
    } else {
      output.println("\nList of stocks available on date: " + date);
      output.print("\nS.No");
      output.print("\t" + "Name");
      output.println(" (" + "Symbol" + ") \n");
      for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
        if (stkObj.stockQuantity[i] != 0) {
          output.print(i + 1 + ".");
          output.print("\t" + stkObj.stockName[i]);
          output.println(" (" + stkObj.stockSymbol[i] + ") ");
        }
      }
      output.println("\nWhich stock would you like to sell?");
    }
  }

  /**
   * A helper method to display the no stocks on the date.
   */
  private void noStockBeforeDate() {
    output.println("You don't own any stocks before this date");
  }

  /**
   * This method renders the mentioned output when user tries to enter an invalid date while trying
   * to view the value of a portfolio on a certain date.
   */
  private void correctDateScreen() {
    output.println("Not a valid input. Please enter the correct date.");
    output.println("Press 'b' to go back\n");
  }

  /**
   * This method renters the below output when the user tries to create a portfolio with a name
   * which already exists.
   */
  private void pfReenterDuplicateName() {
    output.println("If you want to override this portfolio, then press 'y'. "
            + "If you want to enter another name, press 'n'. If you want to go main screen, "
            + "press 'b'.\n");
  }

  /**
   * This method renders the below-mentioned output when user tries to input an invalid portfolio
   * number.
   */
  private void portfolioInvalidEntryScreen() {
    output.println("Not a valid input. Please enter the correct portfolio.");
    output.println("Press 'b' to go back to the previous menu.\n");
  }

  /**
   * This method renders the mentioned output if the user has bought a stock and would like to know
   * more options other than buying a stock.
   */
  private void showPortfolioReviewScreen() {
    output.println("Press 'b' to go back and 'm' for main menu.\n");
  }

  /**
   * This method helps in rendering the composition of the portfolio when the user tries to view
   * the same.
   *
   * @param option the index of the portfolio
   */
  private void showPortfolioIndividualScreen(String option, String portfolioType) {
    int portfolioNumber = Integer.parseInt(option) - 1;
    StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
    StockPortFolioDataImpl stkObj =
            (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber, false,
                    null, true, false, portfolioType);

    String[] portfolioNames = obj.getPortFolioNames(portfolioType);
    String date = stkObj.createdTimeStamp;
    if (date == null) {
      output.println("The portfolio is empty.\n");
      return;
    }
    date = date.substring(0, 10);
    output.println("\n" + portfolioNames[portfolioNumber].toUpperCase() + " PORTFOLIO (inflexible)"
            + " COMPOSITION - Created on " + date + "\n");

    output.print("Name");
    output.print(" (" + "Symbol" + ") \t ");
    output.print("Quantity \t ");
    output.print("Date of Purchase(DOP)\t");
    output.println("Price of a share on DOP\n");

    for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
      output.print(stkObj.stockName[i]);
      output.print(" (" + stkObj.stockSymbol[i] + ") ");
      output.print("\t " + Math.floor(stkObj.stockQuantity[i] * 100) / 100);
      output.print("\t " + stkObj.stockLastKnownValueDate[i]);
      output.println("\t $" + stkObj.valueOfSingleStock[i]);
    }
    output.println();
  }

  /**
   * This method renders the list of all the portfolio that a user has created in the application.
   *
   * @param portfolioNames    the names of all the available portfolio
   * @param numberOfPortFolio the number of portfolios
   */
  private void showPortFolioCompositionScreen(String[] portfolioNames, int numberOfPortFolio) {
    output.println("\nLIST OF PORTFOLIO");
    output.println();
    StockCompositionData obj = new StockCompositionDataImpl("ALL");
    for (int i = 0; i < numberOfPortFolio - 1; i++) {
      if (Objects.equals(portfolioNames[numberOfPortFolio - 1], "ALL")) {
        String fileName = obj.getPortFolioFileNameByIndex(i, "ALL");
        String pfType;
        if (obj.isPortfolioOfGivenType(fileName, "FLEXIBLE")) {
          pfType = "FLEXIBLE";
        } else {
          pfType = "INFLEXIBLE";
        }
        output.println(i + 1 + ". " + portfolioNames[i].toUpperCase() + " (" + pfType + ")");
      } else {
        output.println(i + 1 + ". " + portfolioNames[i].toUpperCase());
      }
    }

  }

  private void whichPortfolioLikeToCheck() {
    output.println("\nWhich portfolio would you like to check?");
  }

  /**
   * This method renders a success message when a portfolio is successfully created.
   *
   * @param currentPortfolioName the name of the portfolio which user created
   */
  private void showDisplayPortFolioCreated(String currentPortfolioName) {
    output.println("\n" + currentPortfolioName.toUpperCase() + " PORTFOLIO CREATED...!!!");
  }

  /**
   * This method renders the mentioned output when user tries to buy a share.
   */
  private void showBuyStockValueScreen() {
    output.println("\nHow many shares would you like to buy?");
    output.println("Press 'b' to go back to the previous menu, 'm' to main menu.\n");
  }

  /**
   * This method renders the below-mentioned output if the user inputs an invalid entry while
   * trying to buy the shares.
   */
  private void showStockBuyInvalidRetryScreen(String[] args) {
    if (args == null) {
      output.println("Not a valid input. Reenter.");
    } else {
      output.println("Not a valid input. You can only sell until" + args[0] + " shares."
              + " Also please enter number of shares as natural numbers.");
    }
    output.println("Press 'b' to go back to the previous menu, 'm' to main menu.\n");
  }

  /**
   * This method renders the below-mentioned output if the user wants to buy another share after
   * successfully buying a share.
   */
  private void wouldYouLikeToBuyAnotherStockScreen() {
    output.println("Would you like to buy another stock? (Y|N)");
  }

  /**
   * This method helps in showing the current stock data, and it's share price that the user wants
   * to purchase.
   * @param stockData the stock data
   */
  public void showStockDataScreen(String[] stockData) {
    output.println("\nSTOCK DETAILS");
    output.println("StockName: " + stockData[0]);
    output.println("Symbol: " + stockData[2]);
    output.println("Time: " + stockData[3]);
    output.println("Price: $" + stockData[1]);
  }

  /**
   * This method renders the list of all the supported stocks by the application.
   */
  private void listOfSupportedStocksScreen() {
    int index = 0;
    StockNameMap snp = new StockNameMapImpl();
    Map<String, String> map = snp.getMap();
    output.println("\n");
    for (Map.Entry<String, String> entry : map.entrySet()) {
      output.print(++index + ". ");
      output.print(entry.getValue());
      output.println(" (" + entry.getKey() + ")");
    }
  }

  /**
   * This is the main screen and it's options. This method renders the below output when user
   * wants to view the main screen.
   */
  private void mainScreen() {
    output.println("\nMENU\n");
    output.println("1. Create a portfolio");
    output.println("2. Value and Composition of portfolio");
    output.println("3. Value of portfolio on full composition");
    output.println("4. Add a stock to portfolio");
    output.println("5. Sell a stock from portfolio");
    output.println("6. Performance of portfolio");
    output.println("7. Total amount invested on certain date");
    output.println("8. Configure the commission cost");
    output.println("9. Add stocks to portfolio using Dollar-Cost strategy");
    output.println("e. Exit\n");
    output.println("ENTER YOUR CHOICE: ");
  }

  /**
   * This method is called when a user has inputted a name of the portfolio and would like to
   * do more on that portfolio like when a user wants to buy a stock.
   *
   * @param name the name of the portfolio that the user inputted
   */
  private void showStockMainScreen(String name) {
    output.println("\nCREATE PORTFOLIO MENU\n");
    output.println(name.toUpperCase() + " Portfolio\n");
    output.println("1. Buy a stock");
    output.println("2. Invest by dollar-cost averaging");
    output.println("3. Main Menu");
    output.println("e. Exit\n");
    output.println("ENTER YOUR CHOICE: ");
  }

  /**
   * This method is rendered when the user is trying to create a portfolio. This method basically
   * tells the user to enter the name for the portfolio.
   */
  private void showPortfolioName() {
    output.println("Enter the name for this portfolio.");
  }

  /**
   * When a user tries to input an invalid input while buying a stock, the below method is called
   * and the mentioned output is rendered on the screen.
   */
  private void showStockBuyReenter() {
    output.println("Not a valid input. Please enter the correct number.");
    output.println("If you want to go back to main menu, press 'm'.\n");
  }

  /**
   * This method is called when a user tries to create a portfolio with an empty name. The below
   * output is rendered during that time.
   */
  private void showPortfolioNameReenter() {
    output.println("Cannot create a portfolio with empty name. Enter a valid name.");
    output.println("If you want to go back to main menu, press '0'.\n");
  }

}
