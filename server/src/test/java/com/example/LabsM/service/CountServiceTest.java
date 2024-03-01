//package com.example.LabsM.service;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CountServiceTest {
//    private CountService countService = new CountService();
//    private UnSyncCountService unSyncCountService = new UnSyncCountService();
//    @Test
//    public void testService() {
//        int amount = 8;
//        for(int i = 0; i < amount; i++) {
//            countService.increment();
//            unSyncCountService.increment();
//        }
//        AmountEntity result = new AmountEntity(countService.getNumber());
//        assertNotNull(result);
//        assertEquals(amount, result.getAmount());
//        countService.reset();
//        result.setAmount(countService.getNumber());
//        assertEquals(0, result.getAmount());
//    }
//}
