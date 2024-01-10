package assessment.events;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;

@Factory
public class Streams {


	@Inject
	private SerdeRegistry serdeRegistry;
	
	@Singleton
	public KStream<WindowedIdentifier, Long> dummyStream2(ConfiguredStreamBuilder builder) {
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "metrics");

		/*
		 * This makes the stream more predictable, and also has the side effect of reducing
		 * Kafka Streams' commit interval to 100ms (rather than the default of 30s, which
		 *  would significantly slow down our tests).
		 */
		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

		KStream<Long, Object> objectStream = builder
			.stream("DummyProducer2", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Object.class)));

		KStream<WindowedIdentifier, Long> stream = objectStream.groupByKey()
			.windowedBy(TimeWindows.of(Duration.ofDays(1)).advanceBy(Duration.ofDays(1)))
			.count(Materialized.as("dummyStream2"))
			.toStream()
			.selectKey((k, v) -> new WindowedIdentifier(k.key(), k.window().start(), k.window().end()));

		stream.to("DummyStreamOutput2",
			Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

		return stream;
	}


}