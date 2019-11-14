/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.Clients;
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
@Path("/clients")
public class ClientsFacadeREST extends AbstractFacade<Clients> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public ClientsFacadeREST() {
        super(Clients.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void create(Clients entity) {
        super.create(entity);
    }
    
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void editClient(@PathParam("id") Integer id, Clients entity) {
        super.edit(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public void edit(@PathParam("id") Integer id, Clients entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Clients find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("/list")
    @Override
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Clients> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Clients> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("auth/{username}/{password}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Clients login(@PathParam("username") String username, @PathParam("password") String password) {
        List<Clients> clients = 
                em.createNamedQuery("Clients.login", Clients.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        
        Clients c = null;
        
        if(!clients.isEmpty()){
            return clients.get(0);
        }
        return c;
    }
    
    @GET
    @Path("auth/{username}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Clients findByUsername(@PathParam("username") String username){
        List<Clients> clients = 
                em.createNamedQuery("Clients.findByUsername", Clients.class)
                .setParameter("username", username)
                .getResultList();
        
        Clients c = null;
        
        if(!clients.isEmpty()){
            return clients.get(0);
        }
        return c;
    }
}
