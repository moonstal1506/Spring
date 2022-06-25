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
            em.persist(member);
            tx.commit();


            TypedQuery<Member> querySingle = em.createQuery("select m from Member m where m.id =10", Member.class);
            Member singleResult = querySingle.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username,m.age from Member m");

            Member singleResult1 = em.createQuery("select m from Member m where m.username= :username", Member.class)
                    .setParameter("username", "m1")
                    .getSingleResult();
            System.out.println("singleResult1 = " + singleResult1.getUsername());

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
