package ir.sobhan.internship.consumer.utils;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberConsumerRouteTest {

    private NumberAggregationStrategy aggregationStrategy;
    private SumNumbersProcess process;

    private CamelContext context;

    @BeforeEach
    void setUp() {
        context = new DefaultCamelContext();
        aggregationStrategy = new NumberAggregationStrategy();
        process = new SumNumbersProcess();
    }

    @Test
    void aggregateAndSumTest() {
        Exchange ex1 = ExchangeBuilder.anExchange(context)
                .withBody(10)
                .build();

        Exchange ex2 = ExchangeBuilder.anExchange(context)
                .withBody(15)
                .build();

        Exchange aggregated = aggregationStrategy.aggregate(null, ex1);
        aggregated = aggregationStrategy.aggregate(aggregated, ex2);

        List<Integer> resultList = aggregated.getMessage().getBody(List.class);
        assertEquals(2, resultList.size());
        assertEquals(10, resultList.get(0));
        assertEquals(15, resultList.get(1));

        process.process(aggregated);
    }
}