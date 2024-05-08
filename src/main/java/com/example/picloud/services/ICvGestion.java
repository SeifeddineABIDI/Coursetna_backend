package com.example.picloud.services;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.picloud.entities.Cv;

import java.io.IOException;

public interface ICvGestion {
    @Transactional
    Cv addCv(Cv cv, MultipartFile photo) throws IOException;
    //ajoutcv

}
