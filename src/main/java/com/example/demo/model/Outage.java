package com.example.demo.model;

import java.time.ZonedDateTime;

public record Outage(String id, ZonedDateTime begin,  ZonedDateTime end) {
}
