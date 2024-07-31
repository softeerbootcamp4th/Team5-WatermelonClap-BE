//package com.watermelon.server.event.order.result.config;
//
//import com.watermelon.server.event.order.result.repository.OrderResultRepository;
//import com.watermelon.server.event.order.result.service.OrderResultCommandService;
//import com.watermelon.server.event.order.result.service.OrderResultQueryService;
//import com.watermelon.server.token.ApplyTokenProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TestOrderResultConfig {
//
//
//    @Bean
//    public OrderResultCommandService orderResultCommandService(){
//
//        return new OrderResultCommandService(
//                OrderResultRepository,
//
//                )
//    }
//
//    @Bean
//    public OrderResultQueryService orderResultQueryService(){
//        return new OrderResultQueryService();
//    }
//    @Bean
//    public OrderResultRepository orderResultRepository(){
//        return OrderResultRepository;
//    }
//
//
//
//}
