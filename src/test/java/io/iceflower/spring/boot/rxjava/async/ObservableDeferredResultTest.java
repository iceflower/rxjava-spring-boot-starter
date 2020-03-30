package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = ObservableDeferredResultTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("ObservableDeferredResult 클래스")
public class ObservableDeferredResultTest {


  @Autowired
  private TestRestTemplate restTemplate;

  @Configuration
  @EnableAutoConfiguration
  @RestController
  protected static class Application {

    @RequestMapping(method = RequestMethod.GET, value = "/empty")
    public ObservableDeferredResult<String> empty() {
      return new ObservableDeferredResult<String>(Observable.<String>empty());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/single")
    public ObservableDeferredResult<String> single() {
      return new ObservableDeferredResult<String>(Observable.just("single value"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/multiple")
    public ObservableDeferredResult<String> multiple() {
      return new ObservableDeferredResult<String>(Observable.just("multiple", "values"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event", produces = APPLICATION_JSON_VALUE)
    public ObservableDeferredResult<EventDto> event() {
      return new ObservableDeferredResult<EventDto>(
          Observable.just(
              new EventDto("Spring.io", new Date()),
              new EventDto("JavaOne", new Date())
          )
      );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/throw")
    public ObservableDeferredResult<Object> error() {
      return new ObservableDeferredResult<Object>(Observable.error(new RuntimeException("Unexpected")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/timeout")
    public ObservableDeferredResult<String> timeout() {
      return new ObservableDeferredResult<String>(Observable.timer(1, TimeUnit.MINUTES).map(new Function<Long, String>() {
        @Override
        public String apply(Long aLong) {
          return "single value";
        }
      }));
    }
  }

  @Test
  public void shouldRetrieveEmptyResponse() {

    // when
    ResponseEntity<List> response = restTemplate.getForEntity("/empty", List.class);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Collections.emptyList(), response.getBody());
  }

  @Test
  public void shouldRetrieveSingleValue() {

    // when
    ResponseEntity<List> response = restTemplate.getForEntity("/single", List.class);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Collections.singletonList("single value"), response.getBody());
  }

  @Test
  public void shouldRetrieveMultipleValues() {

    // when
    ResponseEntity<List> response = restTemplate.getForEntity("/multiple", List.class);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Arrays.asList("multiple", "values"), response.getBody());
  }

  @Test
  public void shouldRetrieveJsonSerializedListValues() {

    // when
    ResponseEntity<List<EventDto>> response = restTemplate.exchange("/event", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<EventDto>>() {});

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    assertEquals("JavaOne", response.getBody().get(1).getName());
  }

  @Test
  public void shouldRetrieveErrorResponse() {

    // when
    ResponseEntity<Object> response = restTemplate.getForEntity("/throw", Object.class);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void shouldTimeoutOnConnection() {

    // when
    ResponseEntity<Object> response = restTemplate.getForEntity("/timeout", Object.class);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


}