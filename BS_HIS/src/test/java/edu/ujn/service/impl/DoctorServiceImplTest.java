package edu.ujn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.ujn.pojo.Doctor;
import edu.ujn.pojo.Patient;
import edu.ujn.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DoctorServiceImplTest {
    @Resource
    private DoctorService doctorService;


    @Test
    public void queryByDoctorId() {
        Doctor doctor = doctorService.getDoctor("1");
        System.out.println("doctor = " + doctor);
    }

    @Test
    public void findByDocIdAndQuery(){
        List<Patient> patientsList = doctorService.getPatientsList("1", "w");
        for (Patient patient : patientsList) {
            System.out.println("patient = " + patient);
        }
    }

    @Test
    public void findByDocId(){
        PageHelper.startPage(1,2);
        List<Patient> patientsList = doctorService.getPatientsList("1","w");
        System.out.println("patientsList = " + patientsList.size());
        PageInfo<Patient> patientPageInfo = new PageInfo<Patient>(patientsList);
        System.out.println("当前页"+patientPageInfo.getPageNum());
        System.out.println("每页显示条数"+patientPageInfo.getPageSize());
        System.out.println("总条数"+patientPageInfo.getTotal());
        System.out.println("总页数"+patientPageInfo.getPages());
        System.out.println("上一页"+patientPageInfo.getPrePage());
        System.out.println("下一页"+patientPageInfo.getNextPage());
        System.out.println("是否是第一页"+patientPageInfo.isIsFirstPage());
        System.out.println("是否是最后一页"+patientPageInfo.isIsLastPage());
        for (Patient patient : patientPageInfo.getList()) {
            System.out.println("patient = " + patient);
        }
    }

    @Test
    public void testSavePatient(){
        
    }
}