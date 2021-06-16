package xmz.block.chain.blockchainxmz.common.exceptions;

/**
 * Encoding exception.
 */
public class MessageEncodingException extends RuntimeException {
    public MessageEncodingException(String message) {
        super(message);
    }

    public MessageEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
