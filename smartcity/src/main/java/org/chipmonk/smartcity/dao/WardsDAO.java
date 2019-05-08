package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.chipmonk.smartcity.controller.SmartcityController;
import org.chipmonk.smartcity.model.Wards;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class WardsDAO {
	public int save(Wards w) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(w);
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

	public List<Wards> getAll() {
		List<Wards> list = new ArrayList<Wards>();
		Session session = null;
		try {
			final Logger logger = Logger.getLogger(SmartcityController.class.getName());
			logger.info("in WardDAO class->get session factory object");
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			logger.info("open session\n");
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			list = session.createCriteria(Wards.class).list();
			tx.commit();
			logger.info("return List");
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	public List<Wards> getWardDetails(int wardId) {

		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query("select * from wards where id = ?", new Object[] { wardId }, new WardMapper());
	}

	public List<Wards> getAllWards() {

		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query("select * from wards ", new Object[] {}, new WardMapper());
	}
}

class WardMapper implements RowMapper<Wards> {

	public Wards mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Wards ward = new Wards();
		ward.setId(resultSet.getInt("id"));
		ward.setName(resultSet.getString("name"));
		ward.setSmartcity_id(resultSet.getInt("smartcity_id"));
		return ward;
	}

}
