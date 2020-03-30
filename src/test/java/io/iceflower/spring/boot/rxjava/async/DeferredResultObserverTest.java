package io.iceflower.spring.boot.rxjava.async;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DeferredResultObserver 클래스")
class DeferredResultObserverTest {

  @Nested
  @DisplayName("DeferredResultObserver를 활용한 비동기 테스트")
  class Describe_of_DeferredResultObserver {

    @Nested
    @DisplayName("응답 데이터가 empty일 때")
    class Context_when_retrive_empty_response {

      @Test
      @DisplayName("HTTP 200 코드를 반환받는다")
      void it_returns_200_http_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 Single일 때")
    class Context_when_retrive_single_value {

      @Test
      @DisplayName("HTTP 200 코드를 반환받는다")
      void it_returns_200_http_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 여러가지 값일 경우")
    class Context_when_retrive_multiple_values {
      @Test
      @DisplayName("HTTP 200 코드를 반환받는다")
      void it_returns_200_http_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 Json으로 직렬화한 목록 값일 경우")
    class Context_when_retrive_json_serialized_list_values {
      @Test
      @DisplayName("HTTP 200 코드를 반환받는다")
      void it_returns_200_http_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 서버 오류 예외일 경우")
    class Context_when_retrive_error_response {
      @Test
      @DisplayName("HTTP 500 코드를 반환받는다")
      void it_returns_500_http_code() {

      }
    }

    @Nested
    @DisplayName("응답 데이터가 타임아웃 예외일 경우")
    class Context_when_timeout_on_connection {
      @Test
      @DisplayName("HTTP 500 코드를 반환받는다")
      void it_returns_500_http_code() {

      }
    }
  }
}

