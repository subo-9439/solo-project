package api.vi.user.service;

import api.vi.user.dto.RequestDto;
import api.vi.user.entity.User;
import api.vi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //사이트 내 전체 회원을 조회할 수 있습니다.
    public List<User> getUserList(){
        return userRepository.selectAllDescJPQL();
    }
    public Page<User> getUserPage(int page, int size){
//        return userRepository.selectAllDescJPQL();
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
//        return userRepository.findUserPage(pageRequest);
    }
    //사이트 내 회원을 특정 조건에 맞게 조회할 수 있습니다. (조건 : 지역, 업종)
    public User getUser(RequestDto requestDto){

        return userRepository.searchUserByRequestDto(requestDto);
    }
}
