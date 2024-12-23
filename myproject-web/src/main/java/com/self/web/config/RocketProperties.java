package com.self.web.config;

import lombok.Data;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.stereotype.Component;

@Component
@Data
public class RocketProperties {

    private String topic;
    private String group;
    private Consumer consumer = new Consumer();
    @Data
    public static final class Consumer {


        private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;


        /**
         *
         *              consumeThreadMin和consumeThreadMax参数说明
         *
         * 在RocketMQ中，一个队列（queue）在同一时间只会被一个消费者线程消费。这是为了保证消息的顺序性。
         * 即使你设置了consumeThreadMin和consumeThreadMax参数，也不会改变这个行为。
         * consumeThreadMin和consumeThreadMax参数决定的是消费者客户端可以使用的线程池的大小，
         * 也就是并发消费消息的线程数量。但是，这些线程是分配给消费者客户端订阅的所有队列的，而不是单个队列。
         * 例如，如果你的消费者订阅了一个主题，这个主题有10个队列，你的consumeThreadMin和consumeThreadMax参数都设置为20，
         * 那么你的消费者客户端会有20个线程并发消费这10个队列的消息，但是每个队列在同一时间只会被一个线程消费。
         */

        /**
         * Minimum consumer thread number
         */
        private int consumeThreadMin = 20;

        /**
         * Max consumer thread number
         */
        private int consumeThreadMax = 64;

        /**
         * Concurrently max span offset.it has no effect on sequential consumption
         */
        private int consumeConcurrentlyMaxSpan = 2000;

        /**
         * Flow control threshold
         */
        private int pullThresholdForQueue = 1000;

        /**
         * Message pull Interval
         */
        private long pullInterval = 0;

        /**
         * Batch consumption size
         */
        private int consumeMessageBatchMaxSize = 1;

        /**
         * Batch pull size
         */
        private int pullBatchSize = 32;

        /**
         * Max re-consume times. -1 means 16 times.
         * </p>
         * <p>
         * If messages are re-consumed more than  #maxReconsumeTimes before success, it's be directed to a deletion
         * queue waiting.
         */
        private int maxReconsumeTimes = 3;

        /**
         * Suspending pulling time for cases requiring slow pulling like flow-control scenario.
         */
        private long suspendCurrentQueueTimeMillis = 1000;

        /**
         * Maximum amount of time in minutes a message may block the consuming thread.
         */
        private long consumeTimeout = 15;

    }


}
