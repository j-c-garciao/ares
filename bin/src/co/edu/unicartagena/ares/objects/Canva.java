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

package co.edu.unicartagena.ares.objects;

import co.edu.unicartagena.ares.app.util.HexString;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Canva {

    private String nombre;
    private ArrayList<Arc> arcs = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private List<Model> models = new ArrayList<>();
    private Set<String> ids = new HashSet<>();
    
    
    //OBJECTS ID GENERATOR 
    HexString codegen;

    private int max_routes=100;
    private int max_solutions=10000;
    private double lowerbounds=0.0000001;
    private boolean flow_text_check = true;

    public int getMax_solutions() {
        return max_solutions;
    }

    public void setMax_solutions(int max_solutions) {
        this.max_solutions = max_solutions;
    }

    public double getLowerbounds() {
        return lowerbounds;
    }

    public void setLowerbounds(double lowerbounds) {
        this.lowerbounds = lowerbounds;
    }
    private int max_plans=10;
    private int upper_bound=50;

    public Canva(String nombre) {
        this.nombre = nombre;
        this.codegen=new HexString(32);       
    }

    public boolean addNode(Node node, int caso) {
        if(caso==0){
            nodes.add(node);
        } else{
            for(Node n : nodes) {
                if (n.getName().equals(node.getName())) {
                    return false;
                }
            }
            String id=getID();
            //System.out.println(id);
            node.setId(id);
            nodes.add(node);
        }
        return true;
    }

    public boolean addArc(Arc arcoN, int caso) {
        if(caso==0){
            arcs.add(arcoN);
        } else{
            for(Arc arc : arcs) {
                if (arc.getIdh().equals(arcoN.getIdh())) {
                    return false;
                }
            }
            String id=getID();
            arcoN.setIdh(id);
            arcs.add(arcoN);
            System.out.println("ARCS SIZE "+arcs.size());
        }
        return true;
    }     


    public Node searchNode(Point punto) {
        
        
        
        Node nodoC = null;
        for (Node nodo : nodes) {
            //PRINT NODE POINT
            //UBICACIÓN NODO
            //System.out.println("NODE X"+nodo.getX()+" NODO Y"+nodo.getY());
            //System.out.println("PUNTO X"+punto.x+" PUNTO Y"+punto.y);
                       
            int radio = 80 / 2;
            double distancia = Math.sqrt(Math.pow(nodo.getX() - punto.x, 2) + Math.pow(nodo.getY() - punto.y, 2));
            if (distancia < radio) {
                nodoC = nodo;
                break;
            }
        } 
        return nodoC;
    }

    public Arc searchArc(Point punto) {
        Arc arcoC = null;
        for (Arc arco : arcs) {
            //System.out.println(arco.getIdh());
            Node n1 = searchNode(arco.getSource());
            Node n2 = searchNode(arco.getDestination());
            double slope = (double) (n1.getY() - n2.getY())/(double) (n1.getX() - n2.getX());
            double yAux = slope * (punto.x - n1.getX()) + n1.getY();

            if (Math.abs(yAux - punto.y) < 8) {
                arcoC = arco;
                System.out.print(" -*- "+arco.getIdh());
                break;
            }
        }

        return arcoC;
    }
    
    public Node searchNode(String idx) {
        Node nodoC = null;
        for (Node nodo : nodes) {
            
            //System.out.println(nodo.getId()+" "+nodo.getName()+" "+idx);
            if (nodo.getId().equals(idx)) {
                nodoC = nodo;
            break;
            }
        }
        return nodoC;
    }
    
    public Node searchNodeByName(String name) {
        Node nodoC = null;
        for (Node nodo : nodes) {
            if (nodo.getName().equals(name)) {
                //System.out.println("FOUND "+name);
                nodoC = nodo;
                return nodoC;
            }
        }
        return nodoC;
    }
    
    
    public boolean existNode(String nombre) {
        for(Node nodo : nodes) {
            if (nodo.getName().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
    
    public Arc searchArc(int id) {
        Arc arcoC = null;
        for (Arc arco : arcs) {
            if (arco.getId() == id) {
                arcoC = arco;
                break;
            }
        }
        return arcoC;
    }
    
    public Arc searchArc(String idh) {
        Arc arcoC = null;
        for (Arc arco : arcs) {
            if (arco.getIdh().compareTo(idh)==0) {
                arcoC = arco;
                break;
            }
        }
        return arcoC;
    }
    
    
    
    
    

    public void modifyNode(Node nodo, Point punto) {

        for (int i = 0; i < nodes.size(); i++) {

            if (nodes.get(i).getName().equals(nodo.getName())) {

                nodo.setX(punto.x);
                nodo.setY(punto.y);
                nodes.set(i, nodo);
                break;
            }
        }
    }
    
    public boolean modifyArc(Arc arco, int ident) {

        for (int i = 0; i < arcs.size(); i++) {

            if (arcs.get(i).getId() == arco.getId()) {
                arcs.set(i, arco);
                return true;
            }
        }
        return false;
    }
    
    public boolean modifyArc(Arc arco, String idh) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getIdh().compareTo(arco.getIdh())==0) {
                arcs.set(i, arco);
                return true;
            }
        }
        return false;
    } 

    public boolean modifyNode(Node nodo, String nombre) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals(nombre)) {
                nodes.set(i, nodo);
                return true;
            }
        }
        return false;
    }
    
        public boolean modifyNode(Node nodo, String idh,int dummy) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId().equals(idh)) {
                nodes.set(i, nodo);
                return true;
            }
        }
        return false;
    }
    

    public void deleteNode(Node nodo) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals(nodo.getName())) {
                /*
                for (int j = 0; j < arcs.size(); j++) {
                    if (arcs.get(j).getSource().equals(nodo.getId())
                            || arcs.get(j).getDestination().equals(nodo.getId())) {
                        arcs.remove(j);
                    }
                }
                */
                nodes.remove(i);
                break;
            }
        }
    }

    public void deleteArc(Arc arco) {
        for (int i = 0; i < arcs.size(); i++) {
            if (arcs.get(i).getIdh().equals(arco.getIdh())) {
                arcs.remove(i);
                break;
            }
        }
    }

    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(ArrayList<Arc> arcs) {
        this.arcs = arcs;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

    public int getMax_routes() {
        return max_routes;
    }

    public void setMax_routes(int max_routes) {
        this.max_routes = max_routes;
    }

    public int getMax_plans() {
        return max_plans;
    }

    public void setMax_plans(int max_plans) {
        this.max_plans = max_plans;
    }

    public int getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(int upper_bound) {
        this.upper_bound = upper_bound;
    }
    
    private String getID(){
        boolean flag=false;
        String id="";
        while(!flag){
            id=codegen.getID();
            if(!ids.contains(id)){
               flag=true;
               ids.add(id);
            }
        }       
        return id;   
    }
    
    public void setFlowText(){
        this.flow_text_check=!this.flow_text_check;
    }
    
    public boolean getFlowTextCheck(){
        return this.flow_text_check;
    }
    
            
    
}