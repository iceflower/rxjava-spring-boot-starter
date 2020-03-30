package io.iceflower.spring.boot.rxjava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.iceflower.spring.boot.rxjava.mvc.SingleReturnValueHandlerTest;
import io.reactivex.rxjava3.core.Observable;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Demo.InvoiceResource.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("Demo 클래스")
public class Demo {
  @Autowired
  private TestRestTemplate restTemplate;

  @Configuration
  @EnableAutoConfiguration
  @RestController
  protected static class InvoiceResource {

    @RequestMapping(method = RequestMethod.GET, value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Observable<Invoice> getInvoices() {

      return Observable.just(
          new Invoice("Acme", new Date()),
          new Invoice("Oceanic", new Date())
      );
    }
  }

  @Test
  public void shouldRetrieveInvoices() {

    // when
    ResponseEntity<List<Invoice>> response = restTemplate.exchange("/invoices",
        HttpMethod.GET, null, new ParameterizedTypeReference<List<Invoice>>() {
        });

    // then
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals("Acme", response.getBody().get(0).getTitle());
  }

  private static class Invoice {

    private final String title;

    private final Date issueDate;

    @JsonCreator
    public Invoice(@JsonProperty("title") String title, @JsonProperty("issueDate") Date issueDate) {
      this.title = title;
      this.issueDate = issueDate;
    }

    public String getTitle() {
      return title;
    }

    public Date getIssueDate() {
      return issueDate;
    }
  }
}
