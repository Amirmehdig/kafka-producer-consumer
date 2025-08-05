package ir.sobhan.internship.consumer.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SumNumbersProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        List<Integer> numbers = exchange.getIn().getBody(List.class);
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        log.info("Sum of recent numbers is {}", sum);
    }
}
