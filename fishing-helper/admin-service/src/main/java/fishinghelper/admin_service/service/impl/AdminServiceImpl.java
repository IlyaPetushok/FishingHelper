package fishinghelper.admin_service.service.impl;


import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;
import fishinghelper.admin_service.exception.NoAccessRightException;
import fishinghelper.admin_service.exception.UserNotFoundException;
import fishinghelper.admin_service.mapper.RoleMapper;
import fishinghelper.admin_service.mapper.UserMapper;
import fishinghelper.admin_service.service.AdminService;
import fishinghelper.common_module.dao.PrivilegesRepository;
import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.*;
import fishinghelper.security_server.service.KeyCloakService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepositories userRepositories;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    public final RoleRepositories roleRepositories;
    public final PrivilegesRepository privilegesRepository;
    private final KeyCloakService keyCloakService;

    @Autowired
    public AdminServiceImpl(UserRepositories userRepositories, UserMapper userMapper, RoleMapper roleMapper, RoleRepositories roleRepositories, PrivilegesRepository privilegesRepository, KeyCloakService keyCloakService) {
        this.userRepositories = userRepositories;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.roleRepositories = roleRepositories;
        this.privilegesRepository = privilegesRepository;
        this.keyCloakService = keyCloakService;
    }

    /**
     * Updates the roles of a user identified by the given ID.
     *
     * @param userDTORequest The UserDTO containing the updated roles.
     * @param id             The ID of the user whose roles are to be updated.
     * @throws UserNotFoundException If no user is found with the specified ID.
     */
    @Override
    public void updateRoleForUser(UserDTORequest userDTORequest, Integer id) {
        log.info("start admin service for update user role");

        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by id for update role"));

        log.info("User found with ID {} for role update", id);

        List<String> roleNames=userDTORequest.getRoles().stream()
                .map(RoleDTO::getName)
                .collect(Collectors.toList());
        List<Role> roleList=roleRepositories.findRolesByNameIn(roleNames);
        user.setRoles(roleList);

        userRepositories.save(user);
        keyCloakService.updateUserRole(user);

        log.info("User roles updated successfully for user with ID {}", id);
    }

    @Override
    public List<UserDTOResponse> findUserByFilter(UserDTOFilter userDTOFilter) {
        Specification<User> userSpecification = createSpecification(userDTOFilter);
        Page<User> userPage = userRepositories.findAll(userSpecification, userDTOFilter.getPageable());
        return userPage.stream()
                .map(userMapper::toDTOResponse)
                .collect(Collectors.toList());
    }

    /**
     * Checks if the current user has the necessary permissions to update roles for a given user.
     *
     * @param userDTORequest The DTO containing the new roles to be assigned to the user.
     * @param id             The ID of the user whose roles are to be updated.
     * @return true if the user has the necessary permissions, false otherwise.
     * @throws UserNotFoundException  If the user with the given ID is not found.
     * @throws NoAccessRightException If the current user does not have sufficient rights to perform the operation.
     */
    @Override
    public boolean checkUpdateUserRole(UserDTORequest userDTORequest, Integer id) {
        log.debug("Checking update user role for user ID: {}", id);

        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by id:" + id));

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleType.MODERATOR.name()) || role.getName().equals(RoleType.ADMIN.name()))) {
            throw new NoAccessRightException(HttpStatus.FORBIDDEN, "This user have larger role than you!!!");
        }

        List<Role> roles = roleRepositories.findRolesByNameIn(userDTORequest.getRoles().stream()
                .map(RoleDTO::getName)
                .collect(Collectors.toList()));

        if (roles.stream().anyMatch(role -> role.getName().equals(RoleType.MODERATOR.name()) || role.getName().equals(RoleType.ADMIN.name()))) {
            throw new NoAccessRightException(HttpStatus.FORBIDDEN, "You dont have right");
        }

        log.info("User with ID {} has permission to update roles.", id);
        return true;
    }

    /**
     * Sets constraints (privileges) for a user based on the given ConstrainDTO.
     *
     * @param constrainDTO The DTO containing constraint names to set for the user.
     * @param idUser       The ID of the user to whom constraints are to be applied.
     * @throws UserNotFoundException If the user with the given ID is not found.
     */
    @Override
    public void setConstrainUser(ConstrainDTO constrainDTO, Integer idUser) {
        log.debug("Setting constraints for user with ID: {}", idUser);
        List<Privileges> constrainNew = privilegesRepository.findAllByNameIn(constrainDTO.getName());

        User user = userRepositories.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by id:" + idUser));
        List<Privileges> constrainUser = user.getPrivileges();

        for (Privileges constraint : constrainNew) {
            if (constrainUser.stream().noneMatch(cons -> cons.getName().equals(constraint.getName()))){
                constrainUser.add(constraint);
            }
        }

        user.setPrivileges(constrainUser);
        userRepositories.save(user);
        log.info("Constraints set successfully for user with ID: {}", idUser);
    }

    private Specification<User> createSpecification(UserDTOFilter userDTOFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(userDTOFilter.getName())) {
                predicates.add(criteriaBuilder.like(root.get(User_.NAME), "%" + userDTOFilter.getName() + "%"));
            }

            if (Objects.nonNull(userDTOFilter.getMail())) {
                predicates.add(criteriaBuilder.like(root.get(User_.MAIL), "%" + userDTOFilter.getMail() + "%"));
            }

            if (Objects.nonNull(userDTOFilter.getRole())) {
                Join<User, Role> userRoleJoin = root.join(User_.ROLES);
                predicates.add(userRoleJoin.get(Role_.NAME).in(userDTOFilter.getRole()));
            }

            if (userDTOFilter.getDateRegistrationStart() != null && userDTOFilter.getDateRegistrationFinish() != null) {
                predicates.add(criteriaBuilder.between(root.get(User_.DATE_REGISTRATION), userDTOFilter.getDateRegistrationStart(), userDTOFilter.getDateRegistrationFinish()));
            } else {
                if (userDTOFilter.getDateRegistrationFinish() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(User_.DATE_REGISTRATION), userDTOFilter.getDateRegistrationFinish()));
                }
                if (userDTOFilter.getDateRegistrationStart() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(User_.DATE_REGISTRATION), userDTOFilter.getDateRegistrationStart()));
                }
            }
            if (userDTOFilter.isMailStatus()) {
                predicates.add(criteriaBuilder.equal(root.get(User_.MAIL_STATUS), userDTOFilter.isMailStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
