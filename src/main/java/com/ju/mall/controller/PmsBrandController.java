package com.ju.mall.controller;

import com.ju.mall.Common.api.CommonPage;
import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.mbg.model.PmsBrand;
import com.ju.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    /**
     * @param id:
     * @param pmsBrandDto:
     * @return CommonResult
     * @author jcm
     * @description TODO 更新指定品牌的信息 @PathVariable 用于从请求路径中提取参数值。
     * @date 2023/5/28 22:20
     */
    @ApiOperation("更新品牌信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id,@RequestBody PmsBrand pmsBrandDto){
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id,pmsBrandDto);
        if(count == 1){
            commonResult = CommonResult.success("");
            LOGGER.debug("updateBrand success:{}",pmsBrandDto);
        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed:{}",pmsBrandDto);
        }
        return commonResult;
    }

    @ApiOperation("删除品牌信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id){
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(id);
        if(count == 1){
            commonResult = CommonResult.success("");
            LOGGER.debug("deleteBrand success:id={}",id);
        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("deleteBrand failed:id={}",id);
        }
        return commonResult;
    }

    /**
     * @param pageNum:
     * @param pageSize:
     * @return CommonResult
     * @author jcm
     * @description TODO 分页获取品牌信息 @RequestParam 请求参数中获取值
     * @date 2023/5/28 22:43
     */
    @ApiOperation("分页查询品牌信息")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@RequestParam(value = "pageNum",defaultValue = "1")
                                    @ApiParam("页码") Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "3")
                                    @ApiParam("每页数量")
                                    Integer pageSize){
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(pageNum, pageSize);
        //用自己的分页类封装后返回
        return CommonResult.success(CommonPage.restPage(pmsBrands));
    }

    @ApiOperation("获取指定id的品牌信息")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getBrandById(@PathVariable("id") Long id){
        return CommonResult.success(pmsBrandService.getBrand(id));
    }
}
