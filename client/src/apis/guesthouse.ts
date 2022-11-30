import axios, { AxiosResponse } from 'axios';
import React from 'react';
import { GuestHouseShort, RoomShort } from '../types/guesthouse';

export const getGuesthouseList = async (
  path: string,
  setTotalCount?: React.Dispatch<React.SetStateAction<number>>
) => {
  const data = await axios.get(`${path}`).then((res: AxiosResponse) => {
    // console.log(res.data);
    const totalCount = res.data.pageInfo.totalElements;
    if (setTotalCount) {
      if (totalCount) setTotalCount(totalCount);
      else setTotalCount(0);
    }
    const newGuesthouses: GuestHouseShort[] = res.data.data.map(
      (el: {
        guestHouseImage: string[];
        guestHouseName: string;
        guestHouseStar: number;
        guestHouseTag: string[];
        guestHouseId: number;
        rooms: RoomShort[];
      }) => {
        const price = Math.min(...el.rooms.map((room) => room.roomPrice));
        const guesthouse: GuestHouseShort = {
          imgSrc: `${process.env.REACT_APP_SERVER_URL}${el.guestHouseImage[0]}`,
          name: el.guestHouseName,
          price: price,
          star: el.guestHouseStar,
          tags: el.guestHouseTag,
          id: el.guestHouseId,
          rooms: el.rooms,
        };
        return guesthouse;
      }
    );
    return newGuesthouses;
  });
  return data;
};
