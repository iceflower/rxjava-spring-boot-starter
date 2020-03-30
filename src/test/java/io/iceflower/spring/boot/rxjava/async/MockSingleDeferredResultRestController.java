package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Single;
import java.util.Date;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableAutoConfiguration
@RestController
public class MockSingleDeferredResultRestController {
  @RequestMapping(method = RequestMethod.GET, value = "/single")
  public SingleDeferredResult<String> single() {
    return new SingleDeferredResult<String>(Single.just("single value"));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/singleWithResponse")
  public SingleDeferredResult<ResponseEntity<String>> singleWithResponse() {
    return new SingleDeferredResult<ResponseEntity<String>>(
        Single.just(new ResponseEntity<String>("single value", HttpStatus.NOT_FOUND)));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/event", produces = APPLICATION_JSON_VALUE)
  public SingleDeferredResult<EventDto> event() {
    return new SingleDeferredResult<EventDto>(Single.just(new EventDto("Spring.io", new Date())));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/throw")
  public SingleDeferredResult<Object> error() {
    return new SingleDeferredResult<Object>(Single.error(new RuntimeException("Unexpected")));
  }
}
