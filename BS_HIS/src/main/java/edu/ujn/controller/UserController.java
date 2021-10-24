package edu.ujn.controller;

import edu.ujn.config.JwtConfig;
import edu.ujn.pojo.User;
import edu.ujn.service.UserService;
import edu.ujn.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello SpringBoot!";
    }

    @RequestMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        User user = userService.login(username, password);
        try {
            String uid = "" + user.getUid();
            String jwt = jwtUtils.createJwt(uid, user.getUsername(), map);
            map.put("token", jwt);
            map.put("userId", uid);
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        }
    }
}
