package main.project.server.guesthouseimage.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestHouseImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestHouseImageId;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    private String guestHouseImageDownUrl;
    private String guestHouseImageUploadUrl;


}
