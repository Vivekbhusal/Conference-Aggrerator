/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExternalAPI;

import Entity.GoogleSeed;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author vivekbhusal
 */
public class GoogleSearch {
    
    String API_KEY = "AIzaSyDEOKZJquTDUQOtSnAwYG-XfhXgs9X0mSE";
    String Backup_API_KEY = "AIzaSyArfDx3oJiL4DR7WM4fpkGIKF3dhSalqkE";
    
    String SEARCH_ID_CX = "010621176268725740300:-y4rbxxurqo";
    String backup_SEARCH_ID_CX = "010621176268725740300:h9ipgqjmyag";
    
    String IMAGE_SEARCH_CX = "010621176268725740300:1adf9gkgsnq";
    String backup_IMAGE_SEARCH_CX = "010621176268725740300:9a1ndadkme0";
    
    String title = null;
    String link = null;
    String snippet = null;
    String startdate = null;
    String enddate = null;
    String location = null;
    List<String> imageURL;
    GoogleSeed googleSeed;
    
    String googleImageresult;
    RestResponse imageResponse;
    RestResponse confResponse;
    
    String status ="";
    String message = "";
    
    public GoogleSearch(String query){
        System.out.println("query");
        googleSeed = new GoogleSeed();
        
        confResponse = new RestResponse();
        confResponse = doSearch(query, Boolean.FALSE);
        parseSearch(confResponse);
        
        imageResponse = new RestResponse();
        imageResponse = doSearch(location +" Research Conference ;"+title, Boolean.TRUE);
        parseImageSearch(imageResponse);
        
    }
    
    public RestResponse getImageReponse(){
        return imageResponse;
    }
    
    public RestResponse getConfResponse(){
        return confResponse;
    }
    
    public GoogleSeed getGoogleSeed(){
        return googleSeed;
    }
    
     public RestResponse doSearch(String query, Boolean image){
        RestResponse response = new RestResponse();
        
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        
        RestConnection conn= new RestConnection(this.buildQuery(query, image), pathParams, queryParams);
        try {
           response= conn.get();
        } catch (IOException ex) {
            System.out.println("Connection failed::"+ex.getMessage());
            response.setResponseMessage("Response Failed :"+ex.getMessage());
        }
        
        return response;
    }
    
    private String buildQuery(String Query, Boolean image){
        String ConvertedQuery="";
        String URL;
        for(int i=0; i<Query.length(); i++){
           ConvertedQuery += (Query.charAt(i)==' ' || Query.charAt(i)=='\\' || Query.charAt(i)=='/' ?"%20": Query.charAt(i));
        }
        if(image){
            URL = "https://www.googleapis.com/customsearch/v1?key="+Backup_API_KEY+"&cx="+backup_IMAGE_SEARCH_CX+"&q="+ConvertedQuery+"&num=10&searchType=image";
        }else{
            URL = "https://www.googleapis.com/customsearch/v1?key="+Backup_API_KEY+"&cx="+backup_SEARCH_ID_CX+"&q="+ConvertedQuery+"&num=5";
        }
        
        return URL;
    }
    
    private void parseSearch(RestResponse response){
//        System.out.println("parseSearch method");
//        System.out.println(response.getDataAsString());
        JSONArray items = null;
        try {
            JSONObject jsonobj = new JSONObject(response.getDataAsString());
            if(jsonobj.has("items")){
                 items = jsonobj.getJSONArray("items");
            }else{
                status ="error";
                message = "No details found";
                return;
            }
//            System.out.println(items.toString());
            int size = items.length();
            
            for(int i=0; i<size; i++){
                JSONObject itemobj = items.getJSONObject(i);
                if(itemobj.has("pagemap")){
                    JSONObject pagemapobj = itemobj.getJSONObject("pagemap");
                    System.out.println("pagemag--> "+pagemapobj.toString());
                    if(pagemapobj.has("event")){
                        JSONArray event = pagemapobj.getJSONArray("event");
                        JSONObject eventobj = event.getJSONObject(0);
                        startdate = eventobj.getString("dtstart");
                        enddate = eventobj.getString("dtend");
                        location = eventobj.getString("location");
                    }else{
                        JSONArray event = pagemapobj.getJSONArray("Event");
                        JSONObject eventobj = event.getJSONObject(0);
                        startdate = eventobj.getString("startDate");
                        enddate = eventobj.getString("endDate");
                    }
                    title = itemobj.getString("title");
                    link = itemobj.getString("link");
                    snippet = itemobj.getString("snippet");
                    googleSeed.setTitle(title);
                    googleSeed.setLink(link);
                    googleSeed.setSnippet(snippet);
                    googleSeed.setLocation(location);
                    googleSeed.setStartdate(startdate);
                    googleSeed.setEnddate(enddate);
                    break;
                }else{
                    title = itemobj.getString("title");
                    link = itemobj.getString("link");
                    snippet = itemobj.getString("snippet");
                    googleSeed.setTitle(title);
                    googleSeed.setLink(link);
                    googleSeed.setSnippet(snippet);
                }
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void parseImageSearch(RestResponse result){
//        System.out.println("image result->> "+ result.getDataAsString());
        imageURL = new ArrayList<>();
        try {
            JSONObject jsonobj = new JSONObject(result.getDataAsString());
            JSONArray items = jsonobj.getJSONArray("items");
            int size = items.length();
            for(int i =0; i<size; i++){
                JSONObject item = items.getJSONObject(i);
                imageURL.add(item.getString("link"));
            }
            googleSeed.setImageURL(imageURL);
        } catch (JSONException ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
