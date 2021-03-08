package vip.ph.aliyun.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssUploadService {
    String uploadFile(MultipartFile file);
}
