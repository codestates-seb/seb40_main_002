package main.project.server.guesthouse.entity;


import lombok.*;
import main.project.server.audit.Auditable;

import main.project.server.city.entity.City;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;

import main.project.server.heart.entity.Heart;
import main.project.server.review.entity.Review;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import main.project.server.guesthouseimage.entity.GuestHouseImage;
import main.project.server.member.entity.Member;
import main.project.server.room.entity.Room;
import main.project.server.roomreservation.entity.RoomReservation;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false)
    private String guestHouseLocation; //게스트 하우스의 경위도

    @Column(nullable = false)
    private String guestHouseAddress; //게스트 하우스 주소

    @Column(nullable = false)
    private String guestHousePhone; //게스트 하우스 연락처

    @Enumerated(EnumType.STRING)
    private GuestHouseStatus guestHouseStatus;//게스트 하우스 상태

    @Column(nullable = false)
    private float guestHouseStar = 0f; //평점, 멤버필드 선언시 바로 초기화 하는것은 빌더패턴으로 해당 객체를 만들때에는 적용되지 않음..... 생성자로 인해 객체를 생성할때만 적용됨

    private String guestHouseTag; //태그 문자열

    private String guestHouseInfo;

    @Column(nullable = false)
    private long guestHouseReviewCount = 0L; //리뷰갯수, 멤버필드 선언시 바로 초기화 하는것은 빌더패턴으로 해당 객체를 만들때에는 적용되지 않음..... 생성자로 인해 객체를 생성할때만 적용됨

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<GuestHouseImage> guestHouseImage = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.REMOVE)
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.PERSIST)
    private List<RoomReservation> roomReservations = new ArrayList<>();

    @OneToOne(mappedBy = "guestHouse",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GuestHouseDetails guestHouseDetails;

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.REMOVE)
    private List<Heart> heart = new ArrayList<>();

    @Column
    private Integer hearts = 0;

    public static GuestHouse GuestHouse(Long guestHouseId) {
        GuestHouse guestHouse = new GuestHouse();
        guestHouse.setGuestHouseId(guestHouseId);
        return guestHouse;
    }

    public List<String> guestHouseImageListToUrlList() {

        return this.getGuestHouseImage().stream().map(g -> new String(g.getGuestHouseImageUrl())).collect(Collectors.toList());
    }
}
