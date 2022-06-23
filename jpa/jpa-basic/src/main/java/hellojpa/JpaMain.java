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

//            Movie movie = new Movie();
//            movie.setActor("aa");
//            movie.setDirector("bb");
//            movie.setName("네임");
//            movie.setPrice(11111);
//            em.persist(movie);
//            em.flush();
//            em.clear();
//
//            Movie findMovie = em.find(Movie.class, movie.getId());

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
