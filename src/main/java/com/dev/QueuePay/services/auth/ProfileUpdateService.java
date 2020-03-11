//package com.dev.QueuePay.services.auth;
//
//
//import com.dev.QueuePay.exceptions.FileStorageException;
//import com.dev.QueuePay.user.models.document.ProfileUpdate;
//import com.dev.QueuePay.user.repositories.ProfileUpdateRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//
//@Service
//public class ProfileUpdateService {
//
//    @Autowired
//    private ProfileUpdateRepository profileUpdateRepository;
//
//    public ProfileUpdate storeFile(MultipartFile file) {
//        // Normalize file name
//        String logoName = StringUtils.cleanPath(file.getOriginalFilename());
//
//
//        try {
//            // Check if the file's name contains invalid characters
//            if(logoName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + logoName);
//            }
//
//
//            ProfileUpdate dbFile = new ProfileUpdate(logoName, file.getContentType(), file.getBytes());
//
//            return profileUpdateRepository.save(dbFile);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file . Please try again!", ex);
//        }
//    }
//
//    public ProfileUpdate getFile(String fileId) throws FileNotFoundException {
//        return profileUpdateRepository.findById(fileId)
//                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
//    }
//}
//
