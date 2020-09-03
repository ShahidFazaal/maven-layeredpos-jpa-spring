package lk.ijse.dep.spring.dao.custom.impl;

import lk.ijse.dep.spring.dao.CrudDAOImpl;
import lk.ijse.dep.spring.dao.custom.CustomerDAO;
import lk.ijse.dep.spring.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAOImpl extends CrudDAOImpl<Customer,String> implements CustomerDAO {


    @Override
    public String getLastCustomerId() throws Exception {
        return (String) entityManager.createNativeQuery("SELECT c.id FROM Customer c ORDER BY id DESC LIMIT 1").getSingleResult();
//            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer ORDER BY id DESC LIMIT 1");
//            if (!rst.next()){
//                return null;
//            }else{
//                return rst.getString(1);
//            }
    }

}
