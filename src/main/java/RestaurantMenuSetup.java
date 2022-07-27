import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

// Run this just once to create the Restaurant Menu Collection.
// It can be rerun whenever modifications are to be made to the Restaurant Menu

public class RestaurantMenuSetup {
    public static void main(String[] args) {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating Credentials
        MongoCredential credential;
        String databaseName = "restaurantDB";
        credential = MongoCredential.createCredential("sampleUser", databaseName, "password".toCharArray());
        System.out.println("Connected to the database " + "'" + databaseName + "'" + " successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase(databaseName);

        // Making soups collection
        MongoCollection<Document> soupsCollection = database.getCollection("soups");
        // Clearing the collection
        soupsCollection.deleteMany(new Document());

        // You can add menu items for soups here
        List<Document> soupsList = new ArrayList<>();
        soupsList.add(new Document("name", "Basil Tomato Soup").append("cost", 200));
        soupsList.add(new Document("name", "Cheesy Potato Soup").append("cost", 200));
        soupsList.add(new Document("name", "Minestrone Soup").append("cost", 200));
        soupsList.add(new Document("name", "Hot & Sour Soup").append("cost", 250));
        soupsList.add(new Document("name", "Manchow Soup").append("cost", 250));
        soupsList.add(new Document("name", "Vegetable Noodle Soup").append("cost", 250));
        soupsList.add(new Document("name", "French Onion Soup").append("cost", 300));
        soupsList.add(new Document("name", "Corn And Miso Soup").append("cost", 300));
        soupsList.add(new Document("name", "Cream Of Mushroom Soup").append("cost", 300));
        soupsList.add(new Document("name", "Cream Of Broccoli Soup").append("cost", 300));
        soupsCollection.insertMany(soupsList);
        System.out.println("Created soups collection successfully!");

        // Making starters collection
        MongoCollection<Document> startersCollection = database.getCollection("starters");
        // Clearing the collection
        startersCollection.deleteMany(new Document());

        // You can add menu items for starters here
        List<Document> startersList = new ArrayList<>();
        startersList.add(new Document("name", "Potato Cheese Croquettes").append("cost", 200));
        startersList.add(new Document("name", "Hara Bhara Kabab").append("cost", 200));
        startersList.add(new Document("name", "Spring Rolls").append("cost", 200));
        startersList.add(new Document("name", "Steamed Wontons").append("cost", 250));
        startersList.add(new Document("name", "Cheesy Garlic Bread").append("cost", 250));
        startersList.add(new Document("name", "Veg Manchurian").append("cost", 250));
        startersList.add(new Document("name", "Chilli Baby Corn").append("cost", 250));
        startersList.add(new Document("name", "Sesame Falafel").append("cost", 300));
        startersList.add(new Document("name", "Nachos Supreme").append("cost", 300));
        startersList.add(new Document("name", "Tandoori Platter").append("cost", 300));
        startersCollection.insertMany(startersList);
        System.out.println("Created starters collection successfully!");

        // Making maincourse collection
        MongoCollection<Document> maincourseCollection = database.getCollection("maincourse");
        // Clearing the collection
        maincourseCollection.deleteMany(new Document());

        // You can add menu items for maincourse here
        List<Document> maincourseList = new ArrayList<>();
        maincourseList.add(new Document("name", "Layered Baked Ratatouille").append("cost", 300));
        maincourseList.add(new Document("name", "Spicy Vegetable Stew With Coconut").append("cost", 300));
        maincourseList.add(new Document("name", "Mushroom Curry").append("cost", 300));
        maincourseList.add(new Document("name", "Paneer Butter Masala").append("cost", 350));
        maincourseList.add(new Document("name", "Pesto Penne Pasta").append("cost", 350));
        maincourseList.add(new Document("name", "Spaghetti Aglio e Olio").append("cost", 350));
        maincourseList.add(new Document("name", "Mac & Cheese").append("cost", 400));
        maincourseList.add(new Document("name", "Malai Kofta").append("cost", 400));
        maincourseList.add(new Document("name", "Gourmet Mushroom Risotto").append("cost", 400));
        maincourseList.add(new Document("name", "Broccoli, Rice & Cheese Casserole").append("cost", 400));
        maincourseCollection.insertMany(maincourseList);
        System.out.println("Created maincourse collection successfully!");

        // Making desserts collection
        MongoCollection<Document> dessertsCollection = database.getCollection("desserts");
        // Clearing the collection
        dessertsCollection.deleteMany(new Document());

        // You can add menu items for desserts here
        List<Document> dessertsList = new ArrayList<>();
        dessertsList.add(new Document("name", "Gulab Jamun").append("cost", 150));
        dessertsList.add(new Document("name", "Apple Pie").append("cost", 150));
        dessertsList.add(new Document("name", "Baklava").append("cost", 150));
        dessertsList.add(new Document("name", "Gelato").append("cost", 200));
        dessertsList.add(new Document("name", "Belgian Waffle").append("cost", 200));
        dessertsList.add(new Document("name", "Creme Brulee").append("cost", 200));
        dessertsList.add(new Document("name", "Nanaimo Bar").append("cost", 200));
        dessertsList.add(new Document("name", "Cinnamon Roll").append("cost", 250));
        dessertsList.add(new Document("name", "Tiramisu").append("cost", 250));
        dessertsList.add(new Document("name", "Mango Kulfi").append("cost", 250));
        dessertsCollection.insertMany(dessertsList);
        System.out.println("Created desserts collection successfully!");

        // Making drinks collection
        MongoCollection<Document> drinksCollection = database.getCollection("drinks");
        // Clearing the collection
        drinksCollection.deleteMany(new Document());

        // You can add menu items for desserts here
        List<Document> drinksList = new ArrayList<>();
        drinksList.add(new Document("name", "Hot Chocolate").append("cost", 150));
        drinksList.add(new Document("name", "Mocha Cappuccino").append("cost", 150));
        drinksList.add(new Document("name", "Cafe Latte").append("cost", 150));
        drinksList.add(new Document("name", "Fresh Lime Soda").append("cost", 150));
        drinksList.add(new Document("name", "Peach Colada").append("cost", 200));
        drinksList.add(new Document("name", "Spicy Guava").append("cost", 200));
        drinksList.add(new Document("name", "Berry Cooler").append("cost", 200));
        drinksList.add(new Document("name", "Refreshing Mojito").append("cost", 200));
        drinksList.add(new Document("name", "Blue Lagoon").append("cost", 200));
        drinksList.add(new Document("name", "Triple Chocolate").append("cost", 200));
        drinksCollection.insertMany(drinksList);
        System.out.println("Created drinks collection successfully!");
    }
}
