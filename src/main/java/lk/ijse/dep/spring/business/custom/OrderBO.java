package lk.ijse.dep.spring.business.custom;

import lk.ijse.dep.spring.business.SuperBO;
import lk.ijse.dep.spring.util.OrderDetailTM;
import lk.ijse.dep.spring.util.OrderTM;

import java.util.List;

public interface OrderBO extends SuperBO {
    public  String getNewOrderId() throws Exception;
    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception;

}
