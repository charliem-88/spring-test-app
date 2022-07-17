package com.example.demo.model;

import java.util.List;

public record SiteInfo(String id, String name, List<Device> devices) {
}
