package com.candidateresult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j; 
@Slf4j
public class ReadProperties {
    
    
    
    private ReadProperties() {
        
       
    }

    public static Properties read() 
    { 
        // create a reader object on the properties file 
        try{
            Properties p = new Properties(); 
            
            // Add a wrapper around reader object 
            //p.load(new FileInputStream(Paths.get(".")); 
           
            p.load( new FileInputStream(Paths.get(".").toAbsolutePath().normalize().toString()+File.separator+"credentials.properties"));
            
           
            // access properties data 
            return p; 
            
        }
        
  
        // create properties object
        catch(Exception e) {
            log.info(ConsoleColors.RED+"error in loading properties file"+ConsoleColors.RESET);
          
            
      
        }
        return null;
       
    } 

}
