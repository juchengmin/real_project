package com.ju.mall.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @projectName: mall
 * @package: com.ju.mall.component
 * @className: OrderTimeOutCancleTask
 * @author: Eric
 * @description: TODO 订单超时并且解锁的定时器
 * @date: 2023/5/31 15:57
 * @version: 1.0
 */
@Component
public class OrderTimeOutCancelTask {

    /**
     * @param :
     * @return void
     * @author jcm
     * @description TODO 每10分钟扫描一次，取消订单 Quartz 的 Cron 表达式语法和传统的表达式语法不一样
     * @date 2023/5/31 16:02
     */
    @Scheduled(cron = "0 0/1 * ? * ?")
    private void cancelTimeOutOrder(){
        System.out.println("取消订单并且更具sku编号取消库存");
    }
}
