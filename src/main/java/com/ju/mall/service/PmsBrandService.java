package com.ju.mall.service;

import com.ju.mall.mbg.model.PmsBrand;

import java.util.List;

/**
 * @author: jcm
 * @description: TODO 品牌服务层接口
 * @date: 2023/5/28 17:17
 * @version: 1.0
 */
public interface PmsBrandService {
    /**
     * @param :
     * @return List<PmsBrand>
     * @author jcm
     * @description TODO 返回所有的品牌
     * @date 2023/5/28 17:20
     */
   List<PmsBrand> listAllBrand();

   /**
    * @param pmsBrand: 创建品牌
    * @return int
    * @author jcm
    * @description TODO
    * @date 2023/5/28 21:33
    */
   int createBrand(PmsBrand pmsBrand);
}
