import Api from './index';

export const getUser = async () => {
  const response = await Api.get(`/api/auth/members/`);
  return response.data.data;
};
