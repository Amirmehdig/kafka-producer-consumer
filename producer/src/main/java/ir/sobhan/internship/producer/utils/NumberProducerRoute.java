package ir.sobhan.internship.producer.utils;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NumberProducerRoute extends RouteBuilder {

    @Value("${producer.interval-in-sec:10}")
    private int interval;

    @Value("${camel.component.kafka.brokers:localhost:9092}")
    private String broker;

    private final Random random = new Random();

    @Override
    public void configure() {
        String fromUri = "timer:numberProducerRoute?period=" + interval * 1000;
        String toUri = "kafka:random-numbers?brokers=" + broker;
        from(fromUri)
                .process(exchange -> {
                    int number = random.nextInt(101);
                    exchange.getIn().setBody(number);
                    exchange.getIn().setHeader("kafka.KEY", "default-partition");
                })
                .to(toUri)
                .log("Sent ${body}");
    }
}
