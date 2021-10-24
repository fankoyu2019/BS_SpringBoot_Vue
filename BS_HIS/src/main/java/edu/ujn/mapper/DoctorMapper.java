package edu.ujn.mapper;

import edu.ujn.pojo.Doctor;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DoctorMapper extends Mapper<Doctor> {

}
