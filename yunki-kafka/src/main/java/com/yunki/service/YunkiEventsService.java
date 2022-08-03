package com.yunki.service;

import com.yunki.entity.Yunki;
import com.yunki.events.YunkiCreatedEvent;
import com.yunki.events.Event;
import com.yunki.events.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class YunkiEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.yunki.name:yunkis}")
	private String topicYunki;
	
	public void publish(Yunki yunki) {

		YunkiCreatedEvent created = new YunkiCreatedEvent();
		created.setData(yunki);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());

		this.producer.send(topicYunki, created);
	}
	
	

}
