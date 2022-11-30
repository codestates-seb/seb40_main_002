import axios, { AxiosResponse } from 'axios';
import React from 'react';
import { GuestHouseShort, RoomShort } from '../types/guesthouse';

const API = 'http://3.37.58.81:8080';

export const getGuesthouseList = async (
  path: string,
  setTotalCount: React.Dispatch<React.SetStateAction<number>>
) => {
  const data = await axios.get(`${path}`).then((res: AxiosResponse) => {
    // console.log(res.data);
    const totalCount = res.data.pageInfo.totalElements;
    if (totalCount) setTotalCount(totalCount);
    else setTotalCount(0);
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
          imgSrc: `${API}${el.guestHouseImage[0]}`,
          // imgSrc:
          // 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/PhodopusSungorus_2.jpg/640px-PhodopusSungorus_2.jpg',
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
