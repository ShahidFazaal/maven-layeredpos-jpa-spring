package lk.ijse.dep.spring.dao.custom;

import lk.ijse.dep.spring.dao.CrudDAO;
import lk.ijse.dep.spring.entity.Order;

public interface OrderDAO extends CrudDAO<Order,String> {
    public  String getLastOrderId() throws Exception;
}
