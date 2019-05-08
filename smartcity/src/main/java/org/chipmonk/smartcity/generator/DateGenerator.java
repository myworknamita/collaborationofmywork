package org.chipmonk.smartcity.generator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DateGenerator {
/*This method create date list and returns */
	public ArrayList<Date> smallDateGenerator() 
	{
		ArrayList<Date> dal=new ArrayList<Date>();
		try 
		{
			
			Date d1 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-06-15 13:45:30");
			dal.add(0,d1);
			Date d2 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-06-18 16:25:20");
			dal.add(1,d2);
			Date d3 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-06-22 9:12:30");
			dal.add(2,d3);
			Date d4 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-06-29 8:34:13");
			dal.add(3,d4);
			Date d5 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-07-5 5:32:32");
			dal.add(4,d5);
			Date d6 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-07-15 13:45:54");
			dal.add(5,d6);
			Date d7 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-08-17 17:33:30");
			dal.add(6,d7);
			Date d8 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-09-12 16:45:17");
			dal.add(7,d8);
			Date d9 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-09-22 22:22:12");
			dal.add(8,d9);
			Date d10 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-10-15 12:54:30");
			dal.add(9,d10);
			Date d11 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-10-22 13:45:54");
			dal.add(10,d11);
			Date d12 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-11-10 23:56:30");
			dal.add(11,d12);
			Date d13 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2009-11-28 23:54:43");
			dal.add(12,d13);
			Date d14 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-01-15 4:44:30");
			dal.add(13,d14);
			Date d15 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-01-23 23:45:34");
			dal.add(14,d15);
			Date d16 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-02-14 6:48:54");
			dal.add(15,d16);
			Date d17 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-02-24 12:34:56");
			dal.add(16,d17);
			Date d18 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-02-29 10:45:34");
			dal.add(17,d18);
			Date d19 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-03-8 9:13:14");
			dal.add(18,d19);
			Date d20 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-03-18 8:27:34");
			dal.add(19,d20);
			Date d21 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-03-24 3:45:25");
			dal.add(20,d21);
			Date d22 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2010-03-31 5:45:34");
			dal.add(21,d22);
		
		return dal;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dal;
	}
	public ArrayList<Date> bigDateGenerator()
	{
		ArrayList<Date> dal=new ArrayList<Date>();
		try 
		{
			
			Date d1 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-06-15 13:45:30");
			dal.add(0,d1);
			Date d2 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-06-18 16:25:20");
			dal.add(1,d2);
			Date d3 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-06-22 9:12:30");
			dal.add(2,d3);
			Date d4 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-06-29 8:34:13");
			dal.add(3,d4);
			Date d5 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-07-5 5:32:32");
			dal.add(4,d5);
			Date d6 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-07-15 13:45:54");
			dal.add(5,d6);
			Date d7 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-08-17 17:33:30");
			dal.add(6,d7);
			Date d8 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-09-12 16:45:17");
			dal.add(7,d8);
			Date d9 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-09-22 22:22:12");
			dal.add(8,d9);
			Date d10 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-10-15 12:54:30");
			dal.add(9,d10);
			Date d11 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-10-22 13:45:54");
			dal.add(10,d11);
			Date d12 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-11-10 23:56:30");
			dal.add(11,d12);
			Date d13 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2012-11-28 23:54:43");
			dal.add(12,d13);
			Date d14 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-01-15 4:44:30");
			dal.add(13,d14);
			Date d15 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-01-23 23:45:34");
			dal.add(14,d15);
			Date d16 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-02-14 6:48:54");
			dal.add(15,d16);
			Date d17 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-02-24 12:34:56");
			dal.add(16,d17);
			Date d18 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-02-29 10:45:34");
			dal.add(17,d18);
			Date d19 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-03-8 9:13:14");
			dal.add(18,d19);
			Date d20 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-03-18 8:27:34");
			dal.add(19,d20);
			Date d21 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-03-24 3:45:25");
			dal.add(20,d21);
			Date d22 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse("2013-03-31 5:45:34");
			dal.add(21,d22);
			return dal;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dal;
	}
	public int[] randomNumber()
	{
		int arr[]=new int[20];
		int low=0,high=20;
		Random random=new Random();
		for(int i=0;i<=3;i++)
		{
			arr[i]=random.nextInt(high-low)+low;
		}
		return arr;
	}

}
