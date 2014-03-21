package com.ericsson.xmlverify;

import java.util.Date;

public class Channel implements Comparable<Channel> {
	public String id;
	public String name;
	public Date beg;
	public Date end;
	public String str_beg;
	public String str_end;
	public int prog_num;
	public String description;
	public Channel(){
		this.id = "";
		this.name = "";
		this.str_beg ="";
		this.str_end = "";
		this.description = "";
		this.prog_num = 0;
		this.beg = null;
		this.end = null;
	}
	
	@Override
	public int compareTo(Channel arg0) {
		// TODO Auto-generated method stub
		return Integer.parseInt(this.id) - Integer.parseInt(arg0.id);
	}
}
