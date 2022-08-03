package com.yunki.service;

import com.yunki.entity.Yunki;
import org.springframework.stereotype.Service;

@Service
public class YunkiService {
	
	private final YunkiEventsService yunkiEventsService;

	public YunkiService(YunkiEventsService yunkiEventsService) {
		super();
		this.yunkiEventsService = yunkiEventsService;
	}

	public Yunki save(Yunki yunki) {
		System.out.println("Received " + yunki);
		this.yunkiEventsService.publish(yunki);
		return yunki;
	}

}
