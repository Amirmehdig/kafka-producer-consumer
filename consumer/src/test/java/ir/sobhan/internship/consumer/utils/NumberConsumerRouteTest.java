package ir.sobhan.internship.consumer.utils;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberConsumerRouteTest {

    private NumberConsumerRoute numberConsumerRoute;
    private CamelContext camelContext;
    private Processor kafkaMessageProcessor;

    @BeforeEach
    void setUp() throws Exception {
        numberConsumerRoute = new NumberConsumerRoute();
        numberConsumerRoute.setSum(0);
        camelContext = new DefaultCamelContext();

        numberConsumerRoute.setKafkaBroker("direct:start");
        numberConsumerRoute.setRouteId("test-rout");

        camelContext.addRoutes(numberConsumerRoute);
        camelContext.start();
        kafkaMessageProcessor = camelContext.getRoute("test-rout").getProcessor();
    }

    @Test
    void testProcessKafkaMessage() throws Exception {
        numberConsumerRoute.setRouteId("test-route");

        Exchange exchange1 = ExchangeBuilder.anExchange(camelContext)
                .withBody("5")
                .build();
        kafkaMessageProcessor.process(exchange1);

        assertEquals(5, numberConsumerRoute.getSum());

        Exchange exchange2 = ExchangeBuilder.anExchange(camelContext)
                .withBody("10")
                .build();
        kafkaMessageProcessor.process(exchange2);

        assertEquals(15, numberConsumerRoute.getSum());

        Exchange exchange3 = ExchangeBuilder.anExchange(camelContext)
                .withBody("90")
                .build();
        kafkaMessageProcessor.process(exchange3);

        assertEquals(105, numberConsumerRoute.getSum());
    }
}