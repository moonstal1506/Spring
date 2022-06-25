package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);

            em.persist(member);


            em.flush();
            em.clear();

            //case
            String query =
                    "select "+
                            "case when m.age <=10 then '학생요금' "+
                            "     when m.age >=60 then '경로요금' "+
                            "     else '일반요금' "+
                            "end "+
                        "from Member m";

            //coalesce
            String query1 = "select coalesce(m.username, '이름 없는 회원') from Member m";

            //nullif: 두값이 값으면 null
            String query2 = "select nullif(m.username, '관리자') from Member m";

            //기본함수
            String query3 = "select concat('a' , 'b') from Member m";
            String query4 = "select substring(m.username,2,3) from Member m";
            String query5 = "select locate('de','abcdef') from Member m";//4
            String query6 = "select size(t.members) from Team t";//4

            //사용자 정의 함수
            String query7 = "select function('group_concat', m.username) from Member m";
            String query8 = "select group_concat(m.username) from Member m";

            List<String> resultList = em.createQuery(query2)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
