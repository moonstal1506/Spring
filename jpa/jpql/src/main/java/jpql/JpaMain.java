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

            Member member = new Member();
            member.setUsername("m1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            Member member1 = resultList.get(0);
            member1.setAge(10);

            //팀-조인
            List<Team> resultList1 = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            //임베디드
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            //스칼라
            List resultList2 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();
            Object o = resultList2.get(0);
            Object[] result = (Object[]) o;

            List<Object[]> resultList3 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();
            Object[] result2 = resultList3.get(0);

            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

            //dto로 만들어
            List<MemberDTO> resultList4 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList4.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getUsername() = " + memberDTO.getAge());


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
