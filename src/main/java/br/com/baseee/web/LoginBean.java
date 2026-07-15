package br.com.baseee.web;

import br.com.baseee.model.User;
import br.com.baseee.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoginBean {

    private String email;
    private String password;

    @EJB
    private UserService userService;

    @Inject
    private SessionBean sessionBean;

    public String login() {
        User user = userService.authenticate(email, password);

        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido.", "E-mail ou senha incorretos."));
            return null;
        }

        sessionBean.login(user);
        keepMessages();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Login realizado.", "Bem-vindo, " + user.getName() + "."));

        return "/app/home.xhtml?faces-redirect=true";
    }

    private void keepMessages() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

