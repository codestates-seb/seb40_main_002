import axios from 'axios';

const baseUrl = process.env.REACT_APP_SERVER_URL;
const Api = axios.create({
  baseURL: baseUrl,
});
// 요청을 가로채는 인터셉터 (필요할때만 토큰을 싣기 위해)
Api.interceptors.request.use(function (config: any) {
  const access_token = localStorage.getItem('accessToken');
  const refresh_token = localStorage.getItem('refreshToken');
  console.log('요청');
  if (!access_token && !refresh_token) {
    config.headers['Authorization'] = null;
    config.headers['refreshToken'] = null;
    return config;
  }
  if (config.headers && access_token && refresh_token) {
    config.headers['Authorization'] = `${access_token}`;
    config.headers['refreshToken'] = `${refresh_token}`;
  }
  return config;
});
// 답변을 가로채는 인터셉터
Api.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (err) {
    const originConfig = err.config;
    if (err?.response?.status === 401) {
      const accessToken = originConfig.headers['Authorization'];
      const refreshToken = localStorage.getItem('refreshToken');
      originConfig.sent = true;

      try {
        const data = await axios.post(
          '/api/token',
          {},
          {
            headers: {
              RefreshToken: refreshToken,
              'Content-Type': '',
            },
          }
        );

        if (data.headers.authorization) {
          localStorage.setItem('accessToken', data.headers.authorization);
          originConfig.headers.Authorization = data.headers.authorization;
          return Api(originConfig);
        } else {
          localStorage.removeItem('refreshToken');
          localStorage.removeItem('accessToken');
          return;
        }
      } catch (err) {
        console.log('토큰 인증 오류 발생');
      }
      return Promise.reject(err);
    } else {
      return Promise.reject(err);
    }
  }
);

export default Api;
