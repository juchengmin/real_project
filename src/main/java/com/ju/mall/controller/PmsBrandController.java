package com.ju.mall.controller;

import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.mbg.model.PmsBrand;
import com.ju.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.controller
 * @className: PmsBrandController
 * @author: Eric
 * @description: TODO 品牌表的操作,实现PmsBrand表中的添加、修改、删除及分页查询接口。
 * TODO @Controller 将一个类标记为控制器，使其成为 Spring 容器的一个组件，可以接收请求并进行相应的处理。
 * TODO @RequestMapping用于映射请求路径和处理方法
 * TODO @ResponseBody 将方法的返回值直接作为响应体返回给客户端
 * @date: 2023/5/28 16:24
 * @version: 1.0
 */
@Api(tags = "PmsBrandController",description = "商品品牌管理")
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * @param :
     * @return CommonResult<PmsBrand>
     * @author jcm
     * @description TODO 查询所有的品牌
     * @date 2023/5/28 17:16
     */
    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList(){
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    /**
     * @param pmsBrand:
     * @return CommonResult<List<PmsBrand>>
     * @author jcm
     * @description TODO 创建品牌 @RequestBody 当一个请求到达 Spring MVC 控制器时，Spring MVC 将请求的内容解析为对象，并将其绑定到带有 @RequestBody 注解的方法参数上
     * TODO 需要确保请求的内容与目标类型能够正确转换。通常需要配置适当的消息转换器，以支持不同的数据格式
     * @date 2023/5/28 21:26
     */
    @ApiOperation("创建品牌")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand){
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrand);
        if(count == 1){
            commonResult = CommonResult.success("");
            LOGGER.debug("createBrand success:{}",pmsBrand);
        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}",pmsBrand);
        }
        return commonResult;
    }
}
