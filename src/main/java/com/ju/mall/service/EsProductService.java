package com.ju.mall.service;

import com.ju.mall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: jcm
 * @description: TODO esProduct操作类
 * @date: 2023/5/31 23:04
 * @version: 1.0
 */
public interface EsProductService {

    /**
     * 数据初始化进入到es中
     * @return
     */
    int importAll();

    /**
     * @param id:
     * @return void
     * @author jcm
     * @description TODO 根据id删除商品
     * @date 2023/5/31 23:05
     */
    void delete(Long id);

    /**
     * @param id:
     * @return EsProduct
     * @author jcm
     * @description TODO 根据Id商品
     * @date 2023/5/31 23:06
     */
    EsProduct create(Long id);

    /**
     * @param ids:
     * @return int
     * @author jcm
     * @description TODO 批量删除商品
     * @date 2023/5/31 23:07
     */
    void delete(List<Long> ids);

    /**
     * @param keyword:
     * @param pageNum:
     * @param pageSize:
     * @return List<EsProduct>
     * @author jcm
     * @description TODO 搜索商品
     * @date 2023/5/31 23:08
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);
}
