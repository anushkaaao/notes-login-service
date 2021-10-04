package com.anushka.notesloginservice.controllers;
import com.anushka.notesloginservice.models.User;
import com.anushka.notesloginservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class UserController {


    @Autowired
    UserRepo ur;

   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(path = "/signup")
    public String signup(@RequestBody User user)  {
        String e=user.getEmail();
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean b = ur.existsByEmail(e);
        if(b) {
            return "Error!";
        }
        ur.save(user);
        return "You just signed up!";
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody User user)  {
        String e=user.getEmail();
        String pw=user.getPassword();
        boolean b = ur.existsByEmail(e);
        if(b)
        {
            User u=ur.findByEmail(e);
            if(passwordEncoder.matches(pw,u.getPassword())) {
                user.setLoggedIn(true);
                return "success";
            }
        }

      return "Error!!!";
    }

    @PostMapping(path = "/logout")
    public String logout(@RequestBody User user)
    {
        user.setLoggedIn(false);
        return "You logged out!";
    }




    @DeleteMapping("/users/{id}")
    public String delUser(@PathVariable String id)
    {
        ur.deleteById(id);
        return "deleted!";
    }


    @GetMapping(path="/users")
    public List<User> getAllUsers()
    {
        return ur.findAll();
    }

    @GetMapping(path="/users/{id}")
    public Optional<User> fetchUser(@PathVariable String id)
    {
        return ur.findById(id);
    }

    @PutMapping(path="/users/{email}")
    public User addOrUpdateUser(@PathVariable String email, @RequestParam String pw, @RequestParam String name)
    {
        User u = ur.findByEmail(email);
        u.setPassword(pw);
        u.setName(name);
        ur.save(u);
        return u;
    }
}
