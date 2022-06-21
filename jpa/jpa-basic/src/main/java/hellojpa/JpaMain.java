package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("a");
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("aaa");

//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

//            Member a = new Member(11L, "a");
//            Member b = new Member(22L, "b");
//
//            em.persist(a);
//            em.persist(b);

//            Member findMember = em.find(Member.class, 11L);
//            findMember.setName("aaa");
//            System.out.println("----------------");

            Member member = new Member();
            member.setId(1L);
            member.setUsername("a");
            member.setRoleType(RoleType.USER);
            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
