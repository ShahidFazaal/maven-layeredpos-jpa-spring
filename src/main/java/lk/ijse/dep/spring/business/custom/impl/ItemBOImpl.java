package lk.ijse.dep.spring.business.custom.impl;

import lk.ijse.dep.spring.business.custom.ItemBO;
import lk.ijse.dep.spring.dao.custom.ItemDAO;
import lk.ijse.dep.spring.db.JPAUtil;
import lk.ijse.dep.spring.entity.Item;
import lk.ijse.dep.spring.util.ItemTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class ItemBOImpl implements ItemBO {
    @Autowired
    ItemDAO itemDAO;
    public  String getNewItemCode() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);

        String lastItemCode = null;
        try {
            em.getTransaction().begin();
            lastItemCode = itemDAO.getLastItemCode();
            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }

            if (lastItemCode == null) {
                return "I001";
            } else {
                int maxId = Integer.parseInt(lastItemCode.replace("I", ""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "I00" + maxId;
                } else if (maxId < 100) {
                    id = "I0" + maxId;
                } else {
                    id = "I" + maxId;
                }
                return id;
            }
    }
    public  List<ItemTM> getAllItems() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);
        List<Item> allItems = null;
        try{
            em.getTransaction().begin();
            allItems = itemDAO.findAll();

            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }

            ArrayList<ItemTM> items = new ArrayList<>();
            for (Object entity : allItems) {
                Item item = (Item) entity;
                items.add(new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
            }
            return items;
    }
    public void saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);
        try{
            em.getTransaction().begin();
            itemDAO.save(new Item(code, description, unitPrice, qtyOnHand));
            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }
    }
    public void deleteItem(String itemCode) throws Exception{
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);
        try{
            em.getTransaction().begin();
            itemDAO.delete(itemCode);
            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }

            return ;

    }
    public void updateItem(String description, int qtyOnHand, BigDecimal unitPrice, String itemCode) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        itemDAO.setEntityManager(em);
        try{
            em.getTransaction().begin();
            itemDAO.update(new Item(itemCode, description, unitPrice, qtyOnHand));
            em.getTransaction().commit();
        }catch (Throwable t){
            em.getTransaction().rollback();
            throw t;
        }finally {
            em.close();
        }

    }


}
