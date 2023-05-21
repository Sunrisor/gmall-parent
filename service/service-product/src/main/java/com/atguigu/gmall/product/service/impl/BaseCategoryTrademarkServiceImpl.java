package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategoryTrademark;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.CategoryTrademarkVo;
import com.atguigu.gmall.product.mapper.BaseCategoryTrademarkMapper;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.product.service.BaseCategoryTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("all")
public class BaseCategoryTrademarkServiceImpl extends ServiceImpl<BaseCategoryTrademarkMapper,BaseCategoryTrademark> implements BaseCategoryTrademarkService {

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Autowired
    private BaseCategoryTrademarkMapper baseCategoryTrademarkMapper;
    /**
     * 根据category3Id获取品牌列表
     * @param category3Id
     * @return
     */
    @Override
    public List<BaseTrademark> findTrademarkList(Long category3Id) {

        //封装返回结果
        List<BaseTrademark> baseTrademarkList=new ArrayList<>();

        //设置插叙条件
        QueryWrapper<BaseCategoryTrademark> baseCategoryTrademarkQueryWrapper=new QueryWrapper<>();

        baseCategoryTrademarkQueryWrapper.eq("category3_id",category3Id);

        //根据三级分类id查询中间表获取关联的品牌id
        List<BaseCategoryTrademark> baseCategoryTrademarkList = baseCategoryTrademarkMapper.selectList(baseCategoryTrademarkQueryWrapper);

        //处理获取品牌id
        if(!CollectionUtils.isEmpty(baseCategoryTrademarkList)){

            //stream流   filter:过滤    map：获取遍历过程中指定的数据

            List<Long> trademarkIdList = baseCategoryTrademarkList.stream().map(baseCategoryTrademark -> {


                //遍历后返回品牌id
                return baseCategoryTrademark.getTrademarkId();

            }).collect(Collectors.toList());


            //查询数据
//            for (Long id : trademarkIdList) {
//
//                BaseTrademark baseTrademark = baseTrademarkMapper.selectById(id);
//
//                baseTrademarkList.add(baseTrademark);
//            }
//            select *from base_trademark where in (1,2);

            QueryWrapper<BaseTrademark> baseTrademarkQueryWrapper=new QueryWrapper<>();
            baseTrademarkQueryWrapper.in("id",trademarkIdList);

            baseTrademarkList=  baseTrademarkMapper.selectList(baseTrademarkQueryWrapper);

        }

        return baseTrademarkList;
    }

    /**
     * 删除分类品牌关联
     * @param category3Id
     * @param trademarkId
     */
    @Override
    public void remove(Long category3Id, Long trademarkId) {


        //设置条件
        QueryWrapper<BaseCategoryTrademark> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("category3_id",category3Id);
        queryWrapper.eq("trademark_id",trademarkId);

        //删除关联
        baseCategoryTrademarkMapper.delete(queryWrapper);
    }

    /**
     * 根据category3Id获取可选品牌列表
     * @param category3Id
     * @return
     */
    @Override
    public List<BaseTrademark> findCurrentTrademarkList(Long category3Id) {

        List<BaseTrademark> baseTrademarkListResult=new ArrayList<>();
        //创建条件对象
        QueryWrapper<BaseCategoryTrademark> categoryTrademarkQueryWrapper=new QueryWrapper<>();
        categoryTrademarkQueryWrapper.eq("category3_id",category3Id);
        //获取当前分类关联的信息
        List<BaseCategoryTrademark> baseCategoryTrademarkList = baseCategoryTrademarkMapper.selectList(categoryTrademarkQueryWrapper);
        //判断
        if(!CollectionUtils.isEmpty(baseCategoryTrademarkList)){

            //获取当前分类关联的品牌id集合
            List<Long> tradeMarkIdList = baseCategoryTrademarkList.stream().map(baseCategoryTrademark -> {

                return baseCategoryTrademark.getTrademarkId();
            }).collect(Collectors.toList());


//            //查询所有品牌
//            List<BaseTrademark> baseTrademarkList = baseTrademarkMapper.selectList(null);
//            //遍历处理
//            baseTrademarkListResult= baseTrademarkList.stream().filter(baseTrademark -> {
//
//                return !tradeMarkIdList.contains(baseTrademark.getId());
//            }).collect(Collectors.toList());

            //创建条件对象
            QueryWrapper<BaseTrademark> queryWrapper=new QueryWrapper<>();
            queryWrapper.notIn("id",tradeMarkIdList);
            //条件查询品牌列表
            baseTrademarkListResult=  baseTrademarkMapper.selectList(queryWrapper);


        }


        return baseTrademarkListResult;
    }

    /**
     * 保存分类和品牌关联
     * @param categoryTrademarkVo
     *  61 5,7
     *
     *  61 5  对象 BaseCategoryTrademark
     *  61 7  对象 BaseCategoryTrademark
     *
     */
    @Override
    public void save(CategoryTrademarkVo categoryTrademarkVo) {

        //获取品牌id集合
        List<Long> trademarkIdList = categoryTrademarkVo.getTrademarkIdList();

        //遍历处理-封装成对象
        List<BaseCategoryTrademark> baseCategoryTrademarkList = trademarkIdList.stream().map(trademarkId -> {
            //创建分类品牌对象
            BaseCategoryTrademark baseCategoryTrademark = new BaseCategoryTrademark();
            baseCategoryTrademark.setCategory3Id(categoryTrademarkVo.getCategory3Id());
            baseCategoryTrademark.setTrademarkId(trademarkId);
            return baseCategoryTrademark;
        }).collect(Collectors.toList());


        this.saveBatch(baseCategoryTrademarkList);
    }
}
