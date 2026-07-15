package br.com.baseee.web;

import br.com.baseee.model.User;
import br.com.baseee.service.BusinessException;
import br.com.baseee.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class RegisterBean {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    @EJB
    private UserService userService;

    @Inject
    private SessionBean sessionBean;

    public String register() {
        if (password == null || !password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas diferentes.", "Confirme a senha corretamente."));
            return null;
        }

        try {
            User user = userService.register(name, email, password);
            sessionBean.login(user);
            keepMessages();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro concluído.", "Sua conta foi criada com sucesso."));
            return "/app/home.xhtml?faces-redirect=true";
        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar.", e.getMessage()));
            return null;
        }
    }

    private void keepMessages() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

