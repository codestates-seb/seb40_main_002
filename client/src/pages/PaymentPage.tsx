import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

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
  };

  const [ghData, setGhdata] = useState<Props>({
    date: [],
    ghname: null,
    payMoney: null,
  });

  useEffect(() => {
    // api 요청을 적어주세용 로직 수정 필요
    if (!ghData.ghname) {
      setGhdata({ ...ghData, ...ghDummy });
    }

    if (ghData.ghname && ghData.date.length > 0) {
      const data = dateCheck([...ghData.date]);
      setGhdata(() => {
        return { ...ghData, date: [...data] };
      });
    }
  }, [ghData.ghname]);

  return (
    <div
      className={`flex flex-col w-full justify-center items-center p-[10px] 
        sm:justify-center sm:items-center`}
      // 최소 764이상일때 sm들이 실행됨
    >
      <div className="flex flex-col  w-full sm:flex-row-reverse sm:justify-center sm:items-end">
        <PaymentRight ghData={ghData} />
        <PaymentLeft ghData={ghData} />
      </div>
    </div>
  );
}
