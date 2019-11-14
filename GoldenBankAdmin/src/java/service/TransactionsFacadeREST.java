/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.Transactions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Rhea
 */
@Stateless
@Path("/transactions")
public class TransactionsFacadeREST extends AbstractFacade<Transactions> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public TransactionsFacadeREST() {
        super(Transactions.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void create(Transactions entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Transactions entity) {
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
    public Transactions find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("/list")
    @Override
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Transactions> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Transactions> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("/cardid/{cardId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Transactions> findByCardId(@PathParam("cardId") String cardId){
        List<Transactions> transactions = 
                em.createNamedQuery("Transactions.findByCardId", Transactions.class)
                .setParameter("cardId", Long.valueOf(cardId))
                .getResultList();
        
        List<Transactions> t = null;
        
        if(!transactions.isEmpty()){
            return transactions;
        }
        return t;
    }
    
    @GET
    @Path("/accounts1/{account1}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Transactions> findByAccount1(@PathParam("account1") String account1){
        List<Transactions> transactions = 
                em.createNamedQuery("Transactions.findByAccount1", Transactions.class)
                .setParameter("account1", Long.valueOf(account1))
                .getResultList();
        
        List<Transactions> t = null;
        
        if(!transactions.isEmpty()){
            return transactions;
        }
        return t;
    }
    
    @GET
    @Path("/accounts2/{account2}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Transactions> findByAccount2(@PathParam("account2") String account2){
        List<Transactions> transactions = 
                em.createNamedQuery("Transactions.findByAccount2", Transactions.class)
                .setParameter("account2", Long.valueOf(account2))
                .getResultList();
        
        List<Transactions> t = null;
        
        if(!transactions.isEmpty()){
            return transactions;
        }
        return t;
    }
}
