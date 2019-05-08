package org.chipmonk.smartcity.traffic.dao;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.chipmonk.smartcity.dao.HibernateUtil;
import org.chipmonk.smartcity.traffic.model.Road;
import org.chipmonk.smartcity.traffic.model.TrafficEstimation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TrafficEstimationDAO {

	private static DriverManagerDataSource dataSource;
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
	public int save(List<TrafficEstimation> trafficList)
	{
		Session session=null;
		try
		{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			for(int trafficCounter=0;trafficCounter <trafficList.size();trafficCounter++)
			{
				session.save(trafficList.get(trafficCounter));
			}
			transaction.commit();
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		return 0;
	}
	public List<Road> getHighDensityRoadDetails(Date startDate,Date endDate,LocalTime startTime,LocalTime endTime) {
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select
		.query("select * from road r,traffic_estimation t where date(date) between ? and ? and speed>30 and time(date) between ? and ?",
				new Object[] {startDate,endDate,startTime,endTime},new RoadMapper());
		
	}
}
