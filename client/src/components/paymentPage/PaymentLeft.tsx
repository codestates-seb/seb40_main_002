import React from 'react';

import PaymentContainer from './PaymentContainer';
import ReservationContainer from './ReservationContainer';
import { Props } from '../../types/payment';
import CommonBtn from '../common/CommonBtn/CommonBtn';

export default function PaymentLeft({ ghData }: { ghData: Props }) {
  return (
    <div className="flex flex-col w-[100%] justify-center items-center sm:mr-[110px] sm:w-[550px] sm:h-[570px] sm:justify-between">
      <ReservationContainer ghData={ghData} />
      <PaymentContainer ghData={ghData} />
      <CommonBtn btnSize="w-[270px] h-[55px]" text="계속" btnFs="text-lg" />
    </div>
  );
}
