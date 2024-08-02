package com.example.HealthCheckApplication;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("healthcheck")
public class HealthCheckController {

    @GetMapping
    @Cacheable(value = "healthCheckCache", keyGenerator = "customCacheKeyGenerator")
    public ResponseEntity<Map<String, String>> getHealthCheck(
            @RequestParam(value = "format") String format) {
        Map<String, String> response = new HashMap<>();
        if ("short".equalsIgnoreCase(format)) {
            response.put("status", "OK");
            return ResponseEntity.ok(response);
        } else if ("full".equalsIgnoreCase(format)) {
            String formattedTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now());
            response.put("status", "OK");
            response.put("currentTime", formattedTime);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "BAD Request");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Void> handleUnsupportedMethods() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}

