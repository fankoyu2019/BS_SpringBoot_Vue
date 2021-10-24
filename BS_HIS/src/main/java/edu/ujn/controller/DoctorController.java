package edu.ujn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.ujn.pojo.Doctor;
import edu.ujn.pojo.Patient;
import edu.ujn.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @RequestMapping("/getDoctor")
    public ResponseEntity<Map> getDoctor(@RequestBody Map map){
        try {
            String dcId = (String) map.get("queryId");
            Doctor doctor = doctorService.getDoctor(dcId);
            map.put("doctor",doctor);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/getPatientsList")
    public ResponseEntity<Map> getPatientsList(@RequestBody Map map){
        String DocId = (String) map.get("queryId");
        String query = (String) map.get("query");
        int pageNum = (Integer) map.get("pageNum");
        int pageSize = (Integer) map.get("pageSize");

        try {
            PageHelper.startPage(pageNum,pageSize);
            List<Patient> patientsList = doctorService.getPatientsList(DocId,query);
            PageInfo<Patient> patientPageInfo = new PageInfo<>(patientsList);
            map.clear();
            map.put("patientsList",patientPageInfo.getList());
            map.put("total",patientPageInfo.getTotal());
            map.put("pageNum",patientPageInfo.getPageNum());
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/savePatient/{patDocId}")
    public ResponseEntity savePatient(@PathVariable("patDocId") Integer patDocId, @RequestBody Patient patient){
//        JSONObject jsonObject = new JSONObject();
        patient.setPatDocId(patDocId);
        patient.setPatInTime(new Date());
        System.out.println("patDocId = " + patDocId);
        System.out.println("patient = " + patient);

        Map map= new HashMap();
        try {
            doctorService.savePatient(patient);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }


    }
}
