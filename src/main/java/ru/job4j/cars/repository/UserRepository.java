package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        Optional<User> rsl;
        try (Session session = sf.openSession()) {
            session.save(user);
            rsl = Optional.of(user);
        } catch (Exception e) {
            rsl = Optional.empty();
        }
        return rsl;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        User user = new User();
        user.setId(userId);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> list = session.createQuery(
                "from User order by id", User.class).list();
        session.close();
        return list;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> user = session.createQuery(
                "from User as u WHERE u.id = :fId", User.class)
                .setParameter("fId", userId)
                .uniqueResultOptional();
        session.close();
        return user;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> list = session.createQuery(
                        "from User as u WHERE u.login like :fId", User.class)
                .setParameter("fId", "%" + key + "%")
                .list();
        session.close();
        return list;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> rsl = session.createQuery(
                        "from ru.job4j.cars.model.User as u where u.login = :fLogin", User.class)
                .setParameter("fLogin", login)
                .uniqueResultOptional();
        session.close();
        return rsl;
    }

    public Optional<User> checkUser(String login, String pwd) {
        try (Session session = sf.openSession()) {
            var query = session.createQuery("from User where "
                   + "login = : paramLogin and password = : paramPwd");
            query.setParameter("paramLogin", login);
            query.setParameter("paramPwd", pwd);
            return query.uniqueResultOptional();
        }
    }
}
