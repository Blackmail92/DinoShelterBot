package vd.martynov.dinoshelterbot.persist.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    private Long id;

    @OneToMany(mappedBy = "inventory", fetch = FetchType.EAGER)
    private List<Item> items;
}
