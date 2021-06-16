package xmz.block.chain.blockchainxmz.common.crypto;


public class Bip39Wallet {

    private final ECKeyPair keyPair;

    private final String filename;

    private final String mnemonic;

    public Bip39Wallet(ECKeyPair keyPair, String filename, String mnemonic) {
        this.keyPair = keyPair;
        this.filename = filename;
        this.mnemonic = mnemonic;
    }

    public String getFilename() {
        return filename;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public ECKeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public String toString() {
        return "Bip39Wallet{"
                + "filename='" + filename + '\''
                + ", mnemonic='" + mnemonic + '\''
                + '}';
    }
}
