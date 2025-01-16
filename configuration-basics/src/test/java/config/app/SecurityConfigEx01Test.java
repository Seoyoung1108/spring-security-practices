package config.app;

import jakarta.servlet.Filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"classpath:config/WebConfig.xml", "classpath:config/app/SecurityConfigEx01.xml"})
@WebAppConfiguration
public class SecurityConfigEx01Test {
    private MockMvc mvc;
    private FilterChainProxy filterChainProxy;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        filterChainProxy = (FilterChainProxy)context.getBean("springSecurityFilterChain", Filter.class);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new DelegatingFilterProxy(filterChainProxy), "/*")
                .build();
    }
    
    @Test
    public void testSecurityFilterChains() {
    	List<SecurityFilterChain> securityFilterChains = filterChainProxy.getFilterChains();
    	assertEquals(2,securityFilterChains.size());
    }
}
