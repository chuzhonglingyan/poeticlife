package com.yuntian.gencode;

import com.google.common.base.CaseFormat;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.TemplateExceptionHandler;
import static com.yuntian.gencode.CodeGenUtil.*;
import static com.yuntian.gencode.ProjectConstant.*;



/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/poeticlife?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径


    public static final String JAVA_PATH = "/src/main/java"; //java文件路径
    public static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

    public static final String PACKAGE_PATH_DTO = packageConvertPath(DTO_PACKAGE);//生成的dto存放路径
    public static final String PACKAGE_PATH_VO = packageConvertPath(VO_PACKAGE);//生成的vo存放路径
    public static final String PACKAGE_PATH_DAO = packageConvertPath(MAPPER_PACKAGE);//生成的vo存放路径
    public static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    public static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    public static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

    private static final String AUTHOR = "CodeGenerator";//@author
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date


    public static final String MODEL_PATH = PROJECT_PATH + SERVICE_API_PATH+ JAVA_PATH;
    public static final String VO_PATH = PROJECT_PATH + SERVICE_API_PATH+ JAVA_PATH;
    public static final String MAPPER_PATH = PROJECT_PATH + SERVICE_PROVIDER_PATH+ RESOURCES_PATH;
    public static final String DAO_PATH = PROJECT_PATH + SERVICE_PROVIDER_PATH+ JAVA_PATH;

    public static final String SERVICE_PATH = PROJECT_PATH + SERVICE_API_PATH+ JAVA_PATH;
    public static final String SERVICE_IMPL_PATH = PROJECT_PATH + SERVICE_PROVIDER_PATH+ JAVA_PATH;

    public static final String BACKEND_CONTROL_PATH = PROJECT_PATH + BACKEND_PATH+ JAVA_PATH;

    public static final String BACKEND_VIEW = PROJECT_PATH + BACKEND_PATH+ "/src/main/resources/templates/backend/";
    public static final String BACKEND_JS= PROJECT_PATH + BACKEND_PATH+ "/src/main/resources/static/js/backend/";


    public static final String TEMPLATE_FILE_PATH = PROJECT_PATH + TMPFILE_PATH + "/src/test/resources/generator/template";//模板位置



    public static void main(String[] args) {
        genCode(new String[]{"article"},new String[]{"文章"});
        genViewList("article",null,"文章");
        genJSList("article",null,"文章");
        //genCodeByCustomModelName("输入表名","输入自定义Model名称");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     *
     * @param tableNames 数据表名称...
     */
    public static void genCode(String[] tableNames,String[] modelNameDescs) {
        for (int i = 0; i < tableNames.length; i++) {
            genCodeByCustomModelName(tableNames[i], null,modelNameDescs[i]);
        }

    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     *
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName, String modelName,String modelNameDesc) {
        genVOAndDTO(tableName,modelName,modelNameDesc);
        genModelAndMapper(tableName, modelName,modelNameDesc);
        genDaoMapper(tableName,modelName,modelNameDesc);
        genService(tableName, modelName,modelNameDesc);
        genBackendController(tableName, modelName,modelNameDesc);
    }


    public static void genModelAndMapper(String tableName, String modelName,String modelNameDes) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(MODEL_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(MAPPER_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(DAO_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }


    public static void genVOAndDTO(String tableName, String modelName,String modelNameDes) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);
            File fileDTO = new File(MODEL_PATH + PACKAGE_PATH_DTO + modelNameUpperCamel + "DTO.java");
            if (!fileDTO.getParentFile().exists()) {
                fileDTO.getParentFile().mkdirs();
            }
            cfg.getTemplate("dto.ftl").process(data, new FileWriter(fileDTO));
            System.out.println(modelNameUpperCamel + "DTO.java 生成成功");

            File fileVO = new File(MODEL_PATH + PACKAGE_PATH_VO + modelNameUpperCamel + "VO.java");
            if (!fileVO.getParentFile().exists()) {
                fileVO.getParentFile().mkdirs();
            }
            cfg.getTemplate("vo.ftl").process(data, new FileWriter(fileVO));
            System.out.println(modelNameUpperCamel + "VO.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成DTO and VO 失败", e);
        }
    }

    public static void genDaoMapper(String tableName, String modelName,String modelNameDes) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);

            File daoFile = new File(DAO_PATH + PACKAGE_PATH_DAO+ modelNameUpperCamel + "Mapper.java");
            if (!daoFile.getParentFile().exists()) {
                daoFile.getParentFile().mkdirs();
            }
            cfg.getTemplate("dao.ftl").process(data, new FileWriter(daoFile));
            System.out.println(modelNameUpperCamel + "Mapper.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成dao 失败", e);
        }
    }



    public static void genService(String tableName, String modelName,String modelNameDesc) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("modelNameDesc", modelNameDesc);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(SERVICE_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(SERVICE_IMPL_PATH  + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genBackendController(String tableName, String modelName,String modelNameDes) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(BACKEND_CONTROL_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    public static void genViewList(String tableName, String modelName,String modelNameDesc) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("commonHead", "<#include \"../common/head.ftl\">");
            data.put("basePath", "${basePath}");
            data.put("modelNameDesc", modelNameDesc);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(BACKEND_VIEW + data.get("modelNameLowerCamel") + "List.ftl");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("moduleViewList.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "moduleViewList.ftl 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成View失败", e);
        }

    }

    public static void genJSList(String tableName, String modelName,String modelNameDesc) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("modelNameDesc", modelNameDesc);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(BACKEND_JS + data.get("modelNameLowerCamel") + "List.js");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("moduleJsList.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "moduleJsList.js 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成View失败", e);
        }
    }



    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }



}
