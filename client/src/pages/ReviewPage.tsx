import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getGhReviewData } from '../apis/getGhReviewData';
import { getUserInfo } from '../apis/getUserInfo';
import { getGhInfo } from '../apis/getGhInfo';
import DetailReview from '../components/common/Comment/DetailReview';
import EditReply from '../components/common/Comment/EditReply';

interface GhInfo {
  guestHouseName: string;
  memberNickname: string;
  memberId: string;
}

interface UserInfo {
  memberNickname: string;
  memberId: string;
}

export default function ReviewPage() {
  const { ghId } = useParams();
  const [review, setReview] = useState<[]>([]);
  const [ghInfo, setGhInfo] = useState<GhInfo>();
  const [user, setUser] = useState<UserInfo>();
  const [isHost, setIsHost] = useState<string>('');
  const [isLoading, setIsLoading] = useState(false);

  function adminChecker(userId: string, hostId: string) {
    if (userId === hostId) {
      setIsHost('ADMIN');
    } else {
      setIsHost('USER');
    }
  }

  useEffect(() => {
    const test = async () => {
      await getGhReviewData(ghId).then((res) => setReview(res));
      const userInfo = await getUserInfo(localStorage.getItem('accessToken'));
      const ghInfo = await getGhInfo(ghId);
      console.log(userInfo);
      setGhInfo(ghInfo);
      setUser(userInfo);
      adminChecker(userInfo.memberId, ghInfo.memberId);
      setIsLoading(true);
    };
    test();
  }, []);

  return (
    <>
      {isLoading && (
        <div className="mt-8 p-3 w-full flex justify-center md:mt-20">
          <div className="mb-[10px] mt-[10px] ml-[10px] w-[60%]">
            <div className="border-b-2 border-border-color mb-8">
              {ghInfo && (
                <span className="font-bold text-xl">
                  {ghInfo.guestHouseName}
                </span>
              )}
            </div>
            <div className="mb-8">
              <EditReply type={isHost} id={123} />
            </div>

            {review && (
              <div className="mb-4">
                <span className="font-bold text-lg">
                  후기 {review.length}개
                </span>
              </div>
            )}

            {user &&
              review.map((ele, i) => {
                console.log(ele);
                return (
                  <div key={i} className="mb-2 p-2">
                    <DetailReview
                      type={'reviewPage'}
                      reviewComment={ele}
                      userId={user.memberId && user.memberId}
                    />
                  </div>
                );
              })}
          </div>
        </div>
      )}
    </>
  );
}
