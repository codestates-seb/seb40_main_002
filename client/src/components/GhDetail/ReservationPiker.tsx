import React, { useState, SetStateAction, Dispatch, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale';
interface SetDayProps {
  setStartDay: Dispatch<SetStateAction<string>>;
  setEndDay: Dispatch<SetStateAction<string>>;
}
const ReservationPiker = ({ setStartDay, setEndDay }: SetDayProps) => {
  const [dateRange, setDateRange] = useState([new Date(), null]);
  const [startDate, endDate] = dateRange;
  const dateToString = (date: Date) => {
    return (
      date?.getFullYear() +
      '-' +
      (date?.getMonth() + 1).toString().padStart(2, '0') +
      '-' +
      date?.getDate().toString().padStart(2, '0')
    );
  };
  useEffect(() => {
    if (startDate) setStartDay(dateToString(startDate));
    if (endDate) {
      setEndDay(dateToString(endDate));
    } else return;
  }, [dateRange]);

  return (
    <DatePicker
      inline
      dateFormat={'yyyy년 MM월 dd일'}
      placeholderText="날짜를 선택해주세요"
      locale={ko}
      selectsRange={true}
      startDate={startDate}
      endDate={endDate}
      monthsShown={2}
      onChange={(update) => {
        setDateRange(update);
      }}
      isClearable={true}
    />
  );
};

export default ReservationPiker;
