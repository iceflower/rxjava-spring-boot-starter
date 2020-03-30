package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Observable;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = ObservableSseEmitterTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("ObservableDeferredResult 클래스")
public class ObservableSseEmitterTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Configuration
  @EnableAutoConfiguration
  @RestController
  protected static class Application {

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
  }

  @Test
  public void shouldRetrieveSse() {

    // when
    ResponseEntity<String> response = restTemplate.getForEntity("/sse", String.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals("data:single value\n\n", response.getBody());
  }

  @Test
  public void shouldRetrieveSseWithMultipleMessages() {

    // when
    ResponseEntity<String> response = restTemplate.getForEntity("/messages", String.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals("data:message 1\n\ndata:message 2\n\ndata:message 3\n\n", response.getBody());
  }

  @Test
  public void shouldRetrieveJsonOverSseWithMultipleMessages() {

    // when
    ResponseEntity<String> response = restTemplate.getForEntity("/events", String.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }



  private static Date getDate(int year, int month, int day) {
    return new GregorianCalendar(year, month, day).getTime();
  }
}