
package com.adv.java.networking;

import java.io.IOException;

public interface Library {

    static Library newInstance() {
        System.out.println("");
        System.out.println("Called: com.adv.java.networking.Library.newInstance()");
        return new com.adv.java.networking.apis.NYCPLibrary();
    }

    String search(String word) throws IOException;

    String search(String word, boolean publicDomain) throws IOException;

}
