package service;


import domain.AppUser;
import dto.appuser.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import mapper.AppUserMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;


@ApplicationScoped
public class AppUserService {
    private static final Logger LOG = Logger.getLogger(AppUserService.class);

    @Inject
    JsonWebToken jwt;


    //Creation
    @Transactional
    public AppUserResponse createUser(AppUserRequest request) {
        AppUser appUser = AppUserMapper.toEntity(request);


        if (AppUser.findByUserName(request.userName()) != null) {
            LOG.warnv("Try to register existing username: {0}", request.userName());
            throw new ClientErrorException("Username already taken", Response.Status.CONFLICT);
        }
        if (AppUser.findByEmail(request.email()) != null) {
            LOG.warnv("Try to register existing email: {0}", request.email());
            throw new ClientErrorException("Email already taken", Response.Status.CONFLICT);
        }

        appUser.password = BcryptUtil.bcryptHash(request.password());
        
        appUser.roles.add("USER");
        appUser.persist();
        return AppUserMapper.fromEntity(appUser);
    }

    //Deletion
    @Transactional
    public void deleteUser(String id) {
        boolean deleted = AppUser.deleteById(id);
        if (!deleted) {
            LOG.warnv("User with id: {0} not found!", id);
            throw new NotFoundException("User not found");
        }
    }

    //Patches
    @Transactional
    public AppUserResponse updateUser(String id, AppUserUpdateRequest request) {
        AppUser appUser = AppUser.findById(id);
        if (appUser == null) {
            LOG.warnv("User with id: {0} not found!", id);
            throw new NotFoundException("User not found");
        }

        // check the jwt for name and if role admin
        String jwtName = jwt.getName();
        boolean isAdmin = jwt.getGroups().contains("ADMIN");
        if (jwtName.equals(appUser.userName) || isAdmin) {

            if (AppUser.findByUserName(request.userName()) != null) {
                LOG.warnv("Try to register existing username: {0}", request.userName());
                throw new ClientErrorException("Username already taken", Response.Status.CONFLICT);
            }
            if (AppUser.findByEmail(request.email()) != null) {
                LOG.warnv("Try to register existing email: {0}", request.email());
                throw new ClientErrorException("Email already taken", Response.Status.CONFLICT);
            }

            AppUserMapper.updateEntity(appUser, request);
            return AppUserMapper.fromEntity(appUser);
        }
        LOG.warnv("User with username {0} tried to update data for the user with username {1} without being admin", jwt.getName(), appUser.userName);
        throw new ForbiddenException("You do not have permission to update the user");
    }

    @Transactional
    public AppUserResponse updatePassword(String id, AppUserUpdatePasswordRequest request) {
        AppUser appUser = AppUser.findById(id);

        if (appUser == null) {
            LOG.warnv("User with id: {0} not found!", id);
            throw new NotFoundException("User not found");
        }
        if (request.password() == null) {
            LOG.warnv("No password in request!");
            throw new BadRequestException("No password found in request");
        }

        String jwtName = jwt.getName();
        if (jwtName.equals(appUser.userName)) {

            appUser.password = BcryptUtil.bcryptHash(request.password());
            return AppUserMapper.fromEntity(appUser);
        }
        LOG.warnv("User with username: {0} tried to update the password for the user with username: {1} ", jwt.getName(), appUser.userName);
        throw new ForbiddenException("You do not have permission to update the password");
    }

    //Getters
    public AppUserSummaryResponse getByEmail(String email) {
        AppUser appUser = AppUser.findByEmail(email);

        if (appUser == null) {
            throw new NotFoundException("User not found");
        }
        return AppUserMapper.fromEntitySummary(appUser);
    }

    public AppUserResponse getById(String id) {
        AppUser appUser = AppUser.findById(id);

        if (appUser == null) {
            throw new NotFoundException("User not found");
        }
        return AppUserMapper.fromEntity(appUser);
    }
}