package com.ericsson.dataverify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexPageController {
	private ScheduleDaoImpl scheduledaoimpl;
	
	public void setScheduledaoimpl(ScheduleDaoImpl dao){
		this.scheduledaoimpl = dao;
	}
	
	public ScheduleDaoImpl getScheduledaoimple(){
		return this.scheduledaoimpl;
	}
	
	@RequestMapping(value="/getprogramsbyid", method=RequestMethod.GET)  
	@ResponseBody
	public List<Programme> getProgramByProgramID(@RequestParam(value = "programid") String id){
		List<Programme> result= this.scheduledaoimpl.getProgramsByProgramID(id);
		return result;
	}
	
	@RequestMapping(value="/getprogramsbytitle", method=RequestMethod.GET)  
	@ResponseBody
	public List<Programme> getProgramByProgramTitle(@RequestParam(value = "programtitle") String title){
		List<Programme> result= this.scheduledaoimpl.getProgramsByProgramTitle(title);
		return result;
	}
	
	@RequestMapping(value="/getschedulesbyprogramid", method=RequestMethod.GET)  
	@ResponseBody
	public List<Schedule> getScheduleByProgramID(@RequestParam(value = "programid") String id,
			@RequestParam(value = "begtime") String begtime,
			@RequestParam(value = "endtime") String endtime){
		String start_time = begtime + " 00:00:00";
		String end_time = endtime + " 00:00:00"; 
		List<Schedule> result= this.scheduledaoimpl.getScheduleByProgramID(id,start_time,end_time);
		return result;
	}
	
	@RequestMapping(value="/getheadendsbyheadendid", method=RequestMethod.GET)  
	@ResponseBody
	public List<Headend> getHeadendByHeadendID(@RequestParam(value = "headendid") String id){
		List<Headend> result = this.scheduledaoimpl.getHeadendByHeadendID(id);		
		return result;
	}
	
	@RequestMapping(value="/getheadendsbyheadendzipcode", method=RequestMethod.GET)  
	@ResponseBody
	public List<Headend> getHeadendByHeadendZipcode(@RequestParam(value = "zipcode") String id){
		List<Headend> result = this.scheduledaoimpl.getHeadendByHeadendZipcode(id);		
		return result;
	}
	
	@RequestMapping(value="/getstationsbystationid", method=RequestMethod.GET)  
	@ResponseBody
	public List<Station> getStationByStationID(@RequestParam(value = "stationid") String id){
		List<Station> result = this.scheduledaoimpl.getStationByStationID(id);
		return result;
	}
	
	@RequestMapping(value="/getstationsbyzipcode", method=RequestMethod.GET)  
	@ResponseBody
	public List<Station> getStationByStationZipcode(@RequestParam(value = "zipcode") String id){
		List<Station> result = this.scheduledaoimpl.getStationByStationZipcode(id);
		return result;
	}
	
	@RequestMapping(value="/getstationsbyheadendid", method=RequestMethod.GET)  
	@ResponseBody
	public List<Station> getStationByHeadendID(@RequestParam(value = "headendid") String id){
		List<Station> result = this.scheduledaoimpl.getStationByHeadendID(id);
		return result;
	}
	
	@RequestMapping(value="/check", method=RequestMethod.GET)  
	@ResponseBody
    public String check(@RequestParam(value = "stationid") String id,
			@RequestParam(value = "begtime") String begtime,
			@RequestParam(value = "endtime") String endtime){
		String result = "";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long HALFHOUR = 30*60*1000;
		List<Schedule> slist = getScheduleList(id,begtime,endtime);
		int slist_size = slist.size();
		String printSize = "There " + slist_size + " schedule(s).";
		if(slist_size < 2){
			return printSize;
		}
		try {
			for(int i=0; i<slist_size-1; i++){
				Date predate = format.parse(slist.get(i).getEnd_time());
				Date nextdate = format.parse(slist.get(i+1).getStart_time());
				if(nextdate.getTime() - predate.getTime() > HALFHOUR){
					result = result + "\t" + i;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		if(result.equals("")){
			return printSize;
		}else{
			return printSize + "The Index of" + result+ "may be error.";
		}
	}
	
	public List<Schedule> getScheduleList(String id, String begtime, String endtime){
		ScheduleDaoImpl sdi = this.scheduledaoimpl;
		String testid = id;
		String start_time = begtime + " 00:00:00";
		String end_time = endtime + " 00:00:00"; 
		List<Schedule> slist = sdi.getByScheduleId(testid, start_time, end_time);
		return slist;
	}
	
	@RequestMapping(value="/dataverify", method=RequestMethod.GET) 
	public String showdataverify(){
		return "dataverify";
	}
	
	@RequestMapping(value="/data", method=RequestMethod.GET)  
	@ResponseBody
    public List<Schedule> dataverifytest(@RequestParam(value = "stationid") String id,
			@RequestParam(value = "begtime") String begtime,
			@RequestParam(value = "endtime") String endtime){
		return getScheduleList(id,begtime,endtime); 
    }
	
}
