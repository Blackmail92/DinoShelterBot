package vd.martynov.dinoshelterbot.persist.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Инвентарь, привязан к Discord ID пользователя
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    // Discord snowflake id
    @Id
    private Long id;

    // Список хранящихся в инвентаре предметов - связь с сущностью "предмет"
    @OneToMany(mappedBy = "inventory", fetch = FetchType.EAGER)
    private List<Item> items;
}
