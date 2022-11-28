import React from 'react';
import { Props } from '../../types/payment';
import GhInfo from './GhInfo';
import GhpayInfo from './GhpayInfo';

export default function PaymentRight({ ghData }: { ghData: Props }) {
  return (
    <div className="mb-[10px] border p-[10px] border-border-color rounded-CommentRadius md:p-[30px] md:w-[550px]">
      <GhInfo ghData={ghData} />
      <GhpayInfo ghData={ghData} />
    </div>
  );
}
