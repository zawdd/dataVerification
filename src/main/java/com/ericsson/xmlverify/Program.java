package com.ericsson.xmlverify;
import java.util.Date;

public class Program implements Comparable<Program>{
	public Date start_time;
	public Date end_time;
	public String Ericsson_ProgramId;
	public String channel;
	public String title;
	public String sub_title;
	public String describe;
	public String category;
	public String episode_num;
	public String str_start_time;
	public String str_end_time;
	public String other_content;
	@Override
	public int compareTo(Program arg0) {
		// TODO Auto-generated method stub
		return (int)(this.start_time.getTime() - arg0.start_time.getTime());
	}
}
