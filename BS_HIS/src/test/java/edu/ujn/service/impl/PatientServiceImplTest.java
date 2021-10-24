package edu.ujn.service.impl;

import edu.ujn.pojo.PatientDailyRecord;
import edu.ujn.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PatientServiceImplTest {

    @Resource
    PatientService patientService;

    @Test
    public void testFindByPdrPatId(){
        List<PatientDailyRecord> list = patientService.getDailyRecordByPdrPatId(1);
        for (PatientDailyRecord patientDailyRecord : list) {
            System.out.println("patientDailyRecord = " + patientDailyRecord);
        }
    }

    @Test
    public void testPatientsNum(){
        Integer nowPatientsNumber = patientService.getNowPatientsNumber(1);
        System.out.println("nowPatientsNumber = " + nowPatientsNumber);

        Integer inNumber = patientService.getNewPatientsInNumber(1);
        System.out.println("inNumber = " + inNumber);

        Integer outNumber = patientService.getNewPatientsOutNumber(1);
        System.out.println("outNumber = " + outNumber);
    }
}