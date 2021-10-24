package edu.ujn;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("edu.ujn.mapper")
@ImportAutoConfiguration(PageHelperAutoConfiguration.class)

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
