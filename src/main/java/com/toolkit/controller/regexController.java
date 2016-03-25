package com.toolkit.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.toolkit.Tools.TXTregexOnline;



import com.toolkit.Tools.TXTregex;

@Controller
public class regexController{
	/*
	@RequestMapping(value="dealRegex" ,method=RequestMethod.POST)
    @ResponseBody
    public void dealRegex(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, Object> map)
    {
		String pos = (Strinjsong) map.get("pos");
		String inputtext = (String) map.get("inputtext");
		String regex = (String) map.get("regex");
		System.out.println(inputtext);
		System.out.println(regex);
		System.out.println(pos);
		System.out.println("1");
    }
    */
	
	@RequestMapping(value = "dealRegex", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String dealRegex(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String[]> paramMap = request.getParameterMap();
		String inputtext = paramMap.get("inputtext")[0];
		String regex = paramMap.get("regex")[0];
		String pos = paramMap.get("pos")[0];
		System.out.println(regex);
		TXTregexOnline d=new TXTregexOnline(inputtext,regex,pos);
		String r=d.deal();
		//System.out.println(r);
		
		String nr=r.replace("\n\r", "\n");
		String dealedFilePath=request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/views/resources/fileDealed/dealedFile.csv";
		File file=new File(dealedFilePath);
		if(file.exists())
            file.delete();
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(out, "gb2312");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(nr);
		bw.close();
        osw.close();
        out.close(); 
		return r;
	}
	@ResponseBody
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String uploadFile( @RequestParam(value = "file_data") MultipartFile file,  @RequestParam(value = "regex") String regex, @RequestParam(value = "pos") int pos,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String r="error";
		if (!file.isEmpty()) {  
			try{
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				System.out.println(regex);
				System.out.println(pos);
				String filePath = request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/views/resources/fileUpload/"+ file.getOriginalFilename();  
				File tfile=new File(filePath);
				if (tfile.exists()) {  
			        tfile.delete();  
				}
				file.transferTo(new File(filePath));  
				/*
				if(files.length>0){
					
				}*/
				TXTregex d=new TXTregex(filePath,regex,pos);
				r=d.deal();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return r;
	}
	
}
