package com.yuntian.poeticlife.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 16:24 2018/11/13
 * @ Description：${description}
 * @see <a href="http://localhost:9090/druid/stat"></a>
 */
@RestController("/druid")
public class DruidStatController {

    @GetMapping("/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#get\ataSourceStatDataList 该方法可以获取所有数据源的监控数据
        // ，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}