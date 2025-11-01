package com.guti.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggersCache {

  private final Map<Class<?>, Logger> cache = new ConcurrentHashMap<>();

  public Logger getLoggerForClass(Class<?> clazz) {
    return cache.computeIfAbsent(clazz, LoggerFactory::getLogger);
  }
}
