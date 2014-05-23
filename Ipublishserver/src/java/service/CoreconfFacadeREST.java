/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author vivekbhusal
 */
@Stateless
@Path("service.coreconf")
public class CoreconfFacadeREST extends AbstractFacade<Coreconf> {
    @PersistenceContext(unitName = "IpublishserverPU")
    private EntityManager em;

    public CoreconfFacadeREST() {
        super(Coreconf.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Coreconf entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Coreconf entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Coreconf find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("searchtitle/{title}")
    @Produces({"application/xml", "application/json"})
    public Coreconf find(@PathParam("title") String title){
        return (Coreconf)em.createNamedQuery("Coreconf.findByConfTitle").setParameter("confTitle", title).getResultList();
    }
    
    
    @GET
    @Path("searchall/{value}")
    @Produces({"application/xml", "application/json"})
    public List<Coreconf> search(@PathParam("value") String value){
        String sql;
        sql =  "SELECT c FROM Coreconf c WHERE UPPER(c.confTitle) LIKE UPPER('%" + value + "%') OR " +
                "UPPER(c.acronym) LIKE UPPER('%" + value + "%') OR " +
                "UPPER(c.rank) LIKE UPPER('%" + value + "%') OR " +
                "UPPER(c.forcode) LIKE UPPER('%" + value + "%')";
        
        TypedQuery<Coreconf> smt = em.createQuery(sql, Coreconf.class);
        return smt.getResultList();
    }
    
    @GET
    @Path("searchwithrank/{value}/{rank}")
    @Produces({"application/xml", "application/json"})
    public List<Coreconf> searchwithrank(@PathParam("value") String value, @PathParam("rank") String rank){
        String sql;
        sql =  "SELECT c FROM Coreconf c WHERE "+
                "(UPPER(c.confTitle) LIKE UPPER('%" + value + "%') OR " +
                "UPPER(c.acronym) LIKE UPPER('%" + value + "%') OR " +
                "UPPER(c.forcode) LIKE UPPER('%" + value + "%') )AND "+
                "UPPER(c.rank) = UPPER('"+rank+"')";
        
        TypedQuery<Coreconf> smt = em.createQuery(sql, Coreconf.class);
        return smt.getResultList();
    }
    
    @GET
    @Path("searchonlyrank/{rank}")
    @Produces({"application/xml", "application/json"})
    public List<Coreconf> searchonlyrank(@PathParam("rank") String rank){
        String sql;
        sql =  "SELECT c FROM Coreconf c WHERE UPPER(c.rank) = UPPER('"+rank+"')";
        
        TypedQuery<Coreconf> smt = em.createQuery(sql, Coreconf.class);
        return smt.getResultList();
    }
    
    
    
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Coreconf> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Coreconf> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
