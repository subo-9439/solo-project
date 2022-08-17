package api.vi.user.repository;

import api.vi.user.dto.RequestDto;
import api.vi.user.entity.User;

import java.util.List;

/**
 * Querydsl로 작성할 쿼리는 이 곳에 시그니처를 선언하고 `~RepositoryImpl`에서 구현한다.
 */
public interface UserQueryRepositoryCustom {
    User searchUserByRequestDto(RequestDto requestDto);
}
