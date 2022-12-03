import { useDispatch } from 'react-redux';
import axios from 'axios';

const baseUrl = process.env.REACT_APP_SERVER_URL;
const Api = axios.create({
  baseURL: baseUrl,
});

Api.interceptors.request.use(function (config: any) {
  const access_token = localStorage.getItem('accessToken');
  const refresh_token = localStorage.getItem('refreshToken');

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

Api.interceptors.response.use(
  function (response) {
    return response;
  },
  async function (err) {
    const originConfig = err.config;
    if (err.response && err.response.status === 401) {
      const accessToken = originConfig.headers['Authorization'];
      const refreshToken = originConfig.headers['refreshToken'];

      try {
        const data = await axios({
          url: `${baseUrl}/api/token`,
          method: 'post',
          data: {
            refreshToken: refreshToken,
          },
        });

        if (data) {
          localStorage.setItem(
            'accessToken',
            JSON.stringify(data.data.data.accessToken)
          );
        }
        return await Api.request(originConfig);
      } catch (err) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        sessionStorage.removeItem('persist:root'); //
      }
      return Promise.reject(err);
    } else {
      return Promise.reject(err);
    }
  }
);

export default Api;
