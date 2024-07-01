package com.example.timetracker.controller;

import com.example.timetracker.entity.Location;
import com.example.timetracker.entity.User;
import com.example.timetracker.service.LocationService;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAllUser")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<User>> getAllUser() throws Exception
    {
        List<User> users = userService.getAllUser();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PostMapping("/loginuser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User loginUser(@RequestBody User user) throws Exception
    {
        String currEmail = user.getEmail();

        String currPassword = user.getPassword();



        User userObj = null;
        if(currEmail != null && currPassword != null)
        {
            userObj = userService.fetchUserByEmailAndPassword(currEmail, currPassword);
            userObj.setTimestamp(user.getTimestamp());
            userService.saveUser(userObj);

        }
        if(userObj == null)
        {
            throw new Exception("User does not exists!!! Please enter valid credentials...");
        }
        return userObj;
    }


    @PostMapping("/saveLocation")
    public String saveLocation(@RequestBody Location location) {
        locationService.saveLocation(location);
        return "Location saved successfully!";
    }

    @GetMapping("/getlocation/{useremail}")
    public Location getlocation(@PathVariable String useremail){
        System.out.println(locationService.getLocation(useremail));
        return locationService.getLocation(useremail);
    }

}
