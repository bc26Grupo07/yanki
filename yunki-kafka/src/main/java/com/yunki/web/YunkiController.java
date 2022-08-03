package com.yunki.web;

import com.yunki.entity.Yunki;
import com.yunki.service.YunkiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yunkis")
public class YunkiController {
	
	private final YunkiService yunkiService;

	public YunkiController(YunkiService yunkiService) {
		super();
		this.yunkiService = yunkiService;
	}
	
	@PostMapping
	public Yunki save(@RequestBody Yunki yunki) {
		return this.yunkiService.save(yunki);
	}
	

}
