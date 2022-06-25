package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
            member.setUsername("member");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);


            em.flush();
            em.clear();

            //페이징
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1).setMaxResults(10).getResultList();
//
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }

            //조인
            String query = "select m from Member m inner join m.team t";
            //where
            String query1 = "select m from Member m inner join m.team t where t.name = :teamName";
            //아우터
            String query2 = "select m from Member m left join m.team t";
            //세타
            String query3 = "select m from Member m, Team t where m.username=t.name";
            //조인대상 필터링
            String query4 = "select m from Member m left join m.team t on t.name = 'teamA'";
            //연관관계 없는 엔티티 외부조인
            String query5 = "select m from Member m left join Team t on m.username = t.name";



            List<Member> resultList2 = em.createQuery(query, Member.class)
                    .getResultList();

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
