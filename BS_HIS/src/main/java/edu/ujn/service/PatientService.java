package edu.ujn.service;


import edu.ujn.pojo.Patient;
import edu.ujn.pojo.PatientDailyRecord;

import java.util.List;

public interface PatientService {
    Patient findPatientByPatId(Integer patId);
    void updatePatientByPatId(Integer patId,Patient patient);
    void updatePatientLeaveHospital(Patient patient);
    List<PatientDailyRecord> getDailyRecordByPdrPatId(Integer pdrPatId);
    Integer getNowPatientsNumber(Integer patDocId);
    Integer getNewPatientsInNumber(Integer patDocId);
    Integer getNewPatientsOutNumber(Integer patDocId);
}
