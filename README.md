# restaurant-billing-system
A full fledged command line interface based restaurant billing system

## Introduction
- This program be used in restaurants to calculate the bill of every table. Since I have used ArrayLists, the number of tables can be 10, 10000 or even more. 
- Every table is an object of class Table stored in an ArrayList of objects. The menu items can be initialized by running RestaurantMenuSetup.java (which can be changed as per the restaurants needs) and the billing application can be run from the root directory.
- The generated bills are stored in a local MongoDB database (in the bills collection of restaurantDB database) to maintain a record of all past bills.
- Menu items are also stored in the MongoDB database and can be modified by either modifying the database directly or by modifying and rerunning RestaurantMenuSetup.java

## Setup
1. Ensure that you have Java 18, Maven and MongoDB installed in your system. (If you are using an IDE like IntelliJ then a local installation of Maven is not required)

2. Clone the repository using 

   ```
   git clone https://github.com/pabloescoder/restaurant-billing-system.git
   ```

3. Navigate to the root folder of the project

   ```
   cd restaurant-billing-system
   ```

4. Build the project using Maven (Note: If you are using an IDE like IntelliJ, you can skip this step)

   ```
   mvn install
   ```

5. Start the local MongoDB server at port 27017

6. Navigate to **src/main/java** and run **RestaurantMenuSetup.java** to initialize the menu and save it to the local DB. You can also modify this file to add or remove menu items as per your requirement and re run the file to re-initialize the menu.

7. Navigate to **src/main/java** and run **RestaurantBillingSystem.java** to start the billing system. All generated bills are stored in the restaurantDB database in the bills collection of the local MongoDB database.

## Concepts used
* Dynamic ArrayLists
* Classes & Objects
* Static Methods
* Exception Handling
* ArrayList of Objects
* MongoDB and Java connection
* String Formatting & more
