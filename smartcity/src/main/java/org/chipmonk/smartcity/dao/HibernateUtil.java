package org.chipmonk.smartcity.dao;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
public class HibernateUtil {
	private static SessionFactory factory;
	private static DriverManagerDataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	private HibernateUtil()
	{
		
	}
@SuppressWarnings("deprecation")
public static synchronized SessionFactory getSessionFactory()
{
	if(factory==null)
	{
		factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}
	return factory;
}
public static synchronized void setDataSource()
{

		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
		dataSource.setUsername("chipmonk");
		dataSource.setPassword("Ch!pm0nk");
}
public static synchronized JdbcTemplate getJdbcTemplate()
{
	setDataSource();
	if(jdbcTemplate==null)
	{
		jdbcTemplate=new JdbcTemplate(dataSource);
	}
	return jdbcTemplate;
	
}
}







