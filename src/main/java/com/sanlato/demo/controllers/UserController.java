package com.sanlato.demo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sanlato.demo.models.user;
import com.sanlato.demo.models.user.CreateUser;
import com.sanlato.demo.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
  
@Autowired    
private UserService userService;

@GetMapping("/{id}")
public ResponseEntity<user> findById (@PathVariable Long id){
     user obj = this.userService.findById(id);
     return ResponseEntity.ok().body(obj);

}

@PostMapping
@Validated(CreateUser.class)

public ResponseEntity<Void> create(@Valid @RequestBody user obj){
    this.userService.create(obj);
    URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}").buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).build();
}

@PutMapping("/{id}")
@Validated(user.UpdateUser.class)

public ResponseEntity<Void> update (@Valid @RequestBody user obj, @PathVariable Long id){
    obj.setId(id);
    this.userService.update(obj);
    return  ResponseEntity.noContent().build();
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id){
    this.userService.delete(id);
    return ResponseEntity.noContent().build();
}
}