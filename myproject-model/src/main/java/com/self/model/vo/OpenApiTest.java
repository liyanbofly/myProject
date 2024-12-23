package com.self.model.vo;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yufan.cui
 */
@Data

public class OpenApiTest {
    /**
     * 主键
     */
    private Long id;
    /**
     * 分组ID
     */
    private Long groupId;
    /**
     * 0 :草稿，3：上线，4下线
     */
    private Integer apiStatus;
    /**
     * API名称前缀
     */
    private String apiNamePrefix;
    /**
     * API名称
     */
    @NotEmpty(message = "API名称不能为空")
    private String apiName;
    /**
     * api中文名称
     */
    @NotEmpty(message = "API中文名称不能为空")
    private String apiChineseName;
    /**
     * API版本
     */
    private String apiVersion;
    /**
     * API描述
     */
    private String apiDesc;
    /**
     * 请求Path
     */
    @NotEmpty(message = "请求Path不能为空")
    private String path;
    /**
     * 请求方式（GET、POST、PUT、PATCH、DELETE、HEAD）
     */
    private String httpMethod;
    /**
     * 后端服务类型（HTTP、DUBBO）
     */
    @NotEmpty(message = "后端服务类型不能为空")
    private String serviceType;
    /**
     * HTTP/DUBBO-后端服务地址
     */
    @NotNull(message = "请输入地址")
    private String servAddr;
    /**
     * HTTP-后端服务Path
     */
    private String servPath;
    /**
     * HTTP-后端服务请求方式（GET、POST、PUT、PATCH、DELETE、HEAD）
     */
    private String servHttpMethod;
    /**
     * HTTP-后端服务ContentType
     */
    private String servContentType;
    /**
     * DUBBO-后端服务接口名
     */
    private String servClass;
    /**
     * DUBBO-后端服务组件名
     */
    private String servApplicationName;
    /**
     * DUBBO-后端服务方法名
     */
    private String servMethodName;
    /**
     * DUBBO-接口版本（非必填）
     */
    private String servVersion;
    /**
     * DUBBO-后端服务组名（非必填）
     */
    private String servGroup;
    /**
     * 后端超时时间
     */
    private Integer servTimeout;
    /**
     * 重试次数
     */
    private Integer retries;
    /**
     * 返回结果类型（JSON、TEXT、BINARY、XML、HTML）
     */
    private String resultType;
    /**
     * 多少秒内访问次数限制
     */
    private Integer secondsAccessLimit;
    /**
     * 每个门店访问限制次数
     */
    private Integer merchantAccessLimit;
    /**
     * API访问次数限制
     */
    private Integer accessLimit;
    /**
     * 是否删除（0:删除  1:未删除）
     */
    private Byte yn;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人ID
     */
    private Long creatorId;
    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;
    /**
     * 修改人
     */
    private String modifier;

    /**
     * 版本号  老接口<2.00 新接口>=2.00
     */
    private float version;
    /**
     * 新版文档发布状态（2012-8):0未发布,1发布
     */
    private Integer documentStatus;

    /**
     * string
     */
    private Integer inrCode;

    private Long addressId;

    private Long creatorOrgId;

    private String servAddrTest;

    private Integer statusProd;



    private  String lastDate;


    private String creatorOrgStr;

}
