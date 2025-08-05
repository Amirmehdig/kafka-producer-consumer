package ir.sobhan.internship.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NumberAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String body = newExchange.getIn().getBody(String.class).trim();
        Integer newNumber = Integer.parseInt(body);
        log.info("Received {}", newNumber);
        List<Integer> numbers;
        if (oldExchange == null) {
            numbers = new ArrayList<>();
            numbers.add(newNumber);
            newExchange.getIn().setBody(numbers);
            return newExchange;
        } else {
            numbers = oldExchange.getIn().getBody(List.class);
            numbers.add(newNumber);
            return oldExchange;
        }
    }
}
