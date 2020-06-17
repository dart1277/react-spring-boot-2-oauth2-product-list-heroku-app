package com.cx.prod.list.controllers.users;

import com.cx.prod.list.model.users.User;
import com.cx.prod.list.services.users.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        userDaoService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
