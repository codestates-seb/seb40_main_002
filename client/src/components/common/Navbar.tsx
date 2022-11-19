import React from 'react';
import Logo from './Nav/Logo';
import Search from './Nav/Search';
import UserIcon from './Nav/UserIcon';

const Navbar = () => {
  return (
    <>
      <div className="flex justify-between items-center w-screen h-[80px] px-10 py-5 fixed top-0 bg-white">
        <Logo />
        <Search />
        <UserIcon />
      </div>
    </>
  );
};

export default Navbar;
