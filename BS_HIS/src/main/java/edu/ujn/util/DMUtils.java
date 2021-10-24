package edu.ujn.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DMUtils {

    public Boolean predict(String heartInfo){
        Process process;
        String[] arg = new String[]{"python","F:\\py\\DecisionTreeModel.py",heartInfo};
        try {
            process = Runtime.getRuntime().exec(arg);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine())!= null){
                if(line.charAt(1)=='1'){
                    return true;
                }
            }
            in.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
