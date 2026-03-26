package controller;

import domain.AppUser;
import dto.auth.LoginRequest;
import dto.auth.LoginResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import service.TokenService;

@Path("/api/auth")
public class AuthResource {
    @Inject
    TokenService tokenService;


    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        AppUser appUser = AppUser.findByUserName(loginRequest.userName());

        if (appUser == null || !BcryptUtil.matches(loginRequest.password(), appUser.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = tokenService.generateToken(appUser.userName, appUser.roles);
        return Response.ok(new LoginResponse(token)).build();
    }
}
