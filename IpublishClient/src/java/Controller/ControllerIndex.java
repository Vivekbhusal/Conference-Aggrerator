/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import javax.faces.bean.ManagedBean;
import Entity.Coreconf;
import java.util.ArrayList;
import iPublish.Client.iPublishRestfulClient;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
/**
 *
 * @author vivekbhusal
 */
@ManagedBean
@RequestScoped
//@ViewScoped
public class ControllerIndex {

    private String pageTitle;
    private List<Coreconf> coreconfs;
    private iPublishRestfulClient iPublishRestfulClient;
    private String query;
    private String message;
    private String rank;
    /**
     * Creates a new instance of ControllerIndex
     */
    public ControllerIndex() {
        pageTitle = "iPublish";
        message = "";
        coreconfs = new ArrayList<>();
        rank = "all";
        iPublishRestfulClient = new iPublishRestfulClient();
        fetchAllConferences();
        
    }
    
    public void fetchAllConferences() {
        if(rank.equalsIgnoreCase("all")){
            GenericType<List<Coreconf>> genericType = new GenericType<List<Coreconf>>(){};
            Response res = iPublishRestfulClient.findAll_XML(Response.class);
            coreconfs = res.readEntity(genericType);
        }else{
            GenericType<List<Coreconf>> genericType = new GenericType<List<Coreconf>>(){};
            Response res = iPublishRestfulClient.searchonlyrank_XML(Response.class, rank);
            coreconfs = res.readEntity(genericType);
        }
        
    }
    
    public String searchConferences() {
       
        try{
           if(query.isEmpty()) {
            fetchAllConferences();
            }
            else {
                if(rank.equalsIgnoreCase("all")){
                    GenericType<List<Coreconf>> genericType = new GenericType<List<Coreconf>>(){};
                    Response res = iPublishRestfulClient.search_XML(Response.class, query);
                    coreconfs = res.readEntity(genericType);
                }else{
                    GenericType<List<Coreconf>> genericType = new GenericType<List<Coreconf>>(){};
                    Response res = iPublishRestfulClient.searchwithrank_XML(Response.class, query, rank);
                    coreconfs = res.readEntity(genericType);
                }

            }
           return "index.xhtml";
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<Coreconf> getCoreconfs() {
        return coreconfs;
    }

    public void setCoreconfs(List<Coreconf> coreconfs) {
        this.coreconfs = coreconfs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    
    
}
