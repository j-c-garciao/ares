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

import co.edu.unicartagena.ares.objects.Arc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Solver extends TimerTask  {

	Thread target = null;
	String file = null;
        int maxRoutes = 15;
	Set<Set<String>> solutions= new HashSet<Set<String>>();
	Set<String> partial= new HashSet<String>();
	public Solver(Thread target){
		this.target=target;
	}
	
	public Solver(String file, int maxRoutes) throws TimeoutException{
		this.file=file;
                this.maxRoutes=maxRoutes;
	}
	
    public ArrayList<Solution> createModel() throws TimeoutException, IOException{
        RuntimeExecutor r = new RuntimeExecutor(10000);
	try{
            //r.execute("C:\\Users\\jc_go\\Documents\\NetBeansProjects\\ARES\\B2PStudio.IDE\\PNSStudio_2015\\pns_depth.exe INSIDEOUT "+
            r.execute("C:\\Users\\jc_go\\Documents\\NetBeansProjects\\ARES\\B2PStudio.IDE\\PNSStudio_2015\\pns_depth.exe INSIDEOUT "+
            this.file+" "+file.substring(0,file.lastIndexOf("\\")+1)+this.file.substring(file.lastIndexOf("\\")+1,file.length()-3)+".out "+maxRoutes);
            //new File("img\\").getAbsolutePath().toString()+"\\"+file.substring(file.lastIndexOf("\\")+1,file.length()-7)+".routes 1000");
            //System.out.println("EXECUTE");
            //System.out.println("C:\\Users\\jc_go\\Documents\\NetBeansProjects\\ARES\\B2PStudio.IDE\\PNSStudio\\pns_depth.exe INSIDEOUT "+
            //this.file+" "+file.substring(0,file.lastIndexOf("\\")+1)+this.file.substring(file.lastIndexOf("\\")+1,file.length()-4)+".out "+maxRoutes);
            return getFeasibleSolutions(file.substring(0,file.lastIndexOf("\\")+1)+this.file.substring(file.lastIndexOf("\\")+1,file.length()-3)+".out",null);
        }
	catch(IOException e){
            System.out.println("RuntimeExecutor "+e);
            return null;
	}        
    }
    
    //UPDATE 10/10/2024
    //CURRENT IMPLEMENTATION
    public ArrayList<Solution> createModel2() throws TimeoutException, IOException, InterruptedException{
       try{
            //CHANGE "C:\\Users\\jc_go\\Documents\\NetBeansProjects\\ARES\\B2PStudio.IDE\\PNSStudio_2015\\
            String rm =file.substring(0,file.lastIndexOf("\\"));
            rm =rm.substring(0,rm.lastIndexOf("\\")+1);
            
            
            Process p = Runtime.getRuntime().exec(rm+"PNSSolver\\pgraph_solver.exe INSIDEOUT "+
            this.file+" "+file.substring(0,file.lastIndexOf("\\")+1)+this.file.substring(file.lastIndexOf("\\")+1,file.length()-3)+".out "+maxRoutes);
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String comp_time="";
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            int index=line.indexOf("min ");
            if(index!=-1)
                comp_time=line.substring(index+4,line.length());
        }
            System.out.println("COMPT_TIME "+comp_time);
            
            
            Thread.sleep(5000);
            return getFeasibleSolutions(file.substring(0,file.lastIndexOf("\\")+1)+this.file.substring(file.lastIndexOf("\\")+1,file.length()-3)+".out",comp_time);
        }
	catch(IOException e){
            System.out.println("RuntimeExecutor "+e);
            return null;
	}        
    }
    
    
    
    
    
    private ArrayList<Solution>  getFeasibleSolutions(String output, String comp_time) throws IOException{
        ArrayList<Solution> array = new ArrayList();
        String str;
        int k=1;
        BufferedReader br= new BufferedReader(new FileReader(output));
	while((str = br.readLine()) != null) {
            if(str.indexOf("Operating units:")==0){
                System.out.println("solution "+k);
                k++;       
                Solution sol = new Solution();
                while ((str = br.readLine()) != null && str.indexOf("Total annual cost=")==-1){
                    if(str.indexOf("*")!=-1){
                        /*
                        CHANGE AUGUST 9TH 2023
                        */
                        //double flow=-1.0;
                        //String flux=str.substring(0,str.indexOf("*"));
                        //if(flux.indexOf(".")==-1)
                            //flow = Integer.valueOf(str.substring(0,str.indexOf("*")));
                        double flow = Double.valueOf(str.substring(0,str.indexOf("*")));
                        //else
                        //    flow = Double.valueOf(str.substring(0,str.indexOf("*")));
                        
                        //int flow = Integer.valueOf(str.substring(0,str.indexOf("*")));
                        str=str.substring(str.indexOf("*")+1, str.indexOf(" "));
                        str=str.replaceAll("_", "-");
                        String info[] = str.split("-");
                        String ni = info[0].trim();
			String nj = info[1].trim();
			System.out.println(ni+"->"+nj+" : flow = "+flow);
                        sol.setArc(new Arc(ni,nj,flow,0));  
                        sol.setComp_time(comp_time);
                    }			    	
                }if(str.contains("Total annual cost=")){
                    int i=str.indexOf("Total annual cost=")+18;
                    int j=str.lastIndexOf(" ");
                    str=str.substring(i,j);
                    //CHANGE
                    
                    double max_flow=Double.parseDouble(str.trim())*-1;
                    if(max_flow>0){
                        System.out.println("Max Flow= "+max_flow);
                        sol.setMax_flow(max_flow);
                        array.add(sol);
                    }
                }
            }
        }  
     return array;   
    } 
     
    @Override
    public void run() {
        // TODO Auto-generated method stub
        target.interrupt();
    }
}