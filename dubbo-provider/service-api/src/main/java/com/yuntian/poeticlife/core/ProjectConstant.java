package com.yuntian.poeticlife.core;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.yuntian.poeticlife";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model.entity";//生成的Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
    public static final String DTO_PACKAGE = BASE_PACKAGE + ".model.dto";//生成的dto所在包
    public static final String VO_PACKAGE = BASE_PACKAGE + ".model.vo";//生成的vo所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名


    public static final String BACKEND_PATH = "/backend-web";

    public static final String SERVICE_API_PATH = "/dubbo-provider/service-api";

    public static final String SERVICE_PROVIDER_PATH = "/dubbo-provider/service-provider";


}
