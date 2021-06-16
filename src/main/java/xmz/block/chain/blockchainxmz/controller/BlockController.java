package xmz.block.chain.blockchainxmz.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmz.block.chain.blockchainxmz.block.service.BlockService;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.page.PagePO;
import xmz.block.chain.blockchainxmz.wallet.service.WalletService;

@RestController
@RequestMapping("block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @RequestMapping(value = "getLastBlock", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getLastBlock() {
        JsonVo jsonVo;
        try {
            jsonVo = JsonVo.success();
            jsonVo.setItem(blockService.getLastBlock().get());
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getBlockIndex", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getBlockIndex(@RequestParam("index") String index) {
        JsonVo jsonVo;
        try {
            if (null == index || StringUtils.isBlank(index)) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("There is no block number !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(blockService.getBlockIndex(index));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getAllBlock", method = RequestMethod.POST)
    public ResponseEntity<JsonVo> getAllBlock(@RequestBody PagePO pagePO) {
        JsonVo jsonVo;
        try {
            if (null == pagePO) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("There is no block index !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(blockService.getAllBlock(pagePO));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

}
