package vip.ph.aliyun.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.ph.aliyun.exception.MyException;
import vip.ph.aliyun.service.InitVideo;
import vip.ph.aliyun.service.VideoUploadService;
import vip.ph.aliyun.utils.ConstantProperties;

import java.io.InputStream;
import java.util.List;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {


    @Override
    public String uploadVideo(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        String title = fileName.substring(0,fileName.lastIndexOf("."));


        String videoId = null;

        try {
            InputStream inputStream = file.getInputStream();

            // String accessKeyId,String accessKeySecret,String title,String fileName,InputStream inputStream
            UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET, title, fileName, inputStream);

            // 阿里云响应对象
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse uploadStreamResponse = uploadVideo.uploadStream(uploadStreamRequest);


            if (uploadStreamResponse.isSuccess()){
                videoId = uploadStreamResponse.getVideoId();
            }else {
                videoId = uploadStreamResponse.getVideoId();
            }


        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(500,"上传失败");
        }

        return videoId;
    }

    @Override
    public void removeBatchVideo(List videoList) {

        DefaultAcsClient client = InitVideo.initObject(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);

        DeleteVideoRequest request = new DeleteVideoRequest();

        String videoId = StringUtils.join(videoList.toArray(), ",");

        request.setVideoIds(videoId);


        try {
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(500,"视频删除失败");
        }

    }

    @Override
    public void removeByVideoId(String id) {
        DefaultAcsClient client = InitVideo.initObject(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);

        DeleteVideoRequest request = new DeleteVideoRequest();

        request.setVideoIds(id);


        try {
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(500,"视频删除失败");
        }
    }
}
