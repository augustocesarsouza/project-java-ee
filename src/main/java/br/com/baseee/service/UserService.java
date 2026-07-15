package br.com.baseee.service;

import br.com.baseee.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "appPU")
    private EntityManager entityManager;

    public User register(String name, String email, String plainPassword) throws BusinessException {
        String normalizedName = normalizeName(name);
        String normalizedEmail = normalizeEmail(email);

        if (normalizedName == null || normalizedName.isBlank()) {
            throw new BusinessException("Informe o nome.");
        }

        if (normalizedEmail == null || normalizedEmail.isBlank()) {
            throw new BusinessException("Informe o e-mail.");
        }

        if (plainPassword == null || plainPassword.length() < 6) {
            throw new BusinessException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (findByEmail(normalizedEmail) != null) {
            throw new BusinessException("Já existe uma conta cadastrada com este e-mail.");
        }

        User user = new User();
        user.setName(normalizedName);
        user.setEmail(normalizedEmail);
        user.setPasswordHash(PasswordHasher.hash(plainPassword));

        entityManager.persist(user);
        entityManager.flush();

        return user;
    }

    public User authenticate(String email, String plainPassword) {
        String normalizedEmail = normalizeEmail(email);
        User user = findByEmail(normalizedEmail);

        if (user == null) {
            return null;
        }

        return PasswordHasher.matches(plainPassword, user.getPasswordHash()) ? user : null;
    }

    public long countUsers() {
        return entityManager.createQuery("select count(u) from User u", Long.class)
                .getSingleResult();
    }

    public User findByEmail(String email) {
        try {
            return entityManager.createQuery(
                            "select u from User u where lower(u.email) = :email", User.class)
                    .setParameter("email", normalizeEmail(email))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim().replaceAll("\\s+", " ");
    }
}

