package api.vi.user.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company {

    private String companyName;
    private String companyType;
    private String companyLocation;

    @Builder
    public Company(String companyName, String companyType, String companyLocation) {
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }
}
