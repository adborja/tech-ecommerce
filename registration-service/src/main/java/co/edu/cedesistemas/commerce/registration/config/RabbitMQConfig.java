package co.edu.cedesistemas.commerce.registration.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String QUEUE_NAME = "registration.event.q";
    private static final String ROUTING_KEY = "registration.event.#";
    public static final String TOPIC_EXCHANGE = "registration.event.x";

    @Value("${spring.rabbitmq.host:localhost}")
    private String hostname;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualhost;
    @Value("${spring.rabbitmq.port:5672}")
    private Integer port;
    @Value("${spring.rabbitmq.username:guest}")
    private String username;
    @Value("${spring.rabbitmq.password:guest}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory(hostname, port);
        cf.setUri("amqp://foxmowaw:GaVJ7_DMMl8PihaPcPuzWsNVcQVVdaai@llama.rmq.cloudamqp.com/foxmowaw");
        //cf.setVirtualHost(virtualhost);
        //cf.setUsername(username);
        //cf.setPassword(password);
        return cf;
    }

    @Bean
    public Queue queue() {
        System.out.println("retorna cola");
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        System.out.printf("retorna topic");
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        System.out.println("Retorna connectionFactory");
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate template() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(ROUTING_KEY);
        System.out.println("retorna template");
        return template;
    }
}
