package ir.sobhan.internship.consumer.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NumberConsumerRoute extends RouteBuilder {

    @Setter
    @Value("${camel.component.kafka.brokers:localhost:9092}")
    private String kafkaBroker;

    @Setter
    @Value("${camel.default.route-id:default-route-id}")
    private String routeId;

    @Setter
    @Value("${consumer.interval-in-sec}")
    private int interval;

    @Getter
    @Setter
    private int sum = 0;

    @Override
    public void configure() {
        from(kafkaBroker)
                .routeId(routeId)
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class).trim();
                    int newNumber = Integer.parseInt(body);
                    sum += newNumber;
                    log.info("Received {}", newNumber);
                });

        from("timer:aggregateAdder?period=" + (interval * 1000))
                .process(exchange -> {
                    log.info("Sum of recent numbers is {}", sum);
                    sum = 0;
                });
    }
}
