package edu.ujn.service.impl;

import edu.ujn.mapper.DoctorMapper;
import edu.ujn.mapper.PatientMapper;
import edu.ujn.pojo.Doctor;
import edu.ujn.pojo.Patient;
import edu.ujn.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private PatientMapper patientMapper;

    @Override
    public Doctor getDoctor(String dcId) {
        try {
            Doctor doctor = doctorMapper.selectByPrimaryKey(dcId);
            return doctor;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Patient> getPatientsList(String dcId, String queryName) {
        Patient patient = new Patient();
        int patDocId = Integer.parseInt(dcId);
        patient.setPatDocId(patDocId);
        if (queryName != "") {
            patient.setPatName(queryName);
        }
        try {
            List<Patient> patientList = patientMapper.findPatientsByDocIdAndPatName(patient);
            return patientList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void savePatient(Patient patient) {
        try {
            patientMapper.insertSelective(patient);
        } catch (Exception e) {
            return ;
        }
    }

}
