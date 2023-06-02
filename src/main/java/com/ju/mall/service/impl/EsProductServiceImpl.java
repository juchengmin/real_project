package com.ju.mall.service.impl;

import com.ju.mall.dao.EsProductDao;
import com.ju.mall.nosql.elasticsearch.document.EsProduct;
import com.ju.mall.nosql.elasticsearch.repository.EsProductRepository;
import com.ju.mall.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.service.impl
 * @className: EsProductServiceImpl
 * @author: Eric
 * @description: TODO es的商品操作
 * @date: 2023/6/2 11:05
 * @version: 1.0
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    @Autowired
    private EsProductDao esProductDao;
    @Autowired
    private EsProductRepository esProductRepository;

    @Override
    public int importAll() {
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(null);
        Iterable<EsProduct> esProducts = esProductRepository.saveAll(allEsProductList);
        Iterator<EsProduct> iterator = esProducts.iterator();
        int result = 0;
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(id);
        if(!CollectionUtils.isEmpty(allEsProductList)){
            EsProduct esProduct = allEsProductList.get(0);
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if(!CollectionUtils.isEmpty(ids)){
            List<EsProduct> esProducts = new ArrayList<>();
            for(Long id : ids){
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProducts.add(esProduct);
            }
            esProductRepository.deleteAll(esProducts);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
