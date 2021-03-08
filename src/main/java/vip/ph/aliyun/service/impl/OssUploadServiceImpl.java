package vip.ph.aliyun.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.ph.aliyun.service.OssUploadService;
import vip.ph.aliyun.utils.ConstantProperties;

import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OssUploadServiceImpl implements OssUploadService {

    @Override
    public String uploadFile(MultipartFile file) {

        //通过工具类获取配置数据
        String endPoint = ConstantProperties.END_POINT;
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        String bucketName = ConstantProperties.BUCKET_NAME;


        try {

            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            //获取原始文件名称
            // 2021.jpg
            String filename = file.getOriginalFilename();
            log.info("filename : "+filename);

            // 修改新文件名称
            // f6a44cf6fb2021.jpg
            filename = UUID.randomUUID().toString().replaceAll("-","").substring(0,10) + filename;
            log.info("filename : "+filename);


            // 配置路径
            // 2021-03-08
            String path = new DateTime().toString("yyyy-MM-dd");
            log.info("path : "+path);


            // 拼接路径和文件名称
            // 2021-03-08/f6a44cf6fb2021.jpg
            filename = path + "/" + filename;
            log.info("filename : "+filename);

            OSS client = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            client.putObject(bucketName,filename,inputStream);

            client.shutdown();

            //https://ph-movies.oss-cn-beijing.aliyuncs.com/2021-03-08/f8300a3c3e2021.jpg
            String url = "https://" + bucketName +"." + endPoint + "/" + filename;

            return url;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
