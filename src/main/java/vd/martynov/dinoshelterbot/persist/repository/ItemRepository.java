package vd.martynov.dinoshelterbot.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vd.martynov.dinoshelterbot.persist.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
