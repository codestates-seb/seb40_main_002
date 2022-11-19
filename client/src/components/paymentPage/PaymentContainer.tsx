import React from 'react';

import Checkbox from '../common/Checkbox';
import { PaymentRoleProp } from '../../types/payment';

export default function PaymentContainer({
  paymentRole,
  setPaymentRole,
}: PaymentRoleProp) {
  return (
    <div className="w-full">
      <div className="flex flex-col items-center">
        <div className="font-bold text-[22px] self-start">결제 방식 선택</div>
        <div className="p-[20px] w-full">
          <div className="border-x border-t h-[45px] flex items-center pl-[8px] rounded-x-btnRadius rounded-t-[5px]">
            <p>카카오페이 결제</p>
            <Checkbox
              isChecked={paymentRole === '카카오페이'}
              setRadio={setPaymentRole}
              name="카카오페이"
            />
          </div>
          <div className="border h-[45px] flex items-center pl-[8px] rounded-x-btnRadius rounded-b-[5px]">
            <p>현금 결제</p>
            <Checkbox
              isChecked={paymentRole === '현금결제'}
              setRadio={setPaymentRole}
              name="현금결제"
            />
          </div>
        </div>
      </div>
    </div>
  );
}
