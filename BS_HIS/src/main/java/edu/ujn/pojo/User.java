package edu.ujn.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//在编译阶段会根据注解自动生成对应的方法；data包含get/set/hashCode/equals/toString等方法
@Data
@Table(name = "users")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private long uid;

    private String username;
    private String password;
    private int status;
    private Date createTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
