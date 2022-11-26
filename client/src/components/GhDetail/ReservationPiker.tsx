import React, { useState, SetStateAction, Dispatch, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale';
interface SetDayProps {
  startDay: string;
  endDay: string;
  setStartDay: Dispatch<SetStateAction<string>>;
  setEndDay: Dispatch<SetStateAction<string>>;
  setDayCal: Dispatch<SetStateAction<number>>;
}

const ReservationPiker = ({
  startDay,
  endDay,
  setStartDay,
  setEndDay,
  setDayCal,
}: SetDayProps) => {
  const [dateRange, setDateRange] = useState<Array<Date | null>>([
    new Date(startDay),
    new Date(endDay),
  ]);
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
    if (startDate) {
      setStartDay(dateToString(startDate));
      setEndDay('');
    }
    if (endDate && startDate) {
      setEndDay(dateToString(endDate));
      setDayCal(
        (Number(new Date(endDate)) - Number(new Date(startDate))) / 86400000
      );
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
      minDate={new Date()}
      maxDate={new Date(new Date().getFullYear(), 20)}
      onChange={(update) => {
        setDateRange(update);
      }}
      isClearable={true}
    />
  );
};

export default ReservationPiker;
