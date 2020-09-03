package lk.ijse.dep.spring.dao.custom.impl;

import lk.ijse.dep.spring.dao.CrudDAOImpl;
import lk.ijse.dep.spring.dao.custom.OrderDAO;
import lk.ijse.dep.spring.entity.Order;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;
@Component
public class OrderDAOImpl extends CrudDAOImpl<Order,String> implements OrderDAO {


    @Override
    public String getLastOrderId() throws Exception {
        try {
            List resultList = entityManager.createQuery("SELECT o.id FROM lk.ijse.dep.spring.entity.Order o ORDER BY o.id DESC").setMaxResults(1).getResultList();
            return (resultList.size()>0? (String) resultList.get(0) :null);
        } catch (NoResultException e) {
            return null;
        }

    }

}
