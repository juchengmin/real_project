package com.ju.mall.nosql.elasticsearch.repository;

import com.ju.mall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @projectName: mall
 * @package: com.ju.mall.nosql.elasticsearch.repository
 * @className: EsProductRepository
 * @author: Eric
 * @description: TODO 商品ES操作类 因为继承了ElasticsearchRepository,就会自动生成代理类
 * @date: 2023/5/31 21:57
 * @version: 1.0
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {
    /**
     * @param name: 商品名称
     * @param subTitle: 商品标题
     * @param keywords: 商品关键字
     * @param pageable: 分页信息
     * @return List<EsProduct>
     * @author jcm
     * @description TODO 查找商品,条件是自动拼接的，根据方法名就知道查询时什么意思了
     * @date 2023/5/31 22:02
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable pageable);
}
