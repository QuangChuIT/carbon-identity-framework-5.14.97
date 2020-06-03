package org.wso2.carbon.identity.sso.authentication.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.equinox.http.helper.ContextPathServletAdaptor;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.http.HttpService;
import org.wso2.carbon.identity.sso.authentication.servlet.SSOAuthenticationServlet;

import javax.servlet.Servlet;

@Component(
        name = "identity.sso.authentication.component",
        immediate = true
)
public class SSOAuthenticationComponent {
    private static final Log log = LogFactory.getLog(SSOAuthenticationComponent.class);

    @Activate
    protected void activate(ComponentContext context) {
        log.info("QuangCV: focus to SSOAuthenticationComponent.activate");
        HttpService httpService = SSOAuthenticationComponentServiceHolder.getHttpService();

        // Register Session IFrame Servlet
        Servlet ssoAuthenticationServlet = new ContextPathServletAdaptor(new SSOAuthenticationServlet(),
                "/account/login");
        try {
            httpService.registerServlet("/account/login",
                    ssoAuthenticationServlet, null, null);
        } catch (Exception e) {
            String msg = "Error when registering OIDC Session IFrame Servlet via the HttpService.";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    protected void deactivate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.info("OIDC Session Management bundle is deactivated");
        }
    }

    @Reference(
            name = "osgi.http.service",
            service = HttpService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetHttpService"
    )
    protected void setHttpService(HttpService httpService) {

        if (log.isDebugEnabled()) {
            log.info("Setting the HTTP Service in OIDC Session Management bundle");
        }
        SSOAuthenticationComponentServiceHolder.setHttpService(httpService);
    }

    protected void unsetHttpService(HttpService httpService) {

        if (log.isDebugEnabled()) {
            log.info("Unsetting the HTTP Service in OIDC Session Management bundle");
        }
        SSOAuthenticationComponentServiceHolder.setHttpService(null);
    }

}
