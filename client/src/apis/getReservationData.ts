import axios from 'axios';

export const getReservationData = async (url: string) => {
  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await axios.get(`/api/auth/members/reservations${url}`, {
      headers: {
        Authorization: accessToken,
      },
    });

    return response.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
