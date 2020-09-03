package lk.ijse.dep.spring.dao.custom.impl;

import lk.ijse.dep.spring.dao.CrudDAOImpl;
import lk.ijse.dep.spring.dao.custom.OrderDetailDAO;
import lk.ijse.dep.spring.entity.*;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailDAOImpl extends CrudDAOImpl <OrderDetail,OrderDetailPK> implements OrderDetailDAO {

}
