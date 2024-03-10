package com.springboot.courses.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class UploadImage {
    @Autowired private Cloudinary cloudinary;

    public String uploadImageOnCloudinary(MultipartFile image) {
        try {
            Map r = cloudinary.uploader().upload(image.getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
            return (String) r.get("secure_url");
        } catch (IOException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Upload image failed!");
        }
    }

    public void deleteImageInCloudinary(User user){
        try {
            String urlTemp = user.getPhoto();
            int lastSlashIndex = urlTemp.lastIndexOf('/');
            int lastDotIndex = urlTemp.lastIndexOf('.');
            String fileName = urlTemp.substring(lastSlashIndex + 1, lastDotIndex);
            cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
        } catch (IOException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Change image failed!");
        }
    }
}
