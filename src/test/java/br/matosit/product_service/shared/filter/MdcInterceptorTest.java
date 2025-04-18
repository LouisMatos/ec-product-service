package br.matosit.product_service.shared.filter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class MdcInterceptorTest {

	private MdcInterceptor mdcInterceptor;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	private Object mockHandler;

	@BeforeEach
	void setUp() {
		mdcInterceptor = new MdcInterceptor();
		mockRequest = mock(HttpServletRequest.class);
		mockResponse = mock(HttpServletResponse.class);
		mockHandler = new Object();
	}

	@Nested
	@DisplayName("preHandle method")
	class PreHandleMethod {

		@Test
		@DisplayName("adds X-ReqId to MDC and returns true")
		void addsXReqIdToMDCAndReturnsTrue() throws Exception {
			boolean result = mdcInterceptor.preHandle(mockRequest, mockResponse, mockHandler);

			assertTrue(result);
			assertNotNull(MDC.get("X-ReqId"));
			assertEquals(32, MDC.get("X-ReqId").length());
		}
	}

	@Nested
	@DisplayName("afterCompletion method")
	class AfterCompletionMethod {

		@Test
		@DisplayName("removes X-ReqId from MDC")
		void removesXReqIdFromMDC() throws Exception {
			MDC.put("X-ReqId", "dummy-id");

			mdcInterceptor.afterCompletion(mockRequest, mockResponse, mockHandler, null);

			assertNull(MDC.get("X-ReqId"));
		}
	}
}
