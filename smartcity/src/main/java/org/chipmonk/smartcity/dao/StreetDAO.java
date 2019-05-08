package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.chipmonk.smartcity.model.Street;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class StreetDAO {
	
	public int save(Street s) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(s);
			tx.commit();
			session.close();
			return 1;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return 0;
	}

	public List<Street> getAll() {
		List<Street> list = new ArrayList<Street>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			list = session.createCriteria(Street.class).list();
			tx.commit();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}
	public List<Street> getAllStreets()
	{
		JdbcTemplate select=HibernateUtil.getJdbcTemplate();
		return select
				.query("select * from street",new Object[] {},new StreetMapper());
	}
	public List<Street> getAllStreetDetailsWithinWard()
	{
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
        return select
                .query(
                		"select s.* from street s",
                        new Object[] {},
                        new StreetMapper());
     }
	
	public List<Street> getStreetDetailsWithinWard(int streetId)
	{
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
        return select
                .query(
                		"select * from street  where id=?",
                        new Object[] {streetId},
                        new StreetMapper());
     }	
}

class StreetMapper implements RowMapper<Street>
{
	public Street mapRow(ResultSet resultSet,int rowNum)throws SQLException
	{
		Street street=new Street();
		street.setId(resultSet.getInt("id"));
		street.setWards_id(resultSet.getInt("wards_id"));
		street.setName(resultSet.getString("name"));
		return street;
		
		
	}
}
