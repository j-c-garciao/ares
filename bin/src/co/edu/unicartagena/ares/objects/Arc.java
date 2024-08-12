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

public class Arc {
    
    private String source;
    private String destination;
    private int travel_time;
    private int capacity;
    private double capacityd;
    private int xmiddle;
    private int ymiddle;
    private int id;
    private int flow;
    private String idh;

    public Arc() {
    }

    public Arc(String source, String destination, int travel_time, int capacity, int id) {
        this.source = source;
        this.destination = destination;
        this.travel_time = travel_time;
        this.capacity = capacity;
        this.id = id;
    }
    
     public Arc(String source, String destination, int capacity, int id) {
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.id = id;
    }
     
    public Arc(String source, String destination, double capacityd, int id) {
        this.source = source;
        this.destination = destination;
        this.capacityd = capacityd;
        this.id = id;
    }
    
        public Arc(String source, String destination, double capacityd, String idh) {
        this.source = source;
        this.destination = destination;
        this.capacityd = capacityd;
        this.idh = idh;
    }
    
    
    
     
    public Arc(String source, String destination, double capacityd) {
        this.source = source;
        this.destination = destination;
        this.capacityd = capacityd;
    } 

    public String getIdh() {
        return idh;
    }

    public void setIdh(String idh) {
        this.idh = idh;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(int travel_time) {
        this.travel_time = travel_time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCapacityd() {
        return capacityd;
    }

    public void setCapacityd(double capacityd) {
        this.capacityd = capacityd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getXmiddle() {
        return xmiddle;
    }

    public void setXmiddle(int xmiddle) {
        this.xmiddle = xmiddle;
    }

    public int getYmiddle() {
        return ymiddle;
    }

    public void setYmiddle(int ymiddle) {
        this.ymiddle = ymiddle;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
    
    @Override
    public String toString(){
        return String.format("Arc [idh = %d,\n"+
                             "     source= %s,\n"+
                             "     destination = %s,\n"+
                             "     travel time = %s,\n"+
                             "     capacity = %s]",
                             idh,source,destination,travel_time,capacity);
    }
}