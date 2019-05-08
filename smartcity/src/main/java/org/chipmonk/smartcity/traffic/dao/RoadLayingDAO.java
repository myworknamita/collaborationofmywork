package org.chipmonk.smartcity.traffic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.chipmonk.smartcity.dao.HibernateUtil;
import org.chipmonk.smartcity.traffic.model.RoadLaying;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class RoadLayingDAO {
 
	private static DriverManagerDataSource dataSource;
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
	public int save(List<RoadLaying> roadLayingList)
	{
		Session session=null;
		try
		{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			for(int layingCounter=0;layingCounter<roadLayingList.size();layingCounter++)
			{
				session.save(roadLayingList.get(layingCounter));
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
	public List<RoadLaying> getAll() {
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select.query("select * from road_laying",new Object[] {},new RoadLayingMapper());
	}
}
class RoadLayingMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet resultSet, int arg1) throws SQLException {
		RoadLaying roadLaying=new RoadLaying();
		roadLaying.setContractorName(resultSet.getString("contractor_name"));
		roadLaying.setCost(resultSet.getFloat("cost"));
		roadLaying.setDate(resultSet.getDate("date"));
		roadLaying.setId(resultSet.getInt("id"));
		roadLaying.setRoadId(resultSet.getInt("road_id"));
		return roadLaying;
	}
	
}
