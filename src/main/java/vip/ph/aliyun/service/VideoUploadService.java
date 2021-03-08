package vip.ph.aliyun.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoUploadService {

    // 上传视频
    String uploadVideo(MultipartFile file);

    // 删除视频
    void removeBatchVideo(List videoList);

    // 根据视频ID删除视频

    void removeByVideoId(String id);
}
