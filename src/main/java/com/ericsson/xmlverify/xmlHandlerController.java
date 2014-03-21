package com.ericsson.xmlverify;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class xmlHandlerController {
    private XmlUtils xmlutils;
    
	public void setXmlutils(XmlUtils xu){
		this.xmlutils = xu;
	}
	
	public XmlUtils getXmlutils(){
		return this.xmlutils;
	}
	
	public Date StringToDate(String time){
		String new_time = time + " 00:00:00";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(new_time);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public ArrayList<Program> getPrograms(String filename, String channelid, String beg, String end){
		ArrayList<Program> programs = new ArrayList<Program>();
		programs =  this.xmlutils.findProgram(filename, channelid, StringToDate(beg), StringToDate(end));
		Collections.sort(programs);
		return programs;
	}
	
	@RequestMapping(value="/getkeys", method=RequestMethod.GET)
	@ResponseBody
	public Set<String> getKeys(){
		return this.xmlutils.getAllKeys();
	}
	
	@RequestMapping(value="/getcurrentkey", method=RequestMethod.GET)
	@ResponseBody
	public String getCurrentKey(HttpSession session){
		return (String) session.getAttribute("filename");
	}
	
	@RequestMapping(value="/setkey", method=RequestMethod.GET)
	@ResponseBody
	public String setKey(@RequestParam(value = "filename") String filename, HttpSession session){
		session.setAttribute("filename", filename);
		return filename;
	}
	
	@RequestMapping(value="/channelsummary", method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Channel> channleInfo(HttpSession session){
		String filename = (String)session.getAttribute("filename");
		ArrayList<Channel> result = this.xmlutils.channelInfo(filename);
		//Collections.sort(result);
		return result;
	}
	
	@RequestMapping(value="/exportsearchresult", method=RequestMethod.GET)
	@ResponseBody
	public String exportSearchResult(@RequestParam(value = "stationid") String id,
				@RequestParam(value = "begtime") String begtime,
				@RequestParam(value = "endtime") String endtime,
				HttpSession session){
		String result = "";
		String outfilename = "/download/searchresult.txt";
		String filename = (String)session.getAttribute("filename");		
		if(this.xmlutils.writeSearchResult(filename, id, StringToDate(begtime), StringToDate(endtime))){
			return outfilename;
		}else{
			return result;
		}		
	}
	
	@RequestMapping(value="/checkxml", method=RequestMethod.GET)
	@ResponseBody
	public String check(@RequestParam(value = "stationid") String id,
			@RequestParam(value = "begtime") String begtime,
			@RequestParam(value = "endtime") String endtime,
			HttpSession session){
		String result = "";
		long HALFHOUR = 30*60*1000;
		String filename = (String)session.getAttribute("filename");
		ArrayList<Program> programs = getPrograms(filename,id,begtime,endtime);
		int prog_size = programs.size();
		String printSize = "There " + prog_size + " program(s).";
		if(prog_size < 2){
			return printSize;
		}
		
		for(int i=0;i<prog_size-1;i++){
			if(programs.get(i).channel.equals(programs.get(i+1).channel)==false){
				continue;
			}
			Date predate = programs.get(i).end_time;
			Date nextdate = programs.get(i+1).start_time;
			if(nextdate.getTime() - predate.getTime() > HALFHOUR){
				result = result + "\t" + i;
			}
		}
		
		if(result.equals("")){
			return printSize;			
		}else{
			return printSize + "The Index of" + result+ "may be error.";
		}
	}
	@RequestMapping(value="/xmlverify", method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Program> xmlverify(@RequestParam(value = "stationid") String id,
			@RequestParam(value = "begtime") String begtime,
			@RequestParam(value = "endtime") String endtime,
			HttpSession session){
		String filename = (String)session.getAttribute("filename");
		ArrayList<Program> programs = getPrograms(filename,id,begtime,endtime);
		return programs;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam(value="file") CommonsMultipartFile myfiles, HttpServletRequest request,HttpSession session) throws IOException{
		if (!myfiles.isEmpty()) {  
            //String path =  request.getSession().getServletContext().getRealPath("/WEB-INF/uploadfile");
            String uploadPath = "/tmp/";
            String unZipPath = "/tmp/";
            String filename = myfiles.getOriginalFilename();
            FileUtils.copyInputStreamToFile(myfiles.getInputStream(), new File(uploadPath, filename));
            String unZipFileName = UnZipFile.unzip(uploadPath+filename, unZipPath);
            session.setAttribute("filename", unZipPath+unZipFileName);
            this.xmlutils.getDocument(unZipPath+unZipFileName);//put the doc in memory
            return "xmlverify";
		} else {  
			return "error";
		}  
    }
	
	@RequestMapping(value="/xmltv", method=RequestMethod.GET)  
    public String xmltv(){
        return "xmlverify";  
    }
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)  
    public String upFile(){
        return "upload";  
    }
	
	
}
