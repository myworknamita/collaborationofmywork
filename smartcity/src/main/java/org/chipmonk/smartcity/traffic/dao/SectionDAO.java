package org.chipmonk.smartcity.traffic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.chipmonk.smartcity.dao.HibernateUtil;
import org.chipmonk.smartcity.traffic.model.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SectionDAO {

	private static DriverManagerDataSource dataSource;
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
	public int save(List<Section> sectionList)
	{
		Session session=null;
		try
		{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			for(int sectionCounter=0;sectionCounter<sectionList.size();sectionCounter++)
			{
				session.save(sectionList.get(sectionCounter));
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
	public List<Section> getAll()
	{
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select
				.query("select * from section",new Object[] {},new SectionMapper());
	}
}
class SectionMapper implements RowMapper<Section>
{

	@Override
	public Section mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Section section=new Section();
		section.setId(resultSet.getInt("id"));
		section.setRoadId(resultSet.getInt("road_id"));
		section.setLength(resultSet.getFloat("length"));
		section.setWidth(resultSet.getFloat("width"));
		return section;
	}
	
}