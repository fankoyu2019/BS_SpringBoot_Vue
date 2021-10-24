package edu.ujn.service;

import edu.ujn.pojo.Doctor;
import edu.ujn.pojo.Patient;
import edu.ujn.pojo.User;

import java.util.List;

public interface DoctorService {
    public Doctor getDoctor(String dcId);
    public List<Patient> getPatientsList(String dcId,String queryName);
    public void savePatient(Patient patient);
}
