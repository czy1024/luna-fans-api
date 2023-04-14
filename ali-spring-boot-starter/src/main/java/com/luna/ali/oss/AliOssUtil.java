package com.luna.ali.oss;

import java.net.URL;
import java.util.Date;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.luna.ali.config.AliOssConfigProperties;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Luna@win10
 * @date 2020/4/26 15:22
 */
public class AliOssUtil {

    /**
     * 设置存储权限
     *
     * @param access
     * @param type
     * @return
     */
    public static ObjectMetadata getObjectMetadata(String access, String type) {
        ObjectMetadata metadata = new ObjectMetadata();
        if (StringUtils.isNotEmpty(access)) {
            metadata.setObjectAcl(CannedAccessControlList.parse(access));
        }

        if (StringUtils.isNotEmpty(type)) {
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.parse(type));
        }
        return metadata;
    }

    public static Callback getCallback(String serverUrl) {
        Callback callback = new Callback();
        // 设置回调服务器地址，如http://oss-demo.aliyuncs.com:23450或http://127.0.0.1:9090。
        callback.setCallbackUrl(serverUrl);
        // 设置发起回调时请求body的值。
        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},{\\\"object\\\":${object},\\\"size\\\":${size}}");
        // 设置发起回调请求的Content-Type。
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        // 设置发起回调请求的自定义参数，由Key和Value组成，Key必须以x:开始，且必须小写。
        callback.addCallbackVar("x:var1", "value1");
        callback.addCallbackVar("x:var2", "value2");
        return callback;
    }


    /**
     * 创建自定义域名oss服务
     * 使用自定义域名时无法使用ossClient.listBuckets方法。
     * 
     * @param configVale
     * @return
     */
    public static OSS createOssWithCname(AliOssConfigProperties configVale) {
        return configVale.getInstanceClient(true);
    }

    /**
     * 视频截帧
     * 
     * @param name
     */
    public static String movie2Image(String name, String bucketName, OSS ossClient) {
        String objectName = name;
        // 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_1024,h_768";
        // 指定过期时间为10分钟。
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req =
            new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
        ossClient.shutdown();
        ossClient.shutdown();

        return signedUrl.toString();
    }

}
