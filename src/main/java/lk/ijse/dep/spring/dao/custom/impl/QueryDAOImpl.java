package lk.ijse.dep.spring.dao.custom.impl;

import lk.ijse.dep.spring.dao.custom.QueryDAO;
import lk.ijse.dep.spring.entity.CustomEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Component
public class QueryDAOImpl implements QueryDAO {
    private EntityManager entityManager;

    @Override
    public CustomEntity getOrderDetail(String orderId) throws Exception {
        try {
            Object[] singleResult = (Object[]) entityManager.createNativeQuery("SELECT o.id AS orderId,c.name AS customerName,o.date AS orderDate  FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id WHERE o.id =?1").setParameter(1, orderId).getSingleResult();
            return new CustomEntity((String) singleResult[0],(String) singleResult[1],(Date) singleResult[2]);
        } catch (NoResultException e) {
            return null;
            
        }


    }


    @Override
    public CustomEntity getOrderDetail2(String orderId) throws Exception {
        try {
            Object[] singleResult = (Object[]) entityManager.createNativeQuery("SELECT o.id,c.name,c.id FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id WHERE o.id=:id").getSingleResult();
            return new CustomEntity((String) singleResult[0],(String)singleResult[1],(String)singleResult[2]);
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<CustomEntity> getAllDetails() throws Exception {
        List<Object[]>  list = entityManager.createNativeQuery("SELECT o.id AS orderId,o.date AS orderDate,c.name AS customerName,c.id AS customerId,SUM(od.qty * od.unitPrice) AS total FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id INNER JOIN OrderDetail od on o.id = od.orderId GROUP BY 1").getResultList();
        ArrayList<CustomEntity> allOrderDetails = new ArrayList<>();

        for (Object[] objects : list) {
            allOrderDetails.add(new CustomEntity((String) objects[0],(String) objects[2],(Date) objects[1],(String) objects[3],((BigDecimal) objects[4]).intValue()));
        }

return allOrderDetails;
//        for (CustomEntity o : resultList) {
//            System.out.println(o);
//        }
//
//        return resultList;

    }
    

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

    }
}
