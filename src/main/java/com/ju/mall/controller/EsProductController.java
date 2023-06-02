package com.ju.mall.controller;

import com.ju.mall.Common.api.CommonPage;
import com.ju.mall.Common.api.CommonResult;
import com.ju.mall.nosql.elasticsearch.document.EsProduct;
import com.ju.mall.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.controller
 * @className: EsProductController
 * @author: Eric
 * @description: TODO
 * @date: 2023/6/2 11:22
 * @version: 1.0
 */
@Controller
@RequestMapping("/esProduct")
@Api(tags = "EsProductController",description = "搜索商品管理")
public class EsProductController {

    @Autowired
    private EsProductService esProductService;

    @ApiOperation("导入所有数据库商品到ES")
    @RequestMapping(value = "/importAll",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList(){
        return CommonResult.success(esProductService.importAll());
    }

    @ApiOperation("根据id删除商品")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable("id") Long id){
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation("根据id批量删除商品")
    @RequestMapping(value = "/delete/batch",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids){
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation("根据id创建商品")
    @RequestMapping(value = "/create/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> create(@PathVariable("id") Long id){
        EsProduct esProduct = esProductService.create(id);
        if(esProduct == null){
            return CommonResult.failed();
        }
        return CommonResult.success(esProduct);
    }

    @ApiOperation("简单搜索")
    @RequestMapping(value = "/search/simaple",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(value = "keywords",required = false) String keywords,
                                                      @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        Page<EsProduct> search = esProductService.search(keywords, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(search));
    }
}
