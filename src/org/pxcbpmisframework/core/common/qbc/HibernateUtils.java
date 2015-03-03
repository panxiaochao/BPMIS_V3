package org.pxcbpmisframework.core.common.qbc;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static final Logger logger = Logger.getLogger(HibernateUtils.class);
	// private static final String DEFAULT_HIBERNATE_CFG = "/hibernate.cfg.xml";
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	static {
		try {
			configuration = ConfigurationUtils.getHibernateConfig();
			sessionFactory = SessionFactoryUtils.getSessionFactory();
		} catch (Exception ex) {
			logger.error(ex);
			System.err
					.println("****************** Error Creating SessionFactory ***********************");
			ex.printStackTrace();
		}
	}

	private HibernateUtils() {
	}

	/**
	 * 
	 * @Title: beginTransaction
	 * @Description: TODO(获得beginTransaction)
	 */
	public static Transaction beginTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx == null) {
				logger.debug("Starting new database transaction in this thread.");
				tx = currentSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException ex) {
			logger.error(ex);
			// throw new BaseRuntimeException(ex);
		}
		return tx;
	}
	/**
	 * 
	 * @Title: CommitTransaction
	 * @Description: TODO(提交事务CommitTransaction)
	 */
	public static void CommitTransaction() {
		Transaction tran = (Transaction) threadTransaction.get();
		try {
			if (tran != null && !tran.wasCommitted() && !tran.wasRolledBack()) {
				tran.commit();
				threadTransaction.set(null);
			}
		} catch (HibernateException e) {
			// TODO: handle exception
			logger.error(e);
			RollbackTransaction();
		}
		
	}

	/**
	 * 
	 * @Title: RollbackTransaction
	 * @Description: TODO(回滚RollbackTransaction)
	 */
	public static void RollbackTransaction() {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			threadTransaction.set(null);
			if ((tx != null) && (!tx.wasCommitted()) && (!tx.wasRolledBack())) {
				logger
						.debug("Tyring to rollback database transaction of this thread.");
				tx.rollback();
			}
		} catch (HibernateException ex) {
			logger.error(ex);
			// throw new BaseRuntimeException(ex);
		} finally {
			closeSession();
		}
	}

	/**
	 * 获得当前的Hibernate Session
	 * 
	 * @return session
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) threadLocalSession.get();
		try {
			if (s == null) {
				s = sessionFactory.openSession();
				threadLocalSession.set(s);
			}
			logger.info("<---Session openSession--->");
			// System.err.println("--------------Session openSession------------");
		} catch (Exception e) {
			logger.error("get currentSession error", e);
		}
		return s;
	}

	/**
	 * 返回ThreadLocal中的session实例
	 */
	public static Session getSession() {
		Session session = (Session) threadLocalSession.get();
		try {
			if (session == null || !session.isOpen()) {
				if (sessionFactory == null) {
					rebuildSessionFactory();
				}
				session = (sessionFactory != null) ? sessionFactory
						.openSession() : null;
				threadLocalSession.set(session);
			}
			logger.info("<---Session openSession--->");
		} catch (HibernateException ex) {
			logger.error(ex);
			ex.printStackTrace();
			// TODO: handle exception
		}

		return session;
	}

	/**
	 * 返回Hibernate的SessionFactory
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration = ConfigurationUtils.getHibernateConfig();
			sessionFactory = SessionFactoryUtils.getSessionFactory();
		} catch (Exception ex) {
			logger.error(ex);
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			ex.printStackTrace();
		}
	}

	/**
	 * 关闭Session实例
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocalSession.get();
		if (session != null) {
			threadLocalSession.set(null);
			if (session.isOpen()) {
				session.close();
				logger.debug("session关闭了");
			} else {
				logger.debug("session已经是关闭的");
			}
		}
		System.err.println("--------------Session close------------");
	}

	/**
	 * 返回SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static ThreadLocal<Session> getThreadlocalsession() {
		return threadLocalSession;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtils.sessionFactory = sessionFactory;
	}

	public static void setConfiguration(Configuration configuration) {
		HibernateUtils.configuration = configuration;
	}

	public static ThreadLocal<Transaction> getThreadtransaction() {
		return threadTransaction;
	}

	/* test */
	public static void main(String[] args) {

		System.out.println(HibernateUtils.getConfiguration());
		System.out.println(HibernateUtils.getSessionFactory());
		System.out.println(HibernateUtils.currentSession());
		System.out.println(HibernateUtils.getSession());
	}

}
