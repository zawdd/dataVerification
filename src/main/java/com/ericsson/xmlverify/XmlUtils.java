package com.ericsson.xmlverify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils{ 

	private HashMap<String, Document> allXmlData;
	
	public XmlUtils(){
		this.allXmlData = new HashMap<String, Document>();
	}
	
	//the label synchronized used to make sure one document only have one copy in the hash map,
	//if not there will be two
	public synchronized Document getDocument(String filePath){
		if (this.allXmlData.containsKey(filePath)){
			return this.allXmlData.get(filePath);
		}else{
			Document newDoc = parse(filePath);
			this.allXmlData.put(filePath, newDoc);
			return this.allXmlData.get(filePath);
		}
	}
	
	public Set<String> getAllKeys(){
		return allXmlData.keySet();
	}
	
	public String elementToString(Node n) {

	    String name = n.getNodeName();

	    short type = n.getNodeType();

	    if (Node.CDATA_SECTION_NODE == type) {
	      return "<![CDATA[" + n.getNodeValue() + "]]&gt;";
	    }

	    if (name.startsWith("#")) {
	      return "";
	    }

	    StringBuffer sb = new StringBuffer();
	    sb.append('<').append(name);

	    NamedNodeMap attrs = n.getAttributes();
	    if (attrs != null) {
	      for (int i = 0; i < attrs.getLength(); i++) {
	        Node attr = attrs.item(i);
	        sb.append(' ').append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append(
	            "\"");
	      }
	    }

	    String textContent = null;
	    NodeList children = n.getChildNodes();

	    if (children.getLength() == 0) {
	      if ((textContent = n.getTextContent()) != null && !"".equals(textContent)) {
	        sb.append(textContent).append("</").append(name).append('>');
	        ;
	      } else {
	        sb.append("/>").append('\n');
	      }
	    } else {
	      sb.append('>').append('\n');
	      boolean hasValidChildren = false;
	      for (int i = 0; i < children.getLength(); i++) {
	        String childToString = elementToString(children.item(i));
	        if (!"".equals(childToString)) {
	          sb.append(childToString);
	          hasValidChildren = true;
	        }
	      }

	      if (!hasValidChildren && ((textContent = n.getTextContent()) != null)) {
	        sb.append(textContent);
	      }

	      sb.append("</").append(name).append('>');
	    }

	    return sb.toString();
	  }
	
	 public Document parse(String filePath) { 
		  DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
	      Document document = null; 
	      try { 
	         //DOM parser instance 
	         DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	         //parse an XML file into a DOM tree 
	         document = builder.parse(new File(filePath)); 
	      } catch (ParserConfigurationException e) { 
	         e.printStackTrace();  
	      } catch (SAXException e) { 
	         e.printStackTrace(); 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      } 
	      return document; 
	 }
	 
	 public synchronized ArrayList<Channel> channelInfo(String filename){
		 ArrayList<Channel> result = new ArrayList<Channel>();
		 HashMap<String, Channel> progChannelHashMap = new HashMap<String, Channel>();
		 
		 Document document = this.getDocument(filename);
		 Element rootElement = document.getDocumentElement();
		 NodeList nodeList = rootElement.getElementsByTagName("channel");
		 for(int i = 0; i < nodeList.getLength() && nodeList != null; i++){
			 Element element = (Element)nodeList.item(i); 
			 Channel tmp = new Channel();
			 tmp.id = element.getAttribute("id");			 
			 
			 NodeList titleList = element.getElementsByTagName("display-name");
			 for(int j=0;j<titleList.getLength() && titleList != null; j++){
				 Element titleele = (Element)titleList.item(j);
				 tmp.name = titleele.getTextContent();
			 }	
			 
			 NodeList descriList = element.getElementsByTagName("description");
			 for(int j=0;j<descriList.getLength() && descriList != null; j++){
				 Element descriele = (Element)descriList.item(j);
				 tmp.description = descriele.getTextContent();
			 }
			 progChannelHashMap.put(tmp.id, tmp);
			 result.add(tmp);
		 }
		 //scan all the program, class them with channel id
		 ArrayList<Program> allProgram = getAllPrograms(filename);
		 Iterator<Program> proIter = allProgram.iterator();
		 while(proIter.hasNext()){
			 Program pro = proIter.next();
			 if(progChannelHashMap.containsKey(pro.channel)){
				 Channel channel = progChannelHashMap.get(pro.channel);
				 channel.prog_num++;
				 if(channel.beg == null || pro.start_time.before(channel.beg)){
					 channel.beg = new Date(pro.start_time.getTime());
					 channel.str_beg = channel.beg.toString();
				 }
				 if(channel.end == null || pro.end_time.after(channel.end)){
					 channel.end = new Date(pro.end_time.getTime());
					 channel.str_end = channel.end.toString();
				 }
			 }			
		 }
		 return result;
	 }

	 public boolean writeSearchResult(String filename, String channelid, Date beg, Date end){
		  ArrayList<Program> result = findProgram(filename, channelid, beg, end);
		  String outfilename = "./src/main/webapp/download/searchresult.txt";
		  try{
			  FileWriter outFile = new FileWriter(outfilename);
	          BufferedWriter outStream = new BufferedWriter(outFile); 
	          Iterator<Program> proIter = result.iterator();
	          while(proIter.hasNext()){
	        	  outStream.write(proIter.next().other_content);
	          }
	          outStream.close();  
	          return true;
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
			  return false;
		  }  

	 }
	 public synchronized ArrayList<Program> findProgram(String filename, String channelid, Date beg, Date end){
		 ArrayList<Program> result = new ArrayList<Program>();
		 Document document = this.getDocument(filename);
		 Element rootElement = document.getDocumentElement();
		 NodeList nodeList = rootElement.getElementsByTagName("programme");
		 DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		 
		 for(int i = 0; i < nodeList.getLength() && nodeList != null; i++){
			 Element element = (Element)nodeList.item(i); 
			 Program tmp = new Program();
			 try {
				 Date start_time = format.parse(element.getAttribute("start")); 
				 Date end_time = format.parse(element.getAttribute("stop"));
				 if(start_time.after(beg) && end_time.before(end) && element.getAttribute("channel").equals(channelid)){
					 tmp.start_time = start_time;
					 tmp.end_time = end_time;
					 tmp.str_start_time = tmp.start_time.toString();
					 tmp.str_end_time = tmp.end_time.toString();
					 tmp.channel = channelid;
					 tmp.Ericsson_ProgramId = element.getAttribute("Ericsson_ProgramId");
					 
					 NodeList titleList = element.getElementsByTagName("title");
					 for(int j=0;j<titleList.getLength() && titleList != null; j++){
						 Element titleele = (Element)titleList.item(j);
						 String title = titleele.getTextContent();
						 tmp.title=title;
					 }					 
					 
					 NodeList descList = element.getElementsByTagName("desc");
					 for(int j=0; j < descList.getLength() && descList != null;j++){
						 Element desce = (Element)descList.item(j);
						 String desc = desce.getTextContent();
						 tmp.describe = desc;
					 }
					 
					 tmp.other_content = elementToString(element);
					 result.add(tmp);
					 
				 }//if
			 } catch (ParseException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
			 
		 }//for
		 return result;
	 }
	 
	 
	public ArrayList<Program> getAllPrograms(String filename){
		 ArrayList<Program> result = new ArrayList<Program>();
		 Document document = this.getDocument(filename);
		 Element rootElement = document.getDocumentElement(); 
		 NodeList nodeList = rootElement.getElementsByTagName("programme"); 
		 DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

		 for (int i = 0 ; i < nodeList.getLength() && nodeList != null; i++) 
		 { 
			 Element element = (Element)nodeList.item(i); 
			 Program tmp = new Program();
			 
			 NodeList titleList = element.getElementsByTagName("title");
			 for(int j=0;j<titleList.getLength() && titleList != null; j++){
				 Element titleele = (Element)titleList.item(j);
				 String title = titleele.getTextContent();
				 tmp.title=title;
			 }
			 
			 tmp.channel = element.getAttribute("channel");   
			 String startTime = element.getAttribute("start"); 
			 String endTime = element.getAttribute("stop");
			 try {
				 tmp.start_time = format.parse(startTime);
				 tmp.end_time = format.parse(endTime);
				 tmp.str_start_time = tmp.start_time.toString();
				 tmp.str_end_time = tmp.end_time.toString();
			 } catch (ParseException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }

			 NodeList subtitleList = element.getElementsByTagName("sub-title");
			 for(int j=0; j < subtitleList.getLength() && subtitleList != null;j++){
				 Element subtitleele = (Element)subtitleList.item(j);
				 String subtitle = subtitleele.getTextContent();
				 tmp.sub_title = subtitle;
			 }
			 
			 NodeList descList = element.getElementsByTagName("desc");
			 for(int j=0; j < descList.getLength() && descList != null;j++){
				 Element desce = (Element)descList.item(j);
				 String desc = desce.getTextContent();
				 tmp.describe = desc;
			 }
			 
			 tmp.other_content = elementToString(element);
			 result.add(tmp);
		 } 

		 return result;
	 }
	
	 public static void main(String args[]){
		 XmlUtils xmlutils = new XmlUtils();
		 //ArrayList<Program> programs1 = xmlutils.getAllPrograms("/tmp/xmltv.xml");
		 //System.out.println(programs1.get(0).other_content);
		 //System.out.println(programs.get(0).title);
		ArrayList<Channel> test = xmlutils.channelInfo("/tmp/xmltv-20140310.xml");
		//ArrayList<Channel> test = xmlutils.channelInfo("/tmp/xmltv.xml");
/*		Iterator<Channel> ite = test.iterator();
		while(ite.hasNext()){
			Channel c = ite.next();
			System.out.println(c.id);
			System.out.println(c.name);
			System.out.println(c.beg.toString());
			System.out.println(c.end.toString());
			System.out.println(c.prog_num);
		}*/
	 }
	 
} 