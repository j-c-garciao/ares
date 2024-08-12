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

package co.edu.unicartagena.ares.window;

import co.edu.unicartagena.ares.draw.ArrowHead;
import co.edu.unicartagena.ares.window.actions.AddArc;
import co.edu.unicartagena.ares.window.actions.AddNode;
import co.edu.unicartagena.ares.window.actions.ModifyArc;
import co.edu.unicartagena.ares.window.actions.ModifyNode;
import co.edu.unicartagena.ares.objects.Arc;
import co.edu.unicartagena.ares.objects.Node;
import co.edu.unicartagena.ares.objects.Canva;
import co.edu.unicartagena.ares.draw.PointArcs;
import co.edu.unicartagena.ares.pgraph.solver.Solution;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener {
    private Canva project;

    private Point p1;
    private Point p2;

    private int mode = 1;

    private int diameter = 80;

    private String source;
    private String destination;
    
    private boolean middlepoint =false;
    private boolean dragged =false;
    private boolean unselected = false; 
    
    
    private String ArcSource = "";
    private String ArcDestination = "";
    private Arc arcP;
    private Arc arco;
    private Node node;
        
    private int middlePX=0;
    private int middlePY=0;
    
    private int fromPX=0;
    private int toPX=0;
    private int fromPY=0;
    private int toPY=0;
    
    private boolean grid=false; 
    private boolean flow_text=false; 
    private boolean solution=false;
    boolean exist=true;
    
    private Solution feasible_solution;
    private Set<String> solution_node;
    // constructor
    public DrawingPanel() {
        addMouseMotionListener(this);
        addMouseListener(this);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        // PANEL SIZE
        setPreferredSize(new Dimension(6000,6000));
    }

    public void setProject(Canva project) {
        this.project = project;
        repaint();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        p1 = null;
        p2 = null;
        node=null;
        //arco=null;
        repaint();
    }

    // Metodo para pintar el project
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(2));
        ArrayList<Arc> arcs_rm = new ArrayList<>();
        if(grid){
            //GUIDELINES
            for (int i = 0; i < 250; i++) {
                g.drawLine(i * 18 + 1, 0, i * 18 + 1, 6000);
                g.drawLine(0, i * 18 + 1, 7200, i * 18 + 1);
            }
        }

        try{  
            /* UPDATE NEW VERSION */             
            // loop for drawing every arc    
            int h=1; 
            for (Arc arc : project.getArcs()) {
                exist=false;
                double flow=0;
                Node source = project.searchNode(arc.getSource());
                Node destination = project.searchNode(arc.getDestination());
                    
                if((source==null || destination==null)){
                    arcs_rm.add(arc);
                    repaint();
                }
  
                if(feasible_solution!=null){                  
                    for(Arc a: feasible_solution.getArcs()){  
                        if(project.searchNodeByName(a.getSource())!=null &&
                           project.searchNodeByName(a.getDestination())!=null){
                            if(project.searchNodeByName(a.getSource()).getId().compareTo(source.getId())==0 &&
                                project.searchNodeByName(a.getDestination()).getId().compareTo(destination.getId())==0){
                                exist=true;
                                flow=a.getCapacityd();
                            }   
                        }else{
                        continue;
                        } 
                    }
                }
                
             
                if (flow==0 && feasible_solution!=null)
                    g2d.setColor(Color.LIGHT_GRAY);
                else
                    g2d.setColor(Color.BLACK);

                // ARC
                // EXTERNAL HELP
                // https://stackoverflow.com/questions/47369565/connect-two-circles-with-a-line
                
                angleBetween(source, destination);
               
                double from = angleBetween(source, destination);
                double to = angleBetween(destination, source);
                
                PointArcs pointFrom = getPointOnCircle(new PointArcs(source.getX(), source.getY()), from,40);
                PointArcs pointTo = getPointOnCircle(new PointArcs(destination.getX(), destination.getY()), to,40);

                g2d.drawLine((int)pointFrom.getX(), (int)pointFrom.getY(), (int)pointTo.getX(),(int)pointTo.getY());
             
                
                /* UPDATE NEW VERSION */
                if(middlepoint && arco.getIdh().equals(arc.getIdh()) && !unselected){
                    //look for the middle point
                    if(arc.getId()==arcP.getId() && arcP.getXmiddle()==0){
                        //System.out.println("PASO ... 3.1");
                        Node s = project.searchNode(ArcSource);
                        Node d = project.searchNode(ArcDestination);
                        
                        fromPX=(int)pointFrom.getX();
                        fromPY=(int)pointFrom.getY();
                        
                        toPX=(int)pointTo.getX();
                        toPY=(int)pointTo.getY();
                        
                        middlePX=(((int)pointFrom.getX()+(int)pointTo.getX())/2-5);
                        middlePY=(((int)pointFrom.getY()+(int)pointTo.getY())/2-5);
                        //SET UP ORIENTATION

                        //System.out.println("PASO ... 3.2");                        
                        if((int)pointFrom.getX()>(int)pointTo.getX()){
                            g.setColor(Color.red);
                            g.fillRect(((int)pointFrom.getX()+ (int)pointTo.getX())/2-5, ((int)pointFrom.getY()+(int)pointTo.getY())/2-5, 10, 10);
                        }else{
                            g.setColor(Color.red);
                            g.fillRect(((int)pointFrom.getX()+ (int)pointTo.getX())/2-5, ((int)pointFrom.getY()+(int)pointTo.getY())/2-5, 10, 10);
                        }

                    }
                }
                //System.out.println("PASO ... 4");       
                //DRAW ARROW
                ArrowHead arrowHead = new ArrowHead();
                AffineTransform at = AffineTransform.getTranslateInstance(
                                     pointTo.getX() - (arrowHead.getBounds2D().getWidth() / 2d), 
                                     pointTo.getY());
                at.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
                arrowHead.transform(at);
                g2d.draw(arrowHead);

                /* END DRAW ARROW*/
                 
                Point pMedio = new Point((source.getX() + destination.getX()) / 2, (source.getY() + destination.getY()) / 2);
                if(feasible_solution==null){
                    g.setColor(Color.black);
                    g.setFont(new Font("Default",Font.BOLD, 20));
                    /*if(destination.getY()>=source.getY())
                        //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x+10, pMedio.y-10);
                        //g.drawString("(" + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x+10, pMedio.y-10);
                        //g.drawString("(" + arc.getCapacity()+ ")", pMedio.x+10, pMedio.y-10);
                        if(project.getFlowTextCheck()){
                            System.out.println(project.getFlowTextCheck()+" "+(h++));
                            g.drawString( String.valueOf(arc.getCapacityd()), pMedio.x+10, pMedio.y-10);
                        }
                        //System.out.println("");
                    else */
                    //if(source.getX()<=destination.getX())
                      //  System.out.println(project.getFlowTextCheck()+" "+(h++));
                        //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x-10, pMedio.y-10);
                        //g.drawString("(" + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x-10, pMedio.y-10);
                        //g.drawString("(" + arc.getCapacity()+ ")", pMedio.x-10, pMedio.y-10);
                        if(project.getFlowTextCheck()){
                             //System.out.println(project.getFlowTextCheck()+" "+(h++));
                            g.drawString(String.valueOf(arc.getCapacityd()), pMedio.x-10, pMedio.y-10);
                        }     
                        //System.out.println("");
                }else{
                    if(!exist){
                        //LETRA
                        g.setColor(Color.LIGHT_GRAY);
                        g.setFont(new Font("Default",Font.BOLD, 20));
                        //if(destination.getY()>=source.getY())
                            //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x+10, pMedio.y-10);
                            //g.drawString("(" + flow+ "," + arc.getCapacity() + ")", pMedio.x+10, pMedio.y-10);
                            //g.drawString("(" + flow+ ")", pMedio.x+10, pMedio.y-10);
                           // if(project.getFlowTextCheck())
                           //     g.drawString(String.valueOf(flow+ "/"+ arc.getCapacityd()), pMedio.x+10, pMedio.y-10);
                        //else 
                        //if(source.getX()<=destination.getX())
                            //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x-10, pMedio.y-10);
                            //g.drawString("(" + flow+ "," + arc.getCapacity() + ")", pMedio.x-10, pMedio.y-10);                  
                            if(project.getFlowTextCheck())
                                g.drawString(String.valueOf(flow+"/"+arc.getCapacityd()), pMedio.x-10, pMedio.y-10);                  
                    }else{
                        //LETRA
                        g.setColor(Color.black);
                        g.setFont(new Font("Default",Font.BOLD, 20));
                        //if(destination.getY()>=source.getY())
                            //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x+10, pMedio.y-10);
                            //g.drawString("(" + flow+ "," + arc.getCapacity() + ")", pMedio.x+10, pMedio.y-10);
                            //if(project.getFlowTextCheck())
                            //    g.drawString(String.valueOf(flow+"/"+arc.getCapacityd()), pMedio.x+10, pMedio.y-10);
                        //else 
                        //if(source.getX()<=destination.getX())
                            //g.drawString("(" + arc.getTravel_time()+ "," + arc.getCapacity()+ "," + arc.getId() + ")", pMedio.x-10, pMedio.y-10);
                            //g.drawString("(" + flow+ "," + arc.getCapacity() + ")", pMedio.x-10, pMedio.y-10);             
                            //g.drawString("(" + flow+ ")", pMedio.x-10, pMedio.y-10);  
                            if(project.getFlowTextCheck())
                                g.drawString(String.valueOf(flow+"/"+arc.getCapacityd()), pMedio.x-10, pMedio.y-10);             
                    }
                }
            }  
        } catch (Exception e) {
            System.out.println("Drawing panel ARCS "+e);
        }

        project.getArcs().removeAll(arcs_rm);
        
        
        
        /*
         DRAW NODES
        */
            
        try {
            for(Node n : project.getNodes()) {
                exist=true;
                // FEASIBLE SOLUTIONS
                if(!solution){
                    g2d.setColor(Color.WHITE);
                }
                else{
                    exist=false;
                    for(String s: solution_node){
                        if(s.compareTo(n.getName())==0){
                            exist=true;
                        }
                    }   
                    // FILL NODE                    
                    if(!exist){
                        g2d.setColor(Color.LIGHT_GRAY);
                    } else{
                        g2d.setColor(Color.WHITE);  
                    }
                }       
                            
                int esc = diameter;
                
                //CAMBIO ESC/2          
                
                g2d.fillOval(n.getX() - esc/2, n.getY() - esc/2, esc, esc);
                
                //BORDER AND TEXT      
                if(!exist){
                    g2d.setColor(Color.LIGHT_GRAY);}
                else{
                    //if(node!=null && node.getName().equals(n.getName())){
                   //if(node!= null && mode == 2 && p1!=null){
                    //  g.setColor(Color.red);
                    //}else{
                    g2d.setColor(Color.black);    
                    // }
                }
                
                if(node!=null && node.getName().equals(n.getName()) && ( mode == 2 || mode == 5 ) && p1!=null){
                    g2d.setColor(Color.RED);    
                    g2d.drawOval(n.getX() - esc/2, n.getY() - esc/2, esc, esc); 
                    g2d.setColor(Color.black);
                } else{
                    g2d.drawOval(n.getX() - esc/2, n.getY() - esc/2, esc, esc);
                    }
                
                               
                g2d.setFont(new Font("Default", 0, 30));
                g2d.drawString(n.getName(), n.getX() - 16, n.getY() + 8);
                g2d.setFont(new Font("Default", Font.BOLD, 15));        
                /*
                CURRENT APROACH: MAXIMUM FLOW PROBLEM
                */
                String ntype="";
                if (n.getNtype().compareTo("Source")==0){
                    ntype="(s)";
                } else if (n.getNtype().compareTo("Sink")==0){
                    ntype="(t)";
                }     
                          
                g2d.drawString(ntype, n.getX() - 10, n.getY() + 30);
                
                
                
                
            }
        } catch (Exception e) {
            System.out.println("DRAW NODE ...");
        }

        if (p1 != null) {
            if (mode == 1) {
                int esc = diameter;
                g.setColor(Color.yellow);
                g.fillOval(p1.x - esc/2, p1.y - esc/2, esc, esc);
                g.setColor(Color.black);
                g.drawOval(p1.x - esc/2, p1.y - esc/2, esc, esc);
            } else if (mode == 2) {
                if (p2 != null) {
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                } 
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if (mode == 3) {
            Node n = project.searchNode(me.getPoint());
            if (n != null) {
                p1 = me.getPoint();
                project.modifyNode(n, p1);
                repaint();
            }
        }
        
        /* UPDATE NEW VERSION */
        if(middlepoint){
            if((me.getPoint().x>=middlePX && me.getPoint().x<=middlePX+5) ||
                (me.getPoint().y>=middlePY && me.getPoint().y<=middlePY+5)){
                //System.out.println("Movement "+me.getPoint().x+" "+me.getPoint().y);
                //System.out.println(middlePX+" "+middlePY);
                //System.out.println("Trying to move element");
                p1=me.getPoint();
                repaint();
                //middlepoint=false;
            }
        }
        /**/
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (mode == 1) {
            p1 = me.getPoint();
            repaint();
        } else if (mode == 2) {
             if (p1 != null) {
                p2 = me.getPoint();
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Arc a = project.searchArc(me.getPoint());
        arco = a;
        if (a != null) {
            middlepoint=true;
            ArcSource=a.getSource();
            ArcDestination=a.getDestination();
            arcP=a;
            p1=me.getPoint();
            unselected=false;
            repaint();
        }
        else{
            middlepoint=false;
            unselected=true;
            repaint();
        }
    
        if (mode == 1) {
            p1 = me.getPoint();
            // si no hay un nodo en el punto p1 agregamos el nuevo nodo
            if (project.searchNode(p1) == null) {
                AddNode window = new AddNode(project, p1);
                window.setVisible(true);
                mode = 0;
            }
            // CHANGE OCTOBER 04/10/2023
            
        } else if (mode == 2) {
            Node n = project.searchNode(me.getPoint());
            node = n;
            if (n != null) {
                // si p1 es igual a nulo guardamos el primer punto
                if (p1 == null) {
                    //source = n.getName();
                    source = n.getId();
                    p1 = me.getPoint();
                    unselected=false;
                } else {      // si no, significa que ya hay un nodo seleccionado
                              // y con segundo punto terminamos de crear el arc
                    
                    //destination = n.getName();
                    destination = n.getId();
                    
                    p2 = me.getPoint();

                    AddArc vent = new AddArc(project, source, destination);
                    vent.setVisible(true);
                    mode = 0;
                }
            }
        } else if (mode == 4) {

            Node n = project.searchNode(me.getPoint());
            if (n != null) {
                unselected=false;
                ModifyNode vent = new ModifyNode(project, n.getId());
                vent.setVisible(true);
                mode = 0;
            } else {
                a = project.searchArc(me.getPoint());
                if (a != null) {
                    ModifyArc vent = new ModifyArc(project, a.getIdh());
                    vent.setVisible(true);

                    mode = 0;
                }
            }
        } else if (mode == 5) {
            Node n = project.searchNode(me.getPoint());
            node = n;
            if (n != null) {
                if (p1 == null) {
                    source = n.getId();
                    p1 = me.getPoint();
                }           
                
                int resp = JOptionPane.showConfirmDialog(null, "¿Do you want to delete this node?",
                        "Delete node", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (resp == 0) {
                    project.deleteNode(n);
                    repaint();
                }
            } else {
                a = project.searchArc(me.getPoint());
               
                if (a != null) {
                    System.out.println("ARC "+a.getIdh());
                    int resp = JOptionPane.showConfirmDialog(null, "¿Do you want to delete this arc?",
                            "Delete arc", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                    if (resp == 0) {
                        project.deleteArc(a);
                        //System.out.println("Arc size "+project.getArcs().size());
                        repaint();
                    }
                }
            }
        //repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    private double angleBetween(Node source, Node destination){
        int     p1s_x = source.getX();
        int p1s_y = source.getY();
                
        int p2d_x = destination.getX();
        int p2d_y = destination.getY();
                
        //DELTA
                
        int deltaX = p2d_x - p1s_x;
        int deltaY = p2d_y - p1s_y;
                
        //ROTATION
                
        double rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toRadians(Math.toDegrees(rotation) + 180);
        return rotation;
    }
    
    private PointArcs getPointOnCircle(PointArcs center, double radians, double radius) {

        double x = center.getX();
        double y = center.getY();

        radians = radians - Math.toRadians(90.0); // 0 becomes the top
        // Calculate the outter point of the line
        double xPosy = Math.round((float) (x + Math.cos(radians) * radius));
        double yPosy = Math.round((float) (y + Math.sin(radians) * radius));

        return new PointArcs(xPosy,yPosy);
    }
    
    public void removeNodes_Arcs(Canva c){
        this.project=null;
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.project=c;
        this.revalidate();
        this.repaint();
    }
    
    public void setGrid(boolean grid){
        this.grid=grid;
        this.repaint();
    }
    
     public void setFlowText(boolean ft){
        this.flow_text=ft;
        this.repaint();
    }   
    
    
    
    public void displaySolution(Solution feasible_solution, boolean solution,UIMain UIMain){
        this.feasible_solution=feasible_solution;
        this.solution=solution;
        setSolutionNodes(feasible_solution);
        //CHANGE JAN 23 2024
        UIMain.setMaxFlow(String.valueOf(Math.ceil(this.feasible_solution.getMax_flow())));
        
        this.repaint();
    }
    
    private void setSolutionNodes(Solution sol){
        //System.out.println("SetSolutionNodes");
        solution_node=new HashSet();
        for(Arc a: sol.getArcs()){
            System.out.println(a.getSource()+" * "+a.getDestination());
            System.out.println(a.getIdh());
            if(!solution_node.contains(a.getSource())){
                System.out.println("source "+a.getSource());
                solution_node.add(a.getSource().trim());
            }
            if(!solution_node.contains(a.getDestination())){
                System.out.println("destination "+a.getDestination());
                solution_node.add(a.getDestination().trim());
            }
        }      
    }   
}