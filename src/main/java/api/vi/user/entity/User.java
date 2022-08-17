package api.vi.user.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
@Table(name = "Users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Embedded
    private Company company;
    @Builder
    public User(Long id, String name, String password, Sex sex, Company company) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.company = company;
    }
}
