package com.luna.baidu.api;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.luna.baidu.config.BaiduApiConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.luna.baidu.dto.face.UserFaceListResultDTO;
import com.luna.baidu.dto.face.UserFaceResultDTO;
import com.luna.baidu.dto.face.UserInfoListDTO;
import com.luna.common.net.HttpUtils;
import com.luna.common.net.HttpUtilsConstant;

/**
 * @author luna@mac
 * 2021年05月07日 19:21
 */
public class BaiduUserFaceGroupApi {

    private static final Logger log = LoggerFactory.getLogger(BaiduUserFaceGroupApi.class);

    /**
     * 人脸注册Api 返回face_token
     * 
     * @param key
     * @param image 图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断。
     * 注：组内每个uid下的人脸图片数目上限为20张
     * @param imageType 图片类型
     * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     * FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     * @param groupId
     * 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param userId 用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo 用户资料，长度限制256B 默认空 (非必需)
     * @param qualityControl 图片质量控制 (非必需)
     * NONE: 不进行控制
     * LOW:较低的质量要求
     * NORMAL: 一般的质量要求
     * HIGH: 较高的质量要求
     * 默认 NONE
     * 若图片质量不满足要求，则返回结果中会提示质量检测失败
     * @param livenessControl 活体检测控制 (非必需)
     * NONE: 不进行控制
     * LOW:较低的活体要求(高通过率 低攻击拒绝率)
     * NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
     * HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
     * 默认NONE
     * 若活体检测结果不满足要求，则返回结果中会提示活体检测失败
     * @param actionType 操作方式 (非必需)
     * APPEND: 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下
     * REPLACE : 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片
     * 默认使用APPEND
     * @param faceSortType 人脸检测排序类型 (非必需)
     * 0:代表检测出的人脸按照人脸面积从大到小排列
     * 1:代表检测出的人脸按照距离图片中心从近到远排列
     * 默认为0
     * @return
     */
    public static UserFaceResultDTO faceUserAdd(String key, String image, String imageType, String groupId,
        String userId,
        String userInfo, String qualityControl, String livenessControl, String actionType, String faceSortType) {
        log.info("faceUserAdd start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("image", image);
        map.put("image_type", imageType);
        map.put("group_id", groupId);
        map.put("user_id", userId);
        if (StringUtils.isNotEmpty(userInfo)) {
            map.put("user_info", userInfo);
        }
        if (StringUtils.isNotEmpty(qualityControl)) {
            map.put("quality_control", qualityControl);
        }
        if (StringUtils.isNotEmpty(livenessControl)) {
            map.put("liveness_control", livenessControl);
        }
        if (StringUtils.isNotEmpty(actionType)) {
            map.put("action_type", actionType);
        }
        if (StringUtils.isNotEmpty(faceSortType)) {
            map.put("face_sort_type", faceSortType);
        }

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_ADD,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        System.out.println(s);
        UserFaceResultDTO userFaceResultDTO =
            JSON.parseObject(JSON.parseObject(s).getString("result"), UserFaceResultDTO.class);
        log.info("faceUserAdd success userFaceResultDTO={}", userFaceResultDTO);
        return userFaceResultDTO;
    }

    public static UserFaceResultDTO faceUserAdd(String key, String image, String imageType, String groupId,
        String userId) {
        return faceUserAdd(key, image, imageType, groupId, userId, null, null, null, null, null);
    }

    public static UserFaceResultDTO faceUserAdd(String key, String image, String imageType, String groupId,
        String userId, String userInfo) {
        return faceUserAdd(key, image, imageType, groupId, userId, userInfo, null, null, null, null);
    }

    /**
     * 更新人脸接口
     * 
     * @param key
     * @param image
     * @param imageType
     * @param groupId
     * @param userId
     * @param userInfo
     * @param qualityControl
     * @param livenessControl
     * @param actionType
     * @return
     */
    public static UserFaceResultDTO faceUserUpdate(String key, String image, String imageType, String groupId,
        String userId, String userInfo, String qualityControl, String livenessControl, String actionType) {

        log.info("faceUserUpdate start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("image", image);
        map.put("image_type", imageType);
        map.put("group_id", groupId);
        map.put("user_id", userId);
        if (StringUtils.isNotEmpty(userInfo)) {
            map.put("user_info", userInfo);
        }
        if (StringUtils.isNotEmpty(qualityControl)) {
            map.put("quality_control", qualityControl);
        }
        if (StringUtils.isNotEmpty(livenessControl)) {
            map.put("liveness_control", livenessControl);
        }
        if (StringUtils.isNotEmpty(actionType)) {
            map.put("action_type", actionType);
        }

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_UPDATE,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        UserFaceResultDTO userFaceResultDTO =
            JSON.parseObject(JSON.parseObject(s).getString("result"), UserFaceResultDTO.class);
        log.info("faceUserUpdate success userFaceResultDTO={}", JSON.toJSONString(userFaceResultDTO));
        return userFaceResultDTO;

    }

    public static UserFaceResultDTO faceUserUpdate(String key, String image, String imageType, String groupId,
        String userId) {
        return faceUserUpdate(key, image, imageType, groupId, userId, null, null, null, null);
    }

    /**
     * 删除人脸
     * 
     * @param key
     * @param groupId
     * @param userId
     * @param faceToken
     * @return
     */
    public static Boolean faceUserDelete(String key, String groupId, String userId, String faceToken) {
        log.info("faceUserDelete start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("face_token", faceToken);
        map.put("group_id", groupId);
        map.put("user_id", userId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_FACE_DELETE,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        Integer errorCode = JSON.parseObject(s).getInteger("error_code");
        log.info("faceUserDelete success error_code={}", errorCode);
        return errorCode == 0;
    }

    /**
     * 用户信息查询
     *
     * @param key
     * @param groupId 用户组id(由数字、字母、下划线组成，长度限制48B)，如传入“@ALL”则从所有组中查询用户信息。注：处于不同组，但uid相同的用户，我们认为是同一个用户。
     * @param userId 用户id（由数字、字母、下划线组成），长度限制48B
     */
    public static UserInfoListDTO getUserInfo(String key, String userId, String groupId) {
        log.info("getUserInfo start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("user_id", userId);
        if (StringUtils.isNotEmpty(groupId)) {
            map.put("group_id", groupId);
        } else {
            map.put("group_id", "@ALL");
        }

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_INFO,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        UserInfoListDTO userInfoListDTO =
            JSON.parseObject(JSON.parseObject(s).getString("result"), UserInfoListDTO.class);
        log.info("faceUserDelete success userInfoListDTO={}", JSON.toJSONString(userInfoListDTO));
        return userInfoListDTO;
    }

    /**
     * 获取用户人脸列表
     * 
     * @param key
     * @param userId
     * @param groupId
     */
    public static UserFaceListResultDTO userFaceList(String key, String userId, String groupId) {
        log.info("userFaceList start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("user_id", userId);
        map.put("group_id", groupId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_FACE_LIST,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        UserFaceListResultDTO userFaceListResultDTO =
            JSON.parseObject(JSON.parseObject(s).getString("result"), UserFaceListResultDTO.class);
        log.info("faceUserDelete success userInfoListDTO={}", JSON.toJSONString(userFaceListResultDTO));
        return userFaceListResultDTO;
    }

    /**
     * 删除用户
     *
     * @param key
     * @param userId
     * @param groupId
     */
    public static Boolean deleteUser(String key, String userId, String groupId) {
        log.info("deleteUser start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("user_id", userId);
        map.put("group_id", groupId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_DELETE,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        System.out.println(s);
        Integer errorCode = JSON.parseObject(s).getInteger("error_code");
        log.info("userCopy success errorCode={}", errorCode);
        return errorCode == 0;
    }

    /**
     * 复制用户
     * 
     * @param key
     * @param userId 用户id，长度限制48B
     * @param srcGroupId 从指定组里复制信息
     * @param dstGroupId 需要添加用户的组id
     * @return
     */
    public static Boolean userCopy(String key, String userId, String srcGroupId, String dstGroupId) {
        log.info("userCopy start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("user_id", userId);
        map.put("src_group_id", srcGroupId);
        map.put("dst_group_id", dstGroupId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_COPY,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        System.out.println(s);
        Integer errorCode = JSON.parseObject(s).getInteger("error_code");
        log.info("userCopy success errorCode={}", errorCode);
        return errorCode == 0;
    }

    /**
     * 查询用户组
     * 
     * @param key
     * @param start 默认值0，起始序号
     * @param length 返回数量，默认值100，最大值1000
     * @return
     */
    public static List<String> getUserGroup(String key, Integer start, Integer length) {
        log.info("getUserGroup start");

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("start", start);
        map.put("length", length);
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_GROUP_LIST,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        List<String> groupIdList = JSON.parseArray(
            JSON.parseObject(JSON.parseObject(s).getString("result")).getString("group_id_list"), String.class);
        log.info("getUserGroup success groupIdList={}", groupIdList);
        return groupIdList;
    }

    /**
     * 添加用户组
     *
     * @param key
     * @param groupId
     * @return
     */
    public static Boolean createUserGroup(String key, String groupId) {
        log.info("createUserGroup start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("group_id", groupId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_CREATE_GROUP,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        Integer errorCode = JSON.parseObject(s).getInteger("error_code");
        log.info("createUserGroup success errorCode={}", errorCode);
        return errorCode == 0;
    }

    /**
     * 删除用户组
     *
     * @param key
     * @param groupId
     * @return
     */
    public static Boolean deleteUserGroup(String key, String groupId) {
        log.info("deleteUserGroup start");

        HashMap<String, String> map = Maps.newHashMap();
        map.put("group_id", groupId);

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.FACE_USER_GROUP_DELETE,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        Integer errorCode = JSON.parseObject(s).getInteger("error_code");
        log.info("deleteUserGroup success errorCode={}", errorCode);
        return errorCode == 0;
    }

    /**
     *
     * @param key
     * @param image 图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
     * @param imageType 图片类型
     * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     * FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     * @param groupIdList 从指定的group中进行查找 用逗号分隔，上限10个
     * @param qualityControl 图片质量控制 (非必需)
     * NONE: 不进行控制
     * LOW:较低的质量要求
     * NORMAL: 一般的质量要求
     * HIGH: 较高的质量要求
     * 默认 NONE
     * 若图片质量不满足要求，则返回结果中会提示质量检测失败
     * @param livenessControl 活体检测控制 (非必需)
     * NONE: 不进行控制
     * LOW:较低的活体要求(高通过率 低攻击拒绝率)
     * NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
     * HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
     * 默认NONE
     * 若活体检测结果不满足要求，则返回结果中会提示活体检测失败 (非必需)
     * @param userId 当需要对特定用户进行比对时，指定user_id进行比对。即人脸认证功能。
     * @param maxUserNum 查找后返回的用户数量。返回相似度最高的几个用户，默认为1，最多返回50个。
     * @param faceSortType 人脸检测排序类型 (非必需)
     * 0:代表检测出的人脸按照人脸面积从大到小排列
     * 1:代表检测出的人脸按照距离图片中心从近到远排列
     * 默认为0
     */
    public static UserInfoListDTO userFaceSearch(String key, String image, String imageType, String groupIdList,
        String qualityControl, String livenessControl, String userId, Integer maxUserNum, String faceSortType) {
        log.info("userFaceSearch start");

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("image", image);
        map.put("image_type", imageType);
        map.put("group_id_list", groupIdList);

        if (StringUtils.isNotEmpty(qualityControl)) {
            map.put("quality_control", qualityControl);
        }
        if (StringUtils.isNotEmpty(livenessControl)) {
            map.put("liveness_control", livenessControl);
        }
        if (StringUtils.isNotEmpty(userId)) {
            map.put("user_id", userId);
        }
        if (Objects.nonNull(maxUserNum)) {
            map.put("max_user_num", maxUserNum);
        }
        if (StringUtils.isNotEmpty(faceSortType)) {
            map.put("face_sort_type", faceSortType);
        }

        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiConstant.HOST, BaiduApiConstant.SEARCH,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON), ImmutableMap.of("access_token", key),
            JSON.toJSONString(map));
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        System.out.println(s);
        UserInfoListDTO userFaceResultDTO =
            JSON.parseObject(JSON.parseObject(s).getString("result"), UserInfoListDTO.class);
        log.info("userFaceSearch success userFaceResultDTO={}", userFaceResultDTO);
        return userFaceResultDTO;
    }

    public static UserInfoListDTO userFaceSearch(String key, String image, String imageType, String groupIdList) {
        return userFaceSearch(key, image, imageType, groupIdList, null, null, null, null, null);
    }
}
