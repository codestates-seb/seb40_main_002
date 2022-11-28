import React, { useEffect } from 'react';
import { getuserParams } from '../libs/getuserParams';
import { useNavigate } from 'react-router-dom';
import { convertURLtoFile } from '../libs/srcToFile';
export default function UserData() {
  const navigate = useNavigate();
  useEffect(() => {
    const url = new URL(window.location.href);
    const userData = url.searchParams.get('memberId');
    if (userData) {
      const user = getuserParams(url, [
        'memberId',
        'memberEmail',
        'memberImageUrl',
      ]);
      sessionStorage.setItem('userData', JSON.stringify(user));
      navigate('/register');
    }
  }, []);
  return <></>;
}

// memberId memberEmail memberImageUrl
