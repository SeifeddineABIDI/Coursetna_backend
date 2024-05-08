package com.example.picloud.controllers;

import com.example.picloud.entities.Cv;
import com.example.picloud.repository.ICvRepo;
import com.example.picloud.services.CVServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cv")
@CrossOrigin
public class CvController {

//cvcontroller
    @Autowired
    private ICvRepo cvRepo;
    @Autowired
    private CVServiceImp cvServiceImp;


    @PostMapping("/cv")
    public Cv addCv(@RequestBody Cv cv, @RequestParam("file") MultipartFile file) {
        try {
            return cvServiceImp.addCv(cv, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Cv cv = new Cv();
            cv.setCheminFichier(file.getOriginalFilename());
            cvRepo.save(cv);
            return "Fichier téléchargé avec succès: " + file.getOriginalFilename();
        } catch (Exception e) {
            return "Échec du téléchargement du fichier: " + file.getOriginalFilename();
        }
    }
}
