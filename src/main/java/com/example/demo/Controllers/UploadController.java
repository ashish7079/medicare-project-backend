package com.example.demo.Controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repositorys.UploadRepo;

import com.example.demo.model.Upload;

@RestController
@CrossOrigin(origins =
        "http://localhost:5173")

public class UploadController {

    @Autowired
    private UploadRepo repo;

    private final String PROFILE_PATH =
            "C:/uploads/profile/";

    private final String CERT_PATH =
            "C:/uploads/certificates/";

    @PostMapping("/upload")

    public String uploads(

            @RequestParam("file1")
            MultipartFile file1,

            @RequestParam("file2")
            MultipartFile file2,

            @RequestParam
            String userName,

            @RequestParam
            String degree,

            @RequestParam
            String specialist,

            // IMPORTANT
            @RequestParam
            Long authId
    ) {

        try {

            File dir1 =
                    new File(PROFILE_PATH);

            File dir2 =
                    new File(CERT_PATH);

            if (!dir1.exists())
                dir1.mkdirs();

            if (!dir2.exists())
                dir2.mkdirs();

            // IMAGE NAME

            String file1Name =

                    System.currentTimeMillis()

                    + "_"

                    + file1
                    .getOriginalFilename();

            // CERTIFICATE NAME

            String file2Name =

                    System.currentTimeMillis()

                    + "_"

                    + file2
                    .getOriginalFilename();

            // FULL PATH

            String file1Path =
                    PROFILE_PATH + file1Name;

            String file2Path =
                    CERT_PATH + file2Name;

            // SAVE FILE

            file1.transferTo(
                    new File(file1Path)
            );

            file2.transferTo(
                    new File(file2Path)
            );

            // SAVE DATABASE

            Upload doc =
                    new Upload();

            doc.setUserName(
                    userName
            );

            doc.setDegree(
                    degree
            );

            doc.setSpecialist(
                    specialist
            );

            doc.setFile1Path(
                    file1Name
            );

            doc.setFile2Path(
                    file2Name
            );

            // IMPORTANT

            doc.setAuthId(
                    authId
            );

            repo.save(doc);

            return
                    "Doctor Uploaded Successfully";

        } catch (IOException e) {

            e.printStackTrace();

            return
                    "Upload Failed";
        }
    }

    // =========================
    // SHOW DOCTORS
    // =========================

    @GetMapping("/show")

    public List<Upload> shows() {

        return repo.findAll();
    }

    // =========================
    // SEARCH DOCTOR
    // =========================

    @GetMapping("/search/{specialist}")

    public List<Upload> search(

            @PathVariable
            String specialist
    ) {

        return repo
                .findBySpecialistContainingIgnoreCase(
                        specialist
                );
    }

    // =========================
    // DELETE DOCTOR
    // =========================

    @DeleteMapping("/delete/{id}")

    public String deletes(

            @PathVariable
            Long id,

            // IMPORTANT
            @RequestParam
            Long authId
    ) {

        Upload ds =
                repo.findById(id)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Data not found"
                        )
                );

        // IMPORTANT SECURITY

        if(!ds.getAuthId().equals(authId)){

            return
                    "You Cannot Delete Others Profile";
        }

        repo.delete(ds);

        return
                "Deleted Successfully";
    }

    @PutMapping("/update-doctor/{id}")

    public String updateDoctor(

            @PathVariable
            Long id,

            @RequestParam(required = false)
            MultipartFile file1,

            @RequestParam(required = false)
            MultipartFile file2,

            @RequestParam
            String userName,

            @RequestParam
            String degree,

            @RequestParam
            String specialist,

            // IMPORTANT
            @RequestParam
            Long authId

    ) {

        try {

            Upload doctor =
                    repo.findById(id)

                    .orElseThrow(() ->

                            new RuntimeException(
                                    "Doctor Not Found"
                            )
                    );

            // IMPORTANT SECURITY

            if(!doctor.getAuthId().equals(authId)){

                return
                        "You Cannot Update Others Profile";
            }

            // =========================
            // UPDATE TEXT
            // =========================

            doctor.setUserName(
                    userName
            );

            doctor.setDegree(
                    degree
            );

            doctor.setSpecialist(
                    specialist
            );

            // =========================
            // UPDATE PROFILE IMAGE
            // =========================

            if(file1 != null){

                String file1Name =

                        System.currentTimeMillis()

                        + "_"

                        + file1
                        .getOriginalFilename();

                String file1Path =
                        PROFILE_PATH + file1Name;

                file1.transferTo(
                        new File(file1Path)
                );

                doctor.setFile1Path(
                        file1Name
                );
            }

            // =========================
            // UPDATE CERTIFICATE
            // =========================

            if(file2 != null){

                String file2Name =

                        System.currentTimeMillis()

                        + "_"

                        + file2
                        .getOriginalFilename();

                String file2Path =
                        CERT_PATH + file2Name;

                file2.transferTo(
                        new File(file2Path)
                );

                doctor.setFile2Path(
                        file2Name
                );
            }

            repo.save(doctor);

            return
                    "Doctor Updated Successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return
                    "Update Failed";
        }
    }
}