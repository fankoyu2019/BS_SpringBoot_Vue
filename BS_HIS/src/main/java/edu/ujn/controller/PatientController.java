package edu.ujn.controller;

import edu.ujn.pojo.Patient;
import edu.ujn.pojo.PatientDailyRecord;
import edu.ujn.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping("/findPatientByPatId/{patId}")
    public ResponseEntity savePatient(@PathVariable("patId") Integer patId) {
        Map map = new HashMap();
        try {
            Patient patient = patientService.findPatientByPatId(patId);
            map.put("patient",patient);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/updatePatient/{patId}")
    public ResponseEntity updatePatient(@PathVariable("patId") Integer patId,@RequestBody Patient patient){
        System.out.println("patient = " + patient);
        Map map = new HashMap();
        try {
            patientService.updatePatientByPatId(patId,patient);
            map.put("status",200);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/leaveHospital")
    public ResponseEntity leaveHospital(@RequestBody Patient patient){
        patient.setPatOutTime(new Date());
        System.out.println("patient = " + patient);
        Map map = new HashMap();
        try {
            patientService.updatePatientLeaveHospital(patient);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/getDailyRecord/{patId}")
    public ResponseEntity getDailyRecord(@PathVariable("patId") Integer pdrPatId){
        Map map = new HashMap();
        try {
            List<PatientDailyRecord> patientDailyRecordList = patientService.getDailyRecordByPdrPatId(pdrPatId);
            map.put("patientDailyRecordList",patientDailyRecordList);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/getPatientsNumber/{patDocId}")
    @Transactional
    public ResponseEntity getNowPatientsNumber(@PathVariable("patDocId") Integer patDocId){
        Map map = new HashMap();
        try {
            Integer nowPatientsNumber = patientService.getNowPatientsNumber(patDocId);
            Integer inNumber = patientService.getNewPatientsInNumber(patDocId);
            Integer outNumber = patientService.getNewPatientsOutNumber(patDocId);
            map.put("nowPatientsNumber", nowPatientsNumber);
            map.put("inNumber", inNumber);
            map.put("outNumber", outNumber);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map>(map,HttpStatus.NOT_FOUND);
        }
    }

}
