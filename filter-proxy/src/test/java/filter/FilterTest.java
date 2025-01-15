package filter;

import config.AppConfig;
import config.WebConfig;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class FilterTest {
    private static MockMvc mvc;

    @BeforeAll
    public static void setup(WebApplicationContext webApplicationContext) {
        Filter filter = (Filter) webApplicationContext.getBean("realFilter");
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new DelegatingFilterProxy(filter),"/*")
                .build();
    }

    @Test
    public void testHello() throws Exception {
        mvc
                .perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(cookie().value("RealFilter","Works"))
                .andExpect(content().string("<h1>Hello World</h1>"))
                .andDo(print());
    }
}