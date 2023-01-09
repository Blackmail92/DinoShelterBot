package vd.martynov.dinoshelterbot.persist.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность предмета, хранящегося в инвентаре.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    // Название предмета
    private String name;

    // Описание предмета
    private String description;

    // Количество данных предметов в инвентаре
    private Integer quantity;

    // Связь с сущностью "Инвентарь"
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Override
    public String toString() {
        return quantity + "x " + name;
    }
}
