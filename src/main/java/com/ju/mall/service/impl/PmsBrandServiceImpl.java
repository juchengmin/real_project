package com.ju.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.ju.mall.mbg.mapper.PmsBrandMapper;
import com.ju.mall.mbg.model.PmsBrand;
import com.ju.mall.mbg.model.PmsBrandExample;
import com.ju.mall.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.service.impl
 * @className: PmsBrandServiceImpl
 * @author: Eric
 * @description: TODO @Service 标记业务逻辑的实现类，表示该类承担了服务层的功能
 * @date: 2023/5/28 17:18
 * @version: 1.0
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;


    @Override
    public List<PmsBrand> listAllBrand() {
        //查询时不带任何条件
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand pmsBrand) {
        return pmsBrandMapper.insertSelective(pmsBrand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return pmsBrandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        //分页插件的使用，开启分页后,返回的实际对象为Page类型
        PageHelper.startPage(pageNum,pageSize);
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }
}
