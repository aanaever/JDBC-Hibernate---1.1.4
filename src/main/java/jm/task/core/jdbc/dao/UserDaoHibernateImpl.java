package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
}
@Override
    public void createUsersTable() {
    try {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String createTab = "CREATE TABLE IF NOT EXISTS userdb.users" +
                    "(\n" +
                    "`id` INT NOT NULL AUTO_INCREMENT, \n" +
                    "`name` VARCHAR(45) NOT NULL, \n" +
                    "`lastName` VARCHAR(45) NOT NULL, \n" +
                    "`age` INT NOT NULL, \n" +
                    "PRIMARY KEY (`id`))";
            session.createSQLQuery(createTab).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
        }catch (HibernateException e){
        e.printStackTrace();
    }
    }

@Override
    public void dropUsersTable() {
    try{
    Session session = Util.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    try {
        String dropTab = "DROP TABLE IF EXISTS userdb.users";
        session.createSQLQuery(dropTab).executeUpdate();
        transaction.commit();
        session.close();
    } catch (Exception e) {
        e.printStackTrace();
        transaction.rollback();
        session.close();
    }
    }catch (HibernateException e){
        e.printStackTrace();
    }
}
@Override
    public void saveUser(String name, String lastName, byte age) {
    try{
    Session session = Util.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    try {
        session.save(new User(name, lastName, age));
        System.out.println("User с именем " + " добавлен в базу данных");
        transaction.commit();
        session.close();
    } catch (Exception e) {
        e.printStackTrace();
        transaction.rollback();
        session.close();
    }
    }catch (HibernateException e){
        e.printStackTrace();
    }
}
@Override
    public void removeUserById(long id) {
    try{
    Session session = Util.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    try {
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
    } catch (HibernateException e) {
        e.printStackTrace();
        transaction.rollback();
        session.close();
    }
    }catch (HibernateException e){
        e.printStackTrace();
    }
}
@Override
    public List<User> getAllUsers() {
    List<User> listAll = new ArrayList<>();
    try{
    Session session = Util.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    try {
      listAll = session.createQuery("FROM User").list();
        transaction.commit();
        session.close();
    } catch (Exception e) {
        e.printStackTrace();
        transaction.rollback();
        session.close();
    }
    }catch (HibernateException e){
        e.printStackTrace();
    }
    return listAll;
}

@Override
    public void cleanUsersTable() {
     try{
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String cleanTab = "TRUNCATE TABLE userdb.users";
            session.createSQLQuery(cleanTab).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
         session.close();
     }
     }catch (HibernateException e){
         e.printStackTrace();
     }
    }
}


