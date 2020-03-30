package io.iceflower.spring.boot.rxjava.mvc;

import io.reactivex.rxjava3.core.Single;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SingleReturnValueHandlerTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("SingleReturnValueHandler 클래스")
public class SingleReturnValueHandlerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @Configuration
  @EnableAutoConfiguration
  @RestController
  protected static class Application {

    @RequestMapping(method = RequestMethod.GET, value = "/single")
    public Single<String> single() {
      return Single.just("single value");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/singleWithResponse")
    public Single<ResponseEntity<String>> singleWithResponse() {
      return Single.just(new ResponseEntity<String>("single value", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/throw")
    public Single<Object> error() {
      return Single.error(new RuntimeException("Unexpected"));
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
  public void shouldRetrieveErrorResponse() {

    // when
    ResponseEntity<Object> response = restTemplate.getForEntity("/throw", Object.class);

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
}
