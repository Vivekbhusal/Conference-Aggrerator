/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExternalAPI;

import Entity.Tweets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author vivekbhusal
 */
public class TwitterSentimentAnalysis {

    private int positive=0;
    private int negative=0;
    private int neutral=0;
    private final String tweets;
    private final String[] negativeBag;
    private final String[] positiveBag;
    
    private int Psize;
    private int Nsize;
    private int size;
    private List<Tweets> tweetlists;
    public TwitterSentimentAnalysis(String params) {
        this.tweets = params;
        this.negativeBag = SentimentWordsBag.Negativewordbag.wordlist;
        this.positiveBag = SentimentWordsBag.Positivewords.wordlist;
        Psize = this.positiveBag.length;
        Nsize = this.negativeBag.length;
        
    }

    public void doSemanticAnalysis() {
        tweetlists = new ArrayList<>();
        try {
            JSONObject jsonResult = new JSONObject(tweets);
            JSONArray statuses = jsonResult.getJSONArray("statuses");
            size = statuses.length();
            for(int i=0; i<size; i++){
                JSONObject status = statuses.getJSONObject(i);
                String text = status.getString("text");
                for(int j=0; j<Psize; j++){
                    if(text.contains(positiveBag[j])){
                        
                        positive++;
                        break;
                    }
                        
                }
                for(int j=0; j<Nsize; j++){
                    if(text.contains(negativeBag[j])){
                        negative++;
                        break;
                    }
                        
                }
                
                Tweets tweet = new Tweets();
                tweet.setUsername(status.getJSONObject("user").getString("name"));
                tweet.setScreenName(status.getJSONObject("user").getString("screen_name"));
                tweet.setProfilepictureURL(status.getJSONObject("user").getString("profile_image_url"));
                tweet.setText(text);
                tweetlists.add(tweet);
                
            }
            
            neutral = size - (positive-negative);
        } catch (JSONException ex) {
            Logger.getLogger(TwitterSentimentAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Tweets> getTweetlists() {
        return tweetlists;
    }

    public void setTweetlists(List<Tweets> tweetlists) {
        this.tweetlists = tweetlists;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

}
