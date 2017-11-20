package org.streampipes.messaging.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streampipes.commons.exceptions.SpRuntimeException;
import org.streampipes.messaging.EventConsumer;
import org.streampipes.messaging.InternalEventProcessor;
import org.streampipes.model.grounding.KafkaTransportProtocol;

import java.io.Serializable;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by riemer on 01.10.2016.
 */
public class SpKafkaConsumer implements EventConsumer<KafkaTransportProtocol>, Runnable,
        Serializable {

    private String kafkaUrl;
    private String topic;
    private String groupId;
    private InternalEventProcessor<byte[]> eventProcessor;
    private volatile boolean isRunning;

    private static final Logger LOG = LoggerFactory.getLogger(SpKafkaConsumer.class);

    public SpKafkaConsumer() {

    }

    // TODO backwards compatibility, remove later
    public SpKafkaConsumer(String kafkaUrl, String topic, InternalEventProcessor<byte[]> callback) {
        KafkaTransportProtocol protocol = new KafkaTransportProtocol();
        protocol.setKafkaPort(Integer.parseInt(kafkaUrl.split(":")[1]));
        protocol.setBrokerHostname(kafkaUrl.split(":")[0]);
        protocol.setTopicName(topic);

        try {
            this.connect(protocol, callback);
        } catch (SpRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        KafkaConsumer<String, byte[]> kafkaConsumer = new KafkaConsumer<>(getProperties());
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        while (isRunning) {
            ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records)
                eventProcessor.onEvent(record.value());
        }
        LOG.info("Closing Kafka Consumer.");
        kafkaConsumer.close();
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaUrl);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "10000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("zookeeper.session.timeout.ms", "60000");
        props.put("zookeeper.sync.time.ms", "20000");
        return props;
    }

    @Override
    public void connect(KafkaTransportProtocol protocol, InternalEventProcessor<byte[]>
            eventProcessor)
            throws SpRuntimeException {
        LOG.info("Kafka consumer: Connecting to " +protocol.getTopicName());
        this.eventProcessor = eventProcessor;
        this.kafkaUrl = protocol.getBrokerHostname() +":" +protocol.getKafkaPort();
        this.topic = protocol.getTopicName();
        this.groupId = UUID.randomUUID().toString();
        this.isRunning = true;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void disconnect() throws SpRuntimeException {
        LOG.info("Kafka consumer: Disconnecting from " +topic);
        this.isRunning = false;

    }

    @Override
    public Boolean isConnected() {
        return isRunning;
    }
}