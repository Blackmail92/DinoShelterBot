package vd.martynov.dinoshelterbot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vd.martynov.dinoshelterbot.persist.entity.Inventory;
import vd.martynov.dinoshelterbot.persist.entity.Item;
import vd.martynov.dinoshelterbot.persist.repository.InventoryRepository;
import vd.martynov.dinoshelterbot.persist.repository.ItemRepository;

import java.util.Set;

class DinoShelterBotApplicationTests {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        Inventory inventory = Inventory.builder()
                .id(157549610552786944L)
                .build();

        Item carcass = Item.builder()
                .name("Каркасс 100/100")
                .quantity(5)
                .description("Маленький каркасс")
                .inventory(inventory)
                .build();

        Item teleport = Item.builder()
                .name("Телепорт")
                .quantity(2)
                .description("телепорт в любую точку карты")
                .inventory(inventory)
                .build();

        inventoryRepository.save(inventory);
        itemRepository.save(carcass);
        itemRepository.save(teleport);

        Inventory inventory1 = inventoryRepository.findById(157549610552786944L).orElse(null);
        System.out.println(inventory1);
    }

}
