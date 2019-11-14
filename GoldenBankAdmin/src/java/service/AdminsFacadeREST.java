/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.Admins;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
@Path("/admins")
public class AdminsFacadeREST extends AbstractFacade<Admins> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public AdminsFacadeREST() {
        super(Admins.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Admins entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Admins entity) {
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
    public Admins find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("/list")
    @Override
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<Admins> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Admins> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("/authentication")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Admins loginByUsername(@FormParam("username") String username, @FormParam("password") String password){

        Admins admin = null;

        List<Admins> admins =
            em.createNamedQuery("Admins.findByUsernameAndPassword", Admins.class)
                    .setParameter("username", username.toLowerCase())
                    .setParameter("password", password)
                    .getResultList();

        if (admins.size()==1){
            admin = admins.get(0);
        }
        
        return admin;
    }
    
}
