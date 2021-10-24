package edu.ujn.controller;

import edu.ujn.pojo.HeartIndex;
import edu.ujn.pojo.Patient;
import edu.ujn.pojo.PatientDailyRecord;
import edu.ujn.service.PatientService;
import edu.ujn.util.DMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/DM")
public class DMController {

    @Autowired
    private DMUtils dmUtils;

    @RequestMapping("/heartPredict")
    public ResponseEntity heartPredict(@RequestBody HeartIndex heartIndex){
        System.out.println("heartIndex = " + heartIndex);
        Map map = new HashMap();
        String strHeartInfo = "";
        strHeartInfo += heartIndex.getAge()+",";
        strHeartInfo += heartIndex.getSex()+",";
        strHeartInfo += heartIndex.getCp()+",";
        strHeartInfo += heartIndex.getTrestbps()+",";
        strHeartInfo += heartIndex.getChol()+",";
        int fbs = heartIndex.getFbs();
        if(fbs>120){
            strHeartInfo += "1,";
        }
        else{
            strHeartInfo += "0,";
        }
        strHeartInfo += heartIndex.getRestecg()+",";
        strHeartInfo += heartIndex.getThalach()+",";
        strHeartInfo += heartIndex.getExang()+",";
        strHeartInfo += heartIndex.getOldpeak()+",";
        strHeartInfo += heartIndex.getSlope()+",";
        strHeartInfo += heartIndex.getCa()+",";
        strHeartInfo += heartIndex.getThal();
        try {
            Boolean predict = dmUtils.predict(strHeartInfo);
            if(predict){
                heartIndex.setCondition(1);
            }
            else{
                heartIndex.setCondition(0);
            }
            map.put("heartIndex",heartIndex);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

}
