package Lab2.beans;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RawBean implements Serializable {

    private final String unit = "my_unit";
    private List<Raw> raws;
    private Raw raw;

    private EntityTransaction transaction;
    private EntityManager manager;
    private EntityManagerFactory factory;
    
    public RawBean() {
        raws = new ArrayList<>();
        raw = new Raw();
        raw.setR_val(2.0);
        raw.setX_val(0.0);
        raw.setY_val(0.0);
        connect();
    }

    private void connect(){
        factory = Persistence.createEntityManagerFactory(unit);
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        loadRaws();
    }

    private void loadRaws(){
        try {
            transaction.begin();
            Query query = manager.createQuery("SELECT e FROM Raw e");
            raws = query.getResultList();
            transaction.commit();
        }catch (RuntimeException ex){
            if (transaction.isActive())
                transaction.rollback();
            throw ex;
        }
    }

    public List<Raw> getRaws() {
        return raws;
    }

    public Raw getRaw(){
        return raw;
    }

    public String addRow(){
        try{
            transaction.begin();
            raw.checkResult();
            manager.persist(raw);
            raws.add(raw);
            raw = new Raw();
            raw.setR_val(2.0);
            raw.setX_val(0.0);
            raw.setY_val(0.0);
            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw ex;
        }
        return "redirect";
    }

    public void clear(){
        raws.clear();
        try {
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Raw e");
            query.executeUpdate();
            manager.clear();
            transaction.commit();
        }catch (RuntimeException ex){
            if (transaction.isActive())
                transaction.rollback();
            throw ex;
        }
    }
}
