package com.toolkit.Tools;

import java.util.ArrayList;

public class TXTregexOnline {
	public String inputtext;
	public String inputString;
	public String posNum;
	public String pos;
	public TXTregexOnline(String inputtext,String regex,String posNum){
		this.inputtext=inputtext;
		this.inputString=regex;
		this.posNum=pos;
		if(posNum.equals("1"))
			pos="before";
		else
			pos="after";
	}
	/*
	public static void main(String args[]){
		//TXTregexOnline a=new TXTregexOnline("asfasd#\nasfasf*\n","<1>#<2>*","2");
		TXTregexOnline a=new TXTregexOnline("#asfasd\n*asfasf\n","<1>#<2>*","1");
		String out;
		out=a.deal();
		System.out.println("end");
	}
	*/
	public String deal(){
		String out="";
		if(this.pos.equals("after")){
			out=this.dealWithLabelAfter();
		}
		else
			out=this.dealWithLabelBefore();
		System.out.println(out);
		return out;
	}
	public String dealWithLabelAfter(){
		ArrayList<String[]> list=dealRegex(this.inputString);
		int listLength=list.size();
		Boolean blankLine=false;
		String tempString=null;
		int indexForList=0;
		String output="";
		String[] clause=new String[listLength];
		String[] part=this.inputtext.split("\n");
		output=writeInHead(output,list);
		String recordString="";
		Boolean waitingForSingle=true;
		int i=0;
		for(i=0;i<part.length;i++){
			tempString=part[i];
			blankLine=false;
			if(tempString.trim().equals("")){
				blankLine=true;
				waitingForSingle=true;
			}
			if(indexForList==0 && list.get(indexForList)[2].equals("single")){
				tempString=tempString.trim();
				if(!blankLine){
					if(waitingForSingle){
						clause[indexForList]=tempString;
						if(indexForList==listLength-1){
							output=writeInContent(output,clause);
							indexForList=0;
						}
						else
							indexForList=indexForList+1;
						waitingForSingle=false;
						continue;
					}
					else{
						indexForList=indexForList+1;
					}
				}
			}
			if(list.get(indexForList)[2].equals("repeat")){
				if(blankLine){
					clause[indexForList]=recordString;
					recordString="";
					output=writeInContent(output,clause);
					if(indexForList==listLength-1){
						indexForList=0;
					}
					else
						indexForList=indexForList+1;
				}
				else{
					int indexForTest;
					if(indexForList==listLength-1){
						for(indexForTest=0;indexForTest<indexForList;indexForTest++){
							if(tempString.indexOf(list.get(indexForTest)[1])!=-1){
								clause[indexForList]=recordString;
								recordString="";
								output=writeInContent(output,clause);
								indexForList=indexForTest;
								break;
							}
							else{
								if(list.get(indexForTest)[2].equals("onetime")  || list.get(indexForTest)[2].equals("single")){
									continue;
								}
								else{
									break;
								}
							}
						}
					}
					else{
						indexForTest=indexForList+1;
						if(tempString.indexOf(list.get(indexForTest)[1])!=-1){
							clause[indexForList]=recordString;
							recordString="";
							indexForList=indexForTest;
						}
					}
				}
			}
			if(list.get(indexForList)[2].equals("onetime")){
				int indexForTest;
				for(indexForTest=indexForList;;indexForTest++){
					if(tempString.indexOf(list.get(indexForTest)[1])!=-1){
						indexForList=indexForTest;
						break;
					}
					else{
						if(list.get(indexForTest)[2].equals("onetime")){
							continue;
						}
						else{
							break;
						}
					}
				}
			}
			if(blankLine)
				continue;
			if(tempString.indexOf(list.get(indexForList)[1])==-1){
				tempString=tempString.trim();
				recordString=recordString+tempString;
				continue;
			}
			else{
				if(list.get(indexForList)[2].equals("repeat")){
					tempString=tempString.trim();
					tempString=tempString.substring(0, tempString.length()-list.get(indexForList)[1].length());
					recordString=recordString+tempString;
				}
				else{
					tempString=tempString.trim();
					tempString=tempString.substring(0, tempString.length()-list.get(indexForList)[1].length());
					clause[indexForList]=recordString+tempString;
					recordString="";
					if(indexForList==listLength-1){
						output=writeInContent(output,clause);
						indexForList=0;
					}
					else
						indexForList=indexForList+1;
				}
			}
		}
		if(list.get(indexForList)[2].equals("repeat")){
			clause[indexForList]=recordString;
			output=writeInContent(output,clause);
		}
		return output;
	}
	public String dealWithLabelBefore(){
		ArrayList<String[]> list=dealRegex(this.inputString);
		int listLength=list.size();
		String tempString=null;
		Boolean blankLine=false;
		int indexForList=0;
		String[] clause=new String[listLength];
		String recordString="";
		boolean waitingForLine=true; 
		boolean start=true;
		String output="";
		output=writeInHead(output,list);
		String[] part=this.inputtext.split("\n");
		int i=0;
		for(i=0;i<part.length;i++){
			tempString=part[i];
			if(start){
				//tempString=tempString.substring(1,tempString.length());
				start=false;
			}
			blankLine=false;
			if(tempString.trim().equals(""))
				blankLine=true;
			if(indexForList==0 && list.get(indexForList)[2].equals("single")){
				if(waitingForLine==true){
					tempString=tempString.trim();
					if(!blankLine){
						clause[indexForList]=tempString;
						if(indexForList==listLength-1){
							output=writeInContent(output,clause);
							indexForList=0;
							waitingForLine=true;
						}
						else{
							indexForList=indexForList+1;
							waitingForLine=false;
						}
						continue;
					}
				}
			}
			if(indexForList==0 &&(blankLine)){
				if(!recordString.equals("")){
					clause[listLength-1]=recordString;
					output=writeInContent(output,clause);
					recordString="";
				}
				waitingForLine=true;
			}
			else
				waitingForLine=false;
			if(tempString.indexOf(list.get(indexForList)[1])==-1){
				tempString=tempString.trim();
				recordString=recordString+tempString;
			}
			else{
				if(!recordString.equals("")){
					if(indexForList==0){
						clause[listLength-1]=recordString;
						output=writeInContent(output,clause);
						tempString=tempString.trim();
						recordString=tempString.substring(tempString.indexOf(list.get(indexForList)[1])+list.get(indexForList)[1].length(), tempString.length());				
					}
					else{
						clause[indexForList-1]=recordString;
						tempString=tempString.trim();
						recordString=tempString.substring(tempString.indexOf(list.get(indexForList)[1])+list.get(indexForList)[1].length(), tempString.length());	
					}
					if(indexForList==listLength-1)
						indexForList=0;
					else
						indexForList=indexForList+1;
				}
				else{
					tempString=tempString.trim();
					recordString=tempString.substring(tempString.indexOf(list.get(indexForList)[1])+list.get(indexForList)[1].length(), tempString.length());
					if(indexForList==listLength-1)
						indexForList=0;
					else
						indexForList=indexForList+1;
				}
			}
		}
		clause[listLength-1]=recordString;
		output=writeInContent(output,clause);
		return output;
	}
	public static ArrayList<String[]> dealRegex(String s){
		ArrayList<String[]> list=new ArrayList<String[]>();
		int startnum=0;
		if(s.indexOf("<",startnum)==-1){
			System.out.println("unrecognized input format");
			System.exit(0);
		}		
		while(true){
			int startDesPoint=s.indexOf("<",startnum);
			int endDesPoint=s.indexOf(">",startnum);
			String name=s.substring(startDesPoint+1, endDesPoint);
			int nextDesPoint=s.indexOf("<",endDesPoint+1);
			if(nextDesPoint==-1){
				String label=s.substring(endDesPoint+1, s.length());
				String info=null;
				String pos=null;
				list.add(checkAttribute(name,label,info,pos));
				break;

			}
			else{
				String label=s.substring(endDesPoint+1, nextDesPoint);
				String info=null;
				String pos=null;
				list.add(checkAttribute(name,label,info,pos));
				startnum=endDesPoint+1;
			}
		}
		return list;
	}
	public static String[] checkAttribute(String name, String label, String info, String pos){
		if(label.endsWith("{a}")){
			pos="after";
			label=label.substring(0, label.length()-3);
		}
		else
			pos="before";
		
		if(label.endsWith("{r}")){
			info="repeat";
			label=label.substring(0, label.length()-3);
		}
		else if(label.endsWith("{o}")){
			info="onetime";
			label=label.substring(0, label.length()-3);
		}
		else if(label.endsWith("{s}")){
			info="single";
			label=label.substring(0, label.length()-3);
		}
		else
			info="none";
		String[] column={name,label,info,pos};
		return column;
	}
	public static String writeInHead(String output,ArrayList<String[]> list){
		String header="";
		for(int i=0;i<list.size();i++){
			header=header+list.get(i)[0]+",";
		}
		header=header.substring(0, header.length()-1)+"\n\r";
		output=output+header;
		return output;
	}
	public static String writeInContent(String output,String[] clause){
		String content="";
		for(int i=0;i<clause.length;i++){
			content=content+clause[i]+",";
		}
		content=content.substring(0, content.length()-1)+"\n\r";
		output=output+content;
		return output;
	}
}
