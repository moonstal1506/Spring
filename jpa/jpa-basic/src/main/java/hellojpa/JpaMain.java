package hellojpa;

import org.hibernate.Hibernate;

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
//            Member member = em.find(Member.class, 1L);
//            printMember(member);
//            printMemberAndTeam(member);

            Member member = new Member();
            member.setUsername("aa");
            em.persist(member);


//            Member member2 = new Member();
//            member2.setUsername("bb");

//            em.persist(member2);

            em.flush();
            em.clear();

//            Member m1 = em.find(Member.class, member.getId());
            Member Reference = em.getReference(Member.class, member.getId());//프록시
            Reference.getUsername();
            emf.getPersistenceUnitUtil().isLoaded(Reference);//초기화 여부확인

            //준영속 상태
//            em.detach(Reference);
//            em.close();
//            em.clear();
            Hibernate.initialize((Reference));//강제초기화
            Reference.getUsername();//초기화
//            System.out.println(m1==Reference);//항상같아야함
//            Member m2 = em.getReference(Member.class, member2.getId());//프록시

//            System.out.println("findMember = " + findMember.getUsername());

//            System.out.println("m1 = m2: " + (m1.getClass()==m2.getClass()));다름

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team.getName() = " + team.getName());
    }
}
