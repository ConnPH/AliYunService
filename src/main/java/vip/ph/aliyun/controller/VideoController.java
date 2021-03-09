package vip.ph.aliyun.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vip.ph.aliyun.annotation.Idempotent;
import vip.ph.aliyun.service.InitVideo;
import vip.ph.aliyun.service.VideoUploadService;
import vip.ph.aliyun.utils.ConstantProperties;
import vip.ph.aliyun.utils.R;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    VideoUploadService uploadService;


    @ApiOperation("视频上传")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = uploadService.uploadVideo(file);
        return R.OK().data("videoId",videoId);
    }



    @Idempotent
    @DeleteMapping("deleteByVideoId/{videoId}")
    public R deleteByVideoId(@PathVariable String videoId){
        uploadService.removeByVideoId(videoId);
        return R.OK();
    }


    @ApiOperation("删除多个视频")
    @DeleteMapping("deleteBatchVideo")
    public R deleteBatchVideo(@RequestParam("videoLis") List<String> videoList){
        uploadService.removeBatchVideo(videoList);
        return R.OK();
    }



    @ApiOperation("播放视频")
    @RequestMapping("vo/{id}")
    public ModelAndView playVideo(@PathVariable String id){

        try {

            // 阿里云对象
            DefaultAcsClient client = InitVideo.initObject(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);

            //创建请求和响应对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 添加视频id到请求对象
            request.setVideoId(id);
            response = client.getAcsResponse(request);
            // 获取授权码
            String playAuth = response.getPlayAuth();
            // id 授权码传到网页中播放视频
            Map<String,Object> map = new HashMap<>();
            map.put("videoId",id);
            map.put("playAuth",playAuth);

            return new ModelAndView("show",map);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
