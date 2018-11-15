
package com.adv.java.application;

import com.adv.java.database.Database;
import com.adv.java.networking.Library;
import com.adv.java.xml.XML;

public class FinalApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Called: com.adv.java.application.main(String [])");

        System.out.println("This program demonstrates the use of Java modules by:");

        System.out.println("1. Connecting to a Public Library API to do a library search");
        System.out.println("2. Converting the API response into an XML Document");
        System.out.println("3. Parsing the XML Document for results and gathering the UUIDs for reach result.");
        System.out.println("4. Storing the results into an SQLlite database.");
        System.out.println("5. Retrieving the stored results from the SQLlite database.\n");

        Library library = Library.newInstance();

        // Do a library search for the word "bird"... because, why not?
        String librarySearch = library.search("bird");

        System.out.println("The New York Public Library API returned:");
        System.out.print(librarySearch);

        // Create a new XML Object with the search results
        XML xml = new XML(librarySearch);

        // Create a new Database Object to store the search results
        Database db = Database.newInstance();

        // INSERT data into the database
        String title = null;
        String itemLink = null;

        for (String uuid : xml.getUuids()) {
            title = xml.getTitle(uuid);
            itemLink = xml.getItemLink(uuid);

            System.out.println("Adding " + uuid + " to the database:");
            db.create(uuid, title, itemLink);
        }

        // SELECT data from the database
        for (String uuid : xml.getUuids()) {
            System.out.println("Reading " + uuid + " from the database:");
            System.out.println(db.read(uuid));
        }

        System.out.println("All done!");

    }

}
