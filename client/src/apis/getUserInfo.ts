import axios from 'axios';

export const getUserInfo = async (token: string | null) => {
  try {
    const response = await axios.get(`/api/auth/members/`, {
      headers: { Authorization: `${token}` },
    });
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
