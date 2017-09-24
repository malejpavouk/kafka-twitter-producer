package com.zoomint.twitter.kafka.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TwitterProducerApp {
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(TwitterProducerApp.class, args);
	}

	@Bean
	public TwitterProducer twitterProducer() {
		final TwitterProducer twitterProducer = new TwitterProducer(twitterStream(), kafkaTemplate(), environment.getRequiredProperty("kafka.topic"));
		twitterProducer.start();
		return twitterProducer;
	}

	@Bean
	public Map<String, Object> producerConfig() {
		Map<String, Object> cfg = new HashMap<>();
		cfg.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getRequiredProperty("broker.list"));
		cfg.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		cfg.put(ProducerConfig.ACKS_CONFIG, "1");

		return cfg;
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public TwitterStream twitterStream() {
		String consumerKey = environment.getRequiredProperty("consumerKey");
		String consumerSecret = environment.getRequiredProperty("consumerSecret");
		String accessToken = environment.getRequiredProperty("accessToken");
		String accessTokenSecret = environment.getRequiredProperty("accessTokenSecret");

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessTokenSecret);
		cb.setJSONStoreEnabled(true);
		cb.setIncludeEntitiesEnabled(true);

		return new TwitterStreamFactory(cb.build()).getInstance();
	}
}
