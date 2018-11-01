package ethereumj.config;

import ethereumj.EthereumjBeans.EthereumBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class EthereumConfig {
    @Bean
    EthereumBean ethereumBeanConfig() throws Exception{
        EthereumBean ethereumBean = new EthereumBean();
        Executors.newSingleThreadExecutor().submit(ethereumBean::start);
        return ethereumBean;
    }
}
