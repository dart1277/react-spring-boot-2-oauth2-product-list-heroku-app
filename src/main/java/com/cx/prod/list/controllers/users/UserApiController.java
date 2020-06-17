package com.cx.prod.list.controllers.users;

import com.cx.prod.list.model.users.User;
import com.cx.prod.list.services.users.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserApiController {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userDaoService.getAllUsers());
    }
}
