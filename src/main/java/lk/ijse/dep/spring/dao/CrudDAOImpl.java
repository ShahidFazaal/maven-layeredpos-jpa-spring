package lk.ijse.dep.spring.dao;

import lk.ijse.dep.spring.entity.SuperEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,ID extends Serializable> implements CrudDAO<T,ID> {
    Class<T> entity;
    protected EntityManager entityManager;
    public CrudDAOImpl() {
        entity = (Class<T>)(((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public List<T> findAll() throws Exception {
        return entityManager.createQuery("FROM "+entity.getName()).getResultList();
    }

    @Override
    public T find(ID pk) throws Exception {
        return entityManager.find(entity,pk);
    }

    @Override
    public void save(T entity) throws Exception {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        entityManager.merge(entity);
    }

    @Override
    public void delete(ID key) throws Exception {
        entityManager.remove(entityManager.getReference(entity,key));
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager=entityManager;
    }
}
