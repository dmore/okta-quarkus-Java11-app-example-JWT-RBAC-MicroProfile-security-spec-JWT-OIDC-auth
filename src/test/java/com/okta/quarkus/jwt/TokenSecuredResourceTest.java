package com.okta.quarkus.jwt;

import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.smallrye.jwt.runtime.auth.QuarkusJwtCallerPrincipal;
import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestIdentityAssociation;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.jwt.JwtClaims;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.spi.CDI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TokenSecuredResourceTest {

    @Test
    public void testPermitAllEndpoint() {
        given()
          .when().get("/secured/permit-all")
          .then()
             .statusCode(200)
             .body(is("hello + anonymous, isSecure: false, authScheme: null, hasJWT: true, groups: "));
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/secured")
                .then()
                .statusCode(401);
    }

    @Test
    @DisabledOnNativeImage
    public void testHelloEndpointWithUser() {
        // work around for: https://github.com/quarkusio/quarkus/issues/11695
        configureTestUser("testuser", Set.of("Everyone"));

        given()
                .when().get("/secured")
                .then()
                .statusCode(200)
                .body(is("hello + testuser, isSecure: false, authScheme: null, hasJWT: true, groups: [Everyone]"));
    }

    private void configureTestUser(String name, Set<String> roles) {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("groups", new ArrayList<>(roles));
        JsonWebToken jwt = new QuarkusJwtCallerPrincipal(name, claims);

        QuarkusSecurityIdentity user = QuarkusSecurityIdentity.builder()
                .setPrincipal(jwt)
                .addRoles(new HashSet<>(roles))
                .build();
        CDI.current().select(TestIdentityAssociation.class).get().setTestIdentity(user);
    }
}