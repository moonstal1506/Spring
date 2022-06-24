package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Address address = new Address("city", "street", "10");

            Member member = new Member();
            member.setUsername("mem1");
            member.setHomeAddress(address);
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getAddressHistory().add(new AddressEntity("oldCity", "street", "10"));
            member.getAddressHistory().add(new AddressEntity("oldCity2", "street", "10"));


            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);

            em.persist(member);
            em.flush();
            em.clear();

            System.out.println("========start==========");
            Member findMember = em.find(Member.class, member.getId());
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for (Address address1 : addressHistory) {
//                System.out.println("address1 = " + address1.getCity());
//            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }
            //new Address 새로해야함
            findMember.setHomeAddress(new Address("newCity", address.getStreet(), address.getZipcode()));

            //컬랙션 값변경하면 자동으로 디비 반영
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("oldCity", "street", "10"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10"));

//            Member member2 = new Member();
//            member.setUsername("mem2");
//            member.setHomeAddress(address);

//            member.getHomeAddress().setCity("newCity");xxx

//            member.setWorkPeriod(new Period());
//            em.persist(member2);
            tx.commit();

        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

}
