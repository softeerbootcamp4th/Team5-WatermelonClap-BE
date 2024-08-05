package com.watermelon.server.annotations;

import com.watermelon.server.MockLoginInterceptorConfig;
import com.watermelon.server.randomevent.controller.LotteryController;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(MockLoginInterceptorConfig.class)
public @interface ControllerTest {
}
