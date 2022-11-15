import React from 'react';
import Logo from './Logo';
import Search from './Search';
import UserIcon from './UserIcon';

const Navbar = () => {
  return (
    <>
      <div className="flex justify-between items-center mx-14 h-nav">
        <Logo />
        <Search />
        <UserIcon />
      </div>
    </>
  );
};

export default Navbar;
