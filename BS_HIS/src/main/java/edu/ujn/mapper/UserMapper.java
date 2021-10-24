package edu.ujn.mapper;

import edu.ujn.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User>{
    public User findByUsernameAndPassword(User user);
}
