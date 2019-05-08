package org.chipmonk.smartcity.dao;

import org.chipmonk.smartcity.model.SmartCity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmartcityDAO {

	public int save(SmartCity smartcity) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(smartcity);
			tx.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;

	}

}
