import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

class Table {
    int tableNo;

    Table(int tableNo) {
        this.tableNo = tableNo;
    }

    String customerName, date, time;
    ArrayList<String> itemsOrdered = new ArrayList<>(5);
    ArrayList<Integer> quantityOfItemsOrdered = new ArrayList<>(5);
    ArrayList<Double> costList = new ArrayList<>(5);

    void addOrderedItem(String itemName, String costStr) {
        if (!itemsOrdered.contains(itemName)) {
            itemsOrdered.add(itemName);
            quantityOfItemsOrdered.add(1);
            double cost = Double.parseDouble(costStr);
            costList.add(cost);
        } else {
            int itemIndex = itemsOrdered.indexOf(itemName);
            quantityOfItemsOrdered.set(itemIndex, (quantityOfItemsOrdered.get(itemIndex) + 1));
            double cost = Double.parseDouble(costStr);
            costList.set(itemIndex, (costList.get(itemIndex) + cost));
        }
    }

    void displayBill() {
        System.out.println();
        System.out.println("**********************************************************");
        System.out.print("Displaying Bill for Table " + tableNo + System.lineSeparator() + System.lineSeparator());
        System.out.println("Date: " + date + "\t\t\t\t\tTime: " + time);
        System.out.println("Customer name: " + customerName + System.lineSeparator());
        double totalCostNoTax = 0;
        String format = "%-40s %-4d Rs. %-8.2f" + System.lineSeparator();
        System.out.format("%-39s %-7s %-8s%n", "Item Name", "Qty", "Amount");
        System.out.println("--------------------------------------------------------" + System.lineSeparator());

        ArrayList<Map<String, Object>> billItemsArr = new ArrayList<>();

        for (int i = 0; i < itemsOrdered.size(); i++) {
            String itemName = itemsOrdered.get(i);
            int quantity = quantityOfItemsOrdered.get(i);
            double totalItemCost = costList.get(i);
            totalCostNoTax += totalItemCost;
            Map<String, Object> billItem= new HashMap<>();
            billItem.put("itemName", itemName);
            billItem.put("quantity", quantity);
            billItem.put("amount", totalItemCost);
            billItemsArr.add(billItem);
            System.out.format(format, itemName, quantity, totalItemCost);
        }
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.format("%-45s Rs. %-8.2f%n", "Total Cost (Without Tax)", totalCostNoTax);
        double tax = totalCostNoTax * 0.1;
        System.out.format("%-45s Rs. %-8.2f%n%n", "Tax (5% SGST + 5% CGST)", tax);
        System.out.format("%-45s Rs. %-8.2f%n", "Grand Total", totalCostNoTax + tax);
        System.out.println("--------------------------------------------------------" + System.lineSeparator());
        System.out.println("Thank you for dining with us, hope to see you again soon!" + System.lineSeparator());
        System.out.println("**********************************************************");

        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
       // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "restaurantDB", "password".toCharArray());
        // Accessing the database
        MongoDatabase database = mongo.getDatabase("restaurantDB");
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("bills");
        Document customerBill = new Document("date", date)
                .append("time", time)
                .append("customerName", customerName)
                .append("tableNumber", tableNo)
                .append("billItems", billItemsArr)
                .append("totalWithoutTax", totalCostNoTax)
                .append("tax", tax)
                .append("grandTotal", totalCostNoTax + tax);
        collection.insertOne(customerBill);
    }
}

public class RestaurantBillingSystem {

    static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    static void displayCategoryItems(ArrayList<String[]> categoryMenuItems, String collectionName, String format) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);
        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "restaurantDB", "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("restaurantDB");
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection(collectionName);
        FindIterable<Document> iterator = collection.find();

        int srNo = 0;
        for (Document document : iterator) {
            srNo++;
            String dishName = (String) document.get("name");
            double cost = (double) document.getInteger("cost");
            String[] parts = new String[3];
            parts[0] = Integer.toString(srNo);
            parts[1] = dishName;
            parts[2] = Double.toString(cost);
            categoryMenuItems.add(parts);
            System.out.format(format, srNo, dishName, cost);
        }
    }

    static ArrayList<String[]> displayCategoryMenu(char choice) {
        ArrayList<String[]> categoryMenuItems = new ArrayList<>(10);
        if (choice == '2') {
            String collectionName = "starters";
            String format = "%4d) %-25s - Rs. %-8.2f%n";
            System.out.println();
            System.out.println(centerString(46, "Displaying Starters Menu") + System.lineSeparator());
            displayCategoryItems(categoryMenuItems, collectionName, format);
        } else if (choice == '1') {
            String collectionName = "soups";
            String format = "%4d) %-25s - Rs. %-8.2f%n";
            System.out.println();
            System.out.println(centerString(46, "Displaying Soup Menu") + System.lineSeparator());
            displayCategoryItems(categoryMenuItems, collectionName, format);
        } else if (choice == '3') {
            String collectionName = "maincourse";
            String format = "%4d) %-35s - Rs. %-8.2f%n";
            System.out.println();
            System.out.println(centerString(56, "Displaying Main Course Menu") + System.lineSeparator());
            displayCategoryItems(categoryMenuItems, collectionName, format);
        } else if (choice == '4') {
            String collectionName = "desserts";
            String format = "%4d) %-20s - Rs. %-8.2f%n";
            System.out.println();
            System.out.println(centerString(41, "Displaying Desserts Menu") + System.lineSeparator());
            displayCategoryItems(categoryMenuItems, collectionName, format);
        } else if (choice == '5') {
            String collectionName = "drinks";
            String format = "%4d) %-20s - Rs. %-8.2f%n";
            System.out.println();
            System.out.println(centerString(41, "Displaying Drinks & Beverages") + System.lineSeparator());
            displayCategoryItems(categoryMenuItems, collectionName, format);
        }
        return categoryMenuItems;
    }

    static char displayMainMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Which category would you like to display?");
        System.out.println("1) Soups");
        System.out.println("2) Starters");
        System.out.println("3) Main Course");
        System.out.println("4) Desserts");
        System.out.println("5) Drinks & Beverages");
        System.out.print("Enter choice (1-5): ");
        char choice;
        boolean isFirstTime = true;
        do {
            if (!isFirstTime) {
                System.out.println();
                System.out.println("Wrong number! Please enter a number between 1 to 5");
                System.out.print("Enter choice (1-5): ");
            }
            isFirstTime = false;
            choice = sc.nextLine().charAt(0);
        } while (choice != '1' && choice != '2' && choice != '3' && choice != '4' && choice != '5');
        return choice;
    }

    static void setNewTableDetails(Table table) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Seems like this table is ordering for the first time today");
        System.out.println("Please enter the customer's name");
        System.out.print("Enter name: ");
        table.customerName = sc.nextLine();
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        table.date = todaysDate.format(dateFormat);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        char choice;
        String itemChoice;
        boolean isFirstTime;
        ArrayList<String[]> chosenCategoryOptions;
        ArrayList<Table> tables = new ArrayList<>(10);
        System.out.println();
        System.out.println("Welcome to Restaurant Oasis Green");
        do {
            choice = displayMainMenu();
            chosenCategoryOptions = displayCategoryMenu(choice);
            int lastItemIndex = chosenCategoryOptions.size();
            System.out.print(System.lineSeparator() + "Enter choice (1-" + lastItemIndex + "): ");
            isFirstTime = true;
            do {
                if (!isFirstTime) {
                    System.out.println();
                    System.out.println("Wrong number! Please enter a number between 1 to " + lastItemIndex);
                    System.out.print("Enter choice (1-" + lastItemIndex + "): ");
                }
                isFirstTime = false;
                itemChoice = sc.next();
            } while (!(Integer.parseInt(itemChoice) >= 1 && Integer.parseInt(itemChoice) <= lastItemIndex));
            System.out.print("Enter table number: ");
            int tableNo = sc.nextInt();
            Table requiredTable = null;
            boolean newTableAdded = true;
            if (tables.size() != 0) {
                for (Table table : tables) {
                    if (table.tableNo == tableNo) {
                        requiredTable = table;
                        newTableAdded = false;
                    }
                }
                if (newTableAdded) {
                    requiredTable = new Table(tableNo);
                    tables.add(requiredTable);
                    setNewTableDetails(requiredTable);
                }
            } else {
                requiredTable = new Table(tableNo);
                tables.add(requiredTable);
                setNewTableDetails(requiredTable);
            }
            for (String[] categoryItemArray : chosenCategoryOptions) {
                if (categoryItemArray[0].equals(itemChoice)) {
                    requiredTable.addOrderedItem(categoryItemArray[1], categoryItemArray[2]);
                }
            }
            System.out.println();
            System.out.println("Enter 1 to order something more");
            System.out.println("Enter 2 to display bill for a specific table");
            System.out.println("Enter 3 to log off for today");
            System.out.print("Enter choice: ");
            isFirstTime = true;
            do {
                if (!isFirstTime) {
                    System.out.println();
                    System.out.println("Wrong number! Please enter either 1, 2 or 3");
                    System.out.print("Enter choice: ");
                }
                isFirstTime = false;
                itemChoice = sc.next();
            } while (Integer.parseInt(itemChoice) != 1 && Integer.parseInt(itemChoice) != 2 && Integer.parseInt(itemChoice) != 3);
            if (Integer.parseInt(itemChoice) == 2) {
                do {
                    System.out.print(System.lineSeparator() + "Enter table number for displaying bill: ");
                    int tableNumberForBill = sc.nextInt();
                    boolean tableFound = false;
                    Table finshedTable = null;
                    for (Table table : tables) {
                        if (tableNumberForBill == table.tableNo) {
                            LocalDateTime timeWhilePrintingBill = LocalDateTime.now();
                            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                            table.time = timeWhilePrintingBill.format(timeFormat);
                            table.displayBill();
                            finshedTable = table;
                            tableFound = true;
                        }
                    }
                    if (tableFound) {
                        tables.remove(finshedTable);
                    }
                    if (!tableFound) {
                        System.out.println();
                        System.out.println("Given table number has not ordered anything, please verify the number again");
                    }
                    System.out.println(System.lineSeparator() + "Enter 1 to order items for other tables");
                    System.out.println("Enter 2 to display another table's bill");
                    System.out.println("Enter 3 to log off for today");
                    System.out.print("Enter choice: ");
                    isFirstTime = true;
                    do {
                        if (!isFirstTime) {
                            System.out.println();
                            System.out.println("Wrong choice! Please enter either 1, 2 or 3");
                            System.out.print("Enter choice: ");
                        }
                        isFirstTime = false;
                        itemChoice = sc.next();
                    } while (Integer.parseInt(itemChoice) != 1 && Integer.parseInt(itemChoice) != 2 && Integer.parseInt(itemChoice) != 3);
                } while (Integer.parseInt(itemChoice) == 2);
            }
        } while (Integer.parseInt(itemChoice) != 3);
        System.out.println(System.lineSeparator() + "Shutting down Billing System for the day...");
        System.out.println("Thank you.");
    }
}
