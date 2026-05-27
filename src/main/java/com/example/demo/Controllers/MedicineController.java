package com.example.demo.Controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repositorys.MedicineRepo;
import com.example.demo.model.Medicine;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

public class MedicineController {

    @Autowired
    private MedicineRepo repo;

    private final String MedicineImgs =
            "C:/upload/medicine/";


    @PostMapping("/uploads")

    public String uploads(

            @RequestParam("file1")
            MultipartFile file1,

            @RequestParam
            String mediciName,

            @RequestParam
            String causes,

            @RequestParam
            String rate,

            // IMPORTANT
            @RequestParam
            Long authId
    ) {

        try {

            File dirs =
                    new File(MedicineImgs);

            if (!dirs.exists())
                dirs.mkdirs();

            String file1Name =

                    System.currentTimeMillis()
                    + "_"
                    + file1.getOriginalFilename();

            String fullPath =
                    MedicineImgs + file1Name;

            file1.transferTo(
                    new File(fullPath)
            );

            Medicine md =
                    new Medicine();

            md.setMedicineimg(
                    file1Name
            );

            md.setMedicineName(
                    mediciName
            );

            md.setCauses(
                    causes
            );

            md.setRate(
                    rate
            );

            // IMPORTANT
            md.setAuthId(
                    authId
            );

            repo.save(md);

            return
                    "Medicine Uploaded Successfully ✅";

        } catch (IOException e) {

            e.printStackTrace();

            return
                    "Upload Failed ❌";
        }
    }

    @GetMapping("/shows")

    public List<Medicine> shows() {

        return repo.findAll();
    }

    @DeleteMapping("/deleted/{id}")

    public String deletes(

            @PathVariable
            Long id,

            @RequestParam
            Long authId
    ) {

        Medicine ds =
                repo.findById(id)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Data not found"
                        )
                );

        if(!ds.getAuthId().equals(authId)){

            return
                    "You Cannot Delete Others Medicine";
        }

        repo.delete(ds);

        return
                "Deleted Successfully";
    }
    @GetMapping("/Singlemedicine/{id}")

    public Medicine singleMedicine(

            @PathVariable
            Long id
    ) {

        return repo.findById(id)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Medicine Not Found"
                        )
                );
    }


    @PutMapping("/update-medicine/{id}")

    public String updateMedicine(

            @PathVariable
            Long id,

            @RequestParam(required = false)
            MultipartFile file1,

            @RequestParam
            String mediciName,

            @RequestParam
            String causes,

            @RequestParam
            String rate,

            // IMPORTANT
            @RequestParam
            Long authId

    ){

        try{

            Medicine medicine =
                    repo.findById(id)

                    .orElseThrow(() ->

                            new RuntimeException(
                                    "Medicine Not Found"
                            )
                    );

            // SECURITY

            if(!medicine.getAuthId().equals(authId)){

                return
                        "You Cannot Update Others Medicine";
            }

            // UPDATE TEXT

            medicine.setMedicineName(
                    mediciName
            );

            medicine.setCauses(
                    causes
            );

            medicine.setRate(
                    rate
            );

            // UPDATE IMAGE

            if(file1 != null){

                String file1Name =

                        System.currentTimeMillis()

                        + "_"

                        + file1
                        .getOriginalFilename();

                String fullPath =
                        MedicineImgs + file1Name;

                file1.transferTo(
                        new File(fullPath)
                );

                medicine.setMedicineimg(
                        file1Name
                );
            }

            repo.save(medicine);

            return
                    "Medicine Updated";

        } catch (Exception e){

            e.printStackTrace();

            return
                    "Update Failed";
        }
    }

 @GetMapping("/my-medicines/{authId}")

 public List<Medicine> myMedicines(

         @PathVariable
         Long authId
 ) {

     return repo.findByAuthId(authId);
 }
}