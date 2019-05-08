package org.chipmonk.smartcity.dao;

import org.chipmonk.smartcity.model.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StatusDAO {

	public int save(Status status) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(status);
			tx.commit();
			return 1;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;
	}
}
