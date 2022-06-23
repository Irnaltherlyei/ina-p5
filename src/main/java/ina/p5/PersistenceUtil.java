package ina.p5;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;

public class PersistenceUtil<BeanType>{
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;

    public PersistenceUtil() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    public void save(BeanType bean){
        try {
            transaction.begin();

            entityManager.persist(bean);

            transaction.commit();
        } catch (Exception e){

        }
    }

    public ArrayList<BeanType> doNamedQuery(String namedQuery, Class<BeanType> c, String[] parameters){
        ArrayList<BeanType> result = new ArrayList<>();

        try {
            transaction.begin();

            TypedQuery<BeanType> query = entityManager.createNamedQuery(namedQuery, c);
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i + 1, parameters[i]);
            }
            result = new ArrayList<BeanType>(query.getResultList());

            transaction.commit();
        } catch (Exception e){

        }

        return result;
    }

    @Transactional
    public int doUpdateSQLQuery(String sqlString, String[] parameters){
        int flag;
        transaction.begin();

        Query query = entityManager.createNativeQuery(sqlString);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i + 1, parameters[i]);
        }
        flag = query.executeUpdate();

        transaction.commit();

        return flag;
    }

    public void close(){
        if(transaction.isActive()){
            transaction.rollback();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}