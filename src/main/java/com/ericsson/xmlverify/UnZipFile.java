package com.ericsson.xmlverify;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipFile {

     public static void main(String[] args) {
    	 String destDir = "/tmp/";
          if (args.length != 1) {
               System.out.println("please input a zip file");
          } else {
               if (UnZipFile.unzip(args[0],destDir).equals("xmltv.xml")) {
                    System.out.println("success");                    
               } else {
                    System.out.println("false");
               }
               System.out.println(destDir);
          }    
     }

     public static String unzip(String srcZipFile,String destDir) {
          String entryName = "";
          try {
               BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcZipFile));
               ZipInputStream zis = new ZipInputStream(bis);

               BufferedOutputStream bos = null;
               
               ZipEntry entry = null;
               while ((entry=zis.getNextEntry()) != null) {
            	    entryName = entry.getName();
                    bos = new BufferedOutputStream(new FileOutputStream(destDir + entryName));
                    int b = 0;
                    while ((b = zis.read()) != -1) {
                         bos.write(b);
                    }
                    bos.flush();
                    bos.close();
               }
               zis.close();
          } catch (IOException e) {
        	  e.printStackTrace();
          }
          return entryName;
     }
}