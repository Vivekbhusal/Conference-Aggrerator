/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExternalAPI;

import java.io.IOException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author vivekbhusal
 */
public class TwitterSearch {
  public static final String CONSUMER_KEY = "HAcD3P6rQ9vsS5q1Cs3xYA"; 
    public static final String CONSUMER_SECRET = "Pe3HBaCr4rEAPPKCOSae8L2bjINyd4s5BbCCYEqmxjc"; 
    public static final String OAUTH_TOKEN = "584696448-k6xtBkqDurzp8waPjz88qrHMtCz4lJGLnUZG9bQE"; 
    public static final String OAUTH_TOKEN_SECRET = "KYHq3eGlOwdR4HojpG1gHjKVdcXnVAYTbKcMHu2xMbl7I"; 

    public RestResponse doSearch(String hashtag) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException{
        OAuthConsumer consumer  = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(OAUTH_TOKEN, OAUTH_TOKEN_SECRET);
        hashtag = hashtag.replaceAll(" ", "%23");
        String URL="https://api.twitter.com/1.1/search/tweets.json?q=" + hashtag;
        String[][] pathParams = new String[][]{}; 
        String[][] queryParams = new String[][]{}; 
        RestConnection conn = new RestConnection(consumer.sign(URL), pathParams, queryParams); 
        return conn.get(); 
     }
}
