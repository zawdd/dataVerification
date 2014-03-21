import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ericsson.dataverify.Headend;
import com.ericsson.dataverify.Lineup;
import com.ericsson.dataverify.Programme;
import com.ericsson.dataverify.Schedule;
import com.ericsson.dataverify.ScheduleDaoImpl;
import com.ericsson.dataverify.Station;

public class TestScheduleDaoImpl {
	public ScheduleDaoImpl daotest = new ScheduleDaoImpl();
	
	@Test
	public void testGetProgramsByStationID(){	
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		daotest = (ScheduleDaoImpl)context.getBean("scheduledao");
		String sid = "48223";
		List<Programme> programs = daotest.getProgramsByStationID(sid);
		Iterator<Programme> pi = programs.iterator();
		System.out.println(programs.size());
		while(pi.hasNext()){
			Programme p = pi.next();
			System.out.println(p.stationID);
			System.out.println(p.programID);
/*			System.out.println(p.programTitle);
			System.out.println(p.language);
			System.out.println(p.category);
			System.out.println(p.describe);*/
		}
		//assertEquals(34, clTester.getSize());
	}
	
	@Test
	@Ignore
	public void testGetByScheduleID(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		ScheduleDaoImpl sd = (ScheduleDaoImpl)context.getBean("scheduledao");
		String testid = "27480";
		String begtime = "2014-02-16 00:00:00";
		String endtime = "2014-03-1 07:00:00";
		List<Schedule> schedules = sd.getByScheduleId(testid,begtime,endtime);
		Iterator<Schedule> si = schedules.iterator();
		System.out.println(schedules.size());
/*		while(si.hasNext()){
			Schedule tmp = si.next();
			System.out.println("schedule id \t" + tmp.getSchedule_id());
			System.out.println("station_id \t" + tmp.getStation_id());
			System.out.println("station_name \t" + tmp.getStation_name());
			System.out.println("program_id \t" + tmp.getProgram_id());
			System.out.println("program_name \t" + tmp.getProgram_name());
			System.out.println("starttime \t" + tmp.getStart_time());
			System.out.println("end time \t" + tmp.getEnd_time());
			System.out.println("country \t" + tmp.getCountry());
			System.out.println("state \t" + tmp.getState());
		}*/	
	}
	
	@Test
	@Ignore
	public void testGetLineupByStationID(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		daotest = (ScheduleDaoImpl)context.getBean("scheduledao");
		String stationid="48223";
		List<Lineup> linups = daotest.getLineupByStationID(stationid);
		Iterator<Lineup> li = linups.iterator();
		System.out.println(linups.size());
		while(li.hasNext()){
			Lineup tmp = li.next();
			System.out.println(tmp.lineupID);
			System.out.println(tmp.stationID);
			System.out.println(tmp.headendID);
		}
	}
	
	@Test
	@Ignore
	public void testGetHeadendByStationID(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		daotest = (ScheduleDaoImpl)context.getBean("scheduledao");
		String stationid="48223";
		List<Headend> headends = daotest.getHeadendByStationID(stationid);
		Iterator<Headend> hi = headends.iterator();
		System.out.println(headends.size());
		while(hi.hasNext()){
			Headend tmp = hi.next();
			System.out.println(tmp.stationID);
			System.out.println(tmp.headendID);
			System.out.println(tmp.headendName);
			System.out.println(tmp.state);
			System.out.println(tmp.location);
		}
	}
	
	@Test
	@Ignore
	public void testGetStationByStationID(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		daotest = (ScheduleDaoImpl)context.getBean("scheduledao");
		String stationid="48223";
		List<Station> stations = daotest.getStationByStationID(stationid);
		Iterator<Station> hi = stations.iterator();
		System.out.println(stations.size());
		while(hi.hasNext()){
			Station tmp = hi.next();
			System.out.println(tmp.stationID);
			System.out.println(tmp.stationName);
			System.out.println(tmp.country);
			System.out.println(tmp.state);
			System.out.println(tmp.city);
		}
	}
	
}
