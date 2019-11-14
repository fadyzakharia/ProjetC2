/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Rhea
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.AccountsFacadeREST.class);
        resources.add(service.AdminsFacadeREST.class);
        resources.add(service.AtmBranchFacadeREST.class);
        resources.add(service.BankBranchFacadeREST.class);
        resources.add(service.ClientCardFacadeREST.class);
        resources.add(service.ClientsFacadeREST.class);
        resources.add(service.CurrencyFacadeREST.class);
        resources.add(service.ProductsCardsFacadeREST.class);
        resources.add(service.ProductsLoansFacadeREST.class);
        resources.add(service.TransactionsFacadeREST.class);
    }
    
}
