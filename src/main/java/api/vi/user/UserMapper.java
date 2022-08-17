package api.vi.user;


import api.vi.user.dto.UserResponseDto;
import api.vi.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);
    List<UserResponseDto> userToUserResponseDtoByList(List<User> users);

}
