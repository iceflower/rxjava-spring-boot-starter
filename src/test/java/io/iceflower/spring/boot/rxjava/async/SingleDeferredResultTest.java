package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SingleDeferredResultTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("SingleDeferredResultTest 클래스")
public class SingleDeferredResultTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Configuration
  @EnableAutoConfiguration
  @RestController
  protected static class Application {

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

  @Test
  public void shouldRetrieveSingleValue() {

    // when
    ResponseEntity<String> response = restTemplate.getForEntity("/single", String.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals("single value", response.getBody());
  }

  @Test
  public void shouldRetrieveSingleValueWithStatusCode() {

    // when
    ResponseEntity<String> response = restTemplate.getForEntity("/singleWithResponse", String.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Assertions.assertEquals("single value", response.getBody());
  }

  @Test
  public void shouldRetrieveJsonSerializedPojoValue() {

    // when
    ResponseEntity<EventDto> response = restTemplate.getForEntity("/event", EventDto.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals("Spring.io", response.getBody().getName());
  }

  @Test
  public void shouldRetrieveErrorResponse() {

    // when
    ResponseEntity<Object> response = restTemplate.getForEntity("/throw", Object.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}
