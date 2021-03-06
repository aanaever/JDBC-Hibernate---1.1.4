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
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String createTab = "CREATE TABLE IF NOT EXISTS userdb.users" +
                    "(\n" +
                    "`id` INT NOT NULL AUTO_INCREMENT, \n" +
                    "`name` VARCHAR(45) NOT NULL, \n" +
                    "`lastName` VARCHAR(45) NOT NULL, \n" +
                    "`age` INT NOT NULL, \n" +
                    "PRIMARY KEY (`id`))";
            session.createSQLQuery(createTab).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String dropTab = "DROP TABLE IF EXISTS userdb.users";
            session.createSQLQuery(dropTab).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + " добавлен в базу данных");
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listAll = new ArrayList<>();
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            listAll = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return listAll;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String cleanTab = "TRUNCATE TABLE userdb.users";
            session.createSQLQuery(cleanTab).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (HibernateException w) {
                w.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}


