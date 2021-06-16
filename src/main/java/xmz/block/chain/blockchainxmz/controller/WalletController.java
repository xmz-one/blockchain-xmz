package xmz.block.chain.blockchainxmz.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.wallet.service.WalletService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "getAccount", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getAccount(@RequestParam("address") String address) {
        JsonVo jsonVo;
        try {
            if (null == address || StringUtils.isBlank(address)) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Wrong address !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(walletService.getAccount(address));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getrate", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getrate() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(XMZConfig.TRANSACTION_RATE.setScale(0, BigDecimal.ROUND_DOWN));
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }
}
