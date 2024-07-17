package com.example.timetracker.controller;

import com.example.timetracker.entity.Location;
import com.example.timetracker.entity.User;
import com.example.timetracker.service.LocationService;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    private static final String key = "AESEncryptionKey"; // 128 bit key
    private static final String transformation = "AES";

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), transformation);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), transformation);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	@GetMapping("/")
	public String welcomeMessage()
	{
		return "Welcome to Time tracker application !!!";
	}
        @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception{

        if (!isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("enter valid userId", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() != null || !"".equals(user.getEmail())) {
             User userObj = userService.findByEmail(user.getEmail());
            String password=encrypt(user.getPassword());
            user.setPassword(password);
            userService.saveUser(user);

            if (userObj!=null) {
                throw new Exception("User with " + user.getEmail() + " already exists !!!");
            }
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email);
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

        String currPassword = encrypt(user.getPassword());
        System.out.println(currPassword);


        User userObj = null;
        if(currEmail != null && currPassword != null)
        {
            userObj = userService.fetchUserByEmailAndPassword(currEmail, currPassword);
            userObj.setTimestamp(user.getTimestamp());
            userObj.setLocation(user.getLocation());
//            userObj.setPassword(decrypt(userObj.getPassword()));
            userService.saveUser(userObj);

        }
        if(userObj == null)
        {
            throw new Exception("User does not exists!!! Please enter valid credentials...");
        }
        return userObj;
    }


    @CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/logoutuser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User logoutUser(@RequestBody User user) throws Exception
    {
         User usr=userService.findByEmail(user.getEmail());
        if (usr !=null)
        {
            usr.setLogouttime(user.getLogouttime());
        }
        userService.saveUser(usr);
        return usr;
    }

}
