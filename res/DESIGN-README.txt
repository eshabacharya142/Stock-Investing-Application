DESIGN DETAILS

This application is designed in a Model View Controller design pattern.
The model, view and the controller are in three different packages with their corresponding same name.
Also, there are two controllers, one that interacts with the model (will call it by CM) 
and another one that interacts with the view (will call it by CV).
This way, it is easy to maintain the code for the controller.

The overall connection between model, view and controller will look like 
MODEL <-- CM <-- CV --> VIEW.

The main function sits seperate in a Main class outside of all these packages and starts the program by creating
an object for the CV and passing the input and output streams and then by calling the start function of the CV.

Any input to the application will go to the CV, the CV will determine what to based on the input.
If the input only needs to render some data to the user, it will tell the view to display such view.
If the input needs to perform some operations, then CV will inform the model via CM which in-turn will go to the model.

The application makes use of APIs to fetch the real time data of the stock when the user tries to buy the stocks.
The application also uses real-time data to get the value of the stocks/shares when the user wants to view the 
value of the portfolio on a certain date.

All the data that are stored are in CSV format. The stock related data are stored in a file called 'StockData.csv' under
the 'data' directory. The supported stocks are stored in a file called 'SupportedStocks.csv' under data directory.

The portfolio details are stocked in a directory called 'userdata/user1'. 
The naming convention of the portfolio files is that the filename has the prefix 'pf_' followed by the name of the portfolio.
The portfolio details are also save as CSV.

The new design changes in this assignment is the ability to create a portfolio or add stocks using dollar value stragety.
Since the dollar value stragety indirectly allows users to buy shares as fractional values, the users can also
sell stocks using fractional values. The detailed description is given below.
Also, since commission cost is optional in this assignment, the GUI does not have commission cost.

GUI supportablity is added in this assignment. To support the GUI, a new controller has been added without editing
the old controller. The Model interface has not been modified as the model is capable enough to accomodate the new features.

Based on the feedback from the previous assignment, we moved the IO operations to the controller. 
Also, declaration of unnecessary variable for the class has been avoided and removed. The feedback for selecting date
has been integrated in the GUI.

DETAILED FEATURE DESCRIPTION

1. CREATE A PORTFOLIO

As the program starts, the application will display the main screen. Once the user clicks the create portfolio option, the
controller will ask the user what type of portfolio does the user wants to create. Later, the controller will inform 
the view to display the option for user to enter the name. If the name is already taken by a portfolio, then the controller 
will inform the view to display the override option. Once the user enters a valid name, an empty portfolio will be created.
The user will then be given option to whether buy shares normally or buy using Dollar-Value strategy

When the user chooses the buy shares normally, then user will be asked for the stock which he/she wants to buy, then the CV 
will pass the action to the CM, which will instruct the model to perform the API call. The model will fetch the data of that 
stock using the API and will store the result in the StockData.csv file. At any point in time, this file will have information 
about only one stock which was last queried or which is being queried right now. The CV will now inform the view to display this
data to the user. The user can enter the number of shares he/she would like to buy.As the user buys the share, the CV will 
instruct model to keep updating the portfolio of the user. Once the user is done buying shares, the CV will instruct the view 
to display the success message.

When the user chooses to buy shares using Dollar-Value strategy, the user will then be asked for a date and whether the strategy
is recurring or not. If is it not recurring, then the user will be asked how many stocks would they like to buy and the proportion
for all those stocks. The proportions are in percent and they should add upto 100%. Once the user enters right values and click submit,
CV will send command to model which will mark entry for buying the stocks. If the stragety is recurring, the user will be prompted
for whether if is it an ongoing stragety or not, stocks and proportions. If it is an ongoing stragety, the stragety is persisted 
with the details of the stragety like stocks and proportions and the last known date on which stocks where bought. With this help, 
we will be able to determine and buy stocks on the future dates as well. But the model will not hold stock entry for the future. 
As we go into future dates, the stocks will be bought and stored in the portfolio. If it is not an ongoing stragety, the user will be 
asked for end date, and the details of stocks and proportions, and stocks will be bought accordingly. The start date cannot be a future date.

The dollar-value stragety is not applicable for inflexible portfolios.

If the user goes back to main menu or exit the program without buying any shares, then the empty portfolio that was created
will be deleted by the model by the instruction of CV.


2. VIEW/COMPOSITION OF THE PORTFOLIO

User can view the value and composition of portfolio of both flexible and inflexible type.
When the user buys a stock, model stock more than stock data to the portfolio. The portfolio file will have the following details.
- portfolio name
- unique ID for the portfolio
- type of portfolio (flexible or inflexible)
- type of transaction (BUY or SELL)
- stock purchase timestamp
- purchase ID
- timestamp of the last known value of the stock (the timestamp of the stock data that was shown to the user while buying)
- stock ticker symbol
- name of the stock
- number of shares purchased
- price of the share at the time of purchase

With all these data stored, it is really easy for the controller to fetch the data while showing the details of the portfolio.
In the main menu, if the user clicks the 'View Portfolio' option, the list of all the available portfolio's will be displayed
to the user. When the user chooses a particular portfolio, a screen as mentioned below will be displayed. The view basically shows
all the data about that portfolio and the price details at the time of purchase.

If the user choose a inflexible portfolio, the date will not be asked.
Here is an example for inflexible portfolio.

NAME PORTFOLIO COMPOSITION - Created on 2022-11-02

Name (Symbol) 	 Quantity 	 Date of Purchase(DOP)	Price of a share on DOP

Walmart (WMT) 	 2	 2022-11-14	 $141.63
UnitedHealth (UNH) 	 5	 2012-10-10	 $57.42
UnitedHealth (UNH) 	 3	 2013-10-10	 $63.42

If the user choose flexible portfolio, then date will be asked from user for real-time.
Here is an example for flexible portfolio.

Value of FLEX PORTFOLIO

Name (Symbol) 	 Quantity	 Share Value on 2022-11-11	 Total Value

Microsoft (MSFT) 	 1	 $242.99	 $242.99
Walmart (WMT) 	 2	 $142.66	 $285.32

Total Portfolio Value is on 2022-11-11: $528.3



3. VALUE OF PORTFOLIO ON CERTAIN DATE ON FULL COMPOSITION

When the user chooses this option in the main menu, the list of portfolio's will be displayed from which user can choose one.
When the user chooses one, the user will be prompted to enter the date for which he/she wants to view the value. The supported
dates by the application is from Jan 1st 2000 till the current date. Any dates before the year 2000 or future dates will be
considered invalid. The user can choose both flexible and inflexible portfolio to check the values.
The application uses data time with format validator for this. The application does a API call to fetch the stock data
and display's it to the user.

4. ADD STOCKS TO PORTFOLIO

This option is available only for the flexible portfolio. It enables the users to add stocks to the portfolio on certain dates.
This flow follows the same flow when user tries to add a stock while creating the portfolio. When user buys a stock, 
the entry is added to the portfolio as type 'BUY'.

5. SELL STOCKS FROM PORTFOLIO

This option is available only for the flexible portfolio. User can sell stocks from the portfolio on a certain date.
The application has the intelligence to determine how many shares of a stock the user can sell on a certain date.
The application determines all the shares of the stock that are sold and bought in future to determine how many stocks 
can be sold on the date input by the user. When user sells a stock, the entry is added to the portfolio as type 'SELL'.

6. PERFORMANCE OF PORTFOLIO ON A DATE

This option is available only for the flexible portfolio. User can see the performance of the portfolio real-time 
as a graph. The application determines how many shares the user has on the given date and does API calls to determine 
the value of the portfolio on those dates. The application allows users to view performance by Years, Months and Days with
minimum of 5 and maximum of 30 entries. The scale for the graph is determined by the application based on the range of values
of the portfolio. If the range is huge, the application will give a base value and scale value number of times above the base
value. If the range is small, the application will follow absolute scale.

7. TOTAL AMOUNT INVESTED IN PORTFOLIO ON CERTAIN DATE

This option is available only for the flexible portfolio. This option allows users to determine how much money is invested in
a portfolio on a certain date real-time along with the commission charges. The application charges $1.27 dollars as fee for 
every transaction. The cost will be displayed alone with these commission charges.

8. CONFIGURE THE COMMISSION COST

This option allows user to configure the commission cost per transaction for all the portfolios in common. The commission
cost value should be greater than 0. By default, the commission cost will be $1.27 per transaction. As user configures, the
commission cost is stored in the model as well as to a support file. This data can be retrived for calculating the cost
basis of the portfolio.

9. ADD STOCKS USING DOLLAR VALUE STRATEGY

This option is available only for the flexible portfolio. It enables the users to add stocks using dollar-value stragety
to the portfolio on certain dates. This flow follows the same flow when user tries to add a stock using dollar value while 
creating the portfolio.