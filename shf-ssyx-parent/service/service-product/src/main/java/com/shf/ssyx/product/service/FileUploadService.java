package com.shf.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    String fileUpload(MultipartFile multipartFile);
}
