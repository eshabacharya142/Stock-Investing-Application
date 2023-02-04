SETUP FILE

Please run the jar file as an administrator (possibly in a windows machine).
The system on which the application is running should have internet connection.

NOTE: 
All the inputs to the application are in numbers unless specified.
If there are no 'more options' specified on a particular screen just press enter for more options.
The application will not create an empty portfolio.

EXTERNAL DEPENDENCY
1. An open source library, free to use if we mention author name, called JDatePicker to pick dates.
   The author credit is mentioned in the file called LICENSE under 'dependencies/JDatePicker/LICENSE.txt'
   https://github.com/JDatePicker/JDatePicker
   https://github.com/JDatePicker/JDatePicker/blob/master/LICENSE.md
   https://jdatepicker.org/
   
2. An open source library, free to use with no conditions, called JFreeChart to draw graphs. 
   https://www.jfree.org/jfreechart/
   https://www.gnu.org/philosophy/free-sw.html
   
   
HOW TO INCLUDE DEPENDENCY IN IntelliJ
1. Unzip the dependencies zip file and copy paste (in some desirable location) the JAR files 
   present (three in total) under JDatePicker folder and JFreeChart folder present under that dependencies zip file.
2. Open the project in IntelliJ
3. Select File->Project Structure->Modules
4. In the Modules (inner screen), click '+' icon, then click JAR, choose the three JAR files that are mentioned above.
 


STEPS TO CREATE A PORTFOLIO WITH 3 DIFFERENT STOCKS STOCKS

1. In the Main Menu, enter '1' to create a portfolio.
2. You will be prompted to enter the type of portfolio you would like to create. Choose a type.
3. You will be prompted to enter the name for the portfolio. If the name already exists, the application will ask you if you want to override the already existing portfolio.
4. Once the portfolio is created, press '1' to buy stocks. If you don't buy any stocks and return to main menu or exit the application, the empty portfolio will be deleted.
5. You will be provided with a list of supported stocks. Choose one by inputting the number of that stock.
6. If you had choosen a flexible portfolio option, the application will ask you the date on which you would like to buy the
   stocks for. If you had choosen inflexible portfolio, then this option will be skipped.
7. The application now will fetch the data of the stock like current price, the date/time at which the value is last known of. 
   You can only buy shares only from that last known value.
   Now enter the number of shares you would like to buy (can only buy from 1 to 2147483647 shares).
   Application will not allow you to buy 0 shares or shares that are more than 2147483647.
8. Once the shares are bought, the application will ask you if you want to buy more shares. If you press 'Y', then the process repeats from step 5. 
   If you press 'N', then the application will show the portfolio is created message and take you to the Main Menu.
   
   Press 'Y' and repeat from step 5 two more times to buy shares for different stocks.
   Once 3 different stocks are bought, press 'N' to exit to the Menu Menu and the portfolio will be created.


STEPS TO CREATE A PORTFOLIO WITH 2 DIFFERENT STOCKS STOCKS
The steps are the same as mentioned above but buy two different stocks.


STEPS TO CREATE A PORTFOLIO WITH DOLLAR VALUE STRATEGY (GUI)
1. In the Main Menu, select create portfolio.
2. Select the portfolio type as 'Flexible', enter the name for the portfolio and in the select options, choose, 
   'Invest by dollar value'.
3. In the next screen, choose a date and choose whether it is recurring or not.
4. If it is non recurring, the enter the number of stocks you want to buy. Then select the stock name and proportion for each
   stock (total proportion should be 100) and the amount you want to invest. Click next/buy, stocks will be bought.
5. If it is recurring, choose whether the strategy is on going or not.
6. If it is ongoing, choose the stocks and proportions, amount to invest and click next/buy, stocks will be bought.
   if it is not ongoing, choose the end date, number of stocks you want to buy.
   In the next screen, choose stocks and proportions, the amount you want to invest and click next/buy. Stocks will be bought.

COMPOSITON OF PORTFOLIO
1. In the Main Menu, press '2' to check the contents of any portfolio that was created.
2. The application will display the list of available portfolio's and will ask you for the input for which you would like to
   check the contents of. Input the number accordingly.
3. Then the application will show all the stocks in that portfolio along with the number of shares, it's price at the time
   of purchase and the total value of the portfolio at the time of purchase.
   

VALUE OF PORTFOLIO ON A CERTAIN DATE
1. In the Main Menu, press '2' to check the value of the portfolio on a certain date.
2. Once pressed, the application will display the list of available portfolio's and will ask you for the input for which you would like to
   check the contents of. Input the number accordingly. Also choose the flexible portfolio.
3. Then the application will ask you the date for which you would like to know the value of. The supported date ranges are
   (from Jan 1st 2000 to the current date). Enter the date in the format YYYY-MM-DD as mentioned in the screen.
4. The application will show the details of the stocks and it's shares, it's price and the total value of the portfolio on the
   date mentioned by the user.
   
   
COST BASIS OF PORTFOLIO ON A CERTAIN DATE
1. In the Main Menu, press '7' to check the cost basis of the portfolio on a certain date.
2. Once pressed, the application will display the list of available flexible portfolio's and 
   will ask you for the input for which you would like to check the cost basis of. Input the number accordingly.
3. Then the application will ask you the date for which you would like to know the value of. The supported date ranges are
   (from Jan 1st 2000 to the current date). Enter the date in the format YYYY-MM-DD as mentioned in the screen.
4. The application will show the details of the money invested alone with the commission charges.
   

STOCKS SUPPORTED BY THE APPLICATION

1.  Apple 			(AAPL)
2.  Amazon 			(AMZN)
3.  Microsoft 		(MSFT)
4.  Tesla 			(TSLA)
5.  Meta 			(META)
6.  Walmart 		(WMT)
7.  Johnson 		(JNJ)
8.  JPMorgan Chase 	(JPM)
9.  Google 			(GOOG)
10. UnitedHealth 	(UNH)

The last known value of these stocks are detemined by the API call and will be displayed in the screen for the user.
The use can buy or view the stock value only from Jan 01 2000 to till date.