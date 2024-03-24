package com.nhat220801.udemydemo.entity;

import com.nhat220801.udemydemo.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserEntity {
    private static List<User> userList = new ArrayList<>();
    private static Integer usersCount = 0;
    static{
        userList.add(new User(++usersCount,"Adam", LocalDate.now().minusYears(30)));
        userList.add(new User(++usersCount,"Beta", LocalDate.now().minusYears(32)));
        userList.add(new User(++usersCount,"Gama", LocalDate.now().minusYears(33)));
    }

    public List<User> findAll(){
        return userList;
    }

    public User save(User user){
        user.setId(++usersCount);
        userList.add(user);
        return user;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return userList.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        userList.removeIf(predicate);
    }
}
