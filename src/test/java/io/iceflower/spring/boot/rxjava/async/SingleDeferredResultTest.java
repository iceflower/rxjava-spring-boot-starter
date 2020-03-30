package io.iceflower.spring.boot.rxjava.async;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SingleDeferredResult 클래스")
class SingleDeferredResultTest {

  @Nested
  @DisplayName("SingleDeferredResult 테스트")
  class Descrive_of_SingleDeferredResult {

    @Nested
    @DisplayName("응답 데이터가 Single value일 때")
    class Context_when_retrive_single_value {
      @Test
      @DisplayName("HTTP 200 코드와 함께 값을 전달받는다")
      void it_returns_http_200_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 Single value와 HTTP 코드가 함께 왔을 때")
    class Context_when_retrive_single_value_and_http_code {
      @Test
      @DisplayName("HTTP 404 코드와 함께 값을 전달받는다")
      void it_returns_http_404_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 json으로 직렬화한 POJO일 때")
    class Context_when_retrive_json_serialized_pojo_value {
      @Test
      @DisplayName("HTTP 200 코드와 함께 값을 전달받는다")
      void it_returns_http_200_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 오류일 때")
    class Context_when_retrive_error {
      @Test
      @DisplayName("HTTP 500 코드를 전달받는다")
      void it_returns_http_500_code() {

      }
    }
  }
}

