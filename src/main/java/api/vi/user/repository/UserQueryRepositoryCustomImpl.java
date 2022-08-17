package api.vi.user.repository;

import api.vi.user.dto.RequestDto;
import static api.vi.user.entity.QUser.user;
import api.vi.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

//@Repository       합쳤기 떄문에
public class UserQueryRepositoryCustomImpl implements UserQueryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public User searchUserByRequestDto(RequestDto requestDto) {
        BooleanBuilder builder = new BooleanBuilder();
        String location = requestDto.getLocation();
        String business = requestDto.getBusiness();
        if (StringUtils.hasText(location)){
            builder.and(user.company.companyLocation.eq(location));
        }
        if (StringUtils.hasText(business)){
            builder.and(user.company.companyType.eq(business));
        }
        User userEntity = queryFactory.selectFrom(user).where(builder).fetchOne();
        return userEntity;

    }
}
