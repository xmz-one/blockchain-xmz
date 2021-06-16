package xmz.block.chain.blockchainxmz.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xmz.block.chain.blockchainxmz.block.service.BlockService;
import xmz.block.chain.blockchainxmz.calculation.CalculationService;
import xmz.block.chain.blockchainxmz.common.utils.DateUtils;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.config.XMZConfig;

@RestController
@RequestMapping("calculation")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    @RequestMapping(value = "getCalculationToTalMinier", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getCalculationToTalMinier() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(calculationService.getCalculation(XMZConfig.TOTAL_MINIER_NUM));
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }
    @RequestMapping(value = "getCalculationDateToTalMinier", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getCalculationDateToTalMinier() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(calculationService.getCalculation(XMZConfig.TOTAL_DATE_MINIER_NUM+ DateUtils.getDated()));
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }



    @RequestMapping(value = "getCalculationToTalTransaction", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getCalculationToTalTransaction() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(calculationService.getCalculation(XMZConfig.TOTAL_TRANSACTION_NUM));
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getCalculationDateToTalTransaction", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getCalculationDateToTalTransaction() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(calculationService.getCalculation(XMZConfig.TOTAL_DATE_TRANSACTION_NUM+ DateUtils.getDated()));
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }

    @RequestMapping(value = "getCalculationTotalAccount", method = RequestMethod.GET)
    public ResponseEntity<JsonVo> getCalculationTotalAccount() {
        JsonVo jsonVo;
        try {
            jsonVo= JsonVo.success();
            jsonVo.setItem(calculationService.getCalculationTotalAccount());
            return ResponseEntity.ok(jsonVo);
        } catch (Exception e) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Do not attack XMZ chain !");
            return ResponseEntity.ok(jsonVo);
        }
    }
}
