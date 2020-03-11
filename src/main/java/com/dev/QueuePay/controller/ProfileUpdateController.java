//package com.dev.QueuePay.controller;
//
//
//import com.dev.QueuePay.payload.Response;
//import com.dev.QueuePay.services.auth.ProfileUpdateService;
//import com.dev.QueuePay.user.models.document.ProfileUpdate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//@RestController
//@RequestMapping("auth")
//public class ProfileUpdateController {
//
//    @Autowired
//    private ProfileUpdateService fileStorageService;
//    private ProfileUpdate document;
//
//    @PostMapping("/uploadFile")
//    public Response uploadFile(@RequestParam("file") MultipartFile file) {
//        ProfileUpdate profileUpdate = fileStorageService.storeFile(file);
//
//        String logoDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadLogo/")
//                .path(profileUpdate.getFileName())
//                .toUriString();
////
////        String cacDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
////                .path("/downloadCAC/")
////                .path(profileUpdate.getCACName())
////                .toUriString();
//
//
//        return new Response(profileUpdate.getFileName(), logoDownloadUri,
//                file.getContentType(), file.getSize());
//    }
//
////    @PostMapping("/uploadMultipleFiles")
////    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
////        return Arrays.asList(files)
////                .stream()
////                .map(file -> uploadFile(file))
////                .collect(Collectors.toList());
////    }
//}
//
