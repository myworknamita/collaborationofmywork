package org.chipmonk.smartcity.traffic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.chipmonk.smartcity.dao.HibernateUtil;
import org.chipmonk.smartcity.traffic.model.Vehicle;
import org.chipmonk.smartcity.traffic.model.VehicleCount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class VehicleDAO {

	private static DriverManagerDataSource dataSource;
	public void setDataSource()
	{
	 dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://smartcity.chtvslcfju6w.us-east-2.rds.amazonaws.com:3306/smartcity");
        dataSource.setUsername("chipmonk");
        dataSource.setPassword("Ch!pm0nk");	
	}
	public int save(List<Vehicle> vehicleList)
	{
		Session session=null;
		try
		{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			for(int vehicleCounter=0;vehicleCounter<vehicleList.size();vehicleCounter++)
			{
				session.save(vehicleList.get(vehicleCounter));
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
	public List<VehicleCount> getVehicleCount(String ownership,Date startDate,Date endDate,LocalTime startTime,LocalTime endTime) {
		JdbcTemplate select=new JdbcTemplate(dataSource);
		return select
				.query("select count(v.type) as count,v.type type from vehicle v,road r,traffic_estimation t where r.id=t.road_id and r.id=v.road_id and v.ownership like ? and date(t.date) between ? and ? and time(t.date) between ? and ? group by v.type",
						new Object[] {ownership,startDate,endDate,startTime,endTime},new VehicleCountMapper() );
	}
}
class VehicleCountMapper implements RowMapper<VehicleCount>
{
	@Override
	public VehicleCount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		VehicleCount vehicle=new VehicleCount();
		vehicle.setNumOfVehicles(resultSet.getInt("count"));
		vehicle.setVehicleType(resultSet.getString("type"));
		return vehicle;
	}
	
}
