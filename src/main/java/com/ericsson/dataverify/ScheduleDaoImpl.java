package com.ericsson.dataverify;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ScheduleDaoImpl extends JdbcDaoSupport{

	public List<Schedule> getScheduleByProgramID(String programid, String begtime, String endtime){
		String[] args={programid, begtime, endtime};
		String sql = "select schedule.schedule_id, to_char(schedule.start_time, 'yyyy-mm-dd hh24:mi:ss') as start_time, to_char(schedule.end_time, 'yyyy-mm-dd hh24:mi:ss') as end_time," +
				"schedule.station_id, station.station_name, program.program_id, program.title, " +
				"station.country, station.state from program,schedule,station where program.program_id=schedule.program_id " +
				"and station.station_id=schedule.station_id and program.program_id = ? " +
				"and start_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and end_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')" +
				"order by schedule.start_time";
		List<Schedule> schedules = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Schedule>>() {  
            @Override  
            public List<Schedule> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Schedule> scheduleList = new ArrayList<Schedule>();  
                while (rs.next()) {  
                	Schedule schedule = new Schedule();  
                	schedule.setSchedule_id(rs.getString("SCHEDULE_ID"));  
                	schedule.setStation_id(rs.getString("STATION_ID"));  
                	schedule.setStation_name(rs.getString("STATION_NAME"));
                	schedule.setProgram_id(rs.getString("PROGRAM_ID"));
                	schedule.setProgram_name(rs.getString("TITLE"));
                	schedule.setStart_time(rs.getString("START_TIME"));
                	schedule.setEnd_time(rs.getString("END_TIME"));
                	schedule.setCountry(rs.getString("COUNTRY"));
                	schedule.setState(rs.getString("STATE"));
                    scheduleList.add(schedule);  
                }  
                return scheduleList;  
            }  
        });
		return schedules;
	}
	
	public List<Schedule> getByScheduleId(String stationid, String begtime, String endtime){
		String[] args={stationid, begtime, endtime};
		String sql = "select schedule.schedule_id, to_char(schedule.start_time, 'yyyy-mm-dd hh24:mi:ss') as start_time, to_char(schedule.end_time, 'yyyy-mm-dd hh24:mi:ss') as end_time," +
				"schedule.station_id, station.station_name, program.program_id, program.title, " +
				"station.country, station.state from program,schedule,station where program.program_id=schedule.program_id " +
				"and station.station_id=schedule.station_id and schedule.station_id = ? " +
				"and start_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and end_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')" +
				"order by schedule.start_time";
		List<Schedule> schedules = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Schedule>>() {  
            @Override  
            public List<Schedule> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Schedule> scheduleList = new ArrayList<Schedule>();  
                while (rs.next()) {  
                	Schedule schedule = new Schedule();  
                	schedule.setSchedule_id(rs.getString("SCHEDULE_ID"));  
                	schedule.setStation_id(rs.getString("STATION_ID"));  
                	schedule.setStation_name(rs.getString("STATION_NAME"));
                	schedule.setProgram_id(rs.getString("PROGRAM_ID"));
                	schedule.setProgram_name(rs.getString("TITLE"));
                	schedule.setStart_time(rs.getString("START_TIME"));
                	schedule.setEnd_time(rs.getString("END_TIME"));
                	schedule.setCountry(rs.getString("COUNTRY"));
                	schedule.setState(rs.getString("STATE"));
                    scheduleList.add(schedule);  
                }  
                return scheduleList;  
            }  
        });
		return schedules;
	}
	
	public List<Programme> getProgramsByProgramID(String sid){
		String[] args = {sid};
		String sql = "select program.program_id, program.language, program.title, program.genre_desc1, program.genre_desc2,"+
				"program.genre_desc3, program.genre_desc4, program.genre_desc5, program.description1, program.description2 from program where program.program_id= ?";
		List<Programme> programs = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Programme>>() {  
            @Override  
            public List<Programme> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Programme> programList = new ArrayList<Programme>();  
                while (rs.next()) {  
                	Programme program = new Programme();  
                	program.programID = rs.getString("PROGRAM_ID");  
                	program.language = rs.getString("LANGUAGE");
                	program.programTitle = rs.getString("TITLE");
                	program.category = rs.getString("GENRE_DESC1")+"  "+
                			rs.getString("GENRE_DESC2")+"  "+
                			rs.getString("GENRE_DESC3")+"  "+
                			rs.getString("GENRE_DESC4")+"  "+
                			rs.getString("GENRE_DESC5");
                	program.describe = rs.getString("DESCRIPTION1")+"  "+rs.getString("DESCRIPTION2");
                    programList.add(program);  
                }  
                return programList;  
            }  
        });
		return programs;		
	}
	
	public List<Programme> getProgramsByProgramTitle(String title){
		String[] args = {title};
		String sql = "select program.program_id, program.language, program.title, program.genre_desc1, program.genre_desc2,"+
				"program.genre_desc3, program.genre_desc4, program.genre_desc5, program.description1, program.description2 from program where program.title like '%"+title+"%'";
		List<Programme> programs = this.getJdbcTemplate().query(sql,
				new ResultSetExtractor<List<Programme>>() {  
            @Override  
            public List<Programme> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Programme> programList = new ArrayList<Programme>();  
                while (rs.next()) {  
                	Programme program = new Programme();  
                	program.programID = rs.getString("PROGRAM_ID");  
                	program.language = rs.getString("LANGUAGE");
                	program.programTitle = rs.getString("TITLE");
                	program.category = rs.getString("GENRE_DESC1")+"  "+
                			rs.getString("GENRE_DESC2")+"  "+
                			rs.getString("GENRE_DESC3")+"  "+
                			rs.getString("GENRE_DESC4")+"  "+
                			rs.getString("GENRE_DESC5");
                	program.describe = rs.getString("DESCRIPTION1")+"  "+rs.getString("DESCRIPTION2");
                    programList.add(program);  
                }  
                return programList;  
            }  
        });
		return programs;		
	}
	
	public List<Headend> getHeadendByHeadendID(String hid){
		String[] args = {hid};
		String sql = "select headend.headend_id, headend.headend_name, headend.state, headend.headend_location, zipcode from headend where headend.headend_id = ?";
		List<Headend> headends = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Headend>>() {  
            @Override  
            public List<Headend> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Headend> headendList = new ArrayList<Headend>();  
                while (rs.next()) {  
                	Headend headend = new Headend();  
                	headend.headendID = rs.getString("HEADEND_ID");  
                	headend.headendName = rs.getString("HEADEND_NAME");
                	headend.zipcode = rs.getString("ZIPCODE");
                	headend.state = rs.getString("STATE");
                	headend.location = rs.getString("HEADEND_LOCATION");
                    headendList.add(headend);  
                }  
                return headendList;  
            }  
        });
		return headends;	
	}
	
	public List<Headend> getHeadendByHeadendZipcode(String hid){
		String[] args = {hid};
		String sql = "select headend.headend_id, headend.headend_name, headend.state, headend.headend_location, zipcode from headend where headend.zipcode = ?";
		List<Headend> headends = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Headend>>() {  
            @Override  
            public List<Headend> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Headend> headendList = new ArrayList<Headend>();  
                while (rs.next()) {  
                	Headend headend = new Headend();  
                	headend.headendID = rs.getString("HEADEND_ID");  
                	headend.headendName = rs.getString("HEADEND_NAME");
                	headend.zipcode = rs.getString("ZIPCODE");
                	headend.state = rs.getString("STATE");
                	headend.location = rs.getString("HEADEND_LOCATION");
                    headendList.add(headend);  
                }  
                return headendList;  
            }  
        });
		return headends;	
	}
	
	public List<Station> getStationByStationID(String sid){
		String[] args = {sid};
		String sql = "select station.station_id, station.station_name, station.country, station.state, station.city, headend.headend_id, " +
				"headend.zipcode from station, lineup, headend where station.station_id = ? and" +
				" lineup.station_id = station.station_id and lineup.headend_id = headend.headend_id";
		List<Station> stations = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Station>>() {  
            @Override  
            public List<Station> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Station> stationList = new ArrayList<Station>();  
                while (rs.next()) {  
                	Station station = new Station();  
                	station.stationID = rs.getString("STATION_ID");  
                	station.zipcode = rs.getString("ZIPCODE");
                	station.headendID = rs.getString("HEADEND_ID");
                	station.stationName = rs.getString("STATION_NAME");
                	station.country = rs.getString("COUNTRY");
                	station.state = rs.getString("STATE");
                	station.city = rs.getString("CITY");
                    stationList.add(station);  
                }  
                return stationList;  
            }  
        });
		return stations;
	}
	
	public List<Station> getStationByStationZipcode(String sid){
		String[] args = {sid};
		String sql = "select station.station_id, station.station_name, station.country, station.state, station.city, headend.headend_id, " +
				"headend.zipcode from station, lineup, headend where headend.zipcode = ? and" +
				" lineup.station_id = station.station_id and lineup.headend_id = headend.headend_id";
		List<Station> stations = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Station>>() {  
            @Override  
            public List<Station> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Station> stationList = new ArrayList<Station>();  
                while (rs.next()) {  
                	Station station = new Station();  
                	station.stationID = rs.getString("STATION_ID");  
                	station.zipcode = rs.getString("ZIPCODE");
                	station.headendID = rs.getString("HEADEND_ID");
                	station.stationName = rs.getString("STATION_NAME");
                	station.country = rs.getString("COUNTRY");
                	station.state = rs.getString("STATE");
                	station.city = rs.getString("CITY");
                    stationList.add(station);  
                }  
                return stationList;  
            }  
        });
		return stations;
	}
	
	public List<Station> getStationByHeadendID(String sid){
		String[] args = {sid};
		String sql = "select station.station_id, station.station_name, station.country, station.state, station.city, headend.headend_id, " +
				"headend.zipcode from station, lineup, headend where headend.headend_id = ? and" +
				" lineup.station_id = station.station_id and lineup.headend_id = headend.headend_id";
		List<Station> stations = this.getJdbcTemplate().query(sql, args,
				new ResultSetExtractor<List<Station>>() {  
            @Override  
            public List<Station> extractData(ResultSet rs)  
                    throws SQLException, DataAccessException {  
                List<Station> stationList = new ArrayList<Station>();  
                while (rs.next()) {  
                	Station station = new Station();  
                	station.stationID = rs.getString("STATION_ID");  
                	station.zipcode = rs.getString("ZIPCODE");
                	station.headendID = rs.getString("HEADEND_ID");
                	station.stationName = rs.getString("STATION_NAME");
                	station.country = rs.getString("COUNTRY");
                	station.state = rs.getString("STATE");
                	station.city = rs.getString("CITY");
                    stationList.add(station);  
                }  
                return stationList;  
            }  
        });
		return stations;
	}

}
