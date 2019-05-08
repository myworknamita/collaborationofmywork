package org.chipmonk.smartcity.traffic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.chipmonk.smartcity.dao.HibernateUtil;
import org.chipmonk.smartcity.traffic.model.Road;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class RoadDAO {
	private static DriverManagerDataSource dataSource;
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
	public int save(List<Road> roadList)
	{
		Session session=null;
		try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			for(int roadCounter=0;roadCounter<roadList.size();roadCounter++)
			{
				session.save(roadList.get(roadCounter));
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
	public List<Road> getRoadIntersection(int id)
	{
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select 
				.query("select distinct r.id,name,type_id,directionality_id,capacity from road_mapping m ,road r where r.id=m.road2_id and road2_id in (select m.road2_id from road_mapping m where m.road1_id=?)", 
						new Object[] {id},new RoadMapper());
		
	}
	public List<Road> getAll() {
		List<Road> list = new ArrayList<Road>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			list = session.createCriteria(Road.class).list();
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}
	/*public List<Road> getAll()
	{
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select
				.query("select * from road",new Object[] {},new RoadMapper());
	}*/
	public List<Road> getRoadBasedOnType(String type) {
	
		JdbcTemplate select=new JdbcTemplate(dataSource);
		if(type.equalsIgnoreCase("kaccha"))
		{
		return select
				.query("select * from road r,type t where r.type_id=t.id and t.id=1",new Object[] {},new RoadMapper());
		}
		else if(type.equalsIgnoreCase("tarred"))
		{
			return select
					.query("select * from road r,type t where r.type_id=t.id and t.id=2",new Object[] {},new RoadMapper());
		}
		else
		{
			return select
					.query("select * from road r,type t where r.type_id=t.id and t.id=3",new Object[] {},new RoadMapper());
		}
		
	}
	public List<Road> getRoadBasedOnDirectionality(String directionality) {
		JdbcTemplate select=new JdbcTemplate(dataSource);
		if(directionality.equalsIgnoreCase("oneway"))
		{
		return select
				.query("select * from road r,directionality d where r.directionality_id=d.id and d.id=1",new Object[] {},new RoadMapper() );
		}
		else
		{
			return select
					.query("select * from road r,directionality d where r.directionality_id=d.id and d.id=2",new Object[] {},new RoadMapper() );
				
		}
	}
	
}
class RoadMapper implements RowMapper<Road>
{

	@Override
	public Road mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Road road=new Road();
		road.setId(resultSet.getInt("id"));
		road.setDirectionality(resultSet.getInt("directionality_id"));
		road.setName(resultSet.getString("name"));
		road.setCapacity(resultSet.getInt("capacity"));
		road.setType(resultSet.getInt("type_id"));
		return road;
	}
	
}
