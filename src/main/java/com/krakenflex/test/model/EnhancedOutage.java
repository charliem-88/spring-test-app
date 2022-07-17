package com.krakenflex.test.model;

import java.time.ZonedDateTime;

public record EnhancedOutage(String id, String name, ZonedDateTime begin, ZonedDateTime end)  {
}
