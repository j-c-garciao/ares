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

package co.edu.unicartagena.ares.app;

import co.edu.unicartagena.ares.app.util.HexString;
import co.edu.unicartagena.ares.window.UIMain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class AppvX {
    public static void main(String[] args) {
        //DOWNLOAD PNS_SOLVER Github Repository
        List pns_solver_files = new ArrayList<String>();
        pns_solver_files.add("libgcc_s_dw2-1.dll");
        pns_solver_files.add("libgcc_s_seh-1.dll");
        pns_solver_files.add("libgomp-1.dll");
        pns_solver_files.add("libstdc++-6.dll");
        pns_solver_files.add("libwinpthread-1.dll");
        pns_solver_files.add("pgraph_solver.exe");
        
        String repo_url = "https://github.com/j-c-garciao/ares/raw/main/PNSStudio_v2022/Solver/";
                
        //USER LOCAL REPO
        JFileChooser jf = new JFileChooser();
        FileSystemView fs = jf.getFileSystemView();
        
        File f= new File(fs.getDefaultDirectory()+"\\ares.IDE\\PNSSolver\\");
               
        if(!f.exists()){
            f.mkdir();
        }   
        
        try{
            while(!pns_solver_files.isEmpty()){
                //org.apache.commons.io.FileUtils.copyURLToFile(new URL(repo_url+pns_solver_files.get(0)), new File(fs.getDefaultDirectory()+"\\ares.IDE\\PNSSolver\\"+pns_solver_files.get(0)));
                new AppvX().copyURLToFile(repo_url+pns_solver_files.get(0),fs.getDefaultDirectory()+"\\ares.IDE\\PNSSolver\\"+pns_solver_files.get(0));
                pns_solver_files.remove(0);
            }
        }
        catch(Exception e){
               System.out.println(e); 
        }
                
        UIMain interfaz = new UIMain();
        interfaz.setVisible(true);
    }
    
    private void copyURLToFile(String u, String f) throws FileNotFoundException, IOException {
        System.out.println(u);
    InputStream in = new URL(u).openStream();        
    try {
       FileOutputStream out = new FileOutputStream(f);
       try {                  
          byte[] buffer = new byte[1024];
          int count;
         while ((count = in.read(buffer)) > 0){
                    out.write(buffer, 0, count);
         }
          out.flush();
       } finally {
          out.close();
       }
    } finally {
       in.close();
    }
}
}
