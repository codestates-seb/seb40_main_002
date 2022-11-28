import React, { useEffect } from 'react';
import { getuserParams } from '../libs/getuserParams';
import { useNavigate } from 'react-router-dom';
import { convertURLtoFile } from '../libs/srcToFile';
export default function UserData() {
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
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
      navigate('/');
    }
  }, []);
  return <></>;
}

// memberId memberEmail memberImageUrl
