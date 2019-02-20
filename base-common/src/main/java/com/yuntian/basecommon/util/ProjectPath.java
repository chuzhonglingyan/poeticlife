package com.yuntian.basecommon.util;

import java.io.File;

/**
 * @Auther: yuntian
 * @Date: 2018/8/31 21:02
 * @Description:
 */
public class ProjectPath {

    private static String rootPath = "";

    private ProjectPath() {
        init();
    }

    @SuppressWarnings("deprecation")
    private static void init() {
        File directory = new File("");//设定为当前文件夹
        try {
            //System.out.println(directory.getCanonicalPath());//获取标准的路径
           // System.out.println(directory.getAbsolutePath());//获取绝对路径
            rootPath = directory.getCanonicalPath();
        } catch (Exception e) {
        }
    }

    /**
     * 获取应用的根目录，路径分隔符为/，路径结尾无/
     *
     * @return
     */
    public static String getProjectPath() {
        if ("".equals(rootPath)) {
            init();
        }
        return rootPath;
    }

}
