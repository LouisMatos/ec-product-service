package br.matosit.product_service.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import br.matosit.product_service.shared.filter.MdcInterceptor;

public class WebConfigTest {

    @Mock
    private InterceptorRegistry interceptorRegistry;

    @InjectMocks
    private WebConfig webConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInterceptors() {
        webConfig.addInterceptors(interceptorRegistry);

        ArgumentCaptor<MdcInterceptor> interceptorCaptor = forClass(MdcInterceptor.class);
        verify(interceptorRegistry, times(1)).addInterceptor(interceptorCaptor.capture());

        assertTrue(interceptorCaptor.getValue() instanceof MdcInterceptor);
    }
}

