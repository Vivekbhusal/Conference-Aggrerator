/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author vivekbhusal
 */
public class Webscrapper {
    
//    public static void main(String[] args) {
//        scrapeTopic("http://www.wikicfp.com/cfp/servlet/event.showcfp?eventid=32647&copyownerid=54960");
//    }
//    /html/body/div[4]/center/table/tbody/tr[8]/td/div

    public static String scrapeTopic(String url) {
        String html = getUrl(url);
        Document doc = Jsoup.parse(html);
        String contentText = doc.select("body > div:nth-child(5) > center > table > tbody > tr:nth-child(8) > td > div").html();
//        System.out.println(contentText);
        return contentText;
    }

    public static String getUrl(String url) {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("The url was malformed!");
            return "";
        }
        URLConnection urlCon = null;
        BufferedReader in = null;
        String outputText = "";
        try {
            urlCon = urlObj.openConnection();
            in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                outputText += line;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(
                    "There was an error connecting to  URL");
            return "";
        }
        return outputText;
    }
}
