package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.entities.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuoteFetcher {
	
	public static Map fetchQuote() {
		try {
            URL url = new URL("https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit"); // or ZenQuotes URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            // Convert JSON to Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> incommingMap = mapper.readValue(content.toString(), Map.class);
            
            Map<String, Object> responseMap = new HashMap<String, Object>();
            
            responseMap.put("quote", incommingMap.get("setup"));
            responseMap.put("response", incommingMap.get("delivery"));
            responseMap.put("category", incommingMap.get("category"));
            responseMap.put("type", incommingMap.get("type"));
            responseMap.put("safe", incommingMap.get("safe"));

            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public static Quote getQuote() {
		Map<String, Object> quoteMap = QuoteFetcher.fetchQuote();
		
		if(quoteMap != null) {
		
		Quote quote = new Quote();
		quote.setQuote((String)quoteMap.get("quote"));
		quote.setResponse((String)quoteMap.get("response"));
		quote.setType((String)quoteMap.get("category"));
		quote.setCategory((String)quoteMap.get("type"));
		quote.setSafe((boolean)quoteMap.get("safe"));
		
		return quote;
		}else {
		return null;
		}
	}

}
