package vd.martynov.dinoshelterbot.persist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vd.martynov.dinoshelterbot.persist.entity.Inventory;
import vd.martynov.dinoshelterbot.persist.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public Inventory getInventory(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Inventory> getInventoriesByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }
}
