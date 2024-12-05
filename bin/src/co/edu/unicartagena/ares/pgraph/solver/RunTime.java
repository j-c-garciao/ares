/*
Copyright (c) 2021 - 2024, Juan Carlos Garcia Ojeda, Universidad de Cartagena
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.
   
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package co.edu.unicartagena.ares.pgraph.solver;
import java.io.*;
import java.util.concurrent.TimeoutException;

public class RunTime {
    String DIR_PNSFILE = null;
    String file = null;
    int maxPlans = 1;
	
	
    public RunTime(String DIR_PNSFILE,String file, int maxPlans) throws TimeoutException{
        this.DIR_PNSFILE=DIR_PNSFILE;
	this.file=file;
        this.maxPlans = maxPlans;
    }	
	 
    public double [] execute() throws TimeoutException{
	     double [] array = new double[2];
		 try{
			 Process p = //Runtime.getRuntime().exec("C:\\Users\\jc_go\\Dropbox\\1.-JCGO\\2.-WIZARDSLAB.SAS\\SOFTWARE\\P-graph\\PNSStudio_old\\pns_solver.exe INSIDEOUT "+
                                 Runtime.getRuntime().exec("C:\\Users\\jc_go\\Dropbox\\1.-JCGO\\2.-WIZARDSLAB.SAS\\SOFTWARE\\P-graph\\PNSStudio_old\\pns_solver.exe INSIDEOUT "+
	    	                                   //C:\\Users\\jc_go\\Documents\\NetBeansProjects\\ARES\\B2PStudio.IDE\\PNSStudio_2015\\pns_depth.exe INSIDEOUT
                                                   //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\in\\"+file+".pns.in").getAbsolutePath() +" "+
	    			                   //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\out").getAbsolutePath().toString()+"\\"+file+".pns.out");
	    			                   new File(DIR_PNSFILE+"\\"+file+".pns.in").getAbsolutePath() +" "+
	    			                   new File(DIR_PNSFILE+"\\files\\"+file+".pns.out") +" "+this.maxPlans);
	 
			while(p.isAlive()){
			      System.out.println("EDEDEDED");
			}		 
			InputStream in = p.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	System.out.println("LINING..."+line);
	            out.append(line);
	        }
	        System.out.println("PRINTING"+out);
	        String time = out.substring(out.indexOf(" min ")+4, out.indexOf(" sS")); 
	        array[0]=1;
	        array[1]=Double.parseDouble(time);
		 }
	     catch(IOException e){
	    	 e.printStackTrace();
	     }
		return array;
	}
    
    public double [] executeUpdated() throws TimeoutException{
	     double [] array = new double[2];
		 try{
			 //Runtime rt = Runtime.getRuntime();
			 //String[] commands = {"system.exe","-get t"};
			 
			 Process proc = Runtime.getRuntime().exec("C:\\Users\\jc_go\\Dropbox\\1.-JCGO\\1.-ACADEMICO\\IES\\7.-WORKSPACE\\PhDProjects\\P-graph\\solver\\pns_solver.exe INSIDEOUT "+
                     //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\in\\"+file+".pns.in").getAbsolutePath() +" "+
	                   //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\out").getAbsolutePath().toString()+"\\"+file+".pns.out");
	                   new File(DIR_PNSFILE+"\\"+file+".pns.in").getAbsolutePath() +" "+
	                   new File(DIR_PNSFILE+"\\"+file+".pns.out") +" 1");
			 
			 //Process proc = rt.exec(commands);

			 BufferedReader stdInput = new BufferedReader(new 
			      InputStreamReader(proc.getInputStream()));

			 BufferedReader stdError = new BufferedReader(new 
			      InputStreamReader(proc.getErrorStream()));

			 // read the output from the command
			 System.out.println("Here is the standard output of the command:\n");
			 String s = null;
			 while ((s = stdInput.readLine()) != null) {
			     System.out.println(s);
			 }

			 // read any errors from the attempted command
			 System.out.println("Here is the standard error of the command (if any):\n");
			 while ((s = stdError.readLine()) != null) {
			     System.out.println(s);
			 }
			 
			 /*Process p = Runtime.getRuntime().exec("C:\\Users\\jc_go\\Dropbox\\1.-JCGO\\2.-WIZARDSLAB.SAS\\SOFTWARE\\P-graph\\PNSStudio_old\\pns_solver.exe INSIDEOUT "+
	    	                           //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\in\\"+file+".pns.in").getAbsolutePath() +" "+
	    			                   //new File(System.getProperty("user.dir")+"files\\"+input_file_name+"\\pns\\out").getAbsolutePath().toString()+"\\"+file+".pns.out");
	    			                   new File(System.getProperty("user.dir")+"files\\"+file+".pns.in").getAbsolutePath() +" "+
	    			                   new File(System.getProperty("user.dir")+"files\\"+file+".pns.out") +" 1");
	 
			while(p.isAlive()){
				
			}		 
			InputStream in = p.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	System.out.println("LINING..."+line);
	            out.append(line);
	        }
	        System.out.println("PRINTING"+out);
	        String time = out.substring(out.indexOf(" min ")+4, out.indexOf(" sS")); 
	        array[0]=1;
	        array[1]=Double.parseDouble(time);*/
		 }
	     catch(Exception e){
	    	 e.printStackTrace();
	    	 System.out.println("HERE");
	     }
		return array;
	}
}