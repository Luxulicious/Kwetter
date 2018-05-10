package boundary.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(JWTTokenNeededFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //log.info("Executing TOKEN response filter");

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        //System.out.println("AuthHeader: " + authorizationHeader);

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("SuperSecretKeyOwow")).build();
            verifier.verify(token);
        } catch (Exception ex) {
            System.out.println("invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        //log.info("Executing REST response filter from token filter");
    }
}
