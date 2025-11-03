package com.guti.service;

import com.guti.AutoLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

  private static final Logger logger = LoggerFactory.getLogger(MyService.class);

  @AutoLog(
      mappings = {
        @AutoLog.Mapping(type = String.class, method = "hashCode"),
        @AutoLog.Mapping(type = String.class, method = "toString", order = 1)
      })
  public String myMethod(String param, String param2) {
    logger.info("param.hashCode()={}", param.hashCode());
    logger.info("param2={}", param2);
    logger.info("Some internal logging in myMethod");
    return "result";
  }

  @AutoLog
  public void myVoidMethod(List<String> aList) {
    logger.info("Some internal logging in myVoidMethod");
  }
}
