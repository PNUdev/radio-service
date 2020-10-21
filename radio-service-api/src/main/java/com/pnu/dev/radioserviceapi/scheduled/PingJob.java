package com.pnu.dev.radioserviceapi.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PingJob {

    private RestTemplate restTemplate;

    @Value("${ping.server}")
    private String pingServerUrl;

    @Autowired
    public PingJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 0/20 7-22 * * *", zone = "Europe/Kiev")
    public void ping() {
        log.info("Ping server...");
        restTemplate.headForHeaders(pingServerUrl);
    }

}
