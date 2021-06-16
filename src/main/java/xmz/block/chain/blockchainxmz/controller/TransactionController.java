package xmz.block.chain.blockchainxmz.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.page.PagePO;
import xmz.block.chain.blockchainxmz.transaction.service.TransactionService;
import xmz.block.chain.blockchainxmz.transaction.vo.TransactionVO;
import xmz.block.chain.blockchainxmz.wallet.service.WalletService;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "getTransaction", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getTransaction(@RequestParam("txhash") String txhash) {
        JsonVo jsonVo;
        try {
            if (null == txhash || StringUtils.isBlank(txhash)) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("There is no transaction hash !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(transactionService.getTransaction(txhash));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getWalletTransaction", method = RequestMethod.POST)
    public ResponseEntity<JsonVo> getWalletTransaction(@RequestBody PagePO pagePO) {
        JsonVo jsonVo;
        try {
            if (null == pagePO || null==pagePO.getData()) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Wrong address !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(transactionService.getAccountTransaction(pagePO));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }


    @RequestMapping(value = "getNewestTransactionList", method = RequestMethod.POST)
    public ResponseEntity<JsonVo> getNewestTransactionList(@RequestBody PagePO pagePO) {
        JsonVo jsonVo;
        try {
            if (null == pagePO) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Wrong address !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(transactionService.getNewestTransactionList(pagePO));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }


    @RequestMapping(value = "getAllAppendingTransaction", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getAllAppendingTransaction() {
        JsonVo jsonVo;
        try {
            return ResponseEntity.ok(transactionService.getAllAppendingTransaction());
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "sendTransaction", method = RequestMethod.POST)
    public ResponseEntity<JsonVo> sendTransaction(@RequestBody TransactionVO transactionVO) {
        JsonVo jsonVo;
        try {
            if (null == transactionVO || StringUtils.isBlank(transactionVO.getPublicKey())) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO || StringUtils.isBlank(transactionVO.getSign())) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO || StringUtils.isBlank(transactionVO.getData())) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO || StringUtils.isBlank(transactionVO.getTo())) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO.getTimestamp()) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO.getAmount()) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            if (null == transactionVO.getRate()) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Parameter error !");
                return ResponseEntity.ok(jsonVo);
            }
            return ResponseEntity.ok(transactionService.sendTransaction(transactionVO));
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }
}
