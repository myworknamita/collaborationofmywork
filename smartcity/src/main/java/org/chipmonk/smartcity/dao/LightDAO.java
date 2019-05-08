package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.chipmonk.smartcity.model.FailedLightCount;
import org.chipmonk.smartcity.model.Light;
import org.chipmonk.smartcity.model.LightCount;
import org.chipmonk.smartcity.model.MaxLightsOff;
import org.chipmonk.smartcity.model.OffLightCount;
import org.chipmonk.smartcity.model.PowerConsmedForWard;
import org.chipmonk.smartcity.model.PowerConsumedForPeriod;
import org.chipmonk.smartcity.model.Street;
import org.chipmonk.smartcity.model.Wards;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LightDAO {
	public int save(ArrayList<Light> lightList) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			for (int lightCounter = 0; lightCounter < lightList.size(); lightCounter++) {
				session.save(lightList.get(lightCounter));
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

	public List<Light> getAll() {
		List<Light> list = new ArrayList<Light>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			list = session.createCriteria(Light.class).list();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	public int getStreetLightsCount(int id) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			org.hibernate.Query query = session.createSQLQuery("select * from light l where l.street_id= :id")
					.addEntity(Light.class).setParameter("id", id);
			int lightsCount = query.list().size();
			return lightsCount;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;
	}

	public int getLightsCount(int wardId, int streetId) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			org.hibernate.Query query = session
					.createSQLQuery(
							"select * from light l,street s,wards w where l.street_id= :id1 and s.wards_id= :id2")
					.addEntity(Light.class).setParameter("id1", streetId).setParameter("id2", wardId);
			int lightsCount = query.list().size();
			return lightsCount;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;
	}

	public int getWardLightsCount(int wardId) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			org.hibernate.Query query = session.createSQLQuery(
					"select * from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and w.id= :id")
					.addEntity(Wards.class).addEntity(Street.class).addEntity(Light.class).setParameter("id", wardId);
			int lightsCount = query.list().size();
			return lightsCount;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;
	}

	public List<MaxLightsOff> getMaxLightsOff(Light light) {

		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select s.name,count(actual_status) as statusCount from light l,street s where l.street_id=s.id and actual_status=2 and last_off_time between ? and ? group by s.name order by count(actual_status) desc limit 3",
				new Object[] { light.getLast_on_time(), light.getLast_off_time() }, new MaxLightsOffMapper());
	}

	public List<Light> getStreetLightDetails(int streetId) {
		Session session=null;
		List<Light> lightList=new ArrayList<Light>();
		try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			lightList=session.createCriteria(Light.class).add(Restrictions.eq("street_id", streetId)).list();
			return lightList;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null)
				session.close();
		}
		return lightList;
	}

	public List<Light> getLightDetails(int streetId, int wardId) {

		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select * from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and l.street_id = ? and s.wards_id = ?",
			new Object[] { streetId, wardId }, new LightMapper());
	}

	public Light getLightDetails(int id) {
		
		Session session=null;
		Light light=new Light();
		try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			 light=(Light)session.get(Light.class, id);
			System.out.println(light);
			return light;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null)
				session.close();
		}
		return light;
	}

	public List<Light> getWardLightDetails(int wardId) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select * from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and s.wards_id = ?",
			new Object[] {wardId }, new LightMapper());
	}

	public List<LightCount> getWardLightCount(int id) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and w.id=?",
				new Object[] { id }, new LightCountMapper());
	}

	public List<LightCount> getStreetLightCount(int id) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and s.id=?",
				new Object[] { id }, new LightCountMapper());
	}

	public List<LightCount> getwardFailedLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and w.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<LightCount> getstreetFailedLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and s.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<LightCount> getwardOffLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and w.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<LightCount> getstreetOffLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and s.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<LightCount> getwardOnLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and w.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<LightCount> getStreetOnLightCount(int id, int status) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select count(*) as Num from light l,street s,wards w where l.street_id=s.id and s.wards_id=w.id and s.id=? and actual_status=?",
				new Object[] { id, status }, new LightCountMapper());
	}

	public List<FailedLightCount> getwardFailedLightCountBasedOnTime(String status, int id, int timePeriod,
			Date startDate, Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id and s.wards_id=w.id and w.id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id and s.wards_id=w.id and w.id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id and s.wards_id=w.id and w.id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}
	}

	public List<FailedLightCount> getstreetFailedLightCountBasedOnTime(String status, int id, int timePeriod,
			Date startDate, Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}

	}

	public List<PowerConsmedForWard> getPowerConsumedByEveryWard() {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select  w.id as id, sum(power) as power from power_info p,light l,street s,wards w where  w.id=s.wards_id and l.street_id=s.id and p.light_id=l.id and p.date between '2009-01-26 11:00:00' and '2009-01-27 11:00:00' group by w.id",
				new Object[] {}, new PowerConsumedMapper());
	}

	public List<OffLightCount> getOffLightCountForEveryWard() {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		return select.query(
				"select w.id as id, count(actual_status) as numOfLights from light l,street s, wards w where w.id=s.wards_id and s.id=l.street_id and actual_status=2 group by w.id",
				new Object[] {}, new OffLightCountMapper());
	}

	public List<PowerConsumedForPeriod> getPowerConsumedBasedOnTimeForStreet(int id, int timePeriod, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select sum(power) as power,dayOfyear(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and s.id=?  and p.date between ? and ? group by dayOfyear(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select sum(power) as power,weekOfyear(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and s.id=?  and p.date between ? and ? group by weekOfyear(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		} else {
			return select.query(
					"select sum(power) as power,month(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and s.id=?  and p.date between ? and ? group by month(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		}

	}

	public List<PowerConsumedForPeriod> getPowerConsumedBasedOnTimeForWard(int id, int timePeriod, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select sum(power) as power,dayOfyear(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and w.id=?  and p.date between ? and ? group by dayOfyear(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select sum(power) as power,weekOfyear(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and w.id=?  and p.date between ? and ? group by weekOfyear(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		} else {
			return select.query(
					"select sum(power) as power,month(p.date) as id from light l,street s,wards w,power_info p where l.street_id=s.id and p.light_id=l.id and s.wards_id=w.id and w.id=?  and p.date between ? and ? group by month(p.date)",
					new Object[] { id, startDate, endDate }, new PowerConsumedBasedOnTime());
		}

	}

	public List<FailedLightCount> getStreetOnLightCountBasedOnTime(String status, int id, int timePeriod,
			Date startDate, Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}

	}

	public List<FailedLightCount> getWardOnLightCountBasedOnTime(String status, int id, int timePeriod, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}

	}

	public List<FailedLightCount> getWardOffLightCountBasedOnTime(String status, int id, int timePeriod, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}

	}

	public List<FailedLightCount> getStreetOffLightCountBasedOnTime(String status, int id, int timePeriod,
			Date startDate, Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (timePeriod == 1) {
			return select.query(
					"select count(*) as Num,dayOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by dayOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (timePeriod == 7) {
			return select.query(
					"select count(*) as Num,weekOfyear(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by weekOfyear(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else {
			return select.query(
					"select count(*) as Num,month(e.time) as day from light l,street s,wards w,event e where l.street_id=s.id and e.light_id=l.id  and l.street_id=? and action like ? and e.time between ? and ? group by month(e.time)",
					new Object[] { id, status, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}
	}

	public List<FailedLightCount> getHistoricalLightCountInStreetDateRange(int streetId, int period, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (period == 1) {
			return select.query(
					"select dayofyear(l.installed_date) as day,count(*) as Num from light l,street s where s.id = l.street_id and s.id=? and l.installed_date between ? and ? group by dayofyear(installed_date)",
					new Object[] { streetId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (period == 7) {
			return select.query(
					"select weekofyear(l.installed_date) as day,count(*) as Num from light l,street s where s.id = l.street_id and s.id=? and l.installed_date between ? and ? group by weekofyear(l.installed_date)",
					new Object[] { streetId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (period >= 28 && period <= 31) {
			return select.query(
					"select month(l.installed_date) as day,count(*) as Num from light l,street s where s.id = l.street_id and s.id=? and l.installed_date between ? and ? group by month(l.installed_date)",
					new Object[] { streetId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}
		return null;
	}

	public List<FailedLightCount> getHistoricalLightCountInWardDateRange(int wardsId, int period, Date startDate,
			Date endDate) {
		JdbcTemplate select = HibernateUtil.getJdbcTemplate();
		if (period == 1) {
			return select.query(
					"select dayofyear(l.installed_date) as day ,count(*) as Num from light l,street s,wards w where w.id=s.wards_id and s.id = l.street_id and w.id=? and l.installed_date between ? and ? group by dayofyear(l.installed_date)",
					new Object[] { wardsId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (period == 7) {
			return select.query(
					"select weekofyear(l.installed_date) as day ,count(*) as Num from light l,street s,wards w where w.id=s.wards_id and s.id = l.street_id and w.id=? and l.installed_date between ? and ? group by weekofyear(l.installed_date)",
					new Object[] { wardsId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		} else if (period >= 28 && period <= 31) {
			return select.query(
					"select month(l.installed_date) as day ,count(*) as Num from light l,street s,wards w where w.id=s.wards_id and s.id = l.street_id and w.id=? and l.installed_date between ? and ? group by month(l.installed_date)",
					new Object[] { wardsId, startDate, endDate }, new LightCountMapperBaesdOnTime());
		}
		return null;
	}

	public Date[] getOnOffTime() {
		Date[] date = { new Date(), new Date() };
		LocalDate d = LocalDate.now();
		String lastOnTimeStirng = d.toString() + " 18:00:00";
		String lastOffTimeString = d.toString() + " 06:00:00";
		Date lastOnTime = null, lastOffTime = null;
		try {
			lastOffTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lastOffTimeString);
			lastOnTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(lastOnTimeStirng);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date[0] = lastOnTime;
		date[1] = lastOffTime;
		return date;

	}

	public void updateOnOffTimeForLight() {
		Session session = null;
		Date[] date = getOnOffTime();
		System.out.println(date[0]);
		System.out.println(date[1]);
		int status=1;
		if(System.currentTimeMillis()<date[0].getTime() && System.currentTimeMillis()>=date[1].getTime()) {
			status=2;
		}
		else if(System.currentTimeMillis()>=date[0].getTime() && System.currentTimeMillis()<date[1].getTime()) {
			status=1;
		}
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("update Light l set l.last_off_time= :date2,last_on_time=:date1,actual_status = :status,required_status= :status");
			query.setParameter("date1", date[0]);
			query.setParameter("date2", date[1]);
			System.out.println(date[0]);
			System.out.println(date[1]);
			query.setParameter("status", status);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void updateLightStatus() {
		int status = randomGeneratorToToggleStatus();
		int id = randomGeneratorForLightId();
		System.out.println(id);
		System.out.println(status);
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("update Light set actualStatus=:status where id=:id");
			query.setParameter("status", status);
			query.setParameter("id", id);
			query.executeUpdate();
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

	}

	public static int randomGeneratorToToggleStatus() {
		int high = 3;
		int low = 1;
		Random random = new Random();
		return random.nextInt(high - low) + low;
	}

	public static int randomGeneratorForLightId() {
		int low = 1;
		int high = 250;
		Random random = new Random();
		return random.nextInt(high - low) + low;
	}

}

class OffLightCountMapper implements RowMapper<OffLightCount> {

	@Override
	public OffLightCount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		OffLightCount offLightCount = new OffLightCount();
		offLightCount.setId(resultSet.getInt("id"));
		offLightCount.setNumOfLights(resultSet.getInt("numOfLights"));
		return offLightCount;
	}

}

class LightCountMapperBaesdOnTime implements RowMapper<FailedLightCount> {

	@Override
	public FailedLightCount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		FailedLightCount failedLightCount = new FailedLightCount();
		failedLightCount.setNumOfLights(resultSet.getInt("Num"));
		failedLightCount.setPeriod(resultSet.getInt("day"));
		return failedLightCount;

	}

}
class LightMapper implements RowMapper<Light> {
	public Light mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Light light = new Light();
		light.setId(resultSet.getInt("id"));
		light.setName(resultSet.getString("name"));
		light.setStreet_id(resultSet.getInt("street_id"));
		light.setLast_on_time(resultSet.getDate("last_on_time"));
		light.setLast_off_time(resultSet.getDate("last_off_time"));
		light.setPower_rating(resultSet.getInt("power_rating"));
		light.setManufactured_date(resultSet.getDate("manufactured_date"));
		light.setInstalled_date(resultSet.getDate("installed_date"));
		light.setActualStatus(resultSet.getInt("actual_status"));
		light.setRequiredStatus(resultSet.getInt("required_status"));
		light.setLongitude(resultSet.getDouble("longitude"));
		light.setLatitude(resultSet.getDouble("latitude"));
		return light;
	}

}

class MaxLightsOffMapper implements RowMapper<MaxLightsOff> {

	public MaxLightsOff mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		MaxLightsOff maxLightsOff = new MaxLightsOff();
		maxLightsOff.setLocation(resultSet.getString("s.name"));
		maxLightsOff.setStatusCount(resultSet.getInt("statusCount"));
		return maxLightsOff;
	}

}

class LightCountMapper implements RowMapper<LightCount> {

	@Override
	public LightCount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		LightCount lightCount = new LightCount();
		lightCount.setNumOfLights(resultSet.getInt("Num"));
		return lightCount;
	}

}

class PowerConsumedMapper implements RowMapper<PowerConsmedForWard> {

	@Override
	public PowerConsmedForWard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		PowerConsmedForWard pc = new PowerConsmedForWard();
		pc.setId(resultSet.getInt("id"));
		pc.setPower(resultSet.getFloat("power"));
		return pc;
	}
}

class PowerConsumedBasedOnTime implements RowMapper<PowerConsumedForPeriod> {

	@Override
	public PowerConsumedForPeriod mapRow(ResultSet rs, int arg1) throws SQLException {
		PowerConsumedForPeriod pc = new PowerConsumedForPeriod();
		pc.setPeriod(rs.getInt("id"));
		pc.setPower(rs.getFloat("power"));
		return pc;
	}
}
