/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import goldenbank.AtmBranch;
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
@Path("/atmbranche")
public class AtmBranchFacadeREST extends AbstractFacade<AtmBranch> {
    @PersistenceContext(unitName = "GoldenBankAdminPU")
    private EntityManager em;

    public AtmBranchFacadeREST() {
        super(AtmBranch.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AtmBranch entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, AtmBranch entity) {
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
    public AtmBranch find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("/list")
    @Override
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public List<AtmBranch> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AtmBranch> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
