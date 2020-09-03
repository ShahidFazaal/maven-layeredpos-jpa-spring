package lk.ijse.dep.spring.dao.custom;

import lk.ijse.dep.spring.dao.CrudDAO;
import lk.ijse.dep.spring.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public  String getLastCustomerId() throws Exception;
}
