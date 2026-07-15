package br.com.baseee.web;

import br.com.baseee.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DashboardBean {

    @EJB
    private UserService userService;

    public long getTotalUsers() {
        return userService.countUsers();
    }
}

