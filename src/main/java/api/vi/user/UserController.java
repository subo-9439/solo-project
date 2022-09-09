package api.vi.user;

import api.vi.user.dto.MultiResponseDto;
import api.vi.user.dto.RequestDto;
import api.vi.user.dto.SingleResponseDto;
import api.vi.user.dto.UserResponseDto;
import api.vi.user.entity.User;
import api.vi.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    
    //젠킨스 확인용
     @GetMapping("/hello")
    public String hello(@RequestParam int page, @RequestParam int size){
        return "hello";
    }
    
    
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam int page, @RequestParam int size){
        Page<User> userEntityPage = userService.getUserPage(page,size);
//        content,pageInfo
        List<User> userEntityList = userEntityPage.getContent();
        List<UserResponseDto> userResponseDtoList = mapper.userToUserResponseDtoByList(userEntityList);
        //
        return new ResponseEntity<>(new MultiResponseDto<>(userResponseDtoList,userEntityPage), HttpStatus.OK);
    }

    // todo. handlermethodargument이용해서 리팩토링하기
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam("location")String location,@RequestParam("business")String business){
        RequestDto requestDto = new RequestDto(location,business);
        User userEntity = userService.getUser(requestDto);
        UserResponseDto userResponseDto = mapper.userToUserResponseDto(userEntity);
        return new ResponseEntity<>(new SingleResponseDto<>(userResponseDto),HttpStatus.OK);
    }
}
