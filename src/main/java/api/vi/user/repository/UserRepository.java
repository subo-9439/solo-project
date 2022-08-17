package api.vi.user.repository;

import api.vi.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>
        , UserQueryRepositoryCustom {

    @Query(value = "SELECT u FROM User u ORDER BY u.createdAt DESC")
    List<User> selectAllDescJPQL();
//
//    @Query(value = "select u from User u ORDER BY u.createdAt DESC")
//    Page<User> findUserPage(Pageable pageable);

}
