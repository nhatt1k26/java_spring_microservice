package com.nhat220801.udemydemo.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.nhat220801.udemydemo.entity.UserEntity;
import com.nhat220801.udemydemo.exception.UserNotFoundException;
import com.nhat220801.udemydemo.user.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserEntity userEntity;
    public UserController(UserEntity userDaoService){
        this.userEntity = userDaoService;
    }

    @GetMapping("users/all")
    public List<User> getAllUser(){
        return userEntity.findAll();
    }

    @GetMapping("users/{id}")
    public EntityModel<User> getUserById(@PathVariable Integer id){
        User user = userEntity.findOne(id);
        if (user==null)
            throw new UserNotFoundException("user not found");
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUser());
        entityModel.add(link.withRel("all-user"));
        return entityModel;
    }

    @GetMapping("users/{id}/filter")
    public MappingJacksonValue getUserByIdAndFilter(@PathVariable Integer id){
        User user = userEntity.findOne(id);
        if (user==null)
            throw new UserNotFoundException("user not found");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        FilterProvider filter = new SimpleFilterProvider().addFilter("FilterName", SimpleBeanPropertyFilter.filterOutAllExcept("birthDate"));
        mappingJacksonValue.setFilters(filter);
        return mappingJacksonValue;
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userEntity.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userEntity.save(user);
        URI location1 = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location1).build();
    }
}
