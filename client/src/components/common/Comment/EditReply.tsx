import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import CommonBtn from '../CommonBtn/CommonBtn';
import StarRating from './StarRating';
type Props = {
  type: string;
  id: string;
};

// type : admin(관리자), user(사용자)
// 코멘트의 고유 아이디를 내려주면 해당 고유아이디에 대한 저장이 필요함
// 전체 댓글창도 아이디가 있을수 밖에 없음
export default function EditReply({ type, id }: Props) {
  const [reply, setReply] = useState('');
  const ref = useRef<HTMLTextAreaElement>(null);
  useEffect(() => {
    if (ref.current && ref) {
      ref.current.focus();
    }
  }, []);

  const [star, setStar] = useState<Array<boolean>>([
    ...new Array(5).fill(false),
  ]);

  const sendComment = async (
    message: string,
    commentId: string,
    star: boolean[]
  ) => {
    if (message.length > 0) {
      if (type === 'user') {
        // 유저가 후기 작성 시 유저에 쌓이게 api 호출 필요
        // starCount 는 별점이기 때문에 유저일때만 보내주면 됩니다.
        const starCount = star.filter((x) => x === true).length;
        if (starCount === 0) return alert('별점을 눌러주세요');
        setStar([...new Array(5).fill(false)]); // 유저가 별점 남긴후에 별점 초기화
        await axios.post(commentId, {});
      } else {
        // 관리자가 후기에 대댓글 작성 시 해당 대댓글로 보내줘야함
        console.log(type);
        console.log(message);
        await axios.post(commentId, {});
      }
    } else {
      alert('메세지를 입력하세요');
    }
  };
  return (
    <div>
      {type === 'user' && (
        <div className="flex flex-row items-center">
          <p className="text-lg font-bold mr-[8px]">나의 후기</p>
          <StarRating star={star} setStar={setStar} />
        </div>
      )}
      <div className="flex flex-row  w-[100%] h-[60px] mt-[10px]">
        <textarea
          ref={ref}
          className="border mr-[15px] border-border-color rounded-btnRadius w-[80%] h-[100%] resize-none pl-[5px] focus:border-border-color focus : border focus:outline-none"
          onChange={(e) => setReply(e.target.value)}
          value={reply}
        />
        <CommonBtn
          btnSize="w-[120px] h-[60px]"
          btnHandler={() => sendComment(reply, id, star)}
          text={'작성 완료'}
        />
      </div>
    </div>
  );
}
