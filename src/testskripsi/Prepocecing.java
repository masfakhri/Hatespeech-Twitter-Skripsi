/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testskripsi;

import static java.awt.SystemColor.text;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static testskripsi.TestSkripsi.conn;

/**
 *
 * @author sayam
 */
public class Prepocecing {
    
    public static String Bersihkan(String id, String tweet, String dateTweet, String location) {
        
        tweet = tweet.toLowerCase();
        Pattern urlPattern = Pattern.compile("((https?|http):[\\w\\d:#@%/;$()~_?\\+-\\\\\\.&]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(tweet);
        while (matcher.find()) {
            tweet = tweet.replaceAll(matcher.group(), "");
        }

        tweet = tweet.replaceAll("(rt @|@|#|http)[-a-zA-Z0-9+&@#/%?=~_|:,.;…]*", " ");
        tweet = tweet.replaceAll("[^a-z ]+", " ");
        tweet = tweet.replaceAll("\n", " ");
        tweet = tweet.replaceAll("rt @\\w+|#\\w+|\\brt\\b", " ");
        tweet = tweet.replaceAll("[']", "");
        tweet = tweet.replaceAll(" +", " ").trim();
        
        System.out.println(tweet);
        try {
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("INSERT into tweet (user_id, tweet, tanggal_tweet, location) VALUES ('" 
                            + id + "','" 
                            + tweet + "','" 
                            + dateTweet + "','" 
                            + location + "')");

        }catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return tweet;
    }

}
