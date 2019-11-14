/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.ClientCard;
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
@Path("/clientcard")
public class ClientCardFacadeREST extends AbstractFacade<ClientCard> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public ClientCardFacadeREST() {
        super(ClientCard.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ClientCard entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, ClientCard entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ClientCard find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ClientCard> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ClientCard> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("/accounts/{accountId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<ClientCard> findByAccountId(@PathParam("accountId") String accountId){
        List<ClientCard> cards = 
                em.createNamedQuery("ClientCard.findByAccountId", ClientCard.class)
                .setParameter("accountId", Long.valueOf(accountId))
                .getResultList();
         
        List<ClientCard> cc = null;
        
        if(!cards.isEmpty()){
            return cards;
        }
        return cc;
    }
    
}
