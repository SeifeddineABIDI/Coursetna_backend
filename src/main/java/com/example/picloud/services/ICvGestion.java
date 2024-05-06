package com.example.picloud.services;

import com.example.picloud.entities.Cv;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICvGestion {
    @Transactional
    Cv addCv(Cv cv, MultipartFile photo) throws IOException;
}
