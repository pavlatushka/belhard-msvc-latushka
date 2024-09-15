package by.latushka.songservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MessageBrokerConfig {

    private final MessageBrokerProperties properties;

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("java.util.*"));
        return converter;
    }

    @Bean
    public Queue queue() {
        return new Queue(properties.getQueue(), true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(properties.getRoutingKey() + "#");
    }
}
