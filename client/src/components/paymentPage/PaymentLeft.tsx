import React from 'react';

import PaymentContainer from './PaymentContainer';
import ReservationContainer from './ReservationContainer';
import { PropsMix } from '../../types/payment';

export default function PaymentLeft({
  ghData,
  paymentRole,
  setPaymentRole,
}: PropsMix) {
  return (
    <div className="flex flex-col w-[100%] justify-center items-center md:mr-[110px] md:w-[550px] md:h-[440px] md:justify-end">
      <ReservationContainer ghData={ghData} />
      <PaymentContainer
        paymentRole={paymentRole}
        setPaymentRole={setPaymentRole}
      />
    </div>
  );
}
