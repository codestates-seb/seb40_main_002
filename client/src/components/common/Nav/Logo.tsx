import React from 'react';
import { GiTangerine } from 'react-icons/gi';
import { Link } from 'react-router-dom';

const Logo = () => {
  return (
    <>
      <Link to={'/'}>
        <GiTangerine size={50} color={'FFA237'} />
      </Link>
    </>
  );
};

export default Logo;
