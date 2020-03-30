package io.iceflower.spring.boot.rxjava.async;

import io.iceflower.spring.boot.rxjava.dto.EventDto;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class MockDeferredResultObserverRestController {
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
