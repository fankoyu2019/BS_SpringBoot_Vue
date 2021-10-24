package edu.ujn.mapper;

import edu.ujn.pojo.PatientDailyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface PatientDailyRecordMapper extends Mapper<PatientDailyRecord> {
    List<PatientDailyRecord> findDailyRecordsByPdrPatId(@Param(value="pdrPatId") Integer pdrPatId);
}
