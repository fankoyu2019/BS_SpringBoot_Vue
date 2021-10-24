package edu.ujn.service.impl;

import edu.ujn.mapper.PatientDailyRecordMapper;
import edu.ujn.mapper.PatientMapper;
import edu.ujn.pojo.Patient;
import edu.ujn.pojo.PatientDailyRecord;
import edu.ujn.service.PatientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Resource
    PatientMapper patientMapper;

    @Resource
    PatientDailyRecordMapper patientDailyRecordMapper;

    @Override
    public Patient findPatientByPatId(Integer patId) {
        try {
            Patient patient = patientMapper.selectByPrimaryKey(patId);
            return patient;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updatePatientByPatId(Integer patId, Patient patient) {
        try {
            int i = patientMapper.updateByPrimaryKey(patient);
            System.out.println("i = " + i);
        } catch (Exception e) {
        }
    }

    @Override
    public void updatePatientLeaveHospital(Patient patient) {
        patientMapper.updatePatientLeaveHospital(patient);
    }

    @Override
    public List<PatientDailyRecord> getDailyRecordByPdrPatId(Integer pdrPatId) {

        List<PatientDailyRecord> list = null;
        try {
            list = patientDailyRecordMapper.findDailyRecordsByPdrPatId(pdrPatId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public Integer getNowPatientsNumber(Integer patDocId) {
        Integer nowPatientsNumber = 0;
        try {
            nowPatientsNumber = patientMapper.findNowPatientsNumberByPatDocIdAndOutIsNULL(patDocId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowPatientsNumber;
    }

    @Override
    public Integer getNewPatientsInNumber(Integer patDocId) {
        Integer inNumber = null;
        try {
            inNumber = patientMapper.findNewPatientsInNumber(patDocId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inNumber;
    }

    @Override
    public Integer getNewPatientsOutNumber(Integer patDocId) {
        Integer OutNumber = null;
        try {
            OutNumber = patientMapper.findNewPatientsOutNumber(patDocId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OutNumber;
    }
}
