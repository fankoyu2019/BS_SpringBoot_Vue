package edu.ujn.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "doctor")
public class Doctor {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long dcId;

    private String dcName;
    private String dcSex;
    private Long dcAge;
    //职位
    private String dcPosition;
    //科室
    private String dcDepartment;
    private String dcPhone;


}
