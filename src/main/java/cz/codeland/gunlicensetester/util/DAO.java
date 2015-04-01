package cz.codeland.gunlicensetester.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO
{
  private static final Logger               LOGGER  = Logger.getAnonymousLogger();
  private static final ThreadLocal<Session> session = new ThreadLocal<>();
  private static SessionFactory sessionFactory;

  protected DAO()
  {
  }

  public static Session getSession()
  {
    Session session = DAO.session.get();
    if(session == null) {
      Configuration configuration = new Configuration().configure();
      ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties())
        .build();
      sessionFactory = configuration.buildSessionFactory(serviceRegistry);

      session = sessionFactory.openSession();
      DAO.session.set(session);
    }
    return session;
  }

  public static void close()
  {
    getSession().close();
    DAO.session.set(null);
  }

  public static void beginTransaction()
  {
    getSession().beginTransaction();
  }

  public static void commitTransaction()
  {
    getSession().getTransaction().commit();
  }

  public static void rollbackTransaction()
  {
    try {
      getSession().getTransaction().rollback();
    } catch(HibernateException e) {
      LOGGER.log(Level.WARNING, "DAO cannot rollback", e);
    }
    try {
      getSession().close();
    } catch(HibernateException e) {
      LOGGER.log(Level.WARNING, "DAO cannot close", e);
    }
    DAO.session.set(null);
  }
}
