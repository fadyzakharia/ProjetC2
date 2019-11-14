/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.Accounts;
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
@Path("/accounts")
public class AccountsFacadeREST extends AbstractFacade<Accounts> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public AccountsFacadeREST() {
        super(Accounts.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void create(Accounts entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void edit(@PathParam("id") Long id, Accounts entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Accounts find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Path("/list")
    @Override
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Accounts> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Accounts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @POST
    @Path("/updateaccount")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Accounts updateAccount(Accounts entity) {
        return em.merge(entity);
    }
    
    @GET
    @Path("/{client_id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Accounts> findByClientId(@PathParam("client_id") int client_id){
        List<Accounts> accounts = 
                em.createNamedQuery("Accounts.findByClientId", Accounts.class)
                .setParameter("client_id", client_id)
                .getResultList();
        
        List<Accounts> a = null;
        
        if(!accounts.isEmpty()){
            return accounts;
        }
        return a;
    }
    
}
