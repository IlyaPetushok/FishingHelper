package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositories extends JpaRepository<User,Integer> {
    User findUserByLoginAndPassword(String login,String password);
    User findUserByLogin(String login);
    User findUserByMail(String mail);
    List<User> findUsersByMailStatus(boolean flag);
    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);
    List<User> findUserByLoginIn(List<String> logins);
}
