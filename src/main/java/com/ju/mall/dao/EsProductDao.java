package com.ju.mall.dao;

import com.ju.mall.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.dao
 * @className: EsProductDao
 * @author: Eric
 * @description: TODO
 * @date: 2023/5/31 22:25
 * @version: 1.0
 */
@Mapper
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
