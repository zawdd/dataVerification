package com.ericsson.dataverify;


public class Schedule {
	private String schedule_id;
	private String station_id;
	private String station_name;
	private String program_id;
	private String program_name;
	//private String tv_rating;
	private String start_time;
	private String end_time;
	private String country;
	private String state;
	
	public Schedule(){
		
	}
	public Schedule(String scid,
				String sid,
				String sname,
				String pid,
				String pname,
				String stime,
				String etime,
				String cou,
				String sta){
		this.schedule_id=scid;
		this.station_id=sid;
		this.station_name=sname;
		this.program_id=pid;
		this.program_name=pname;
		this.start_time=stime;
		this.end_time=etime;
		this.country=cou;
		this.state=sta;
	}
	public String getSchedule_id(){
		return this.schedule_id;
	}
	
	public String getStation_id(){
		return this.station_id;
	}
	
	public String getStation_name(){
		return this.station_name;
	}
	
	public String getProgram_id(){
		return this.program_id;
	}
	
	public String getProgram_name(){
		return this.program_name;
	}
	
	public String getStart_time(){
		return this.start_time;
	}
	
	public String getEnd_time(){
		return this.end_time;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public String getState(){
		return this.state;
	}	
	
	public void setSchedule_id(String id){
		this.schedule_id=id;
	}
	public void setStation_id(String id){
		this.station_id=id;
	}
	
	public void setStation_name(String name){
		this.station_name=name;
	}
	
	public void setProgram_id(String id){
		this.program_id=id;
	}
	
	public void setProgram_name(String name){
		this.program_name=name;
	}
	
	public void setStart_time(String time){
		this.start_time=time;
	}
	
	public void setEnd_time(String time){
		this.end_time=time;
	}
	
	public void setCountry(String country){
		this.country=country;
	}
	
	public void setState(String state){
		this.state=state;
	}
}
