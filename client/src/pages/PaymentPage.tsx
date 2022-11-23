import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';

import PaymentLeft from '../components/paymentPage/PaymentLeft';
import PaymentRight from '../components/paymentPage/PaymentRight';
import dateCheck from '../libs/dateCheck';
import { Props } from '../types/payment';

// 제 생각엔 결제 페이지로 넘길 때 route 경로에 규칙을 정해줘서 해당 규칙을 추출하고 해당 규칙으로 api 연결하면 될거같기도 하고,,,
// 아니면 상태 관리로 클릭한 페이지 진입 시 데이터 정제해주는 방법도 괜찮을 거 같습니다(api 요청을 줄인다면)
//[Thu Nov 17 2022 22:40:06 GMT+0900 (한국 표준시), Sat Nov 19 2022 00:00:00 GMT+0900 (한국 표준시)]
export default function PaymentPage() {
  const ghDummy = {
    date: [
      'Thu Nov 17 2022 22:40:06 GMT+0900 (한국 표준시)',
      'Sat Nov 19 2022 00:00:00 GMT+0900 (한국 표준시)',
    ],
    ghname: '팜파스호텔 제주',
    payMoney: 30000,
    url: 'http://gravatar.com/avatar/1',
    reply: ['정말 재밌어요', '행복했어요'],
    ratedScore: 4,
  };

  const [ghData, setGhdata] = useState<Props>({
    date: [],
    ghname: null,
    payMoney: null,
    url: null,
    reply: null,
    ratedScore: null,
    filterDate: [],
    totalMoney: null,
  });

  const [paymentRole, setPaymentRole] = useState<string>('카카오페이');

  useEffect(() => {
    // api 요청을 적어주세용 로직 수정 필요
    if (!ghData.ghname) {
      setGhdata({ ...ghData, ...ghDummy });
    }

    if (ghData.ghname && ghData.date.length > 0) {
      const data = dateCheck([...ghData.date]);
      setGhdata((prev) => {
        return {
          ...prev,
          filterDate: [...data],
          totalMoney: Number(ghData.filterDate[0]) * Number(ghData.payMoney),
        };
      });
    }
  }, [ghData.ghname]);
  const reservationHandler = () => {
    if (paymentRole === '현금결제') return alert('준비 중입니다.');

    // api로 예약 데이터를 전송해야함
    const reservationData = {
      ...ghData,
      paymentRole,
    };
    console.log(reservationData);
  };
  return (
    <div
      className={`flex flex-col w-full justify-center items-center p-[10px] 
      md:justify-center md:items-center`}
      // 최소 764이상일때 sm들이 실행됨
    >
      <div className="flex flex-col  w-full md:flex-row md:justify-center md:items-end">
        <PaymentLeft
          ghData={ghData}
          paymentRole={paymentRole}
          setPaymentRole={setPaymentRole}
        />
        <PaymentRight ghData={ghData} />
      </div>
      <div className="w-full justify-center flex">
        <CommonBtn
          btnSize="mt-[30px] self-start w-[200px] h-[60px] md:w-[270px] md:h-[55px] md:mt-[70px] "
          text="계속"
          btnFs="text-lg"
          btnHandler={reservationHandler}
        />
      </div>
    </div>
  );
}
