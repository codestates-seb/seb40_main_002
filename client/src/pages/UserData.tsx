import React, { useEffect } from 'react';
import { getuserParams } from '../libs/getuserParams';
import { useNavigate } from 'react-router-dom';
import { convertURLtoFile } from '../libs/srcToFile';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { setUser } from '../store/reducer/user';
import { User } from '../types/user';

export default function UserData() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    const url = new URL(window.location.href);
    const userData = url.searchParams.get('memberId');
    const accessToken = url.searchParams.get('access_token');
    const refreshToken = url.searchParams.get('refresh_token');

    if (userData) {
      const user = getuserParams(url, [
        'memberId',
        'memberEmail',
        'memberImageUrl',
      ]);
      sessionStorage.setItem('userData', JSON.stringify(user));
      navigate('/register');
    }
    if (accessToken && refreshToken) {
      console.log(accessToken);
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
      axios
        .get(`/api/auth/members`, {
          headers: {
            Authorization: accessToken,
            'ngrok-skip-browser-warning': 'any',
          },
        })
        .then((res) => {
          console.log(res);
          // user 정보 저장하기
          dispatch(setUser(res.data.data as User));
        })
        .catch((err) => console.log(err));
      navigate('/');
    }
  }, []);
  return <></>;
}

// memberId memberEmail memberImageUrl
