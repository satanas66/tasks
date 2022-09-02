package jpa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConnectionJpaTest {

    private static ConnectionJpa ConnectionJpa;

    private static EntityManager entityManager;

    @BeforeAll
    public static void init(){
        ConnectionJpa = new ConnectionJpa();
    }

    @Test
    public void getPHWVACEntityManager() {
        entityManager = ConnectionJpa.getPHWVACEntityManager();
        assertThat(entityManager.isOpen()).isTrue();
        entityManager.close();
        assertThat(entityManager.isOpen()).isFalse();
    }

    @Test
    public void getSisoraEntityManager() {
        entityManager = ConnectionJpa.getSisoraEntityManager();
        assertThat(entityManager.isOpen()).isTrue();
        entityManager.close();
        assertThat(entityManager.isOpen()).isFalse();
    }

    @Test
    public void getGestfordEntityManager() {
        entityManager = ConnectionJpa.getGestfordEntityManager();
        assertThat(entityManager.isOpen()).isTrue();
        entityManager.close();
        assertThat(entityManager.isOpen()).isFalse();
    }

    @Test
    public void getPAEntityManager() {
        entityManager = ConnectionJpa.getPAEntityManager();
        assertThat(entityManager.isOpen()).isTrue();
        entityManager.close();
        assertThat(entityManager.isOpen()).isFalse();
    }

    @Test
    public void getGestcliSisoraEntityManager() {
        entityManager = ConnectionJpa.getGestcliSisoraEntityManager();
        assertThat(entityManager.isOpen()).isTrue();
        entityManager.close();
        assertThat(entityManager.isOpen()).isFalse();
    }
}
