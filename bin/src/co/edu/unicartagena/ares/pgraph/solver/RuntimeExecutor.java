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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeoutException;

public class RuntimeExecutor {
	private long timeout =Long.MAX_VALUE;
	public RuntimeExecutor(){
		
	}
	
public RuntimeExecutor(long timeout){
		this.timeout=timeout;
	}

public  double [] execute(String command)throws IOException,TimeoutException{
	double [] array = new double[3];
	array[0]=0;
	array[1]=0;
	array[2]=0;
	
	Process p = Runtime.getRuntime().exec(command);
	Timer timer = new Timer();
	timer.schedule(new Solver(Thread.currentThread()), timeout);
	try{
		p.waitFor();
	}
	catch(InterruptedException e){
		p.destroy();
		throw new TimeoutException (command + " did not return after " + this.timeout+ " milliseconds");
	}
	finally{
		timer.cancel();
	}
	
	StringBuilder buffer = new StringBuilder();
	BufferedInputStream br = new BufferedInputStream(p.getInputStream());
	while(br.available()!=0){
		buffer.append((char)br.read());
	}
        
	//System.out.println(buffer.toString().substring(buffer.toString().indexOf("value of ")+9,buffer.toString().indexOf("Euro.")));
	String rsltd = buffer.toString().trim();
	   System.out.println(rsltd);
        if(rsltd.indexOf("Complete.")!=-1){
		if(rsltd.indexOf("Computation")!=-1){
			double comp = Double.parseDouble(rsltd.substring(rsltd.indexOf("Computation"), rsltd.length()).split("\n")[0].split(" ")[2]) * 3600
					      +
					      Double.parseDouble(rsltd.substring(rsltd.indexOf("Computation"), rsltd.length()).split("\n")[0].split(" ")[4]) * 60
					      + 
					      Double.parseDouble(rsltd.substring(rsltd.indexOf("Computation"), rsltd.length()).split("\n")[0].split(" ")[6]);
			array[0]=1;
			array[1]=comp;
			System.out.println("PNS SOLVER FOUND THE BEST NETWORK IN := "+comp+" CPU seconds");
			//System.out.println("****************************************************************\n");
			
			//System.out.println(rsltd.substring(rsltd.indexOf("Computation"), rsltd.length()).split("\n")[0].split(" ")[6]);
		}
		return array;
	}
	return array;
	}
}