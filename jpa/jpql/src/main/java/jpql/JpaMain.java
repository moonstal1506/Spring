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

            Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);

            Team team2 = new Team();
            team1.setName("team2");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("회원1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team1);

            Member member2 = new Member();
            member.setUsername("회원2");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team2);

            em.persist(member);


            em.flush();
            em.clear();

            //페치조인
            String query = "select distinct t from Team t join fetch m.members";
            String query2 = "select m  from Member m join fetch m.team t";//방향뒤집어 해결
            String query3 = "select t  from Team t";

            List<Team> resultList = em.createQuery(query,Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            //엔티티 직접 사용
            String query4 = "select m from Member m where m = :member";
            Member find = em.createQuery(query,Member.class)
                    .setParameter("member",member)
                    .getSingleResult();

            String query5 = "select m from Member m where m.team = :team";
            List<Member> members = em.createQuery(query, Member.class)
                    .setParameter("team", team1)
                    .getResultList();

            //네임드 쿼리
            List<Member> resultList1 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Team t : resultList) {
//                System.out.println("mem.getUsername() = " + mem.getUsername()+","+mem.getTeam().getName());
            }

            //벌크연산 flush 자동호출
            int resultCount = em.createQuery("update Member m set m.age =20")
                    .executeUpdate();
            em.clear();

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
