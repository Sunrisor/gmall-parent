package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.service.ManageService;
import com.atguigu.gmall.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")

public class BaseManagerController {

    @Autowired
    private ManageService managerService;



    /**
     * admin/product/getAttrValueList/{attrId}
     * 根据属性id查询属性值集合
     * @param attrId
     * @return
     */
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable Long attrId){

        //调用service
         BaseAttrInfo baseAttrInfo= managerService.getAttrInfo(attrId);
         List<BaseAttrValue> list=baseAttrInfo.getAttrValueList();

        return Result.ok(list);

    }

    /**
     * admin/product/saveAttrInfo
     * 新增和修改平台属性
     * @param baseAttrInfo
     * @return
     */
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){

        //调用service
        managerService.saveAttrInfo(baseAttrInfo);

        return Result.ok();
    }



    /**
     *
     * 根据分类查询平台属性
     *   /admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
     *
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable Long category1Id,
                                       @PathVariable Long category2Id,
                                       @PathVariable Long category3Id){
            //调用service
        List<BaseAttrInfo> list=managerService.attrInfoList(category1Id,category2Id,category3Id);

        return Result.ok(list);

    }


    /**
     * 查询一级分类
     * @return
     */
    @GetMapping("/getCategory1")
    public Result getCategory1(){


        //查询数据
        List<BaseCategory1> baseCategory1List= managerService.getCategory1();


        return Result.ok(baseCategory1List);
    }


    /**
     * 根据一级分类id查询二级分类数据
     *
     *  路径传值 ：
     *
     * PathVariable("对应路径后面的名称")
     *
     * @return
     */
    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable Long category1Id){


        //调用service

       List<BaseCategory2> category2List= managerService.getCategory2(category1Id);


       return Result.ok(category2List);
    }




    /**
     * admin/product/getCategory3/{category2Id}
     * 根据二级分类查询三级分类数据
     *
     * @return
     */
    @GetMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable Long category2Id){

        //调用service查询数据
       List<BaseCategory3> baseCategory3List= managerService.getCategory3(category2Id);

        return Result.ok(baseCategory3List);

    }

}
