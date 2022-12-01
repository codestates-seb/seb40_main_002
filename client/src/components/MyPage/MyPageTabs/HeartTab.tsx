import GuesthouseList from '../../Main/GuesthouseList';
import { useEffect, useState } from 'react';
import { getHeartData } from '../../../apis/getHeartData';
import { GuestHouseShort } from '../../../types/guesthouse';
import MyPagePagination from './MyPagePagination';
import { HeartDataType, DataType } from '../../../types/MyPage';

function HeartTab() {
  const [pageData, setPageData] = useState<DataType>();
  const [heartData, setHeartData] = useState<Array<GuestHouseShort>>();
  const [page, setPage] = useState<number | null>(1);
  useEffect(() => {
    const data = async () => {
      const data = await getHeartData(`?page=${page}&size=6`);
      setHeartData(
        data.data.map((el: HeartDataType) => {
          return {
            imgSrc: `${process.env.REACT_APP_SERVER_URL}${el.guestHouse.mainImageUrl}`,
            name: el.guestHouse.guestHouseName,
            price: el.guestHouse.minRoomPrice,
            star: el.guestHouse.guestHouseStar,
            tags: el.guestHouse.guestHouseTag,
            id: el.guestHouse.guestHouseId,
          };
        })
      );
      setPageData(data);
    };
    data();
  }, [page]);

  return (
    <div className=" mb-[100px]">
      {pageData && heartData && heartData.length > 0 ? (
        <>
          <GuesthouseList guesthouses={heartData} isMyPage={true} />
          <div className="text-center">
            <MyPagePagination
              totalPages={pageData.pageInfo.totalPages}
              page={page}
              setPage={setPage}
            />
          </div>
        </>
      ) : (
        <div className="text-center">찜한 숙소가 없습니다</div>
      )}
    </div>
  );
}

export default HeartTab;
