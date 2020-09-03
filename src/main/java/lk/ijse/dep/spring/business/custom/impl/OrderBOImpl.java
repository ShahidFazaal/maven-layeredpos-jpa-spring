package lk.ijse.dep.spring.business.custom.impl;

import lk.ijse.dep.spring.business.custom.OrderBO;
import lk.ijse.dep.spring.dao.custom.CustomerDAO;
import lk.ijse.dep.spring.dao.custom.ItemDAO;
import lk.ijse.dep.spring.dao.custom.OrderDAO;
import lk.ijse.dep.spring.dao.custom.OrderDetailDAO;
import lk.ijse.dep.spring.db.JPAUtil;
import lk.ijse.dep.spring.entity.Item;
import lk.ijse.dep.spring.entity.Order;
import lk.ijse.dep.spring.entity.OrderDetail;
import lk.ijse.dep.spring.util.OrderDetailTM;
import lk.ijse.dep.spring.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
@Component
public class OrderBOImpl implements OrderBO {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    CustomerDAO customerDAO;

    public  String getNewOrderId() throws Exception{
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        orderDAO.setEntityManager(em);

        String lastOrderId = null;
        try {
            em.getTransaction().begin();
            lastOrderId = orderDAO.getLastOrderId();
            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }


            if (lastOrderId == null) {
                return "OD001";
            } else {
                int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "OD00" + maxId;
                } else if (maxId < 100) {
                    id = "OD0" + maxId;
                } else {
                    id = "OD" + maxId;
                }
                return id;
            }
    }
    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception{
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);
        orderDAO.setEntityManager(em);
        orderDetailDAO.setEntityManager(em);
        customerDAO.setEntityManager(em);

        try {
           em.getTransaction().begin();
           orderDAO.save(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()),customerDAO.find(order.getCustomerId())));


            for (OrderDetailTM orderDetail : orderDetails) {
                orderDetailDAO.save(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                Object entity = itemDAO.find(orderDetail.getCode());
                Item item = (Item) entity;
                item.setQtyOnHand(item.getQtyOnHand()-orderDetail.getQty());
                itemDAO.update(item);

            }
            em.getTransaction().commit();


        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw  t;

        } finally {
                em.close();
                }
            }
        }

