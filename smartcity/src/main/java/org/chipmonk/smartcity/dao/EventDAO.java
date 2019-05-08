package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.chipmonk.smartcity.model.Event;
import org.chipmonk.smartcity.model.LightCount;
import org.chipmonk.smartcity.model.MaxLightsOff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class EventDAO {
	
	private static DriverManagerDataSource dataSource;
	public int save(ArrayList<Event> eventList) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			for(int eventCount=0;eventCount<eventList.size();eventCount++)
			{
			session.save(eventList.get(eventCount));
			}
			tx.commit();
			return 1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		return 0;
	}
	
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
}

