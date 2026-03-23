package vn.khanguyen.backend.domain.res.user;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.khanguyen.backend.util.constant.GenderEnum;

@Getter
@Setter
public class ResUpdateUserDTO {
    private long id;

    private String name;

    private int age;

    private GenderEnum gender;

    private String address;

    private Instant updatedAt;

    private String updatedBy;

    private CompanyUser company;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyUser {
        private long id;
        private String name;
    }

}
