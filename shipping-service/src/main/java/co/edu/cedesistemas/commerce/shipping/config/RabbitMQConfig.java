package co.edu.cedesistemas.commerce.shipping.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String QUEUE_NAME = "shipment.event.q";
    private static final String ROUTING_KEY = "shipment.event.#";
    public static final String TOPIC_EXCHANGE = "shipment.event.x";

    @Value("${spring.rabbitmq.host:localhost}")
    private String hostname;
    @Value("${spring.rabbitmq.port:5672}")
    private Integer port;
    @Value("${spring.rabbitmq.username:guest}")
    private String username;
    @Value("${spring.rabbitmq.password:guest}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory(hostname, port);
        cf.setUsername(username);
        cf.setPassword(password);
        return cf;
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate template() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(ROUTING_KEY);
        return template;
    }
}
