import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Comment from './Comment';
import DetailReview from './DetailReview';
import EditReply from './EditReply';
import MypageReview from './MypageReview';

interface reviewComment {
  createdAt: string;
  modifiedAt: string;
  reviewComment: string;
  reviewCommentId: number;
}

interface Member {
  memberBirth: string;
  memberEmail: string;
  memberId: string;
  memberImageUrl: string;
  memberNationality: string;
  memberNickname: string;
  memberPhone: string;
  memberRegisterKind: string;
  memberTag: string[] | null;
}

interface Props {
  comment: string;
  createdAt: string;
  guestHouseMemberId: string;
  guestHouseName: string;
  member: Member;
  modifiedAt: string;
  reviewComment: reviewComment;
  reviewId: number;
  star: number; // 해당 댓글 고유 아이디
}

export default function Sample() {
  const id = '해당객실주소';
  const detailReviewPage = '유동적으로 설정';
  const [review, setReview] = useState<Props | null>(null);
  useEffect(() => {
    // axios.get('/api/guesthouse/1/review?page=1&size=4').then((res) => {
    //   setReview(res.data.data[0]);
    // });
    const refreshGet = async () => {
      axios
        .post('/api/token', {
          data: {
            refreshToken: localStorage.getItem('refreshToken'),
          },
        })
        .then((res) => {
          console.log(res);
        });
    };
    refreshGet();
  }, []);
  const reservationData = {
    guestHouseName: '숙소1',
    roomName: '방이름',
    roomImageUrl: `${process.env.REACT_APP_SERVER_URL}/images/IMG_8753.JPG`,
    roomReservationStart: '2022-11-11',
    roomReservationEnd: '2022-11-16',
  };

  const myReservationReview = {
    reviewId: 1,
    guestHouse: {
      guestHouseId: 1,
      guestHouseName: '게스트하우스의이름',
    },
    roomReservation: {
      room: {
        roomId: 1,
        roomName: '좋은룸',
        roomPrice: 40000,
        roomImageUrl: '/방3.jpeg',
        roomInfo: '참 좋은 방입니다',
        reservePossible: false,
      },
      roomReservationStart: '2022-11-28',
      roomReservationEnd: '2022-11-30',
    },
    comment: '후기1',
    star: 4.5,
    createdAt: '2022-11-28T20:58:05',
    modifiedAt: '2022-11-28T20:58:05',
  };

  return (
    <div className="flex flex-col">
      {/* myPage 예약 목록 */}
      {review && (
        <>
          {/* myPage 후기 http://3.37.58.81:8080/api/auth/members/review?page=1&size=4*/}
          {/* 보류 */}
          {/* <div className="mb-[10px] mt-[10px] ml-[10px]">
          
          </div> */}
          {/* 후기 작성 창에서 유저가 후기를 남길 때 */}
          <div className="mb-[10px] mt-[10px] ml-[10px]">
            <EditReply type="user" id={3} />
          </div>
          <MypageReview
            guestHouseName={myReservationReview.guestHouse.guestHouseName}
            roomName={myReservationReview.roomReservation.room.roomName}
            roomLink={myReservationReview.guestHouse.guestHouseId}
            roomImageUrl={myReservationReview.roomReservation.room.roomImageUrl}
            roomReservationStart={
              myReservationReview.roomReservation.roomReservationStart
            }
            roomReservationEnd={
              myReservationReview.roomReservation.roomReservationEnd
            }
            comment={myReservationReview.comment}
            createdAt={myReservationReview.createdAt}
            memberNickname={'유저정보에서 꺼내올것'}
          />
          {/* 개인 예약 확인 데이터 조회 시 해당 후기 주소로 이동하는 아이디와 객실로 이동하는 객실아이디가 필요함 */}
          <div className="mb-[10px] mt-[10px] ml-[10px]">
            <Comment
              // type 종류 : myPage, roomDetail, reviewPage
              houseName={reservationData.roomName}
              date={`${reservationData.roomReservationStart} ~ ${reservationData.roomReservationEnd}`}
              imgsrc={reservationData.roomImageUrl}
              room={reservationData.roomName}
              roomLink={`/room/템플릿 리터럴 방 주소`}
              // 이동되는 라우터를 적어주시면 됩니다. 해당 게스트하우스 페이지로 이동됩니다.
              reviewLink={`${detailReviewPage}`}
              type="myPage"
            />
          </div>

          {/* 후기 작성 페이지에서의 리뷰 목록(관리자) */}
          <div className="mb-[10px] mt-[10px] ml-[10px] w-[1000px]">
            <DetailReview type="reviewPage" reviewComment={review} />
          </div>

          {/* 후기 작성 페이지에서의 리뷰 목록 로그인 유저 상태관리 시 admin(관리자)와 현재 로그인 유저를 검사하기 때문에 따로 분기하지 않아도 됩니다.*/}
          <div className="mb-[10px] mt-[10px] ml-[10px] w-[1000px]">
            <DetailReview type="reviewPage" reviewComment={review} />
          </div>

          {/* 상세 페이지에서의 리뷰 목록 */}
          <div className="mb-[10px] mt-[10px] ml-[10px] w-[560px]">
            <DetailReview type="roomDetail" reviewComment={review} />
          </div>
        </>
      )}
    </div>
  );
}
