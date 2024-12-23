package com.self.web.controller;

import com.self.model.vo.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @RequestMapping("/getDepartment")
    public UserInfo getDepartment(){


        return new UserInfo();
    }
}
