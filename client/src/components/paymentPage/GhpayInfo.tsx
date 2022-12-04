import React, { useEffect, useState } from 'react';
import { Props } from '../../types/payment';
export default function GhpayInfo({ ghData }: { ghData: Props }) {
  const [total, setTotal] = useState(0);

  useEffect(() => {
    if (
      ghData.payMoney &&
      ghData.filterDate[0] &&
      typeof ghData.filterDate[0] === 'number'
    ) {
      setTotal(Number(ghData.filterDate[0]) * Number(ghData.payMoney));
    }
  }, [ghData]);

  return (
    <div className="flex flex-col">
      <p className="text-lg font-bold">요금 세부 정보</p>
      <p className="text-lg ">
        ₩{' '}
        {ghData.payMoney &&
          ghData.payMoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}{' '}
        X {ghData.filterDate[0]} 박
      </p>
      <div className="flex justify-between mt-[20px]">
        <p className="text-lg font-bold">총 합계</p>
        <p className="text-lg font-bold">
          {' '}
          ₩ {total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원
        </p>
      </div>
    </div>
  );
}
