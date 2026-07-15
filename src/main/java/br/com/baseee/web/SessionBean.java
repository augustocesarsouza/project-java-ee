package br.com.baseee.web;

import br.com.baseee.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    private Long userId;
    private String userName;
    private String userEmail;

    public void login(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public void redirectEntryPoint() throws IOException {
        if (isLoggedIn()) {
            redirect("/app/home.xhtml");
        } else {
            redirect("/login.xhtml");
        }
    }

    public void ensureGuest() throws IOException {
        if (isLoggedIn()) {
            redirect("/app/home.xhtml");
        }
    }

    public void ensureAuthenticated() throws IOException {
        if (!isLoggedIn()) {
            redirect("/login.xhtml");
        }
    }

    private void redirect(String page) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + page);
    }

    public boolean isLoggedIn() {
        return userId != null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

