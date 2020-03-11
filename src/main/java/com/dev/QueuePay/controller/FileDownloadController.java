//package com.dev.QueuePay.controller;
//
//import com.dev.QueuePay.services.auth.DatabaseFileService;
//import com.dev.QueuePay.user.models.document.ProfileUpdate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.FileNotFoundException;
//
//
//@RestController
//public class FileDownloadController {
//
//    @Autowired
//    private DatabaseFileService fileStorageService;
//
//    @GetMapping("/downloadFile/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) throws FileNotFoundException {
//        // Load file as Resource
//        ProfileUpdate databaseFile = fileStorageService.getFile(fileId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
//                .body(new ByteArrayResource(databaseFile.getData()));
//    }
//
//}
//