package com.bestseller.coffeestore.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersDAOImpl implements UsersDAO{

    private final EntityManager entityManager;

    public UsersDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean isAdmin(String userId) {

        TypedQuery<Boolean> getUserByUserIdQuery = entityManager
                .createQuery("SELECT u.isAdmin FROM Users u WHERE u.userId = :userId", Boolean.class);
        getUserByUserIdQuery.setParameter("userId", userId);
        List<Boolean> resultList = getUserByUserIdQuery.getResultList();

        Boolean isAdmin = Optional.ofNullable(resultList.get(0)).orElse(null);

        return isAdmin;
    }
}
