package controller;

import dto.appuser.AppUserRequest;
import dto.appuser.AppUserUpdatePasswordRequest;
import dto.appuser.AppUserUpdateRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.AppUserService;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppUserResource {
    @Inject
    AppUserService service;

    @POST
    @Path("/register")
    public Response createUser(@Valid AppUserRequest request) {
        return Response.status(Response.Status.CREATED)
                .entity(service.createUser(request))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        service.deleteUser(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, @Valid AppUserUpdateRequest request) {
        return Response.ok(service.updateUser(id, request)).build();
    }

    @PATCH
    @Path("/{id}/password")
    public Response updatePassword(@PathParam("id") String id, @Valid AppUserUpdatePasswordRequest request) {
        return Response.ok(service.updatePassword(id, request)).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") String id) {
        return Response.ok(service.getById(id)).build();
    }

    @GET
    @Path("/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        return Response.ok(service.getByEmail(email)).build();
    }

}
