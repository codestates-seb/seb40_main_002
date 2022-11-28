import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale';
import { DateRange } from '../../../types/search';

const DatePicker1 = ({ dateRange }: { dateRange: DateRange }) => {
  // const [dateRange, setDateRange] = useState([new Date(), null]);
  // const [startDate, endDate] = dateRange;
  return (
    <div className="flex justify-center items-center text-base w-[275px] h-[50px]">
      <DatePicker
        className="w-[250px]"
        dateFormat={'yyyy년 MM월 dd일'}
        placeholderText="날짜를 선택해주세요"
        locale={ko}
        selectsRange={true}
        startDate={dateRange.startDate}
        endDate={dateRange.endDate}
        onChange={(update: any) => {
          dateRange.setDateRange(update);
        }}
        isClearable={true}
      />
    </div>
  );
};

export default DatePicker1;
