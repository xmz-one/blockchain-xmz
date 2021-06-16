//package xmz.block.chain.blockchainxmz.config;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import xmz.block.chain.blockchainxmz.block.service.BlockService;
//
//
//@Component
//@EnableScheduling
//public class ScheduleConfig {
//
//    static Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
//    @Autowired
//    private BlockService blockService;
//
//    /**
//     * 定时挖矿
//     */
//    @Scheduled(fixedDelay = 1)
//    public void mining() {
//        try {
//            blockService.mining();
//        } catch (Exception e) {
//            logger.error("定时挖矿", e);
//        }
//    }
//
//}
