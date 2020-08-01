package co.edu.cedesistemas.commerce.payment.client.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

@RibbonClient("payment-gateway")
@Slf4j
public class RibbonConfig {
    @Bean
    public IRule loadBalancingRule() {
        return new RoundRobinRule();
    }
}
