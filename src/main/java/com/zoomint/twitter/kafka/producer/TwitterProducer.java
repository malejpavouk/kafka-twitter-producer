package com.zoomint.twitter.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class TwitterProducer {
	private static final Logger logger = LoggerFactory.getLogger(TwitterProducer.class);
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;

	private TwitterStream twitterStream;

	public TwitterProducer(TwitterStream twitterStream, KafkaTemplate<String, Object> kafkaTemplate, String topic) {
		this.twitterStream = twitterStream;
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;

	}

	void start() {
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				kafkaTemplate.send(topic, status);
				logger.debug("Data sent: '{}'", status);
			}
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onScrubGeo(long userId, long upToStatusId) {
			}

			public void onException(Exception ex) {
				logger.info("Shutting down Twitter sample stream, exception thrown: '{}'", ex);
				twitterStream.shutdown();
			}

			public void onStallWarning(StallWarning warning) {
			}
		};

		twitterStream.addListener(listener);
		twitterStream.sample();
	}
}
