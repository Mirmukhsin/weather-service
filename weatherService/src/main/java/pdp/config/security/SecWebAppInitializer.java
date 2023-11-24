package pdp.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
