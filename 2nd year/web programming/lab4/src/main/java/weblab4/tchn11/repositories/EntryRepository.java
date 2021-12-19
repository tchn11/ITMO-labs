package weblab4.tchn11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weblab4.tchn11.entities.Entry;
import weblab4.tchn11.entities.User;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
