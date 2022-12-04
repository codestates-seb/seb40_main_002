import axios from 'axios';

export const login = async () => {
  const data = axios.get('localhost:3000');
  return data;
};
