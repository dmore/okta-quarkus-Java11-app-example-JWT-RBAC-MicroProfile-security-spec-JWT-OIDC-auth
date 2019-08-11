package com.okta.quarkus.jwt;

import java.security.Principal;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Version 1 of the TokenSecuredResource
 */
@Path("/secured")
@RequestScoped
public class TokenSecuredResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim("groups")
    private Set<String> groups;

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt != null;
        String groupsString = groups != null ? groups.toString() : "";
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s, groups: %s", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT, groupsString);
        return helloReply;
    }

    @GET()
    @Path("/")
    @RolesAllowed({"Everyone"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt != null;
        String groupsString = groups != null ? groups.toString() : "";
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s, groups: %s\"", name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT, groupsString);
        return helloReply;
    }
    
}