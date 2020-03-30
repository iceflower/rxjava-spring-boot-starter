package io.iceflower.spring.boot.rxjava.async;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ObservableSseEmitter 클래스")
class ObservableSseEmitterTest {

  @Nested
  @DisplayName("ObservableSseEmitter 응답 테스트")
  class Describe_of_ObservableSseEmitter {

    @Nested
    @DisplayName("SSE로 데이터를 받을 때")
    class Context_when_retrive_sse_value {
      @Test
      @DisplayName("HTTP 200 코드를 반환한다")
      void it_returns_http_200_code() {

      }
    }

    @Nested
    @DisplayName("SSE로 한번에 여러 메시지를 받을 때")
    class Context_when_retrive_sse_multiple_messages {
      @Test
      @DisplayName("HTTP 200 코드를 반환한다")
      void it_returns_http_200_code() {

      }
    }

    @Nested
    @DisplayName("SSE로 한번에 여러 json 메시지를 받을 때")
    class Context_when_retrive_json_over_sse_multiple_messages {

      @Test
      @DisplayName("HTTP 200 코드를 반환한다")
      void it_returns_http_200_code() {

      }
    }

  }

}