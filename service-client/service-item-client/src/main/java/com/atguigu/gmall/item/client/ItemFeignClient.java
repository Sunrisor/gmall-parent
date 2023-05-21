package com.atguigu.gmall.item.client;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.client.impl.ItemDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@FeignClient(value = "service-item",fallback = ItemDegradeFeignClient.class)
public interface ItemFeignClient {


    /**
     * 获取商品详情数据
     *  api/item/{skuId}
     * @param skuId
     * @return
     */
    @GetMapping("/api/item/{skuId}")
    public Result getItem(@PathVariable Long skuId);
}
