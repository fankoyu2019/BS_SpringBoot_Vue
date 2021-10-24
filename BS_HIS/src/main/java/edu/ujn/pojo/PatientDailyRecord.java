package edu.ujn.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "patient_daily_record")
public class PatientDailyRecord {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer pdrId;

    private Integer pdrPatId;
    private Double pdrTemperature;
    private Integer pdrPulse;
    private Integer pdrBreathing;
    private Integer pdrSystolicPressure;
    private Integer pdrDiastolicPressure;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pdrRecordingTime;
}
