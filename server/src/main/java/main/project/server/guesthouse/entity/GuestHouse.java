package main.project.server.guesthouse.entity;


import lombok.*;
import main.project.server.audit.Auditable;

import main.project.server.city.City;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;

import main.project.server.review.entity.Review;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import main.project.server.guesthouseimage.entity.GuestHouseImage;
import main.project.server.member.entity.Member;
import main.project.server.room.entity.Room;
import main.project.server.roomreservation.entity.RoomReservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestHouse extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestHouseId; //아이디

    @Column(nullable = false)
    private String guestHouseName; //게스트 하우스 이름

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //등록한 업주 멤버


    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city; //도시 코드

    private String guestHouseLocation; //게스트 하우스의 경위도

    private String guestHouseAddress; //게스트 하우스 주소

    private String guestHousePhone; //게스트 하우스 연락처


    @Enumerated(EnumType.STRING)
    private GuestHouseStatus guestHouseStatus;//게스트 하우스 상태

    private Float guestHouseStar = 0f; //평점

    private String guestHouseTag; //태그 문자열

    private String guestHouseInfo;



    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<GuestHouseImage> guestHouseImage = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.PERSIST)
    private List<RoomReservation> roomReservations = new ArrayList<>();

    @OneToOne(mappedBy = "guestHouse",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GuestHouseDetails guestHouseDetails;


    public static GuestHouse GuestHouse(Long guestHouseId) {
        GuestHouse guestHouse = new GuestHouse();
        guestHouse.setGuestHouseId(guestHouseId);
        return guestHouse;
    }

    public List<String> guestHouseImageListToUrlList() {

        return this.getGuestHouseImage().stream().map(g -> new String(g.getGuestHouseImageUrl())).collect(Collectors.toList());
    }
}
