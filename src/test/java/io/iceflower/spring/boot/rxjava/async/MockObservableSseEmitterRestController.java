package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Observable;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@EnableAutoConfiguration
@RestController
public class MockObservableSseEmitterRestController {
  @RequestMapping(method = RequestMethod.GET, value = "/sse")
  public ObservableSseEmitter<String> single() {
    return new ObservableSseEmitter<String>(Observable.just("single value"));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/messages")
  public ObservableSseEmitter<String> messages() {
    return new ObservableSseEmitter<String>(Observable.just("message 1", "message 2", "message 3"));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/events")
  public ObservableSseEmitter<EventDto> event() {
    return new ObservableSseEmitter<EventDto>(APPLICATION_JSON, Observable.just(
        new EventDto("Spring.io", getDate(2016, 5, 11)),
        new EventDto("JavaOne", getDate(2016, 9, 22))
    ));
  }

  private static Date getDate(int year, int month, int day) {
    return new GregorianCalendar(year, month, day).getTime();
  }
}
