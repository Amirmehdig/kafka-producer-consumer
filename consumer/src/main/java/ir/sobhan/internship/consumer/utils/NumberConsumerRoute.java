package ir.sobhan.internship.consumer.utils;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NumberConsumerRoute extends RouteBuilder {

    @Value("${camel.component.kafka.brokers:localhost:9092}")
    private String kafkaBroker;

    @Value("${camel.default.route-id:default-route-id}")
    private String routeId;

    @Value("${consumer.interval-in-sec}")
    private int interval;

    private final NumberAggregationStrategy numberAggregationStrategy;
    private final SumNumbersProcess sumNumbersProcess;

    @Override
    public void configure() {
        String uri = "kafka:random-numbers?brokers=" + kafkaBroker;
        from(uri)
                .routeId(routeId)
                .aggregate(constant(true), numberAggregationStrategy)
                .log("Received numbers: ${body}")
                .completionInterval((long) interval * 1000)
                .process(sumNumbersProcess);
    }
}
