
package com.adv.java.networking.apis;

import com.adv.java.networking.Library;
import java.io.*;
import java.net.*;

public class NYCPLibrary implements Library {

    private static final String authenticationToken = "XXXXXXXXXXXXXXXX";
    private static final String endpoint = "http://api.repo.nypl.org/api/v1/items/search.xml";
    private static final Integer requestLimit = 10000;

    private static Integer numberOfRequests = 0;

    public String search(String word) throws IOException {
        System.out.println("Called: com.adv.java.networking.apis.NYCPLibrary.query(String)");
        
        // By default, only query for things unencumbered by copyright
        return search(word, true);
    }

    public String search(String word, boolean publicDomain) throws IOException {
        System.out.println("Called: com.adv.java.networking.apis.NYCPLibrary.query(String, boolean)");

        StringBuilder result = new StringBuilder();

        // Don't make an API call if we've reached the API limit
        if (numberOfRequests < requestLimit) {

            String queryString;

            if (publicDomain) {
                System.out.println("Only querying for items in the public domain...");
                queryString = endpoint + "?q=" + word + "&publicDomainOnly=true&per_page=500";
            } else {
                System.out.println("Querying all items in the library inventory...");
                queryString = endpoint + "?q=" + word + "&per_page=500";
            }

            URL url = new URL(queryString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Token token=" + authenticationToken);
            conn.setRequestMethod("GET");

            System.out.print("Getting XML data from the API: ");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line + "\n");
            }
            rd.close();
            System.out.println("done.");

            // Keep track of the API calls for the API limit
            numberOfRequests++;

        }

        // Return XML as a String
        return result.toString();

    }

}
