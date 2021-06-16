package xmz.block.chain.blockchainxmz.block.service;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.common.utils.PageUtil;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.core.TransactionExecutor;
import xmz.block.chain.blockchainxmz.dbs.BlockDBs;
import xmz.block.chain.blockchainxmz.dbs.TransactionDBs;
import xmz.block.chain.blockchainxmz.mining.BlockMinerServiceImpl;
import xmz.block.chain.blockchainxmz.page.PagePO;
import xmz.block.chain.blockchainxmz.page.PageVO;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockDBs blockDB;
    @Autowired
    private TransactionDBs transactionDB;
    @Autowired
    private BlockMinerServiceImpl blockMinerService;
    @Autowired
    private TransactionExecutor transactionExecutor;
    static Logger logger = LoggerFactory.getLogger(BlockService.class);

    /**
     * 获取最后一个区块数据
     *
     * @return
     */
    public Optional<BlockPO> getLastBlock() {
        return blockDB.getBlockLast();
    }

    public JsonVo getBlockIndex(String index) {
        JsonVo jsonVo;
        Optional<BlockPO> blockPOOptional = blockDB.getBlockTxIndex(index);
        if (!blockPOOptional.isPresent()) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Blockchain number does not exist !");
            return jsonVo;
        }
        jsonVo = JsonVo.success();
        jsonVo.setItem(blockPOOptional.get());
        return jsonVo;
    }

    public JsonVo getAllBlock(PagePO pagePO) {
        JsonVo jsonVo;
        Optional<BlockPO> optional = blockDB.getBlockLast();
        if (!optional.isPresent()) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("No mining !");
            return jsonVo;
        }
        List<String> indexList=new ArrayList<>();
        for (int i = optional.get().getHeader().getIndex(); i >0 ; i--) {
            indexList.add(i+"");
        }
        PageUtil<String> pageUtil=new PageUtil<>();
        pageUtil.pageUtil(pagePO.getPage(),pagePO.getRows(),indexList);
        List<BlockPO> blockPOS = blockDB.getAllBlock1000(pageUtil.getContent());
        PageVO pageVO = new PageVO();
        pageVO.setTotal(pageUtil.getTotalElements());
        pageVO.setData(blockPOS);
        jsonVo = JsonVo.success();
        jsonVo.setItem(pageVO);
        return jsonVo;
    }

    /**
     * 产块
     */
    public void mining() {
        try {
            Optional<BlockPO> lastBlock = getLastBlock();
            BlockPO blockPO = blockMinerService.createBlock(lastBlock);
            if (blockPO == null) {
                logger.error("未设置挖矿账户，暂停区块产出 !");
                return;
            }
            List<TransactionPO> transactionPOS = transactionDB.getAllAppendingTransaction();
            for (TransactionPO transactionPO : transactionPOS) {
                if (transactionPO.getRate().compareTo(XMZConfig.TRANSACTION_RATE) != -1 || (System.currentTimeMillis() - transactionPO.getTimestamp()) > (1440 * 60)) {
                    blockPO.getBody().addTransaction(transactionPO);
                    transactionDB.delAppendingTransaction(transactionPO.getTxHash());
                }
            }
            blockDB.putBlockLast(blockPO);
            blockDB.putBlockTxIndex(blockPO);
            logger.info("Find a New Block, {}", blockPO);
            transactionExecutor.run(blockPO);
        } catch (Exception e) {
            logger.error("区块链挖矿出现异常:{}", e);
        }
    }
}
