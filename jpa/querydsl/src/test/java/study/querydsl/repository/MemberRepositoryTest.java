package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void basicTest() {
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

//        List<Member> result1 = memberJpaRepository.findAll();
        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).containsExactly(member);

//        List<Member> result2 = memberJpaRepository.findByUsername("member1");
        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).containsExactly(member);

    }

    @Test
    void searchTest() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member mem1 = new Member("mem1", 10, teamA);
        Member mem2 = new Member("mem2", 20, teamA);
        Member mem3 = new Member("mem3", 30, teamB);
        Member mem4 = new Member("mem4", 40, teamB);

        em.persist(mem1);
        em.persist(mem2);
        em.persist(mem3);
        em.persist(mem4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");
//        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);
        List<MemberTeamDto> result = memberRepository.search(condition);

        assertThat(result).extracting("username").containsExactly("mem4");
    }

    @Test
    void searchPageSimple() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member mem1 = new Member("mem1", 10, teamA);
        Member mem2 = new Member("mem2", 20, teamA);
        Member mem3 = new Member("mem3", 30, teamB);
        Member mem4 = new Member("mem4", 40, teamB);

        em.persist(mem1);
        em.persist(mem2);
        em.persist(mem3);
        em.persist(mem4);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition,pageRequest);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username").containsExactly("mem1", "mem2", "mem3");
    }

    @Test
    void queryDslPredicateExecutorTest() {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member mem1 = new Member("mem1", 10, teamA);
        Member mem2 = new Member("mem2", 20, teamA);
        Member mem3 = new Member("mem3", 30, teamB);
        Member mem4 = new Member("mem4", 40, teamB);

        em.persist(mem1);
        em.persist(mem2);
        em.persist(mem3);
        em.persist(mem4);

        QMember member = QMember.member;
        Iterable<Member> result = memberRepository.findAll(
                member.age.between(10, 40)
                        .and(member.username.eq("mem1")));
        for (Member findMember : result) {
            System.out.println("member1 = " + findMember);
        }
    }
}