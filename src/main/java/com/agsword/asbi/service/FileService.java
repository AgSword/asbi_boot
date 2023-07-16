package com.agsword.asbi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author as
 */
public interface FileService {
    /**
     * 上传头像到OSS
     *
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
