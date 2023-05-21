package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class SpuManagerController {


    @Autowired
    private ManageService managerService;

    /**
     * admin/product/{page}/{limit}
     * 根据三级分类分页查询spu列表
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result getSpuInfoPage(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 SpuInfo spuInfo){

        //封装参数
        Page<SpuInfo> infoPage=new Page<>(page,limit);
        //结果
        IPage<SpuInfo> infoIPage=managerService.getSpuInfoPage(spuInfo,infoPage);

        return Result.ok(infoIPage);
    }

    @GetMapping("baseSaleAttrList")
    public Result baseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrList = managerService.getBaseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }

    /**
     * 保存spu
     * @param spuInfo
     * @return
     */
    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        // 调用服务层的保存方法
        managerService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

}
