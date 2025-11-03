package com.guti.runner;

import com.guti.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

  private final MyService service;

  public Runner(MyService service) {
    this.service = service;
  }

  @Override
  public void run(String... args) {
    service.myMethod("myParam", "myParam2");
    service.myVoidMethod(List.of("element1", "element2", "element3"));
  }
}
