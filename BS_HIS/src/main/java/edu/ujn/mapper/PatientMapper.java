package edu.ujn.mapper;

import edu.ujn.pojo.Patient;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientMapper extends Mapper<Patient> {
    List<Patient> findPatientsByDocIdAndPatName(Patient patient);
    void updatePatientLeaveHospital(Patient patient);
    Integer findNowPatientsNumberByPatDocIdAndOutIsNULL(Integer patDocId);
    Integer findNewPatientsInNumber(Integer patDocId);
    Integer findNewPatientsOutNumber(Integer patDocId);
}
