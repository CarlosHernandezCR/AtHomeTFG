package org.tfg.athometfgcarloshernandez.spring.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesServer;

@RestController
@RequestMapping(ConstantesServer.DESCARGAR)
public class WebController {

    @GetMapping(ConstantesServer.APK)
    public ResponseEntity<Resource> getApk() {
        try {
            Resource apkFile = new ClassPathResource(ConstantesServer.UBI_APK);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, ConstantesServer.HEADER_APK);
            return new ResponseEntity<>(apkFile, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}