package service;


import domain.AppUser;
import dto.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import mapper.AppUserMapper;

@ApplicationScoped
public class AppUserService {

    //Creation
    @Transactional
    public AppUserResponse createUser(AppUserRequest request) {
        AppUser appUser = AppUserMapper.toEntity(request);
        appUser.password = BcryptUtil.bcryptHash(request.password());
        appUser.persist();
        return AppUserMapper.fromEntity(appUser);
    }

    //Deletion
    @Transactional
    public void deleteUser(String id) {
        boolean deleted = AppUser.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("User not found");
        }
    }

    //Patches
    @Transactional
    public AppUserResponse updateUser(String id, AppUserUpdateRequest request) {
        AppUser appUser = AppUser.findById(id);

        if (appUser == null) {
            throw new NotFoundException("User not found");
        }

        AppUserMapper.updateEntity(appUser, request);
        return AppUserMapper.fromEntity(appUser);
    }

    @Transactional
    public AppUserResponse updatePassword(String id, AppUserUpdatePasswordRequest request) {
        AppUser appUser = AppUser.findById(id);

        if (appUser == null) {
            throw new NotFoundException("User not found");
        }
        if (request.password() == null) {
            throw new NotFoundException("No password found in request");
        }

        appUser.password = BcryptUtil.bcryptHash(request.password());
        return AppUserMapper.fromEntity(appUser);
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