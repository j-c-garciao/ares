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
package co.edu.unicartagena.ares.pgraph;

import co.edu.unicartagena.ares.objects.Arc;
import co.edu.unicartagena.ares.objects.Node;
import co.edu.unicartagena.ares.pgraph.solver.Solution;
import co.edu.unicartagena.ares.pgraph.solver.Solver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


/**
 *
 * @author jc_go
 */
public class MF2PNS {
    private ArrayList<Node> nodes;
    private ArrayList<Arc> arcs;
    private int max_solutions;
    private String name;
    private ArrayList<Solution> solution; 
    private double lowerbounds;

    public MF2PNS(ArrayList<Node> nodes, ArrayList<Arc> arcs, int max_solutions, String name) throws IOException, InterruptedException {
        this.arcs = arcs;
        this.nodes = nodes;
        this.max_solutions = max_solutions;
        System.out.println(max_solutions);
        this.name=name;
        reductionAlgorithm(nodes,arcs,max_solutions);
    }
    
    public MF2PNS(ArrayList<Node> nodes, ArrayList<Arc> arcs, int max_solutions, String name, double lowerbounds) throws IOException, InterruptedException {
        this.arcs = arcs;
        this.nodes = nodes;
        this.max_solutions = max_solutions;
        System.out.println(max_solutions);
        this.name=name;
        this.lowerbounds=lowerbounds;
        reductionAlgorithm(nodes,arcs,max_solutions);
    }

    private void reductionAlgorithm(ArrayList<Node> nodes, ArrayList<Arc> arcs, int max_solutions) throws IOException, InterruptedException{
        boolean debug=true;    
        String strLocalDIR= ""; 
        String DIR_PNSFILE= "";
        //System.out.println(generatePNSFile(nodes,arcs));
        JFileChooser jf = new JFileChooser();
        FileSystemView fs = jf.getFileSystemView();
        File f= new File(fs.getDefaultDirectory()+"\\ares.IDE\\");
               
        if(!f.exists()){
            f.mkdir();
        }
        System.out.println(this.name);
        f=new File(fs.getDefaultDirectory()+"\\ares.IDE\\"+this.name);
        if(!f.exists()){
            f.mkdir();
        }else {
        f.delete();
        f.mkdir();
        }
         
        /*
        PNS MODEL
        */
        
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(f.toString()+"\\"+this.name.substring(0, this.name.length()-4)+"pns.in"));
            //out.write(generatePNSFile2(nodes,arcs)+"\n");	    
            //UPDATE MAY172023
            if(lowerbounds>0.0){ //UPDATE
                out.write(generatePNSFile2(nodes,arcs)+"\n");
                System.out.println("lowerbounds > 0.0");
            }
            else{
                System.out.println("lowerbounds = 0.0");
                out.write(generatePNSFile3(nodes,arcs,lowerbounds)+"\n");
            }
            out.close();
        }
	catch(IOException e){
           System.out.println("Error Class pns_model method PgraphConstruction "+e);
	}
		 
        try{
	    Solver is = new Solver(f.toString()+"\\"+this.name.substring(0, this.name.length()-4)+"pns.in",this.max_solutions);
	    setListSolutions(is.createModel2());
        }
	catch(TimeoutException e){
            System.out.println("SOLVER "+e);
            }
	}
    
    private void setListSolutions(ArrayList<Solution> solution){
        this.solution=solution;
    }
    
    public ArrayList<Solution> getListSolution(){
        return this.solution;
    }
    
    private String generatePNSFile(ArrayList<Node> nodes, ArrayList<Arc> arcs){
        String pns_file="file_type=PNS_problem_v1 \n"+
                        "file_name=dert \n"+
		        "\n"+
                        "measurement_units:\n"+
		        "mass_unit=t\n"+
		        "time_unit=yr\n"+
		        "money_unit=Euro\n"+
		        "\n"+
			"defaults:\n"+
			"material_type=intermediate\n"+
			"material_flow_rate_lower_bound=0\n"+
			"material_flow_rate_upper_bound=100000000\n"+
			"material_price=0\n"+
			"operating_unit_capacity_lower_bound=0\n"+
			"operating_unit_capacity_upper_bound=100000000\n"+
			"operating_unit_fix_cost=0\n"+
			"operating_unit_proportional_cost=0\n"+
		        "\n"+
		        "materials:\n";
			String texto2="";
			for(Node n: nodes){
                            if(n.getNtype().compareTo("Source")==0)
                                texto2=texto2+n.getName().trim()+": raw_material\n";
                        }
                        
                        for(Node n: nodes){
                            if(n.getNtype().compareTo("Sink")==0)
                                 texto2=texto2+n.getName()+": product, price=1\n";
                        }
                                  
			texto2=texto2+"\n";
			texto2=texto2+"operating_units:\n";
                        for(Arc a: arcs){
                           texto2=texto2+a.getSource().trim()+"_"+a.getDestination()+": capacity_upper_bound="+a.getCapacity()+"\n";
                        }
			texto2=texto2+"\n";
			texto2=texto2+"material_to_operating_unit_flow_rates:\n";
                        for(Arc a: arcs){
                           texto2=texto2+a.getSource().trim()+"_"+a.getDestination()+": "+a.getSource()+" => "+a.getDestination()+"\n";
                        }
	return pns_file+texto2+"\n".trim();               
    }  
    
    private String generatePNSFile2(ArrayList<Node> nodes, ArrayList<Arc> arcs){
        Set<String> source = new HashSet();
        Set<String> sink = new HashSet();
        
        String pns_file="file_type=PNS_problem_v1 \n"+
                        "file_name=dert \n"+
		        "\n"+
                        "measurement_units:\n"+
		        "mass_unit=t\n"+
		        "time_unit=yr\n"+
		        "money_unit=Euro\n"+
		        "\n"+
			"defaults:\n"+
			"material_type=intermediate\n"+
			"material_flow_rate_lower_bound=0\n"+
			"material_flow_rate_upper_bound=100000000\n"+
			"material_price=0\n"+
			"operating_unit_capacity_lower_bound=0\n"+
			"operating_unit_capacity_upper_bound=100000000\n"+
			"operating_unit_fix_cost=0\n"+
			"operating_unit_proportional_cost=0\n"+
		        "\n"+
		        "materials:\n";
			                
        for(Node n: nodes){
            if(n.getNtype().compareTo("Source")==0){
                source.add(n.getName().trim());
            } else if (n.getNtype().compareTo("Sink")==0){ 
                sink.add(n.getName().trim());
            } 
        }
                            
        System.out.println(source+" *** "+sink);
        String texto2="";
        for(Node n: nodes){
            if(n.getNtype().compareTo("Source")==0)
                if(source.size()==1)
                    texto2=texto2+n.getName()+": raw_material\n";
                else{
                    texto2=texto2+"SuperSource: raw_material\n";
                    break;
                }       
            }
                        
        for(Node n: nodes){
            if(n.getNtype().compareTo("Sink")==0)
                if(sink.size()==1)
                    texto2=texto2+n.getName()+": product, price=1\n";
                else{
                    texto2=texto2+"SuperSink: product, price=1\n";
                    break;
                }
        }
        
        for(Node n: nodes){
            System.out.println(n.getName() +" "+n.getNtype());
            if(n.getNtype().compareTo("Sink")!=0 && n.getNtype().compareTo("Source")!=0) {
                texto2=texto2+n.getName()+": intermediate, flow_rate_upper_bound=0\n";
            }
        }
        
                                  
	texto2=texto2+"\n";
        texto2=texto2+"operating_units:\n";
        
        int total_ou=0;
        
        if(source.size()>1)
            total_ou=total_ou+source.size();
        
        if(sink.size()>1)
            total_ou=total_ou+sink.size();
        
        total_ou=total_ou+arcs.size();
        
        double log = Math.ceil(Math.log10(total_ou*1E4));
        int exp = (int)(log);
        
        
        
        for(Arc a: arcs){
            //UPDATE FIXED COST
            texto2=texto2+getNodeName(a.getSource().trim())+"_"+getNodeName(a.getDestination())+": capacity_upper_bound="+a.getCapacityd()+", fix_cost=1E-"+Integer.toString(exp)+"\n";    
        }
        if(source.size()>1){
            for (String s:source){
                texto2=texto2+"SuperSource_"+s.trim()+": capacity_upper_bound=1000000000"+", fix_cost=1E-"+Integer.toString(exp)+"\n"; 
            }
        }
        
        if(sink.size()>1){
            for (String s:sink){
                 texto2=texto2+s.trim()+"_SuperSink: capacity_upper_bound=1000000000"+", fix_cost=1E-"+Integer.toString(exp)+"\n"; 
            }
        }
               
        texto2=texto2+"\n";
        texto2=texto2+"material_to_operating_unit_flow_rates:\n";
        for(Arc a: arcs){
            texto2=texto2+getNodeName(a.getSource().trim())+"_"+getNodeName(a.getDestination())+": "+getNodeName(a.getSource())+" => "+getNodeName(a.getDestination())+"\n";  
        }
        
        if(source.size()>1){
            for (String s:source){
                texto2=texto2+"SuperSource_"+s.trim()+": SuperSource => "+s.trim()+"\n";
            }
        }    
            
        if(sink.size()>1){
            for (String s:sink){
                texto2=texto2+s.trim()+"_SuperSink: "+s.trim()+" => SuperSink\n";
            } 
        }
        return pns_file+texto2+"\n".trim();               
    }  
    // UPDATE 17/05/2023
    private String generatePNSFile3(ArrayList<Node> nodes, ArrayList<Arc> arcs,double lowerbounds){
        Set<String> source = new HashSet();
        Set<String> sink = new HashSet();
        
        String pns_file="file_type=PNS_problem_v1 \n"+
                        "file_name=dert \n"+
		        "\n"+
                        "measurement_units:\n"+
		        "mass_unit=t\n"+
		        "time_unit=yr\n"+
		        "money_unit=Euro\n"+
		        "\n"+
			"defaults:\n"+
			"material_type=intermediate\n"+
			"material_flow_rate_lower_bound=0\n"+
			"material_flow_rate_upper_bound=100000000\n"+
			"material_price=0\n"+
			"operating_unit_capacity_lower_bound=0\n"+
			"operating_unit_capacity_upper_bound=100000000\n"+
			"operating_unit_fix_cost=0\n"+
			"operating_unit_proportional_cost=0\n"+
		        "\n"+
		        "materials:\n";
			                
        for(Node n: nodes){
            if(n.getNtype().compareTo("Source")==0){
                source.add(n.getName().trim());
            }
            if(n.getNtype().compareTo("Sink")==0) 
                sink.add(n.getName().trim());
        }
                            
        System.out.println(source+" "+sink);
        String texto2="";
        for(Node n: nodes){
            if(n.getNtype().compareTo("Source")==0)
                if(source.size()==1)
                    texto2=texto2+n.getName()+": raw_material\n";
                else{
                    texto2=texto2+"SuperSource: raw_material\n";
                    break;
                }       
        }
                        
        for(Node n: nodes){
            if(n.getNtype().compareTo("Sink")==0)
                if(sink.size()==1)
                    texto2=texto2+n.getName()+": product, price=1\n";
                else{
                    texto2=texto2+"SuperSink: product, price=1\n";
                    break;
                }
        }
                                  
	texto2=texto2+"\n";
        texto2=texto2+"operating_units:\n";
        for(Arc a: arcs){
                texto2=texto2+getNodeName(a.getSource().trim())+"_"+getNodeName(a.getDestination())+": capacity_lower_bound="+lowerbounds+", capacity_upper_bound="+a.getCapacityd()+"\n";    
        }
        if(source.size()>1){
            for (String s:source){
                texto2=texto2+"SuperSource_"+s.trim()+": capacity_upper_bound=1000000000\n";
            }
        }
        
        if(sink.size()>1){
            for (String s:sink){
                 texto2=texto2+s.trim()+"_SuperSink: capacity_upper_bound=1000000000\n";
            }
        }
               
        texto2=texto2+"\n";
        texto2=texto2+"material_to_operating_unit_flow_rates:\n";
        for(Arc a: arcs){
            texto2=texto2+getNodeName(a.getSource().trim())+"_"+getNodeName(a.getDestination())+": "+getNodeName(a.getSource())+" => "+getNodeName(a.getDestination())+"\n";  
        }
        
        if(source.size()>1){
            for (String s:source){
                texto2=texto2+"SuperSource_"+s.trim()+": SuperSource => "+s.trim()+"\n";
            }
        }    
            
        if(sink.size()>1){
            for (String s:sink){
                texto2=texto2+s.trim()+"_SuperSink: "+s.trim()+" => SuperSink\n";
            } 
        }
        return pns_file+texto2+"\n".trim();               
    }

    private String getNodeName(String idh){
        String name = "";
        for (Node node: nodes){
            //System.out.println(node.getId()+" *** "+idh);
            if(node.getId().compareTo(idh)==0){
                //System.out.println("NAME "+node.getName());
                return node.getName();
            }
        }
        return name;
    }
}