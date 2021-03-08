package vip.ph.aliyun.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.ph.aliyun.annotation.Idempotent;
import vip.ph.aliyun.service.VideoUploadService;
import vip.ph.aliyun.utils.R;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    VideoUploadService uploadService;


    @Idempotent
    @ApiOperation("视频上传")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = uploadService.uploadVideo(file);
        return R.OK().data("videoId",videoId);
    }



    @Idempotent
    @ApiOperation("删除单个视频")
    @DeleteMapping("deleteByVideoId/{videoId}")
    public R deleteByVideoId(@PathVariable String videoId){
        uploadService.removeByVideoId(videoId);
        return R.OK();
    }


    @Idempotent
    @ApiOperation("删除多个视频")
    @DeleteMapping("deleteBatchVideo")
    public R deleteBatchVideo(@RequestParam("videoLis") List<String> videoList){
        uploadService.removeBatchVideo(videoList);
        return R.OK();
    }
}
