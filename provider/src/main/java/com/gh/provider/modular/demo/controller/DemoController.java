package com.gh.provider.modular.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.gh.common.SDK;
import com.gh.common.enums.CodeEnum;
import com.gh.common.toolsclass.ResultData;
import com.gh.provider.modular.demo.entity.Demo;
import com.gh.provider.modular.demo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 测试表 前端控制器
 * </p>
 *
 * @author gaohan
 * @since 2020-11-26
 */
@RefreshScope
@RestController
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port}")
    private String serverPort;

    private final DemoService service;

    @Autowired
    public DemoController(DemoService service) {
        this.service = service;
    }

    @PostMapping(value = "/one/{id}")
    public ResultData one(@PathVariable("id") String id) throws Exception {
        logger.info("=====> /one");
        Demo byId = service.getById(id);
        return new ResultData(CodeEnum.SUCCESS.get(), byId, serverPort, SDK.getDateUtils().getDateTime());
    }

    @PostMapping(value = "/getDemoById")
    public ResultData getDemoById(@RequestBody JSONObject json) throws Exception {
        Demo bo = service.getById(json.get("id").toString());
        return new ResultData(CodeEnum.SUCCESS.get(), bo, serverPort, SDK.getDateUtils().getDateTime());
    }

    @PostMapping(value = "/all")
    public ResultData all() throws Exception {
        List<Demo> list = service.list(null);
        return new ResultData(CodeEnum.SUCCESS.get(), list, serverPort, LocalDateTime.now().toString());
    }

    @PostMapping(value = "/saveDemo")
    public ResultData saveDemo(@RequestBody JSONObject json) throws Exception {
        Demo bo = json.toJavaObject(Demo.class);
        boolean save = service.save(bo);
        return new ResultData(save ? CodeEnum.SUCCESS.get() : CodeEnum.BUSINESS_ERROR.get(), "", String.valueOf(save), SDK.getDateUtils().getDateTime());
    }
}

