package api.vi.user.dto;

import api.vi.user.entity.Company;
import api.vi.user.entity.Sex;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String name;
    private String password;
    private Sex sex;
    private Company company;

    @Builder
    public UserResponseDto(String name, String password, Sex sex, Company company){
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.company = company;
    }
}