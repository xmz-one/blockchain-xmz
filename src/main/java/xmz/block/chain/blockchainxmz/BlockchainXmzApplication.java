package xmz.block.chain.blockchainxmz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.Naming;

@SpringBootApplication
public class BlockchainXmzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainXmzApplication.class, args);
    }
}
