package org.chipmonk.smartcity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.chipmonk.smartcity.model.Alert;
import org.chipmonk.smartcity.model.Light;
import org.chipmonk.smartcity.model.Street;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.stat.Statistics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AlertDAO {

	public int save(List<Alert> alertList) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			for (Alert alert:alertList) {
				session.save(alert);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return 0;
	}

	public List<Alert> getAll() {
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			list = session.createCriteria(Alert.class).list();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	public List<Alert> getActiveAlertsForSpecifiedLight(int lightId) {
		List<Alert> alertList = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			alertList = session.createCriteria(Alert.class).add(Restrictions.eq("active", 1)).add(Restrictions.eq("lightId", lightId)).list();
			return alertList;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return alertList;
	}
	public List<Alert> getActiveAlerts() {
		List<Alert> alertList = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			alertList = session.createCriteria(Alert.class).add(Restrictions.eq("active", 1)).list();
			return alertList;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return alertList;
	}

	public List<Alert> getHistoricalAlerts(Date startDate, Date endDate) {
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			list = session.createCriteria(Alert.class).add(Restrictions.between("date", startDate, endDate)).add(Restrictions.eq("active",0)).list();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	public List<Alert> getHistoricalAlertsForSpecifiedLight(Date startDate,Date endDate,int id) {
		Session session=null;
		List<Alert> alertList=new ArrayList<Alert>();
		try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			alertList=session.createCriteria(Alert.class).add(Restrictions.between("date",startDate,endDate)).add(Restrictions.eq("lightId", id)).add(Restrictions.eq("active",0)).list();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null)
				session.close();
		}
		return alertList;
	}

	public void updateAlertActivity() {
		Session session=null;
		int id=randomId();
		try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Query query=session.createQuery("update Alert set active= :active where id= :id");
			query.setParameter("active",0);
			query.setParameter("id",id);
			query.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null)
				session.close();
		}
		
	}

	private int randomId() {
		AlertDAO alertDao=new AlertDAO();
		int high=alertDao.getAll().size();
		int low=1;
		return new Random().nextInt(high-low)+low;
	}

	public List<Alert> getActiveAlertsForStreet(int streetId) {
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			//Query query = session.createSQLQuery("select a.* from alert a,light l,street s where l.street_id=s.id and a.light_id=l.id and s.id=:street").addEntity(Alert.class).addEntity(Street.class).addEntity(Light.class).setParameter("street", streetId);
			//list = query.list();
			list= session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Street s,Light l,Alert a where l.id=a.lightId and l.street_id=s.id and a.active=1 and s.id=:id ").setParameter("id", streetId).list();
			System.out.println("alert"+list.size());
			return list;
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		
		
		return list;	
	}
	
	public List<Alert> getActiveAlertsForWard(int wardId) {
		List<Alert> list = new ArrayList<Alert>();
		Session session=null;
		try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Query query = session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Street s,Light l,Alert a,Wards w where l.id=a.lightId and l.street_id=s.id and a.active=1 and s.wards_id=:id ");
			list=query.setParameter("id", wardId).list();
			return list;
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return list;	
	}

	public List<Alert> getActiveAlertForSpecifiedStreetAndWard(int streetId, int wardsId) {
		
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query=session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Street s,Light l,Alert a,Wards w where l.id=a.lightId and l.street_id=s.id and a.active=1 and s.id=:streetid and s.wards_id=:wardid ");
			list = query.setParameter("streetid", streetId).setParameter("wardid", wardsId).list();
			return list;
			
		}catch(Exception exc) {
			
			if(session!=null)
				session.close();
		}
		
		return list;		
	}
	
	public List<Alert> getHistoricalAlertForSpecifiedLightId(int lightId){
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query=session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Light l,Alert a where a.active=0 and a.lightId=:lightid ");
			list = query.setParameter("lightid", lightId).list();
			return list;
			
		}catch(Exception exc) {
			
			if(session!=null)
				session.close();
		}
		
		return list;		
	}
	
	public List<Alert> getHistoricalAlertsForSpecifiedStreet(int streetId) {
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Query query = session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Street s,Light l,Alert a where l.id=a.lightId and l.street_id=s.id and a.active=0 and s.id=:streetid ");
			list=query.setParameter("streetid", streetId).list();
			return list;
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return list;
	}
	
	public List<Alert> getHistoricalAlertsForSpecifiedWard(int wardId) {
		List<Alert> list = new ArrayList<Alert>();
		Session session=null;
		try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Query query = session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Wards w,Street s,Light l,Alert a where w.id=s.wards_id and l.id=a.lightId and l.street_id=s.id and a.active=0 and w.id=:id");
			list=query.setParameter("id", wardId).list();
			return list;
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(session != null) {
				session.close();
			}
		}
		return list;	
	}
	
	public List<Alert> getHistoricalAlertForSpecifiedStreetAndWard(int streetId, int wardsId) {
		
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query=session.createQuery("select new Alert(a.id,a.lightId,a.streetId,a.status,a.alert,a.active,a.date) from Street s,Light l,Alert a,Wards w where l.id=a.lightId and l.street_id=s.id and a.active=0 and s.id=:streetid and s.wards_id=:wardid ");
			list = query.setParameter("streetid", streetId).setParameter("wardid", wardsId).list();
			return list;
			
		}catch(Exception exc) {
			
			if(session!=null)
				session.close();
		}
		
		return list;		
	}
	public List<Alert> getHistoricalAlerts() {
		List<Alert> list = new ArrayList<Alert>();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			list = session.createCriteria(Alert.class).add(Restrictions.eq("active",0)).list();
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}
	
	
	
}

//class AlertsMapper implements RowMapper<Alert> {
//
//	@Override
//	public Alert mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//		Alert alert = new Alert();
//		alert.setId(resultSet.getInt("id"));
//		alert.setLightId(resultSet.getInt("light_id"));
//		alert.setStreetId(resultSet.getInt("street_id"));
//		alert.setStatus(resultSet.getString("status"));
//		alert.setAlert(resultSet.getString("alert"));
//		alert.setDate(resultSet.getDate("date"));
//		return alert;
//	}
//
//}
