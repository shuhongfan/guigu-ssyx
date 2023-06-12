package com.shf.ssyx.product.controller;

import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation("文件上传")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile multipartFile) {
        String fileName = fileUploadService.fileUpload(multipartFile);
        return Result.ok(fileName);
    }
}
