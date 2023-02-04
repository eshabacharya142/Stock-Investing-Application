package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.swing.JSpinner;
import javax.swing.JComboBox;

import model.DollarValueDataImpl;
import model.PortfolioPerformanceDataImpl;
import model.StockCompositionData;
import model.StockCompositionDataImpl;
import model.StockNameMap;
import model.StockNameMapImpl;
import model.StockPortFolioDataImpl;
import model.TransactValueDataImpl;
import model.TypeofAction;
import view.GUIView;

/**
 * This class represents the controller that controls the GUI and also extents the feature
 * interface.
 */
public class GUIController extends ControllerViewInteractImpl implements Features {
  private GUIView viewGUI;
  private PortfolioPerformanceDataImpl ppd;
  private DollarValueDataImpl dvd;
  private TransactValueDataImpl tvd;


  /**
   * Constructor for the controller that interacts with the view that takes in two arguments and
   * initializes them to the global variables.
   *
   * @param input  the input stream
   * @param output the output stream
   */
  public GUIController(InputStream input, PrintStream output) {
    super(input, output);
    ppd = new PortfolioPerformanceDataImpl();
    dvd = new DollarValueDataImpl();
    tvd = new TransactValueDataImpl();
  }


  @Override
  public void setView(GUIView guiView) {
    viewGUI = guiView;
    viewGUI.addFeatures(this);
  }

  public void createAPortfolioGUI() {
    viewGUI.displayTypeOfPortfolioFlexibleOrInFlexibleScreen();
  }

  @Override
  public void valueAndCompositionGUIMainScreen() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("ALL");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] displayString = getFlexiblePortfolioNames("ALL");
    if (displayString != null) {
      viewGUI.valueAndCompScreenOne(displayString);
    }
  }

  @Override
  public void createPortfolioScreenSubmit(int type, String name, int optionSelected) {

    // reenter if not entered anything
    // if the user didn't enter a string, display the error message and then the same screen
    if (name.length() == 0) {
      //viewGUI.errorReenterName();
      viewGUI.displayErrorMessage("Name cannot be empty. Please Re-enter name");
      return;
    }
    // override

    String typeStr = null;
    if (type == 0) {
      typeStr = "1";
    } else if (type == 1) {
      typeStr = "2";
    }

    if (type == 1 && optionSelected == 1) {
      viewGUI.displayInformationalMessage("This option is not available for Inflexible Portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    if (checkIfPortfolioExists(name)) {
      int yesToOverride = viewGUI.jOptionPortfolioAlreadyExists();
      if (yesToOverride == 0) {
        // do nothing
      } else if (yesToOverride == 1) {
        viewGUI.resetCreatePortfolioScreen();
        return;
      }
    }

    String[] args = new String[2];
    args[0] = name;
    args[1] = typeStr;
    super.currentPortfolioName = name;
    super.cmiObj.controllerModelInteract(TypeofAction.CREATE_PORTFOLIO, args, 1);
    dvd.isCreateDollarValue = false;
    //createPortfolioNameScreenAction(null, args);
    String[] supportedStocks = {
        "1. Microsoft (MSFT)",
        "2. Meta (META)",
        "3. Google (GOOG)",
        "4. Apple (AAPL)",
        "5. Tesla (TSLA)",
        "6. JPMorgan Chase (JPM)",
        "7. Johnson (JNJ)",
        "8. Amazon (AMZN)",
        "9. UnitedHealth (UNH)",
        "10. Walmart (WMT)"
    };
    tvd.isFirstBuy = true;
    if (type == 0) {
      if (optionSelected == 0) {
        // Normal Buy Stocks
        viewGUI.flexiblePortfolioScreenWithDateInput(type, supportedStocks, name);
      } else if (optionSelected == 1) {
        // Dollar Value Investment
        dvd.isCreateDollarValue = true;
        viewGUI.displayAddStocksUsingDollarStrategyMain(null);
      }
    } else if (type == 1) {
      viewGUI.inflexiblePortfolioScreen(supportedStocks);
    }
  }


  @Override
  public void checkCurrentPrice(int type, String date, int stockSelected) {
    if (type == 0) {
      if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
        viewGUI.displayErrorMessage("Invalid Date. Please reenter valid date");
        return;
      }
    } else if (type == 1) {
      SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
      Date now = new Date();
      date = sdfDate.format(now);
    }

    if (stockSelected == -1) {
      viewGUI.displayErrorMessage("Select a stock");
      return;
    }

    String[] stock = new String[2];

    StockNameMap snp = new StockNameMapImpl();
    Map<String, String> map = snp.getMap();
    String[] stockSymbolIndexArray = new String[snp.getMapSize()];

    int index = 0;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      stockSymbolIndexArray[index++] = entry.getKey();
    }
    stock[0] = stockSymbolIndexArray[stockSelected];
    stock[1] = date;
    super.cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, stock, 2);

    String line;
    String splitBy = ",";
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
      stockData.close();
    } catch (Exception e) {
      System.out.println("Error in reading Supported stocks csv file.");
    }
    // vciObj.viewControllerInteract(TypeofViews.SHOW_STOCK_DATA, null, 0);
    String data = "\nSTOCK DETAILS\nStockName:" + splitStockData[0] + "\nSymbol:"
            + splitStockData[2] + "\nTime:" + splitStockData[3] + "\nPrice: $" + splitStockData[1];
    viewGUI.displayInformationalMessage(data);
  }

  @Override
  public void performanceOfPortfolioMain() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] displayString = getFlexiblePortfolioNames("FLEXIBLE");
    if (displayString != null) {
      viewGUI.displayPerformanceOfPortfolio(displayString);
    }
  }

  /**
   * This helper method helps in retriving the name of the portfolios.
   *
   * @param type type of portfolio
   * @return the names of portfolio
   */
  private String[] getFlexiblePortfolioNames(String type) {
    StockCompositionData obj = new StockCompositionDataImpl(type);
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      return null;
    }
    String[] portfolioNames = obj.getPortFolioNames(type);
    String[] args = new String[portfolioNames.length + 1];
    System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
    args[portfolioNames.length] = type;

    return getPortfolioNames(args, args.length);
  }


  @Override
  public void addStocksUsingDollarMain() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] portfolioNames = obj.getPortFolioNames("FLEXIBLE");
    String[] args = new String[portfolioNames.length + 1];
    System.arraycopy(portfolioNames, 0, args, 0, portfolioNames.length);
    args[portfolioNames.length] = "FLEXIBLE";

    String[] displayString = getPortfolioNames(args, args.length);
    viewGUI.displayAddStocksUsingDollarStrategyMain(displayString);
  }

  @Override
  public void dollarValueScreenOne(int pfIndex, String date, int recurIndex) {
    if (pfIndex == -1) {
      viewGUI.displayErrorMessage("Select a portfolio");
      viewGUI.resetDollarValueMainScreen();
      return;
    }

    if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Invalid date. Reenter.");
      viewGUI.resetDollarValueMainScreen();
      return;
    }

    if (recurIndex == -1) {
      viewGUI.displayErrorMessage("Notify recursion");
      viewGUI.resetDollarValueMainScreen();
      return;
    }

    dvd.startDate = date;
    dvd.pfIndex = pfIndex;
    dvd.recurIndex = recurIndex;

    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    if (!dvd.isCreateDollarValue) {
      super.currentPortfolioName = obj.getPortFolioNames("FLEXIBLE")[pfIndex];
    }

    if (recurIndex == 0) {
      // This means Recurring
      viewGUI.dollarValueOnGoingScreen();
    } else if (recurIndex == 1) {
      StockNameMap snp = new StockNameMapImpl();
      String buyStr = "How many stocks would you like to buy? (1 to " + snp.getMapSize() + ")";
      viewGUI.dollarValueHowManyStocksScreen(buyStr);
    }
  }

  @Override
  public void dollarValueScreenTwo(int onGoingIndex) {
    if (onGoingIndex == -1) {
      viewGUI.displayErrorMessage("Select ongoing");
      return;
    }

    dvd.onGoingIndex = onGoingIndex;
    if (onGoingIndex == 0) {
      // Yes
      String str = "Enter the recurring frequency (1 to 30 days)";
      viewGUI.dollarValueFrequencyEnterScreen(str);
    } else if (onGoingIndex == 1) {
      // No
      // Ask for end date
      String str = "Enter the end date for the strategy (YYYY-MM-DD)"
              + " (from " + dvd.startDate + ")";
      viewGUI.dollarValueEndDateScreen(str);
    }
  }

  @Override
  public void dollarValueScreenThreeEndDate(String endDate) {
    if (!super.validateDateEndDate(endDate, "yyyy-MM-dd")
            || (LocalDate.parse(endDate).compareTo(LocalDate.parse(dvd.startDate)) < 0)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter");
      return;
    }

    dvd.endDate = endDate;
    // Now calculate or ask for frequency
    int remainder = (int) ChronoUnit.DAYS.between(LocalDate.parse(dvd.startDate),
            LocalDate.parse(dvd.endDate));

    dvd.remainder = remainder;

    if (remainder == 0) {
      dvd.frequencyStr = "0";
      StockNameMap snp = new StockNameMapImpl();
      String buyStr = "How many stocks would you like to buy? (1 to " + snp.getMapSize() + ")";
      viewGUI.dollarValueHowManyStocksScreen(buyStr);
    } else {
      if (remainder == 1) {
        dvd.frequencyStr = "1";
        StockNameMap snp = new StockNameMapImpl();
        String buyStr = "How many stocks would you like to buy? (1 to " + snp.getMapSize() + ")";
        viewGUI.dollarValueHowManyStocksScreen(buyStr);
      } else {
        String str = "Enter the recurring frequency (1 to " + remainder + " days)";
        viewGUI.dollarValueFrequencyEnterScreen(str);
      }
    }
  }

  @Override
  public void dollarValueScreenFourFrequency(String frequencyStr) {
    if (!validateStockSelectOption(frequencyStr, 1, dvd.remainder)) {
      viewGUI.displayErrorMessage("Not a valid Frequency. Reenter");
      return;
    }
    dvd.frequencyStr = frequencyStr;
    StockNameMap snp = new StockNameMapImpl();
    String buyStr = "How many stocks would you like to buy? (1 to " + snp.getMapSize() + ")";
    viewGUI.dollarValueHowManyStocksScreen(buyStr);
  }

  @Override
  public void dollarValueScreenFiveHowManyShares(String numberOfStocks) {
    StockNameMap snp = new StockNameMapImpl();
    if (!validateStockSelectOption(numberOfStocks, 1, snp.getMapSize())) {
      viewGUI.displayErrorMessage("Not a valid input. Reenter");
      return;
    }

    dvd.numberOfStocks = Integer.parseInt(numberOfStocks);
    String[] supportedStocks = {
        "1. Microsoft (MSFT)",
        "2. Meta (META)",
        "3. Google (GOOG)",
        "4. Apple (AAPL)",
        "5. Tesla (TSLA)",
        "6. JPMorgan Chase (JPM)",
        "7. Johnson (JNJ)",
        "8. Amazon (AMZN)",
        "9. UnitedHealth (UNH)",
        "10. Walmart (WMT)"
    };
    viewGUI.dollarValueStockProportionScreen(dvd.numberOfStocks, supportedStocks);
  }

  @Override
  public void dollarValueScreenSixBuyStocks(String cost, JSpinner[] spinner,
                                            JComboBox[] comboSupportStocksArray) {
    int number = dvd.numberOfStocks;
    int[] options = new int[number];
    double[] proportion = new double[number];
    String[] proportionStr = new String[number];

    StockNameMap snp = new StockNameMapImpl();
    Map<String, String> data = snp.getMap();
    String[] stockNameIndexArray = new String[snp.getMapSize()];
    String[] stockSymbolIndexArray = new String[snp.getMapSize()];
    int index = 0;
    for (Map.Entry<String, String> entry : data.entrySet()) {
      stockSymbolIndexArray[index] = entry.getKey();
      stockNameIndexArray[index++] = entry.getValue();
    }

    double totalProportion = 100;
    for (int i = 0; i < number; i++) {
      options[i] = comboSupportStocksArray[i].getSelectedIndex();
      proportion[i] = (Double) spinner[i].getValue();
      proportionStr[i] = String.valueOf(proportion[i]);
      if (options[i] == -1) {
        viewGUI.displayErrorMessage("Select all the stocks.");
        return;
      }
      totalProportion -= proportion[i];
    }

    if (totalProportion != 0) {
      viewGUI.displayErrorMessage("The total proportion should make up to 100% percent. Reenter");
      return;
    }

    if (!super.validateCommissionCost(cost)) {
      viewGUI.displayErrorMessage("Investment amount should be greater than 0. Reenter.");
      return;
    }

    String[] dateArr;
    int remainderDays = 0;
    String lastKnownStockDate = dvd.startDate;
    boolean isDatesInFuture = false;

    if (dvd.recurIndex == 0 && !Objects.equals(dvd.frequencyStr, "0")) {
      assert dvd.endDate != null;

      if (dvd.onGoingIndex == 1) {
        remainderDays = (int) ChronoUnit.DAYS.between(LocalDate.parse(dvd.startDate),
                LocalDate.parse(dvd.endDate));
      } else {
        remainderDays = (int) ChronoUnit.DAYS.between(LocalDate.parse(dvd.startDate),
                LocalDate.now());
      }
      assert dvd.frequencyStr != null;
      remainderDays = (remainderDays / Integer.parseInt(dvd.frequencyStr)) + 1;

      dateArr = new String[remainderDays];
      if (remainderDays == 0) {
        dateArr = new String[1];
        dateArr[0] = dvd.startDate;
      } else {
        LocalDate sDate = LocalDate.parse(dvd.startDate);
        dateArr[0] = dvd.startDate;
        for (int k = 1; k < remainderDays; k++) {
          sDate = sDate.plusDays(Long.parseLong(dvd.frequencyStr));
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
      dateArr[0] = dvd.startDate;
    }

    String[] stockSymbolRequired = new String[options.length];
    for (int j = 0; j < options.length; j++) {
      stockSymbolRequired[j] = stockSymbolIndexArray[options[j]];
    }

    viewGUI.displayInformationalMessage("Buying Shares, please wait until success message.");
    // Calculate the number of shares to be bought for the
    super.calculateAndBuySharesBasedOnStrategy(dateArr, cost, proportionStr,
            stockSymbolRequired, number, super.currentPortfolioName);

    // Save ongoing strategy data
    // The final data will be like
    // 2022-11-26, 2000, 3, MSFT, 20, GOOG, 60, WMT, 20)
    if ((dvd.recurIndex == 0 && dvd.onGoingIndex == 0) || isDatesInFuture) {
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
        strategyArgs.append(dvd.endDate).append(",");
      }

      // Frequency to buy stocks
      strategyArgs.append(dvd.frequencyStr).append(",");

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
        super.persistStrategyToFile(String.valueOf(strategyArgs), super.currentPortfolioName);
      } catch (Exception e) {
        viewGUI.displayErrorMessage("Error in persisting the strategy. Try again");
        viewGUI.resetMainMenu();
      }
    }

    viewGUI.displayInformationalMessage("Stock successfully added to the portfolio.");
    viewGUI.resetMainMenu();
  }

  @Override
  public void valueAndCompositionScreenOne(int pfIndex) {
    if (pfIndex == -1) {
      viewGUI.displayErrorMessage("Select a portfolio");
      return;
    }

    ppd.pfIndex = pfIndex;
    String portfolioType = "ALL";
    StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
    String fileName = obj.getPortFolioFileNameByIndex(pfIndex, portfolioType);

    if (obj.isPortfolioOfGivenType(fileName, "INFLEXIBLE")) {
      StockPortFolioDataImpl stkObj =
              (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(pfIndex, false,
                      null, true, false, portfolioType);
      String pfName = obj.getPortFolioNames("ALL")[pfIndex];
      String date = stkObj.createdTimeStamp;
      if (date == null) {
        viewGUI.displayInformationalMessage("The portfolio is empty");
        return;
      }
      date = date.substring(0, 10);

      String[] column = { "Name", "Symbol", "Quantity", "DOP", "Price on DOP" };

      String title = pfName.toUpperCase() + " PORTFOLIO (inflexible)"
              + " COMPOSITION - Created on " + date;

      String[][] data = new String[stkObj.numberOfUniqueStocks][column.length];

      String subText = "DOP - Date Of Purchase";

      for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
        data[i][0] = stkObj.stockName[i];
        data[i][1] = stkObj.stockSymbol[i];
        data[i][2] = String.valueOf(Math.floor(stkObj.stockQuantity[i] * 100) / 100);
        data[i][3] = stkObj.stockLastKnownValueDate[i];
        data[i][4] = String.valueOf(stkObj.valueOfSingleStock[i]);
      }

      viewGUI.valueAndCompScreenInflexibleResult(title, column, data, subText);
      viewGUI.resetMainMenu();
    } else {
      ppd.getType = "TRUE";
      ppd.pfType = "ALL";
      viewGUI.valueAndCompFlexDateScreen();
    }
  }

  @Override
  public void resetMainMenu() {
    super.cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
    viewGUI.resetMainMenu();
  }

  @Override
  public void valueAndCompositionFlexDateScreen(String date) {
    if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter");
      return;
    }

    showPortfolioIndividualWithDate(ppd.pfIndex, date, ppd.getType, ppd.pfType);
  }

  @Override
  public void valueOnFullCompMainScreen() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("ALL");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] displayString = getFlexiblePortfolioNames("ALL");
    if (displayString != null) {
      viewGUI.valueOnFullCompScreenOne(displayString);
    }
  }

  @Override
  public void valueOnFullScreenOne(int pfIndex, String date) {
    if (pfIndex == -1) {
      viewGUI.displayErrorMessage("Select a portfolio");
      return;
    }

    if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter");
      return;
    }

    showPortfolioIndividualWithDate(pfIndex, date, "FULL", "ALL");
  }

  @Override
  public void totalCostInvestedByDateMainMenu() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] listOfPortfolioNames = getFlexiblePortfolioNames("FLEXIBLE");
    viewGUI.totalCostInvestedByDateScreenOne(listOfPortfolioNames);
  }

  @Override
  public void totalCostInvestedScreenOne(int pfIndex, String date) {
    if (pfIndex == -1) {
      viewGUI.displayErrorMessage("Select a portfolio");
      return;
    }

    if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter");
      return;
    }

    showPortfolioIndividualWithDate(pfIndex, date, "COST", "FLEXIBLE");
  }

  /**
   * This method helps in displaying the value of portfolio on certain date.
   *
   * @param portfolioNumber index of portfolio
   * @param date            date chosen by user
   * @param type            type of value
   * @param portfolioType   type of portfolio
   */
  private void showPortfolioIndividualWithDate(int portfolioNumber, String date,
                                               String type, String portfolioType) {

    StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
    StockPortFolioDataImpl stkObj = null;

    if (Objects.equals(type, "FULL")) {
      stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber, true,
              null, true, false, portfolioType);
    } else if (Objects.equals(type, "TRUE")) {
      try {
        stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber,
                true, date, true, true, portfolioType);
      } catch (Exception e) {
        viewGUI.displayErrorMessage("Error in getting the data.");
        return;
      }
    } else if (Objects.equals(type, "COST")) {
      try {
        stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(portfolioNumber,
                false, date, false, false, portfolioType);
      } catch (Exception e) {
        viewGUI.displayErrorMessage("Error in getting the data.");
        return;
      }
    }

    if (stkObj == null) {
      viewGUI.displayErrorMessage("Error in getting the data.");
      return;
    }

    double totalPortFolioValue = 0;

    if (stkObj.numberOfUniqueStocks == 0) {
      if (type.equals("COST")) {
        viewGUI.displayInformationalMessage("There are no investments until " + date);
      } else {
        viewGUI.displayInformationalMessage("The value of portfolio on " + date + " is $0.00");
      }
      return;
    }

    String title;

    String[] portfolioNames = obj.getPortFolioNames(portfolioType);
    if (type.equals("COST")) {
      title = "COST BASIS OF " + portfolioNames[portfolioNumber].toUpperCase()
              + " PORTFOLIO";
    } else {
      title = "Value of " + portfolioNames[portfolioNumber].toUpperCase() + " PORTFOLIO";
    }

    String[] column = new String[5];

    if (!type.equals("COST")) {
      column = new String[]{"Name", "Symbol", "Quantity", "Share Value", "Total Value"};
    }

    String[][] data = new String[stkObj.numberOfUniqueStocks][column.length];
    String footer;
    String[] costData = new String[2];

    for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
      if (stkObj.stockQuantity[i] == 0) {
        continue;
      }
      if (!type.equals("COST")) {
        data[i][0] = stkObj.stockName[i];
        data[i][1] = stkObj.stockSymbol[i];
        data[i][2] = String.valueOf(Math.floor(stkObj.stockQuantity[i] * 100) / 100);
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
        data[i][3] = "$" + shareValue;
        data[i][4] = "$" + Math.floor((shareValue * stkObj.stockQuantity[i]) * 100) / 100;
      }
      totalPortFolioValue += Math.floor((shareValue * stkObj.stockQuantity[i]) * 100) / 100;
    }

    totalPortFolioValue = Math.floor(totalPortFolioValue * 100) / 100;

    if (type.equals("COST")) {
      costData[0] = "Total Money invested: $" + totalPortFolioValue;
      costData[1] = "Total number of transactions till date is: " + stkObj.numberOfTransactions;
      viewGUI.displayValueCompForCost(title, costData);
    } else {
      footer = "Total Portfolio Value is on " + date + ": $" + totalPortFolioValue;
      viewGUI.displayValueCompForOthers(title, column, data, footer);
    }
    viewGUI.resetMainMenu();
  }

  /**
   * This method helps in getting the value of a share on a certain date.
   *
   * @param stockSymbol symbol of the stock
   * @param date        date selected by user
   * @return the value of stock
   */
  private double getShareValueOnDate(String stockSymbol, String date) {
    GetStockData gsd = new GetStockDataImpl();
    try {
      String[] dateArr = new String[1];
      dateArr[0] = date;
      gsd.getValue(stockSymbol, dateArr);
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Error in getting stock data on " + date);
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

  @Override
  public void performancePortfolioMainMenu(int pfIndex, int timestampType) {
    if (pfIndex == -1) {
      viewGUI.displayErrorMessage("Select a portfolio");
    }
    if (timestampType == -1) {
      viewGUI.displayErrorMessage("Select proper timestamp type");
    }
    ppd.pfIndexStr = String.valueOf((pfIndex + 1));
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    super.currentPortfolioName = obj.getPortFolioNames("FLEXIBLE")[pfIndex];
    viewGUI.performanceDateEnter(timestampType);
  }

  /**
   * This method helps in form the display string for GUI for the names of portfolio.
   *
   * @param portfolioNames    name of portfolio
   * @param numberOfPortFolio number of portfolio
   * @return the formated name
   */
  private String[] getPortfolioNames(String[] portfolioNames, int numberOfPortFolio) {
    String[] result = new String[numberOfPortFolio - 1];
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
        result[i] = portfolioNames[i].toUpperCase() + " (" + pfType + ")";
      } else {
        result[i] = portfolioNames[i].toUpperCase();
      }
    }
    return result;
  }


  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void performancePortfolioSubmit(String date, int timestampType) {
    String format = null;
    int remainder = 0;
    String timestampTypeStr = "";

    if (timestampType == 0) {
      format = "yyyy";
      timestampTypeStr = "1";
    } else if (timestampType == 1) {
      format = "yyyy-MM";
      timestampTypeStr = "2";
    } else if (timestampType == 2) {
      format = "yyyy-MM-dd";
      timestampTypeStr = "3";
    }

    if (!super.validateDate(date, format, 4)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter.");
    } else {
      String str = "";
      if (timestampType == 0) {
        String tempDateStr = date + "-01-01";
        remainder = (int) ChronoUnit.YEARS.between(LocalDate.parse(tempDateStr),
                LocalDate.now().withDayOfMonth(1));
      } else if (timestampType == 1) {
        String tempDateStr = date + "-01";
        remainder = (int) ChronoUnit.MONTHS.between(LocalDate.parse(tempDateStr),
                LocalDate.now().withDayOfMonth(1));
      } else if (timestampType == 2) {
        remainder = (int) ChronoUnit.DAYS.between(LocalDate.parse(date), LocalDate.now());
      }

      ppd.date = date;
      ppd.timestampType = timestampType;
      ppd.timestampTypeStr = timestampTypeStr;
      ppd.format = format;

      if (remainder != 4) {
        remainder += 1;
        if (remainder > 30) {
          remainder = 30;
        }

        if (timestampType == 0) {
          str = "Enter the number of years (5 to " + remainder + ")";
        } else if (timestampType == 1) {
          str = "Enter the number of months (5 to " + remainder + ")";
        } else if (timestampType == 2) {
          str = "Enter the number of days (5 to " + remainder + ")";
        }

        ppd.remainder = remainder;
        // Ask user for input
        viewGUI.displayPerformanceNumberOfDaysInput(str);
      } else {
        ppd.number = "5";
        // Display graph
        drawGraph();
        viewGUI.resetMainMenu();
      }
    }
  }

  @Override
  public void performanceOfPortfolioGetData(String num) {
    if (!super.validateStockSelectOption(num, 5, ppd.remainder)) {
      viewGUI.displayErrorMessage("Not a valid number. Re-enter");
      return;
    }
    ppd.number = num;
    drawGraph();
    viewGUI.resetMainMenu();
  }

  /**
   * This method helps in drawing the graph for the performance of the portfolio.
   */
  private void drawGraph() {
    String[] dates;

    try {
      dates = super.getDatesFromUserInput(ppd.timestampTypeStr, ppd.date, ppd.number, ppd.format);
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Error in parsing the dates. Try again");
      return;
    }

    dates[Integer.parseInt(ppd.number)] = super.currentPortfolioName;
    dates[Integer.parseInt(ppd.number) + 1] = ppd.pfIndexStr;
    dates[Integer.parseInt(ppd.number) + 2] = ppd.timestampTypeStr;
    cmiObj.controllerModelInteract(TypeofAction.GET_PORTFOLIO_PERFORMANCE, dates, dates.length);

    // Get the performance data
    Map<String, Double> pfPerformance;
    String portfolioType = "FLEXIBLE";
    try {
      StockCompositionData obj = new StockCompositionDataImpl(portfolioType);
      pfPerformance = obj.computePerformanceData(dates, dates.length, portfolioType);
      if (pfPerformance == null) {
        viewGUI.displayErrorMessage("Error in getting portfolio performance. Try again");
        return;
      }
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Error in getting portfolio performance. Try again");
      return;
    }

    boolean isAllNull = true;
    for (Map.Entry<String, Double> set : pfPerformance.entrySet()) {
      if (set.getValue() != null) {
        isAllNull = false;
        break;
      }
    }

    if (isAllNull) {
      viewGUI.displayInformationalMessage("You dont have any stocks on these dates");
      resetMainMenu();
      return;
    }

    viewGUI.getGraph(pfPerformance, super.currentPortfolioName.toUpperCase());
    //vciObj.portfolioPerformanceOverTime(dates, dates.length, pfPerformance, scale, getTitle);
  }

  @Override
  public void buyStockSubmit(String date, int stockSelected, String noOfStocks,
                             String portfolioName) {
    // invalid date
    if (!super.validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Invalid Date. Reenter");
      return;
    }

    // empty no of stocks
    if (!validateBuyStockOption(noOfStocks)) {
      viewGUI.displayErrorMessage("Number of shares invalid. "
              + "Please enter a valid natural number");
      return;
    }

    StockNameMap snp = new StockNameMapImpl();
    Map<String, String> map = snp.getMap();
    String[] stockSymbolIndexArray = new String[snp.getMapSize()];
    String[] stock = new String[2];
    int index = 0;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      stockSymbolIndexArray[index++] = entry.getKey();
    }
    stock[0] = stockSymbolIndexArray[stockSelected];
    stock[1] = date;
    super.cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, stock, 2);

    stock[0] = noOfStocks;
    stock[1] = super.currentPortfolioName;
    super.cmiObj.controllerModelInteract(TypeofAction.BUY_STOCKS, stock, 2);
    String showSuccessfulMsg;
    if (tvd.isFirstBuy) {
      showSuccessfulMsg = super.currentPortfolioName.toUpperCase() + " portfolio created.";
    } else {
      showSuccessfulMsg = "";
    }

    viewGUI.displayBoughtSuccessfulAndWouldLikeToBuyAgainButtonWindow(showSuccessfulMsg);
    super.cmiObj.controllerModelInteract(TypeofAction.DELETE_EMPTY_PORTFOLIO, null, 0);
  }

  @Override
  public void buyAnotherSubmitButton() {
    tvd.isFirstBuy = false;
    String[] supportedStocks = {
        "1. Microsoft (MSFT)",
        "2. Meta (META)",
        "3. Google (GOOG)",
        "4. Apple (AAPL)",
        "5. Tesla (TSLA)",
        "6. JPMorgan Chase (JPM)",
        "7. Johnson (JNJ)",
        "8. Amazon (AMZN)",
        "9. UnitedHealth (UNH)",
        "10. Walmart (WMT)"
    };
    viewGUI.flexiblePortfolioScreenWithDateInput(0, supportedStocks, super.currentPortfolioName);
  }

  @Override
  public void checkHowManyShares() {
    viewGUI.displayErrorMessage("Given input is not digit. Please enter a digit.");
    viewGUI.resetHowManyShares();
  }

  @Override
  public void selectPortfolio() {
    try {
      super.readStrategyAndImplement();
    } catch (Exception e) {
      // Do nothing
    }
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    int numberOfPortFolio = obj.getNumberOfPortFolio();
    if (numberOfPortFolio == 0) {
      viewGUI.displayInformationalMessage("You dont have any portfolio");
      viewGUI.resetMainMenu();
      return;
    }

    String[] listOfPortfolioNames = getFlexiblePortfolioNames("FLEXIBLE");
    viewGUI.displayListOfPortfolioScreen(listOfPortfolioNames);
  }

  @Override
  public void sellStock(String date, int stockSelected, String noOfStocks, int pfNumber) {
    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    StockPortFolioDataImpl stkObj;
    try {
      stkObj = (StockPortFolioDataImpl)
              obj.getAllStockDataInPortFolio(pfNumber,
                      true, date, true, true, "FLEXIBLE");
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Error in getting stock data " + e.getMessage());
      return;
    }

    if (stkObj.numberOfUniqueStocks == 0) {
      return;
    }

    sellSharesOnAStock(pfNumber, stkObj.stockSymbol[stockSelected], date, noOfStocks);
  }


  /**
   * This method helps in selling the stocks on certain date selected by the portfolio.
   *
   * @param pfNumber    portfolio selected by the user
   * @param stockSymbol stock symbol selected by the user
   * @param date        date selected by the user
   * @param sellShares  shares to be sold selected by the user
   */
  private void sellSharesOnAStock(int pfNumber, String stockSymbol, String date,
                                  String sellShares) {

    StockCompositionData stk = new StockCompositionDataImpl("FLEXIBLE");
    super.currentPortfolioName = stk.getPortFolioNames("FLEXIBLE")[pfNumber];
    double numberOfAvailableShares;

    try {
      numberOfAvailableShares = stk.sharesAvailableOnTheDateForSale(pfNumber, stockSymbol, date,
              "FLEXIBLE");
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Unable to get remaining share details on the "
              + "stock for this date.");
      return;
    }

    if (numberOfAvailableShares == 0) {
      viewGUI.displayErrorMessage("You cannot sell any shares of this stock on this "
              + "date as they are already"
              + " sold.");
      return;
    }

    String[] stockGetData = new String[2];
    stockGetData[0] = stockSymbol;
    stockGetData[1] = date;
    super.cmiObj.controllerModelInteract(TypeofAction.GET_STOCK_DATA, stockGetData, 2);

    if ((sellShares == null || sellShares.length() == 0)
            || (!validateSellStockInput(sellShares, numberOfAvailableShares))) {
      String[] args = new String[1];
      args[0] = String.valueOf(numberOfAvailableShares);

      viewGUI.displayErrorMessage("Not a valid input. You can only sell until "
              + args[0] + " shares.");
      return;
    }

    String[] data = new String[3];
    data[0] = sellShares;
    data[1] = super.currentPortfolioName;
    super.cmiObj.controllerModelInteract(TypeofAction.SELL_STOCKS, data, 3);
    viewGUI.displayInformationalMessage("Shares successfully sold");
    viewGUI.resetStockSellScreenAfterSell();
    viewGUI.resetMainMenu();
  }

  // list of stocks as per date will be made available to user in a dropdown menu
  @Override
  public void getStocksAvailableForSaleAsPerDate(String date, int pfIndex) {
    if (!validateDate(date, "yyyy-MM-dd", 0)) {
      viewGUI.displayErrorMessage("Not a valid date. Reenter");
      return;
    }

    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    StockPortFolioDataImpl stkObj;

    try {
      stkObj = (StockPortFolioDataImpl) obj.getAllStockDataInPortFolio(pfIndex, true,
              date, true, true, "FLEXIBLE");
    } catch (Exception e) {
      viewGUI.displayErrorMessage("Error in getting stock data " + e.getMessage());
      return;
    }

    if (stkObj == null) {
      viewGUI.displayErrorMessage("Error in getting stock data");
      return;
    }
    String[] result = new String[stkObj.numberOfUniqueStocks];
    if (stkObj.numberOfUniqueStocks == 0) {
      viewGUI.displayInformationalMessage("You don't own any stocks before on or before date.");
      return;
    } else {
      for (int i = 0; i < stkObj.numberOfUniqueStocks; i++) {
        if (stkObj.stockQuantity[i] != 0) {
          result[i] = stkObj.stockName[i];
        }
      }
    }
    tvd.listOfAvailableStocksForSale = result;
    viewGUI.displaySellScreen2(tvd.listOfAvailableStocksForSale);
  }

  @Override
  public void selectStockSubmit(int buyOrSell, int portfolioNameIndex) {
    if (portfolioNameIndex == -1) {
      viewGUI.displayErrorMessage("Please select a portfolio");
      return;
    }

    StockCompositionData obj = new StockCompositionDataImpl("FLEXIBLE");
    super.currentPortfolioName = obj.getPortFolioNames("FLEXIBLE")[portfolioNameIndex];
    String[] supportedStocks = {
        "1. Microsoft (MSFT)",
        "2. Meta (META)",
        "3. Google (GOOG)",
        "4. Apple (AAPL)",
        "5. Tesla (TSLA)",
        "6. JPMorgan Chase (JPM)",
        "7. Johnson (JNJ)",
        "8. Amazon (AMZN)",
        "9. UnitedHealth (UNH)",
        "10. Walmart (WMT)"
    };

    if (buyOrSell == 3) {
      // buy
      viewGUI.flexiblePortfolioScreenWithDateInput(0, supportedStocks,
              super.currentPortfolioName);
    }
    else if (buyOrSell == 4) {
      // sell
      viewGUI.displaySellScreen();
    }
  }
}
