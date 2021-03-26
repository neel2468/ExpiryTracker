package com.example.expirytracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtility {
    public String readJSONDataFromFile(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();

        try {
            String jsonString = null;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream,"UTF-8"));
                    while ((jsonString = bufferedReader.readLine()) != null) {
                        builder.append(jsonString);
                    }

        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }



}
