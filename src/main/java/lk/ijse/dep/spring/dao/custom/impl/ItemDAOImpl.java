package lk.ijse.dep.spring.dao.custom.impl;

import lk.ijse.dep.spring.dao.CrudDAOImpl;
import lk.ijse.dep.spring.dao.custom.ItemDAO;
import lk.ijse.dep.spring.entity.Item;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;
@Component
public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {



    @Override
    public String getLastItemCode() throws Exception {
        try {
            List resultList = entityManager.createQuery("SELECT i.code FROM lk.ijse.dep.spring.entity.Item i ORDER BY i.code DESC").setMaxResults(1).getResultList();
            return (resultList.size()>0? (String) resultList.get(0) :null);
        } catch (NoResultException e) {
            return null;
        }

    }

}
