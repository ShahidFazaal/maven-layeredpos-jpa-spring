package lk.ijse.dep.spring.dao.custom;

import lk.ijse.dep.spring.dao.CrudDAO;
import lk.ijse.dep.spring.entity.Item;

public interface ItemDAO extends CrudDAO<Item,String> {
    public  String getLastItemCode() throws Exception;
}
