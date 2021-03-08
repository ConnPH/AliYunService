package vip.ph.aliyun.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.ph.aliyun.annotation.Idempotent;
import vip.ph.aliyun.service.OssUploadService;
import vip.ph.aliyun.utils.R;

import javax.annotation.Resource;

@RestController
@RequestMapping("oss")
public class OssController {


    @Resource
    OssUploadService ossUploadService;

    @ApiOperation("Oss文件上传")
    @PostMapping("/upload")
    public R upload(MultipartFile file){
        String url = ossUploadService.uploadFile(file);
        return R.OK().data("url",url);
    }
}
