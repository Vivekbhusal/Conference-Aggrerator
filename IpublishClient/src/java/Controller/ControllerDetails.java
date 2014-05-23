/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Entity.Coreconf;
import Entity.GoogleSeed;
import Entity.Tweets;
import ExternalAPI.GoogleSearch;
import ExternalAPI.TwitterSearch;
import ExternalAPI.TwitterSentimentAnalysis;
import Scrapper.Webscrapper;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author vivekbhusal
 */
@ManagedBean
@SessionScoped
public class ControllerDetails {

    private Coreconf CoreConf;
    private String googleresult;
    private String twitterResult;
    private GoogleSeed googleseed;
    
    private int negative, positive, neutral;
    private List<Tweets> tweetlist;
    private String year;
    /**
     * Creates a new instance of ControllerDetails
     */
    public ControllerDetails() {
        year = "2014";
    }
    
    public String passConf(Coreconf coreconf){
        this.CoreConf = new Coreconf();
        this.CoreConf = coreconf;
        year = "2014";
        getGoogleData(CoreConf.getConfTitle()+" "+year);
        getTwitterData(CoreConf.getAcronym());
        return "Confdetail.xhtml";
    }

    public String loadCOnf(){
        getGoogleData(CoreConf.getConfTitle()+" "+year);
        getTwitterData(CoreConf.getAcronym());
        return "Confdetail.xhtml";
    }
    
    public Coreconf getCoreConf() {
        return CoreConf;
    }

    public void setCoreConf(Coreconf CoreConf) {
        this.CoreConf = CoreConf;
    }
    
    public void getGoogleData(String query){
        GoogleSearch googleapi = new GoogleSearch(query);
        googleseed = googleapi.getGoogleSeed();
    }
    
    public void getTwitterData(String query){
        TwitterSearch twitterapi = new TwitterSearch();
        try {
            RestResponse response = twitterapi.doSearch(query);
            TwitterSentimentAnalysis sentimentAnalysis = new TwitterSentimentAnalysis(response.getDataAsString());
            sentimentAnalysis.doSemanticAnalysis();
            positive = sentimentAnalysis.getPositive();
            negative = sentimentAnalysis.getNegative();
            neutral = sentimentAnalysis.getNeutral();
            tweetlist = sentimentAnalysis.getTweetlists();
            System.out.println("twitter result->> "+ response.getDataAsString());
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException ex) {
            Logger.getLogger(ControllerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public List<Tweets> getTweetlist() {
        return tweetlist;
    }

    public void setTweetlist(List<Tweets> tweetlist) {
        this.tweetlist = tweetlist;
    }

    public String getGoogleresult() {
        return googleresult;
    }

    public void setGoogleresult(String googleresult) {
        this.googleresult = googleresult;
    }

    public String getTwitterResult() {
        return twitterResult;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    
    public void setTwitterResult(String twitterResult) {
        this.twitterResult = twitterResult;
    }

    public GoogleSeed getGoogleseed() {
        return googleseed;
    }

    public void setGoogleseed(GoogleSeed googleseed) {
        this.googleseed = googleseed;
    }
    
    public String getDetails(){
        Scrapper.Webscrapper scrapper = new Webscrapper();
        return scrapper.scrapeTopic(googleseed.getLink());
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }
    
    
}
