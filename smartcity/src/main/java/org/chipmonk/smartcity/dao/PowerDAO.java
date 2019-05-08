package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.chipmonk.smartcity.model.Power;
import org.chipmonk.smartcity.model.PowerConsumedForEveryWardOnHourlyBasis;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class PowerDAO {
	public int save(ArrayList<Power> powerList) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			for (int powerCounter = 0; powerCounter < powerList.size(); powerCounter++) {
				session.save(powerList.get(powerCounter));
				if(powerCounter%100==0)
				{
					session.flush();
					session.clear();
				}
			}
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
	public List<PowerConsumedForEveryWardOnHourlyBasis> getPowerConsumedForEveryWardOnHourlyBasis() {
		JdbcTemplate select=HibernateUtil.getJdbcTemplate();
		return select
				.query("select hour(date) as hour,sum(power)as powerConsumed,w.id as wardId from power_info p,light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and l.id=p.light_id and p.date between '2018-01-01' and '2018-01-31' group by hour(date),w.id", new Object[] {},new PowerConsumedForEveryWardOnHourlyBasisMapper());
	}
}
class PowerConsumedForEveryWardOnHourlyBasisMapper implements RowMapper<PowerConsumedForEveryWardOnHourlyBasis>{

	@Override
	public PowerConsumedForEveryWardOnHourlyBasis mapRow(ResultSet resultSet, int arg1) throws SQLException {
		PowerConsumedForEveryWardOnHourlyBasis powerInstance=new PowerConsumedForEveryWardOnHourlyBasis();
		powerInstance.setHour(resultSet.getInt("hour"));
		powerInstance.setPowerconsumed(resultSet.getFloat("powerConsumed"));
		powerInstance.setWardId(resultSet.getInt("wardId"));
		return powerInstance;
	}
	
}
