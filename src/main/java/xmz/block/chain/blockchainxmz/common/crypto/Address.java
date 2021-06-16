package xmz.block.chain.blockchainxmz.common.crypto;

import cn.hutool.core.util.StrUtil;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;

public class Address {

    public static boolean isETHValidAddress(String input) {
        if (StrUtil.isEmpty(input) || !input.startsWith("0x"))
            return false;
        return isValidAddress(input);
    }

    public static boolean isValidAddress(String input) {
        String cleanInput = Numeric.cleanHexPrefix(input);

        try {
            Numeric.toBigIntNoPrefix(cleanInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return cleanInput.length() == 40;
    }
}
