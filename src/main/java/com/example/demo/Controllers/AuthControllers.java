package com.example.demo.Controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.RegisterDto;
import com.example.demo.Dto.ResponseDto;
import com.example.demo.Repositorys.AuthRepository;
import com.example.demo.Repositorys.MessageRepos;
import com.example.demo.model.AuthLogin;
import com.example.demo.model.Messsage;
import com.example.demo.util.JwtUtils;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins =
        "http://localhost:5173")
public class AuthControllers {

    @Autowired
    private AuthRepository repos;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils utils;
    
    @Autowired
    private MessageRepos messageRepos;

    @PostMapping("/register")

    public ResponseEntity<?> register(

            @RequestBody
            RegisterDto request
    ) {

        if (

            repos.existsByEmailId(
                    request.getEmailId()
            )
        ) {

            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }

        AuthLogin user =
                new AuthLogin();

        user.setUserName(
                request.getUserName()
        );

        user.setEmailId(
                request.getEmailId()
        );

        user.setMobileNo(
                request.getMobileNo()
        );

        user.setAge(
                request.getAge()
        );

        user.setPassword(

                encoder.encode(
                        request.getPassword()
                )
        );

        // ROLE

        user.setRole(

                "ROLE_" +

                request.getRole()
        );

        repos.save(user);

        return ResponseEntity.ok(
                "Registration Success"
        );
    }

 @PostMapping("/login")

 public ResponseEntity<?> login(

         @RequestBody
         LoginDto requests
 ) {


     String email =
             requests.getEmailId();

     String password =
             requests.getPassword();

     AuthLogin users =

             repos.findByEmailId(email)

             .orElse(null);

     if(users == null){

         return ResponseEntity

                 .status(401)

                 .body("User Not Found");
     }

     

     if(

             !encoder.matches(

                     password,

                     users.getPassword()
             )
     ){

         return ResponseEntity

                 .status(401)

                 .body("Invalid Password");
     }

  

     String token =

             utils.generateToken(

                     users.getEmailId(),

                     users.getRole()
             );


     return ResponseEntity.ok(

             Map.of(
                     "token",
                     token,
                     "role",
                     users.getRole(),

                     "id",
                     users.getId(),

                     // IMPORTANT
                     "userName",
                     users.getUserName(),

                     "premium",
                     users.isPremium()
             )
     );
 }

 @GetMapping("/profile")
 public ResponseDto<?> getProfile(
         Authentication authentication
 ) {

     String email =
             authentication.getName();


     AuthLogin user =

             repos.findByEmailId(email)

             .orElse(null);


     if(user == null){

         return new ResponseDto<>(

                 "User Not Found",

                 404,

                 null
         );
     }

     

     Map<String,Object> data =
             new HashMap<>();

     data.put(
             "id",
             user.getId()
     );

     data.put(
             "userName",
             user.getUserName()
     );

     data.put(
             "emailId",
             user.getEmailId()
     );

     data.put(
             "mobileNo",
             user.getMobileNo()
     );

     data.put(
             "age",
             user.getAge()
     );

     data.put(
             "role",
             user.getRole()
     );

     data.put(
             "premium",
             user.isPremium()
     );

     data.put(
             "avatar",
             user.getAvatar()
     );

     data.put(
             "profileImage",
             user.getProfileImage()
     );

    
     return new ResponseDto<>(

             "Profile Found",

             200,

             data
     );
 }


    @PostMapping("/upload-profile")

    public String uploadProfile(

            @RequestParam("file")
            MultipartFile file,

            Authentication authentication

    ) throws Exception {

        String email =
                authentication.getName();

        AuthLogin user =
                repos.findByEmailId(email)
                .orElseThrow();

        String path =
                "C:/uploads/profile/";

        File dir =
                new File(path);

        if(!dir.exists()){
            dir.mkdirs();
        }

        String fileName =

                System.currentTimeMillis()

                + "_"

                + file.getOriginalFilename();

        file.transferTo(

                new File(
                        path + fileName
                )
        );

        user.setProfileImage(
                fileName
        );

        repos.save(user);

        return "Profile Uploaded";
    }

 

 @PutMapping("/premium-user")

 public String premiumUser(

         Authentication authentication
 ) {

     

     String email =
             authentication.getName();

    
     AuthLogin user =

             repos.findByEmailId(email)

             .orElseThrow(() ->

                     new RuntimeException(
                             "User Not Found"
                     )
             );

     user.setPremium(true);

     // =========================
     // SAVE
     // =========================

     repos.save(user);

     return "Premium Activated";
 }

    @PutMapping("/avatar")

    public String saveAvatar(

            @RequestParam
            String avatar,

            Authentication authentication
    ){

        String email =
                authentication.getName();

        AuthLogin user =
                repos.findByEmailId(email)
                .orElseThrow();

        user.setAvatar(
                avatar
        );

        repos.save(user);

        return "Avatar Saved";
    }
    
 @GetMapping("/doctor-users/{doctorId}")

 public List<Map<String,Object>> doctorUsers(

         @PathVariable
         Long doctorId
 ) {

     List<Messsage> messages =
             messageRepos.findAll();

     

     Map<Long, Map<String,Object>>
             users =
             new HashMap<>();

     for(Messsage msg : messages){


         if(msg == null){
             continue;
         }


         if(

                 msg.getReceiverId() != null

                 &&

                 msg.getSenderId() != null

                 &&

                 msg.getReceiverId()
                 .equals(doctorId)

         ){

             AuthLogin user =

                     repos.findById(
                             msg.getSenderId()
                     ).orElse(null);

             if(user != null){

                 Map<String,Object>
                         data =
                         new HashMap<>();

                 data.put(
                         "userId",
                         user.getId()
                 );

                 data.put(
                         "userName",
                         user.getUserName()
                 );

                 data.put(
                         "emailId",
                         user.getEmailId()
                 );

                 users.put(
                         user.getId(),
                         data
                 );
             }
         }

         if(

                 msg.getSenderId() != null

                 &&

                 msg.getReceiverId() != null

                 &&

                 msg.getSenderId()
                 .equals(doctorId)

         ){

             AuthLogin user =

                     repos.findById(
                             msg.getReceiverId()
                     ).orElse(null);

             if(user != null){

                 Map<String,Object>
                         data =
                         new HashMap<>();

                 data.put(
                         "userId",
                         user.getId()
                 );

                 data.put(
                         "userName",
                         user.getUserName()
                 );

                 data.put(
                         "emailId",
                         user.getEmailId()
                 );

                 users.put(
                         user.getId(),
                         data
                 );
             }
         }
     }

     return new ArrayList<>(
             users.values()
     );
 }
}