package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    private String username;
    private int age;

    @QueryProjection//의존성을 가지게 됨
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
