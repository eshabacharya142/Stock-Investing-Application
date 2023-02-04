package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.StockCompositionData;
import model.StockCompositionDataImpl;
import model.StockNameMap;
import model.StockNameMapImpl;
import model.StockPortFolioDataImpl;
import model.TypeofAction;
import view.TypeofViews;
import view.ViewControllerInteract;
import view.ViewControllerInteractImpl;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * This class represents the controller that interacts with the view.
 * This class provides the implementation that helps controller to interact with the view.
 * This class also interacts with the controller that interacts with the model.
 * This class has the following variables.
 * <ul>
 *   <li> cmiObj - an object for the controller that interacts with the model </li>
 *   <li> vciObj - an object for the view </li>
 *   <li> currentPortfolioName - the name of the current portfolio that is being used or read </li>
 *   <li> output - print stream object </li>
 *   <li> scan - scanner object </li>
 * </ul>
 */
public class ControllerViewInteractImpl implements ControllerViewInteract {
  protected final ControllerModelInteract cmiObj = new ControllerModelInteractImpl();
  protected final ViewControllerInteract vciObj;
  protected String currentPortfolioName;
  private final PrintStream output;
  private final Scanner scan;

  /**
   * Constructor for the controller that interacts with the view that takes in two arguments and
   * initializes them to the global variables.
   *
   * @param input  the input stream
   * @param output the output stream
   */
  public ControllerViewInteractImpl(InputStream input, PrintStream output) {
    this.output = output;
    vciObj = new ViewControllerInteractImpl(output);
    scan = new Scanner(input);
  }

  @Override
  public void setup() {
    output.println("\nSetting up the application. Please wait...");
    cmiObj.controllerModelInteract(TypeofAction.INITIAL_SETUP, null, 0);
    String fileName = "data/CommissionCost.txt";
    File file = new File(fileName);
    if (!file.exists()) {
      storeCommissionCost("4.5"); // Default value
    }

    try {
      readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }

    output.println("Setup complete...!!!\n");
  }


  @Override
  public void start() {
    String option;
    do {
      try {
        sleep(500);
      } catch (Exception e) {
        // Exception while sleep
      }
      vciObj.viewControllerInteract(TypeofViews.MAIN, null, 0);
      option = scan.nextLine();
      performMainMenuAction(option);
    }
    while (!option.equals("e"));
  }

  protected void readStrategyAndImplement() throws IOException {
    String path = "userdata/user1/Strategy";
    File directory = new File(path);
    // Check if strategy directory exists
    if (directory.exists()) {
      File[] files = directory.listFiles();
      assert files != null;
      for (File file : files) {
        String filename = file.getName();
        // Name of the portFolio
        String[] arrOfStr = filename.split("\\.", 2);
        File pfFile = new File("userdata/user1/Portfolio/pf_" + filename);
        // Check if corresponding portfolio file exists
        if (pfFile.exists()) {
          BufferedReader strategyData;
          try {
            strategyData = new BufferedReader(new FileReader(file));
          } catch (IOException e) {
            System.out.println("CONTROLLER: Portfolio does not exists\n");
            return;
          }

          String line;
          String splitBy = ",";
          String[] splitData;

          boolean isSharesBought = false;
          StringBuilder newData = new StringBuilder();
          StringBuilder tempData = new StringBuilder();

          while ((line = strategyData.readLine()) != null) {
            tempData.setLength(0);
            tempData.append(line);
            splitData = line.split(splitBy);    // use comma as separator

            // Check the type of strategy
            if (Objects.equals(splitData[0], "DOLLAR-COST")) {
              // Check if the last purchase date has exceeded the recurring date
              String lastPurchaseDate = splitData[1];
              String frequency = splitData[2];
              LocalDate dateToBePurchasedNext = LocalDate.parse(lastPurchaseDate)
                      .plusDays(Long.parseLong(frequency));
              LocalDate today = LocalDate.now();
              if (dateToBePurchasedNext.compareTo(today) <= 0) {
                isSharesBought = true;
                // if stocks are bought, store the new date in the strategy file
                newData.replace(0, 10, dateToBePurchasedNext.toString());

                // Buy stocks on this date
                String[] dateArg = new String[1];
                dateArg[0] = dateToBePurchasedNext.toString();
                String cost = splitData[3];
                int number = Integer.parseInt(splitData[4]);
                String[] stockSymbol = new String[number];
                String[] proportion = new String[number];

                for (int i = 0; i < number; i++) {
                  stockSymbol[i] = splitData[5 + i];
                  proportion[i] = splitData[5 + i];
                }

                // Buy shares
                calculateAndBuySharesBasedOnStrategy(dateArg, cost,
                        proportion, stockSymbol, number, arrOfStr[0]);
              }
            }

            if (Objects.equals(splitData[0], "DOLLAR-COST-END")) {
              // Check if the last purchase date has exceeded the recurring date
              String lastPurchaseDate = splitData[1];
              String endDateStr = splitData[2];
              String frequency = splitData[3];
              LocalDate dateToBePurchasedNext = LocalDate.parse(lastPurchaseDate)
                      .plusDays(Long.parseLong(frequency));
              LocalDate today = LocalDate.now();
              LocalDate endDate = LocalDate.parse(endDateStr);

              if (endDate.compareTo(today) < 0) {
                // This means, the strategy time period is over,
                // don't add them to the strategy as well
                continue;
              }

              if (dateToBePurchasedNext.compareTo(today) <= 0) {
                isSharesBought = true;
                // if stocks are bought, store the new date in the strategy file
                newData.replace(0, 10, dateToBePurchasedNext.toString());

                // Buy stocks on this date
                String[] dateArg = new String[1];
                dateArg[0] = dateToBePurchasedNext.toString();
                String cost = splitData[4];
                int number = Integer.parseInt(splitData[5]);
                String[] stockSymbol = new String[number];
                String[] proportion = new String[number];

                for (int i = 0; i < number; i++) {
                  stockSymbol[i] = splitData[6 + i];
                  proportion[i] = splitData[6 + i];
                }

                // Buy shares
                calculateAndBuySharesBasedOnStrategy(dateArg, cost,
                        proportion, stockSymbol, number, arrOfStr[0]);
              }
            }

            tempData.append('\n');
            newData.append(tempData);
          }
          strategyData.close();

          if (isSharesBought) {
            // Re-write the new buy date in the strategy
            try {
              persistStrategyToFile(String.valueOf(newData), arrOfStr[0]);
            } catch (Exception e) {
              output.println("CONTROLLER: Error in persisting the strategy. " + e.getMessage());
            }
          }
        } else {
          // Portfolio does not exist, delete the strategy as well
          file.delete();
        }
      }
    }
  }

  /**
   * This method takes in the date for the view portfolio menu and tells the view to display the
   * value of the portfolio on the given date.
   *
   * @param options the portfolio number
   * @return false if the user entered back option, else true
   */
  private boolean portfolioViewAction(String options, String type, String portfolioType) {
    String[] args = new String[4];
    args[0] = options;

    output.println("Enter the year in format (YYYY-MM-DD) (2000 to "
            + LocalDate.now().getYear() + "): ");
    String date;
    date = scan.nextLine();
    while (!validateDate(date, "yyyy-MM-dd", 0)) {
      vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
      date = scan.nextLine();
      if (Objects.equals(date, "b") || Objects.equals(date, "B")) {
        return false;
      }
    }
    args[1] = date;
    args[2] = type;
    args[3] = portfolioType;

    showPortfolioIndividualWithDateScreenController(options, date, type, portfolioType);
    //vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INDIVIDUAL_LIST_WITH_DATE, args, 4);
    return true;
  }

  private void showPortfolioIndividualWithDateScreenController(String option, String date,
                                                               String type,
                                                               String portfolioType) {
    int portfolioNumber = Integer.parseInt(option) - 1;
    StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
    double commissionCost = obj.getCommissionCost();
    StockPortFolioDataImpl stkObj = null;

    if (Objects.equals(type, "FULL")) {
      stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber, true,
              null, true, false, portfolioType);
    } else if (Objects.equals(type, "TRUE")) {
      try {
        stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber,
                true, date, true, true, portfolioType);
      } catch (Exception e) {
        output.println("CONTROLLER: Error in getting the data.");
        return;
      }
    } else if (Objects.equals(type, "COST")) {
      try {
        stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber,
                false, date, false, false, portfolioType);
      } catch (Exception e) {
        output.println("CONTROLLER: Error in getting the data.");
        return;
      }
    }

    if (stkObj == null) {
      output.println("CONTROLLER: Error in getting the data.");
      return;
    }

    double totalPortFolioValue = 0;

    if (stkObj.numberOfUniqueStocks == 0) {
      if (type.equals("COST")) {
        output.println("There are no investments until " + date + "\n");
      } else {
        output.println("The value of portfolio on " + date + " is $0.00\n");
      }
      return;
    }

    String title;
    String[] portfolioNames = obj.getPortFolioNames(portfolioType);
    if (type.equals("COST")) {
      title = "\nCOST BASIS OF " + portfolioNames[portfolioNumber].toUpperCase()
              + " PORTFOLIO";
    } else {
      title = "\nValue of " + portfolioNames[portfolioNumber].toUpperCase() + " PORTFOLIO";
    }

    String[] column = new String[5];

    if (!type.equals("COST")) {
      column = new String[]{
        "\nName",
        " (" + "Symbol" + ") ",
        "\t " + "Quantity",
        "\t " + "Share Value on " + date,
        "\t " + "Total Value\n"};
    }

    String[][] data = new String[stkObj.numberOfUniqueStocks][column.length];
    String footer;
    String[] costData = new String[5];

    for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
      if (stkObj.stockQuantity[i] == 0) {
        continue;
      }
      if (!type.equals("COST")) {
        data[i][0] = stkObj.stockName[i];
        data[i][1] = " (" + stkObj.stockSymbol[i] + ") ";
        data[i][2] = "\t " + Math.floor(stkObj.stockQuantity[i] * 100) / 100;
      }
      // Display based on the date purchased on
      double shareValue;
      if (type.equals("COST")) {
        shareValue = stkObj.valueOfSingleStock[i];
      } else {
        shareValue = getShareValueOnDate(stkObj.stockSymbol[i], date);
      }
      shareValue = Math.floor((shareValue) * 100) / 100;
      if (!type.equals("COST")) {
        data[i][3] = "\t $" + shareValue;
        data[i][4] = "\t $" + Math.floor((shareValue * stkObj.stockQuantity[i]) * 100) / 100;
      }
      totalPortFolioValue += Math.floor((shareValue * stkObj.stockQuantity[i]) * 100) / 100;
    }

    totalPortFolioValue = Math.floor(totalPortFolioValue * 100) / 100;
    double totalMoneyInvested = Math.floor((totalPortFolioValue
            + (stkObj.numberOfTransactions * commissionCost)) * 100) / 100;
    if (type.equals("COST")) {
      costData[0] = "\nTotal Money invested in stocks: $" + totalPortFolioValue;
      costData[1] = "Commission cost per transaction is: $" + commissionCost;
      costData[2] = "Total number of transactions till date is: " + stkObj.numberOfTransactions;
      costData[3] = "Total commission charges: $" + stkObj.numberOfTransactions * commissionCost;
      costData[4] = "Total Money spent: $" + totalMoneyInvested + "\n";
      vciObj.displayValueCompForCost(title, costData);
    } else {
      footer = "\nTotal Portfolio Value is on " + date + ": $" + totalPortFolioValue + "\n";
      vciObj.displayValueCompForOthers(title, column, data, footer);
    }
  }

  private double getShareValueOnDate(String stockSymbol, String date) {
    GetStockData gsd = new GetStockDataImpl();
    try {
      String[] dateArr = new String[1];
      dateArr[0] = date;
      gsd.getValue(stockSymbol, dateArr);
    } catch (Exception e) {
      output.println("Error in getting stock data on " + date);
      return 0;
    }

    BufferedReader stockData;
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String line;
    String splitBy = ",";
    String[] splitStockData;

    try {
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
      stockData.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return Double.parseDouble(splitStockData[1]);
  }

  /**
   * This is a helper method that helps in validating the date input the user enters when the user
   * wants to see the value of the portfolio on a certain date.
   *
   * @param dateStr the date that user entered
   * @return true of the input is valid, else false
   */
  protected boolean validateDate(String dateStr, String format, int minDifference) {
    if (dateStr == null || dateStr.length() == 0) {
      return false;
    }

    Date date;
    try {
      DateFormat sdf = new SimpleDateFormat(format);
      date = sdf.parse(dateStr);
      if (!dateStr.equals(sdf.format(date))) {
        return false;
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      if (calendar.get(Calendar.YEAR) > LocalDate.now().getYear()
              || calendar.get(Calendar.YEAR) < 2000) {
        return false;
      }

      if (calendar.get(Calendar.YEAR) == LocalDate.now().getYear()) {
        if (calendar.get(Calendar.MONTH) + 1 > LocalDate.now().getMonthValue()) {
          return false;
        }
        if (calendar.get(Calendar.MONTH) + 1 == LocalDate.now().getMonthValue()) {
          if (calendar.get(Calendar.DATE) > LocalDate.now().getDayOfMonth()) {
            return false;
          }
        }
      }


      if (minDifference != 0) {
        switch (format) {
          case "yyyy":
            if (calendar.get(Calendar.YEAR) + minDifference > LocalDate.now().getYear()) {
              return false;
            }
            break;
          case "yyyy-MM":
            String tempDateStr = dateStr + "-01";
            if (ChronoUnit.MONTHS.between(LocalDate.parse(tempDateStr),
                    LocalDate.now()) < minDifference) {
              return false;
            }
            break;
          case "yyyy-MM-dd":
            if (ChronoUnit.DAYS.between(LocalDate.parse(dateStr),
                    LocalDate.now()) < minDifference) {
              return false;
            }
            break;
          default:
            // No action to be taken
            break;
        }
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  /**
   * This method tells the view to display the list of menu options that can be performed when a
   * user is trying to create a portfolio. The options being, whether the user wants to buy a share
   * or wants to go back to main menu or exit the application.
   *
   * @param options the options that are provided by the user
   * @param args    the helper arguments that are passed by the callee
   */
  protected void createPortfolioNameScreenAction(String options, String[] args) {
    try {
      sleep(100);
    } catch (Exception e) {
      // Exception while sleep
    }
    cmiObj.controllerModelInteract(TypeofAction.CREATE_PORTFOLIO, args, 1);
    String[] name = new String[1];
    name[0] = currentPortfolioName;
    while (true) {
      vciObj.viewControllerInteract(TypeofViews.CREATE_PORTFOLIO, name, 0);
      String option;
      option = scan.nextLine();
      if (performCreatePortfolioMenuAction(option, args[1], null, 0)) {
        break;
      }
    }
  }

  /**
   * This method tells the view to display stock buying screen provided that the user wanted to
   * buy another share after purchasing a share. If the user doesn't want to buy another share,
   * then this method tells the controller to display the portfolio created screen.
   *
   * @param options the option that was provided by the user that whether the user wants
   *                to buy a share
   */
  private void buyAnotherStockMenuAction(String options, String portfolioType, String[] arg,
                                         int length) {
    if (Objects.equals(options, "Y") || Objects.equals(options, "y")) {
      performCreatePortfolioMenuAction("1", portfolioType, null, 0);
    } else {
      String[] args = new String[1];
      args[0] = currentPortfolioName;
      if (arg == null || length == 0) {
        vciObj.viewControllerInteract(TypeofViews.DISPLAY_PORTFOLIO_CREATED, args, 0);
      } else if (arg[0].equals("addStockScreen")) {
        output.println("\nStock successfully added to the portfolio...!!!");
      }
    }
  }

  /**
   * This method tells the model to buy the share that the user asked for. If the user wanted to
   * go back to main menu, this method asks the model to delete the empty portfolio and returns
   * to the main screen. This method also instructs view to display the buy another stock menu if
   * the user wants to buy another stock.
   *
   * @param option the option that was provided by the user that whether the user
   *               wants to buy a share
   */
  private void buyStockValueMenuAction(String option, String portfolioType, String[] args,
                                       int length) {
    if (Objects.equals(option, "m") || Objects.equals(option, "M")) {
      cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
      return;
    }
    String[] stock = new String[2];
    stock[0] = option;
    stock[1] = currentPortfolioName;
    cmiObj.controllerModelInteract(TypeofAction.BUY_STOCKS, stock, 2);
    vciObj.viewControllerInteract(TypeofViews.BUY_ANOTHER_STOCK, null, 0);
    String options;
    options = scan.nextLine();
    while ((options == null || options.length() == 0) || ((!options.equals("Y"))
            && (!options.equals("y")) && (!options.equals("N")) && (!options.equals("n")))) {
      vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
      options = scan.nextLine();
    }
    buyAnotherStockMenuAction(options, portfolioType, args, length);
  }

  /**
   * This method tells the model to get the stock data that user wants to buy. Also, this method
   * tells the view to display the stock data that the user wants to buy.
   *
   * @param options the stock option that user entered
   * @param args    helper arguments if any
   * @param length  length of the arguments
   * @return true if user wants to go back to main menu, else false
   */
  private boolean performListOfStocksMenuAction(String options, String portfolioType,
                                                String[] args, int length) {

    if (Objects.equals(options, "m") || Objects.equals(options, "M")) {
      cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
      return false;
    }

    String date;
    if (Objects.equals(portfolioType, "1")) {
      output.println("Enter the date on which you would like to purchase the stock (YYYY-MM-DD)");
      date = scan.nextLine();
      while (!validateDate(date, "yyyy-MM-dd", 0)) {
        vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
        date = scan.nextLine();
        if (Objects.equals(date, "b") || Objects.equals(date, "B")) {
          return false;
        }
      }
    } else {
      SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
      Date now = new Date();
      date = sdfDate.format(now);
    }

    StockNameMap snp = new StockNameMapImpl();
    Map<String, String> map = snp.getMap();
    String[] stockSymbolIndexArray = new String[snp.getMapSize()];
    String[] stock = new String[2];
    int index = 0;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      stockSymbolIndexArray[index++] = entry.getKey();
    }
    stock[0] = stockSymbolIndexArray[Integer.parseInt(options) - 1];
    stock[1] = date;
    cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, stock, 2);
    String[] stockData = readStockDataToShow();
    if (stockData == null) {
      output.println("CONTROLLER: Error in getting data. Retry.");
      return true;
    }
    vciObj.showStockDataScreen(stockData);
    vciObj.viewControllerInteract(TypeofViews.BUY_STOCKS_VALUE, null, 0);

    String option;
    option = scan.nextLine();
    while ((option == null || option.length() == 0) || (!validateBuyStockOption(option))) {
      if (Objects.equals(option, "b") || Objects.equals(option, "B")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        return true;
      }
      vciObj.viewControllerInteract(TypeofViews.BUY_STOCKS_INVALID_RETRY, null, 0);
      option = scan.nextLine();
    }
    buyStockValueMenuAction(option, portfolioType, args, length);
    return false;
  }

  /**
   * This method tells the view to render the corresponding screen based on the user input to the
   * main menu. The options being, to create a portfolio, view composition, price of share on
   * certain date.
   *
   * @param option the option the user entered the main menu screen
   */
  private void performMainMenuAction(String option) {
    switch (option) {
      case "1": {
        // Create a portfolio
        createAPortfolio(option);
        break;
      }
      case "2": {
        // Value of the portfolio on certain date
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        compositionOfPortfolio();
        break;
      }
      case "3": {
        // Value of the portfolio on a certain date full composition
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        valueOfPortfolio();
        break;
      }
      case "4": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        addStockToPortfolioMainMenu();
        break;
      }
      case "5": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        sellStockFromPortfolio();
        break;
      }
      case "6": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        portfolioPerformanceMainMenu();
        break;
      }
      case "7": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        costBasisByDate();
        break;
      }
      case "8": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        commissionCostMainMenu();
        break;
      }
      case "9": {
        try {
          readStrategyAndImplement();
        } catch (Exception e) {
          output.println("Error in reading strategy.");
        }
        addStockUsingDollarCostMainMenu();
        break;
      }
      case "e":
      case "E": {
        output.println("\nExiting...");
        //exit(0);
        return;
      }
      default: {
        output.println("Invalid command. Enter the right option number.");
        break;
      }
    }
  }

  protected boolean validateDateEndDate(String dateStr, String format) {
    if (dateStr == null || dateStr.length() == 0) {
      return false;
    }

    Date date;
    try {
      DateFormat sdf = new SimpleDateFormat(format);
      date = sdf.parse(dateStr);
      if (!dateStr.equals(sdf.format(date))) {
        return false;
      }

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      if (calendar.get(Calendar.YEAR) > 2050
              || calendar.get(Calendar.YEAR) < 2000) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }


  private void commissionCostMainMenu() {
    StockCompositionData stk = new StockCompositionDataImpl("ALL");
    output.println("\nCONFIGURE COMMISSION COST\n");
    output.println("Current commission cost is: $" + stk.getCommissionCost());
    output.println("Enter the commission cost per transaction:");
    String cost;
    cost = scan.nextLine();
    while (!validateCommissionCost(cost)) {
      vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
      output.println("Commission cost should be greater than 0. Enter 'm' for main menu.");
      cost = scan.nextLine();
      if (cost.equalsIgnoreCase("m")) {
        return;
      }
    }
    storeCommissionCost(cost);
    output.println("Commission cost set to " + cost);
  }

  private void storeCommissionCost(String cost) {
    String[] args = new String[1];
    args[0] = cost;
    cmiObj.controllerModelInteract(TypeofAction.STORE_COMMISSION_COST, args, 1);
  }

  /**
   * A helper function to validate the commission cost.
   *
   * @param cost cost as string
   * @return true if cost is valid
   */
  protected boolean validateCommissionCost(String cost) {
    double commissionCost;
    if (cost == null || cost.length() == 0) {
      return false;
    }
    try {
      commissionCost = Double.parseDouble(cost);
    } catch (Exception e) {
      return false;
    }

    return (commissionCost > 0);
  }

  /**
   * This method helps in determining the cost invested in a portfolio on a certain date.
   */
  private void costBasisByDate() {
    while (true) {
      StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
      int numberOfPortFolio = obj.getNumberOfPortFolio();
      if (numberOfPortFolio == 0) {
        vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
        return;
      }
      String[] portfolioNames = obj.getPortFolioNames("FLEXIBLE");
      String[] args = new String[portfolioNames.length + 1];
      System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
      args[portfolioNames.length] = "FLEXIBLE";

      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, args,
              args.length);
      vciObj.viewControllerInteract(TypeofViews.WHICH_PORTFOLIO_CHECK, portfolioNames,
              numberOfPortFolio);
      String options;
      options = scan.nextLine();
      while ((options == null || options.length() == 0)
              || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
        options = scan.nextLine();
        if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
          return;
        }
      }

      if (!portfolioViewAction(options, "COST", "FLEXIBLE")) {
        continue;
      }

      if (endMenu()) {
        return;
      }
    }
  }

  /**
   * This method provides the main menu options for calculating the performance of a portfolio.
   */
  private void portfolioPerformanceMainMenu() {
    while (true) {
      StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
      int numberOfPortFolio = obj.getNumberOfPortFolio();
      if (numberOfPortFolio == 0) {
        vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
        return;
      }
      String[] portfolioNames = obj.getPortFolioNames("FLEXIBLE");
      String[] args = new String[portfolioNames.length + 1];
      System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
      args[portfolioNames.length] = "FLEXIBLE";

      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, args,
              args.length);
      vciObj.viewControllerInteract(TypeofViews.WHICH_PORTFOLIO_CHECK, portfolioNames,
              numberOfPortFolio);
      String options;
      options = scan.nextLine();
      while ((options == null || options.length() == 0)
              || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
        options = scan.nextLine();
        if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
          return;
        }
      }

      currentPortfolioName = obj.getPortFolioNames("FLEXIBLE")[Integer.parseInt(options) - 1];
      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_PERFORMANCE_DATE_INPUT, null, 0);
      String choice;
      choice = scan.nextLine();
      while (choice == null || choice.length() == 0 || !validatePortfolioSelectOption(choice, 3)) {
        vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
        choice = scan.nextLine();
        if (Objects.equals(choice, "m") || Objects.equals(choice, "M")) {
          return;
        }
      }
      if (portfolioPerformance(options, choice)) {
        break;
      }
    }
  }

  protected boolean validateSellStockInput(String option, double maxSize) {
    if (option == null || option.length() == 0) {
      return false;
    }

    if (option.equals("m") || option.equals("M")) {
      return true;
    }
    // The selected option should be a number, and it should be a valid digit.
    double val;
    try {
      val = Double.parseDouble(option);
    } catch (Exception e) {
      return false;
    }

    return val > (double) 0 && val <= maxSize;
  }


  /**
   * This is a helper method for calculating the performance of a portfolio.
   *
   * @param pfNumber the portfolio number
   * @param choice   type of option the user choose
   * @return true if user wants to return to main menu
   */
  private boolean portfolioPerformance(String pfNumber, String choice) {
    String date;
    String format = null;
    LocalDate today = LocalDate.now();
    int remainder = 0;

    if (Objects.equals(choice, "1")) {
      output.println("Enter the start year in format (YYYY) from year 2000 to "
              + today.minusYears(4).getYear() + ":");
      format = "yyyy";
    } else if (Objects.equals(choice, "2")) {
      output.println("Enter the start month in format (YYYY-MM) from year 2000 to "
              + today.minusMonths(4).getMonth() + " "
              + today.minusMonths(4).getYear() + ":");
      format = "yyyy-MM";
    } else if (Objects.equals(choice, "3")) {
      output.println("Enter the start date in format (YYYY-MM-DD) from year 2000 to "
              + today.minusDays(4).getYear() + "-"
              + today.minusDays(4).getMonth() + "-"
              + today.minusDays(4).getDayOfMonth() + ":");
      format = "yyyy-MM-dd";
    }

    date = scan.nextLine();
    while (!validateDate(date, format, 4)) {
      vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
      date = scan.nextLine();
      if (Objects.equals(date, "b") || Objects.equals(date, "B")) {
        return false;
      }
    }

    if (Objects.equals(choice, "1")) {
      String tempDateStr = date + "-01-01";
      remainder = (int) ChronoUnit.YEARS.between(LocalDate.parse(tempDateStr),
              LocalDate.now().withDayOfMonth(1));
      if (remainder != 4) {
        remainder += 1;
        if (remainder > 30) {
          remainder = 30;
        }
        output.println("Enter the number of years (5 to " + remainder + "): ");
      }
    } else if (Objects.equals(choice, "2")) {
      String tempDateStr = date + "-01";
      remainder = (int) ChronoUnit.MONTHS.between(LocalDate.parse(tempDateStr),
              LocalDate.now().withDayOfMonth(1));
      if (remainder != 4) {
        remainder += 1;
        if (remainder > 30) {
          remainder = 30;
        }
        output.println("Enter the number of months (5 to " + remainder + "): ");
      }
    } else if (Objects.equals(choice, "3")) {
      remainder = (int) ChronoUnit.DAYS.between(LocalDate.parse(date), LocalDate.now());
      if (remainder != 4) {
        remainder += 1;
        if (remainder > 30) {
          remainder = 30;
        }
        output.println("Enter the number of days (5 to " + remainder + "): ");
      }
    }

    String number;
    if (remainder != 4) {
      number = scan.nextLine();
      while (!validateStockSelectOption(number, 5, remainder)) {
        vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
        vciObj.viewControllerInteract(TypeofViews.GOBACK_MAINMENU_OPTION, null, 0);
        number = scan.nextLine();
        if (Objects.equals(number, "b") || Objects.equals(number, "B")) {
          return false;
        }
      }
      if (Objects.equals(number, "m") || Objects.equals(number, "M")) {
        return true;
      }
    } else {
      number = "5";
    }

    String[] dates;

    try {
      dates = getDatesFromUserInput(choice, date, number, format);
    } catch (Exception e) {
      output.println("Error in parsing the dates. Try again");
      return true;
    }

    dates[Integer.parseInt(number)] = currentPortfolioName;
    dates[Integer.parseInt(number) + 1] = pfNumber;
    dates[Integer.parseInt(number) + 2] = choice;
    cmiObj.controllerModelInteract(TypeofAction.GET_PORTFOLIO_PERFORMANCE, dates, dates.length);

    // Get the performance data
    Map<String, Double> pfPerformance;
    String portfolioType = "FLEXIBLE";
    try {
      StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
      pfPerformance = obj.computePerformanceData(dates, dates.length, portfolioType);
      if (pfPerformance == null) {
        output.println("Error in getting portfolio performance.\n");
        return false;
      }
    } catch (Exception e) {
      output.println("Error in getting portfolio performance.\n");
      return false;
    }

    String[] scale = getScale(pfPerformance);
    String getTitle = null;
    try {
      getTitle = getTitle(pfPerformance, dates[dates.length - 1]);
    } catch (Exception e) {
      output.println("Error in getting title");
    }

    portfolioPerformanceOverTimeController(dates, dates.length, pfPerformance, scale, getTitle);
    //vciObj.portfolioPerformanceOverTime(dates, dates.length, pfPerformance, scale, getTitle);

    return endMenu();
  }

  private void portfolioPerformanceOverTimeController(String[] args, int length,
                                                      Map<String, Double> pfPerformance,
                                                      String[] scale, String getTitle) {
    String title = "\nPerformance of portfolio " + args[length - 3].toUpperCase()
            + " " + getTitle + "\n";

    String[] data = new String[pfPerformance.size()];
    int ind = 0;

    for (Map.Entry<String, Double> set : pfPerformance.entrySet()) {
      data[ind] = printPerformance(set.getKey(), set.getValue(), scale, args[length - 1]);
      ind++;
    }

    String[] scaleStr;
    if (scale[1] == null) {
      scaleStr = new String[1];
      scaleStr[0] = "\nScale: * = $" + scale[0];
    } else {
      scaleStr = new String[3];
      scaleStr[0] = "\nBase Amount: $" + scale[1];
      scaleStr[1] = "One * is $" + scale[0] + " more than the base amount.";
      scaleStr[2] = "~ is the base value.";
    }

    String footer = "# - either no stocks or 0 value in portfolio.\n";
    vciObj.portfolioPerformanceOverTimeView(title, data, scaleStr, footer);
  }

  private String printPerformance(String dateStr, Double value, String[] scale, String choice) {
    String result = null;
    LocalDate date = LocalDate.parse(dateStr);
    String year = String.valueOf(date.getYear());
    String month = String.valueOf(date.getMonth());
    month = month.substring(0, 3);
    String dateIn = String.valueOf(date.getDayOfMonth());
    if (dateIn.length() == 1) {
      // Append 0 to dates with single digit
      dateIn = "0" + dateIn;
    }

    if (Objects.equals(choice, "1")) {
      result = year + ": " + getStars(value, scale);
    } else if (Objects.equals(choice, "2")) {
      result = month + " " + year + ": " + getStars(value, scale);
    } else if (Objects.equals(choice, "3")) {
      result = month + " " + dateIn + " " + year + ": " + getStars(value, scale);
    }

    return result;
  }

  private String getStars(Double value, String[] scale) {
    if (value == null || value == 0) {
      return "#";
    }

    int num;
    if (scale[1] == null) {
      num = (int) (value / Long.parseLong(scale[0]));
    } else {
      num = (int) ((value - Long.parseLong(scale[1])) / Long.parseLong(scale[0]));
      if (num == 0) {
        return "~";
      }
    }
    return "*".repeat(Math.max(0, num));
  }

  /**
   * This helper method helps to calculate the scale based on the date ranges.
   *
   * @param pfPerformance the performance map of the portfolio
   * @return the scale and it's type
   */
  protected String[] getScale(Map<String, Double> pfPerformance) {
    double max = Double.MIN_VALUE;
    double min = Double.MAX_VALUE;
    for (Map.Entry<String, Double> set : pfPerformance.entrySet()) {
      if (set.getValue() == null || set.getValue() == 0) {
        continue;
      }
      if (set.getValue() > max) {
        max = set.getValue();
      }
      if (set.getValue() < min) {
        min = set.getValue();
      }
    }

    double scale = (max / 50);
    scale = Math.floor(scale);
    String[] scaleStr = new String[2];
    if (scale < min) {
      scaleStr[0] = String.valueOf((long) scale);
      scaleStr[1] = null;
    } else {
      scaleStr[0] = String.valueOf((long) (max - min) / 50);
      scaleStr[1] = String.valueOf((long) Math.floor(min)); // base amount
    }
    return scaleStr;
  }

  /**
   * This method helps to get the title for portfolio performance.
   *
   * @param pfPerformance map of the portfolio data for the dates, and it's value
   * @param choice        choice entered by the user
   * @return the title for the display
   */
  protected String getTitle(Map<String, Double> pfPerformance, String choice) {
    int ind = 0;
    String dateStart = null;
    String dateEnd = null;

    StringBuilder title = new StringBuilder();

    for (Map.Entry<String, Double> set : pfPerformance.entrySet()) {
      if (ind == 0) {
        dateStart = set.getKey();
      }
      if (ind == pfPerformance.size() - 1) {
        dateEnd = set.getKey();
      }
      ind++;
    }

    assert dateStart != null;
    LocalDate startDate = LocalDate.parse(dateStart);
    assert dateEnd != null;
    LocalDate endDate = LocalDate.parse(dateEnd);

    if (Objects.equals(choice, "1")) {
      title.append("from ").append(startDate.getYear()).append(" to ").append(endDate.getYear());
    } else if (Objects.equals(choice, "2")) {
      title.append("from ").append(startDate.getMonth()).append(" ").append(startDate.getYear())
              .append(" to ").append(endDate.getMonth()).append(" ").append(endDate.getYear());
    } else if (Objects.equals(choice, "3")) {
      title.append("from ").append(startDate.getDayOfMonth()).append(" ")
              .append(startDate.getMonth()).append(" ").append(startDate.getYear())
              .append(" to ").append(endDate.getDayOfMonth()).append(" ")
              .append(endDate.getMonth()).append(" ").append(endDate.getYear());
    }

    return String.valueOf(title);
  }

  /**
   * A helper method to get user input to go back to previous option or to main menu.
   *
   * @return true if user wants to go to main menu
   */
  private boolean endMenu() {
    String options;
    vciObj.viewControllerInteract(TypeofViews.GOBACK_MAINMENU_OPTION, null, 0);
    options = scan.nextLine();
    while ((options == null || options.length() == 0)
            || !((options.equals("M")) || (options.equals("m")) || (options.equals("b"))
            || (options.equals("B")))) {
      vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
      vciObj.viewControllerInteract(TypeofViews.GOBACK_MAINMENU_OPTION, null, 0);
      options = scan.nextLine();
    }

    return Objects.equals(options, "m") || Objects.equals(options, "M");
    // When user presses 'B' continue
  }

  /**
   * This is a helper method that helps in getting the range of dates based on user input while
   * calculating performance of the portfolio.
   *
   * @param choice the input choice of the user
   * @param date   date, month or the year that the user inputted
   * @param number the number of days, month or the year the user needs data for
   * @param format format of the input date
   * @return the dates as array of strings
   * @throws ParseException if error while parsing the data
   */
  protected String[] getDatesFromUserInput(String choice, String date, String number,
                                           String format) throws ParseException {
    String[] dates = new String[Integer.parseInt(number) + 3];
    Date convertedDate;

    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    convertedDate = dateFormat.parse(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(convertedDate);

    if (Objects.equals(choice, "1")) {
      cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
      Date lastDayOfYear = cal.getTime();
      DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      LocalDate startDate = LocalDate.parse(sdf.format(lastDayOfYear));
      dates[0] = String.valueOf(startDate);
      for (int i = 1; i < Integer.parseInt(number); i++) {
        dates[i] = String.valueOf(startDate.plusYears(i));
      }
    } else if (Objects.equals(choice, "2")) {
      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      Date lastDayOfMonth = cal.getTime();
      DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      LocalDate startDate = LocalDate.parse(sdf.format(lastDayOfMonth));
      dates[0] = String.valueOf(startDate);
      for (int i = 1; i < Integer.parseInt(number); i++) {
        startDate = startDate.plusMonths(1);
        convertedDate = sdf.parse(String.valueOf(startDate));
        cal.setTime(convertedDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastDayOfMonth = cal.getTime();
        startDate = LocalDate.parse(sdf.format(lastDayOfMonth));
        dates[i] = String.valueOf(startDate);
      }
    } else if (Objects.equals(choice, "3")) {
      LocalDate startDate = LocalDate.parse(date);
      dates[0] = String.valueOf(startDate);
      for (int i = 1; i < Integer.parseInt(number); i++) {
        dates[i] = String.valueOf(startDate.plusDays(i));
      }
    }
    return dates;
  }

  /**
   * A helper method for selling the stocks from the portfolio.
   */
  private void sellStockFromPortfolio() {
    while (true) {
      StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
      int numberOfPortFolio = obj.getNumberOfPortFolio();
      if (numberOfPortFolio == 0) {
        vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
        return;
      }
      String[] portfolioNames = obj.getPortFolioNames("FLEXIBLE");
      String[] arg = new String[portfolioNames.length + 1];
      System.arraycopy(portfolioNames, 0, arg, 0, portfolioNames.length);
      arg[portfolioNames.length] = "FLEXIBLE";

      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, arg,
              arg.length);
      output.println("\nSelect the portfolio to sell stocks from.");
      String options;
      options = scan.nextLine();
      while ((options == null || options.length() == 0)
              || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
        options = scan.nextLine();
        if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
          return;
        }
      }

      String date = sellStockDateEnter();
      if (date == null) {
        continue;
      }

      String[] args = new String[3];
      args[0] = date;
      args[1] = options;
      args[2] = "FLEXIBLE";
      vciObj.viewControllerInteract(TypeofViews.LIST_OF_STOCKS_ON_DATE, args, 3);

      StockPortFolioDataImpl stkObj;
      try {
        stkObj = (StockPortFolioDataImpl)
                obj.getAllStockDataInPortFolio(Integer.parseInt(options) - 1,
                        true, date, true, true, "FLEXIBLE");
      } catch (Exception e) {
        output.println("Controller: Error in getting stock data " + e.getMessage());
        return;
      }

      if (stkObj.numberOfUniqueStocks == 0) {
        return;
      }

      String stockOptions = scan.nextLine();

      while ((stockOptions == null || stockOptions.length() == 0)
              || (!validateStockSelectOption(stockOptions, 1, stkObj.numberOfUniqueStocks))) {
        vciObj.viewControllerInteract(TypeofViews.STOCK_BUY_REENTER, null, 0);
        stockOptions = scan.nextLine();
        if (Objects.equals(stockOptions, "m") || Objects.equals(stockOptions, "M")) {
          return;
        }
      }

      if (sellSharesOnAStock(Integer.parseInt(options) - 1,
              stkObj.stockSymbol[Integer.parseInt(stockOptions) - 1], date)) {
        return;
      }
    }
  }

  /**
   * A helper method for getting the input date from the user for selling the stocks.
   *
   * @return the user input as a string
   */
  private String sellStockDateEnter() {
    String date;
    output.println("Enter the date on which you would like to sell the stock (YYYY-MM-DD)");
    date = scan.nextLine();
    while (!validateDate(date, "yyyy-MM-dd", 0)) {
      vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
      date = scan.nextLine();
      if (Objects.equals(date, "b") || Objects.equals(date, "B")) {
        return null;
      }
    }
    return date;
  }

  /**
   * This method sells number of shares of a stock from a portfolio on a certain date.
   *
   * @param pfNumber    the portfolio number
   * @param stockSymbol the symbol of the stock
   * @param date        date on which the user needs to sell
   * @return true if user wants to return to main menu
   */
  private boolean sellSharesOnAStock(int pfNumber, String stockSymbol, String date) {
    StockCompositionData stk = new StockCompositionDataImpl("FLEXIBLE");
    currentPortfolioName = stk.getPortFolioNames("FLEXIBLE")[pfNumber];
    double numberOfAvailableShares;

    try {
      numberOfAvailableShares = stk.sharesAvailableOnTheDateForSale(pfNumber, stockSymbol, date,
              "FLEXIBLE");
    } catch (Exception e) {
      output.println("Unable to get remaining share details on the stock for this date.");
      return true;
    }

    if (numberOfAvailableShares == 0) {
      output.println("You cannot sell any shares of this stock on this date as they are already"
              + " sold.");
      return true;
    }

    String[] stockGetData = new String[2];
    stockGetData[0] = stockSymbol;
    stockGetData[1] = date;
    cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, stockGetData, 2);
    String[] stockData = readStockDataToShow();
    if (stockData == null) {
      output.println("CONTROLLER: Error in getting data. Retry.");
      return true;
    }
    vciObj.showStockDataScreen(stockData);

    output.println("\nYou can sell only " + numberOfAvailableShares
            + " shares of this stock on " + date);
    output.println("How many share would you like to sell?\n");

    String sellShares = scan.nextLine();
    while ((sellShares == null || sellShares.length() == 0)
            || (!validateSellStockInput(sellShares, numberOfAvailableShares))) {
      String[] args = new String[1];
      args[0] = String.valueOf(numberOfAvailableShares);
      vciObj.viewControllerInteract(TypeofViews.BUY_STOCKS_INVALID_RETRY, args, 1);
      sellShares = scan.nextLine();
      if (Objects.equals(sellShares, "m") || Objects.equals(sellShares, "M")) {
        return true;
      }
      if (Objects.equals(sellShares, "B") || Objects.equals(sellShares, "b")) {
        return false;
      }
    }

    String[] data = new String[3];
    data[0] = sellShares;
    data[1] = currentPortfolioName;
    cmiObj.controllerModelInteract(TypeofAction.SELL_STOCKS, data, 3);
    output.println("Shares successfully sold.");

    return endMenu();
  }

  /**
   * This method helps to read the current stock data.
   *
   * @return the stock data
   */
  private String[] readStockDataToShow() {
    String line;
    String splitBy = ",";
    BufferedReader stockData = null;
    String[] splitStockData;
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (Exception e) {
      output.println("Supported stocks file not found " + e.getMessage());
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
      output.println("Controller: Error in reading Supported stocks csv file.");
      return null;
    }
    return splitStockData;
  }

  /**
   * This method acts as an abstract method that holds common code.
   *
   * @return true if successful in reading
   */
  private boolean abstractAddStockScreen() {
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
      return false;
    }
    String[] portfolioNames = obj.getPortFolioNames("FLEXIBLE");
    String[] arg = new String[portfolioNames.length + 1];
    System.arraycopy(portfolioNames, 0, arg, 0, portfolioNames.length);
    arg[portfolioNames.length] = "FLEXIBLE";

    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, arg,
            arg.length);
    output.println("\nSelect the portfolio to which you would like to add the stock.");
    String options;
    options = scan.nextLine();
    while ((options == null || options.length() == 0)
            || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
      options = scan.nextLine();
      if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
        return false;
      }
    }
    //addStockToPortfolio(options);
    StockCompositionData stk = new StockCompositionDataImpl("FLEXIBLE");
    currentPortfolioName =
            stk.getPortFolioNames("FLEXIBLE")[Integer.parseInt(options) - 1];
    return true;
  }

  /**
   * This method provides the options for users to adds stocks to the portfolio.
   */
  protected void addStockToPortfolioMainMenu() {
    if (!abstractAddStockScreen()) {
      return;
    }

    String[] args = new String[1];
    args[0] = "addStockScreen";
    performCreatePortfolioMenuAction("1", "1", args, 1);
  }

  /**
   * This method helps in adding stocks using dollar-cost strategy.
   */
  private void addStockUsingDollarCostMainMenu() {
    if (!abstractAddStockScreen()) {
      return;
    }
    dollarCostAverage();
  }

  /**
   * This method performs the menu actions when user wants to view the value of a portfolio on
   * a certain date.
   */
  private void valueOfPortfolio() {
    while (true) {
      StockCompositionData obj = new StockCompositionDataImpl("ALL");
      int numberOfPortFolio = obj.getNumberOfPortFolio();
      if (numberOfPortFolio == 0) {
        vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
        return;
      }
      String[] portfolioNames = obj.getPortFolioNames("ALL");
      String[] args = new String[portfolioNames.length + 1];
      System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
      args[portfolioNames.length] = "ALL";

      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, args,
              args.length);
      vciObj.viewControllerInteract(TypeofViews.WHICH_PORTFOLIO_CHECK, portfolioNames,
              numberOfPortFolio);
      String options;
      options = scan.nextLine();
      while ((options == null || options.length() == 0)
              || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
        options = scan.nextLine();
        if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
          return;
        }
      }
      if (!portfolioViewAction(options, "FULL", "ALL")) {
        continue;
      }

      if (endMenu()) {
        return;
      }
    }
  }

  /**
   * This method performs menu action for the case where user wants to view the composition
   * of a portfolio.
   */
  private void compositionOfPortfolio() {
    while (true) {
      StockCompositionData obj = new StockCompositionDataImpl("ALL");
      int numberOfPortFolio = obj.getNumberOfPortFolio();
      if (numberOfPortFolio == 0) {
        vciObj.viewControllerInteract(TypeofViews.NO_PORTFOLIO, null, 0);
        return;
      }
      String[] portfolioNames = obj.getPortFolioNames("ALL");
      String[] args = new String[portfolioNames.length + 1];
      System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
      args[portfolioNames.length] = "ALL";

      vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_COMPOSITION, args,
              args.length);
      vciObj.viewControllerInteract(TypeofViews.WHICH_PORTFOLIO_CHECK, portfolioNames,
              numberOfPortFolio);
      String options;
      options = scan.nextLine();
      while ((options == null || options.length() == 0)
              || (!validatePortfolioSelectOption(options, numberOfPortFolio))) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INVALID_ENTRY, null, 0);
        options = scan.nextLine();
        if (Objects.equals(options, "b") || Objects.equals(options, "B")) {
          return;
        }
      }

      String fileName = obj.getPortFolioFileNameByIndex(Integer.parseInt(options) - 1,
              "ALL");

      if (obj.isPortfolioOfGivenType(fileName, "INFLEXIBLE")) {
        portfolioCompositionAction(options, "ALL");
      } else {
        if (!portfolioViewAction(options, "TRUE", "ALL")) {
          continue;
        }
      }

      if (endMenu()) {
        return;
      }
    }
  }

  /**
   * This method tells the view to display the composition of the portfolio along with the stocks
   * details like name, symbol, quantity, price of each share and total value.
   *
   * @param options the portfolio number
   */
  private void portfolioCompositionAction(String options, String portfolioType) {
    String[] args = new String[2];
    args[0] = options;
    args[1] = portfolioType;
    vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_INDIVIDUAL_LIST, args, 2);
  }

  /**
   * This method performs the menu actions for the case where user wants to create a new portfolio.
   *
   * @param option the option which user entered
   */
  private void createAPortfolio(String option) {
    while (true) {
      String type;
      vciObj.viewControllerInteract(TypeofViews.TYPEOF_PORTFOLIO_SCREEN, null, 0);
      type = scan.nextLine();
      while (type.length() == 0 || !(type.equals("1") || type.equals("2"))) {
        vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
        type = scan.nextLine();
        if (type.equals("m")) {
          return;
        }
      }

      String name;
      vciObj.viewControllerInteract(TypeofViews.CREATE_PORTFOLIO_NAME_SCREEN, null, 0);
      name = scan.nextLine();
      while (name.length() == 0) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_NAME_REENTER, null, 0);
        name = scan.nextLine();
        if (name.equals("0")) {
          return;
        }
      }

      if (checkIfPortfolioExists(name)) {
        vciObj.viewControllerInteract(TypeofViews.PORTFOLIO_ALREADY_EXISTS, null, 0);
        vciObj.viewControllerInteract(TypeofViews.PF_REENTER_DUPLICATE_NAME, null, 0);
        String input;
        input = scan.nextLine();
        while ((input.length() == 0) || !(input.equals("B") || input.equals("b")
                || input.equals("Y") || input.equals("y") || input.equals("N")
                || input.equals("n"))) {
          vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
          vciObj.viewControllerInteract(TypeofViews.PF_REENTER_DUPLICATE_NAME,
                  null, 0);
          input = scan.nextLine();
        }
        if (input.equals("B") || input.equals("b")) {
          return;
        }
        if (input.equals("N") || input.equals("n")) {
          continue;
        }
        // This case is 'Y', we want to override.
      }

      String[] args = new String[2];
      args[0] = name;
      args[1] = type;
      currentPortfolioName = name;
      createPortfolioNameScreenAction(option, args);
      break;
    }
  }

  /**
   * A helper method that check if the portfolio that the user entered while creating one already
   * exists or not.
   *
   * @param name the name of the portfolio that user wants to create
   * @return true if the portfolio exists, else false
   */
  protected boolean checkIfPortfolioExists(String name) {
    String directoryName = "userdata/user1/Portfolio/";
    File directory = new File(directoryName);
    if (!directory.exists()) {
      return false;
    }
    File dir = new File("userdata/user1/Portfolio/");
    File[] files = dir.listFiles();
    assert files != null;
    for (File file : files) {
      String fullName = file.getName();
      fullName = fullName.substring(3);
      String[] fName = fullName.split("\\.");
      if (Objects.equals(fName[0], name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * A helper method that helps in validation the option the user entered for the portfolio for
   * which the user needs to know the information about.
   *
   * @param option            the index of the portfolio the user entered
   * @param numberOfPortFolio the number of portfolios that are present in the system
   * @return true if the input is valid, else false
   */
  private boolean validatePortfolioSelectOption(String option, int numberOfPortFolio) {
    // The selected option should be a number, and it should be a valid digit.
    int val;
    try {
      val = Integer.parseInt(option);
    } catch (Exception e) {
      return false;
    }

    return val >= 1 && val <= numberOfPortFolio;
  }

  /**
   * After user creates a portfolio with the name, the user has to buy stocks to fill the portfolio
   * with date. This method tells the view to display the options for the user to buy the stocks.
   *
   * @param option the option that user entered after entering name for the portfolio
   * @param args   helper arguments if passed by the callee
   * @return false if the option entered is invalid, else true
   */
  private boolean performCreatePortfolioMenuAction(String option, String portfolioType,
                                                   String[] args, int length) {
    switch (option) {
      case "1": {
        //Buy a new stock
        while (true) {
          vciObj.viewControllerInteract(TypeofViews.LIST_OF_STOCKS, null, 0);
          output.println("\nWhich stock would you like to buy?");
          String options;
          options = scan.nextLine();
          StockNameMap snp = new StockNameMapImpl();
          while (!validateStockSelectOption(options, 1, snp.getMapSize())) {
            vciObj.viewControllerInteract(TypeofViews.STOCK_BUY_REENTER, null, 0);
            options = scan.nextLine();
          }
          if (!performListOfStocksMenuAction(options, portfolioType, args, length)) {
            break;
          }
        }
        // Delete if portfolio is empty
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        break;
      }
      case "2": {
        if (Objects.equals(portfolioType, "1")) {
          dollarCostAverage();
        } else {
          cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
          output.println("This option is not available for inflexible portfolio.");
        }
        break;
      }
      case "3": {
        // Go to Main menu
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        break;
      }
      case "e":
      case "E": {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        output.println("\nExiting...");
        exit(0);
        break;
      }
      default: {
        output.println("Invalid command. Enter the right option number.");
        return false;
      }
    }
    return true;
  }

  /**
   * This method helps in getting inputs and calculating data for buying stocks using dollar-cost
   * strategy.
   */
  private void dollarCostAverage() {
    // Dollar-Cost Averaging investing
    // Ask for the date of investment
    output.println("Enter the date on which you would like to purchase the stock (YYYY-MM-DD)"
            + " (from year 2000 to current day)");
    String startDate = scan.nextLine();
    while (!validateDate(startDate, "yyyy-MM-dd", 0)) {
      vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
      startDate = scan.nextLine();
      if (Objects.equals(startDate, "b") || Objects.equals(startDate, "B")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        return;
      }
    }

    // Ask for recurring investment
    output.println("Do you want to investment to be recurring? (Y|N)");
    String recurringStr = scan.nextLine();
    while (!(recurringStr.equalsIgnoreCase("y")
            || recurringStr.equalsIgnoreCase("n"))) {
      vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
      recurringStr = scan.nextLine();
      if (Objects.equals(recurringStr, "m") || Objects.equals(recurringStr, "M")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        return;
      }
    }

    String frequencyStr = null;
    String endDate = null;
    String onGoingStr = null;
    // If it is recurring investment, then check if it is ongoing
    if (recurringStr.equalsIgnoreCase("y")) {
      output.println("Is this an ongoing strategy? (Y|N)");
      onGoingStr = scan.nextLine();
      while (!(onGoingStr.equalsIgnoreCase("y")
              || onGoingStr.equalsIgnoreCase("n"))) {
        vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
        onGoingStr = scan.nextLine();
        if (Objects.equals(onGoingStr, "m") || Objects.equals(onGoingStr, "M")) {
          cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
          return;
        }
      }

      // If it is not ongoing, then ask the end date
      if (onGoingStr.equalsIgnoreCase("n")) {
        // If the strategy is not going, then ask for the end date
        // The end date can be in future as well
        output.println("Enter the end date for the strategy"
                + " from " + startDate + " (YYYY-MM-DD)");
        endDate = scan.nextLine();
        while (!validateDateEndDate(endDate, "yyyy-MM-dd")
                || (LocalDate.parse(endDate).compareTo(LocalDate.parse(startDate)) < 0)) {
          vciObj.viewControllerInteract(TypeofViews.DATE_RENTER, null, 0);
          endDate = scan.nextLine();
          if (Objects.equals(endDate, "b") || Objects.equals(endDate, "B")) {
            cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
            return;
          }
        }
      }
    }

    if (recurringStr.equalsIgnoreCase("y")) {
      assert onGoingStr != null;
      if (onGoingStr.equalsIgnoreCase("n")) {
        assert endDate != null;
        int remainder = (int) ChronoUnit.DAYS.between(LocalDate.parse(startDate),
                LocalDate.parse(endDate));

        if (remainder == 0) {
          output.println("Since the start date is today, there is no recurring "
                  + "investment applied.");
          frequencyStr = "0";
        } else {
          if (remainder == 1) {
            output.println("The frequency is auto applied to 1 since it is only 1 day");
            frequencyStr = "1";
          } else {
            output.println("Enter the recurring frequency (1 to " + remainder + " days)");
            frequencyStr = scan.nextLine();
            while (!validateStockSelectOption(frequencyStr, 1, remainder)) {
              vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
              frequencyStr = scan.nextLine();
              if (frequencyStr.equalsIgnoreCase("m")) {
                cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
                return;
              }
            }
          }
        }
      } else {
        // Ask frequency for ongoing strategy
        output.println("Enter the recurring frequency for the ongoing strategy (1 to 30 days)");
        frequencyStr = scan.nextLine();
        while (!validateStockSelectOption(frequencyStr, 1, 30)) {
          vciObj.viewControllerInteract(TypeofViews.NOT_VALID_MAIN_MENU, null, 0);
          frequencyStr = scan.nextLine();
          if (frequencyStr.equalsIgnoreCase("m")) {
            cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
            return;
          }
        }
      }
    }

    // Ask how many stocks would the user like to buy
    StockNameMap snp = new StockNameMapImpl();
    output.println("How many stocks would you like to buy? (1 to " + snp.getMapSize()
            + ")");
    String numberOfStocks;
    numberOfStocks = scan.nextLine();
    while (!validateStockSelectOption(numberOfStocks, 1, snp.getMapSize())) {
      vciObj.viewControllerInteract(TypeofViews.STOCK_BUY_REENTER, null, 0);
      numberOfStocks = scan.nextLine();
      // Return to main menu
      if (Objects.equals(numberOfStocks, "m") || Objects.equals(numberOfStocks, "M")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        return;
      }
    }

    // Show list of stocks and ask which one would like to buy
    vciObj.viewControllerInteract(TypeofViews.LIST_OF_STOCKS, null, 0);
    int number = Integer.parseInt(numberOfStocks);
    String[] options = new String[number];

    for (int i = 0; i < number; i++) {
      output.println("\nEnter the stock option (" + (i + 1) + " out of " + number + ")");
      options[i] = scan.nextLine();
      while (!validateStockSelectOption(options[i], 1, snp.getMapSize())) {
        vciObj.viewControllerInteract(TypeofViews.STOCK_BUY_REENTER, null, 0);
        options[i] = scan.nextLine();
      }
      if (Objects.equals(options[i], "m") || Objects.equals(options[i], "M")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO,
                null, 0);
        return;
      }
    }

    // Ask the amount of money the user would like to invest
    output.println("How much money you would like to invest?");
    String cost;
    cost = scan.nextLine();
    while (!validateCommissionCost(cost)) {
      vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
      output.println("Investment amount should be greater than 0. Enter 'm' for main menu.");
      cost = scan.nextLine();
      if (cost.equalsIgnoreCase("m")) {
        cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
        return;
      }
    }

    // Ask for the proportion on the stocks
    Map<String, String> data = snp.getMap();
    String[] stockNameIndexArray = new String[snp.getMapSize()];
    String[] stockSymbolIndexArray = new String[snp.getMapSize()];
    int index = 0;
    for (Map.Entry<String, String> entry : data.entrySet()) {
      stockSymbolIndexArray[index] = entry.getKey();
      stockNameIndexArray[index++] = entry.getValue();
    }

    String[] proportion = new String[number];
    double totalProportion = 100;
    int i;

    for (i = 0; i < number - 1; i++) {
      output.println("Enter the proportion percent for "
              + stockNameIndexArray[Integer.parseInt(options[i]) - 1]
              + " (out of " + totalProportion + "%)");
      proportion[i] = scan.nextLine();

      while (!validateCommissionCost(proportion[i])) {
        vciObj.viewControllerInteract(TypeofViews.NOT_VALID_INPUT_SCREEN, null, 0);
        output.println("Enter 'm' for main menu.");
        proportion[i] = scan.nextLine();

        if (proportion[i].equalsIgnoreCase("m")) {
          cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
          return;
        }
      }

      totalProportion -= Double.parseDouble(proportion[i]);
      if (totalProportion < 0) {
        totalProportion += Double.parseDouble(proportion[i]);
        output.println("Cannot enter this value. The  proportion has to be equal to 100%");
        i--;
        continue;
      }

      if (totalProportion == 0) {
        output.println("No more proportions available.");
        output.println("All the remaining stocks will have 0 proportion and hence they will"
                + "not be bought.");
        i++; // since we break here, 'i' won't be incremented by loop, hence manually increment
        break;
      }
    }

    if (i == number - 1) {
      output.println("The remaining " + totalProportion + " percentage will be automatically"
              + " applied to " + stockNameIndexArray[Integer.parseInt(options[i]) - 1]
              + " stock.");
      proportion[i] = String.valueOf(totalProportion);
    } else {
      // 'i' is already initialized
      for (; i < number; i++) {
        // Stocks that have no remaining proportion, assign it to 0
        proportion[i] = String.valueOf(0);
      }
    }

    String[] dateArr;
    int remainderDays = 0;
    String lastKnownStockDate = startDate;
    boolean isDatesInFuture = false;

    if (recurringStr.equalsIgnoreCase("y")
            && !Objects.equals(frequencyStr, "0")) {
      assert endDate != null;

      if (onGoingStr.equalsIgnoreCase("n")) {
        remainderDays = (int) ChronoUnit.DAYS.between(LocalDate.parse(startDate),
                LocalDate.parse(endDate));
      } else {
        remainderDays = (int) ChronoUnit.DAYS.between(LocalDate.parse(startDate),
                LocalDate.now());
      }
      assert frequencyStr != null;
      remainderDays = (remainderDays / Integer.parseInt(frequencyStr)) + 1;

      dateArr = new String[remainderDays];
      if (remainderDays == 0) {
        dateArr = new String[1];
        dateArr[0] = startDate;
      } else {
        LocalDate sDate = LocalDate.parse(startDate);
        dateArr[0] = startDate;
        for (int k = 1; k < remainderDays; k++) {
          sDate = sDate.plusDays(Long.parseLong(frequencyStr));
          dateArr[k] = sDate.toString();
          if (sDate.compareTo(LocalDate.now()) <= 0) {
            lastKnownStockDate = sDate.toString();
          }
          if ((sDate.compareTo(LocalDate.now()) > 0) && !isDatesInFuture) {
            isDatesInFuture = true;
          }
        }
      }
    } else {
      dateArr = new String[1];
      dateArr[0] = startDate;
    }

    String[] stockSymbolRequired = new String[options.length];
    for (int j = 0; j < options.length; j++) {
      stockSymbolRequired[j] = stockSymbolIndexArray[Integer.parseInt(options[j]) - 1];
    }

    output.println("Buying shares, please wait...");
    // Calculate the number of shares to be bought for the
    calculateAndBuySharesBasedOnStrategy(dateArr, cost, proportion,
            stockSymbolRequired, number, currentPortfolioName);

    // Save ongoing strategy data
    // The final data will be like
    // 2022-11-26, 2000, 3, MSFT, 20, GOOG, 60, WMT, 20)
    if ((recurringStr.equalsIgnoreCase("y")
            && onGoingStr.equalsIgnoreCase("y")) || isDatesInFuture) {
      // Entry for ongoing strategy
      StringBuilder strategyArgs = new StringBuilder();

      // Type of Strategy
      if (isDatesInFuture) {
        strategyArgs.append("DOLLAR-COST-END").append(",");
      } else {
        strategyArgs.append("DOLLAR-COST").append(",");
      }

      // Last known date on which the stocks were bought
      if (isDatesInFuture) {
        strategyArgs.append(lastKnownStockDate).append(",");
      } else {
        strategyArgs.append(dateArr[remainderDays - 1]).append(",");
      }

      // Add end date as well
      if (isDatesInFuture) {
        strategyArgs.append(endDate).append(",");
      }

      // Frequency to buy stocks
      strategyArgs.append(frequencyStr).append(",");

      // Cost invested
      strategyArgs.append(cost).append(",");

      // Number of stocks in the strategy
      strategyArgs.append(stockSymbolRequired.length).append(",");

      // Shares in the strategy
      // Proportion for each shares
      for (int j = 0; j < stockSymbolRequired.length; j++) {
        strategyArgs.append(stockSymbolRequired[j]).append(",");
        if (j < stockSymbolRequired.length - 1) {
          strategyArgs.append(proportion[j]).append(",");
        }
        if (j == stockSymbolRequired.length - 1) {
          strategyArgs.append(proportion[j]).append("\n");
        }
      }

      // Persist the strategy
      try {
        persistStrategyToFile(String.valueOf(strategyArgs), currentPortfolioName);
      } catch (Exception e) {
        output.println("CONTROLLER: Error in persisting the strategy. " + e.getMessage());
      }
    }

    output.println("\nStock successfully added to the portfolio...!!!");
  }

  protected void calculateAndBuySharesBasedOnStrategy(String[] dateArr, String cost,
                                                      String[] proportion,
                                                      String[] stockSymbolIndexArray,
                                                      int number, String pfName) {
    double costInDouble = Double.parseDouble(cost);
    int index;
    LocalDate today = LocalDate.now();
    for (int j = 0; j < dateArr.length; j++) {
      LocalDate dateToBePurchased = LocalDate.parse(dateArr[j]);
      for (index = 0; index < number; index++) {
        double prop = Double.parseDouble(proportion[index]);
        if (prop != 0 && (dateToBePurchased.compareTo(today) <= 0)) {
          // Get the stock price on that date
          String[] args = new String[2];
          args[0] = stockSymbolIndexArray[index]; // Stock Symbol
          args[1] = dateArr[j];  // date
          cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, args, 2);
          String[] splitStockData = readGetStockData();
          double priceOfSingleShare = Double.parseDouble(splitStockData[1]);
          double totalAmountDedicatedToThisStock
                  = costInDouble * Double.parseDouble(proportion[index]) / 100;
          double amountOfSharesToBeBought = totalAmountDedicatedToThisStock / priceOfSingleShare;
          amountOfSharesToBeBought = Math.floor(amountOfSharesToBeBought * 100) / 100;

          String[] buyArgs = new String[2];
          buyArgs[0] = String.valueOf(amountOfSharesToBeBought);
          buyArgs[1] = pfName;
          cmiObj.controllerModelInteract(TypeofAction.BUY_STOCKS, buyArgs, 2);
        }
      }
    }
  }

  /**
   * A private helper method that writes the strategy data to the file.
   *
   * @param strategyArgs strategy data
   * @throws IOException if there is any issue in writing data
   */
  protected void persistStrategyToFile(String strategyArgs, String pfName) throws IOException {
    String path = "userdata/user1/Strategy/";
    File directory = new File(path);
    if (!directory.exists()) {
      directory.mkdir();
    }

    String fileName = path + pfName + ".csv";
    File file = new File(fileName);
    if (!file.exists()) {
      file.createNewFile();
    }

    PrintWriter write = new PrintWriter(new FileOutputStream(fileName, true));
    write.write(strategyArgs);
    write.flush();
    write.close();
  }

  /**
   * A private helper method to read and get the stock data from the StockData CSV file.
   *
   * @return the stock data as a string
   */
  private String[] readGetStockData() {
    BufferedReader stockData;
    try {
      stockData = new BufferedReader(new FileReader("data/StockData.csv"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String line;
    String splitBy = ",";
    String[] splitStockData;

    try {
      line = stockData.readLine();
      splitStockData = line.split(splitBy);
      stockData.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return splitStockData;
  }

  /**
   * A helper method that helps in validating where the option that was entered by the user to buy
   * a stock is valid or not.
   *
   * @param option the stock option that was entered by the user
   * @return true if the input is valid, else false
   */
  protected boolean validateStockSelectOption(String option, int minVal, int maxSize) {
    if (option == null || option.length() == 0) {
      return false;
    }

    if (option.equals("m") || option.equals("M")) {
      return true;
    }
    // The selected option should be a number, and it should be a valid digit.
    int val;
    try {
      val = Integer.parseInt(option);
    } catch (Exception e) {
      return false;
    }

    return val >= minVal && val <= maxSize;
  }

  /**
   * A helper method that helps in validation the user input for the number of shares the user
   * wants to buy for a stock.
   *
   * @param option the number of shares that the user wants to buy
   * @return true if the input is valid, else false
   */
  protected boolean validateBuyStockOption(String option) {
    if (option == null || option.length() == 0) {
      return false;
    }

    if (option.equals("m") || (option.equals("M"))) {
      return true;
    }

    // The selected option should be a number, and it should be a valid digit.
    int val;
    try {
      val = Integer.parseInt(option);
    } catch (Exception e) {
      return false;
    }

    return val > 0;
  }
}