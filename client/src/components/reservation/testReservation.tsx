import React, { useState } from 'react';
import ReservationInfo from './ReservationInfo';
export interface Iprops {
  reservationData: {
    guestHouserName: string;
    guestHouseRoomStart: string;
    guestHouseRoomEnd: string;
    guestHousePrice: number;
    guestHouseDays: number;
  };
}
function TestReservation() {
  const [reservationData, setData] = useState<Iprops['reservationData']>({
    guestHouserName: '팜파스트호텔',
    guestHouseRoomStart: '2022-11-14',
    guestHouseRoomEnd: '2022-11-15',
    guestHousePrice: 60000,
    guestHouseDays: 3,
  });
  return (
    <div className="flex items-center justify-center">
      <ReservationInfo {...reservationData} />
    </div>
  );
}
export default TestReservation;
