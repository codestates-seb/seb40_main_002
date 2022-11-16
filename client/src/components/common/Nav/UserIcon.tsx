import React, { useState } from 'react';
import { FaUserCircle } from 'react-icons/fa';
import UserIconModal from './UserIconModal';

const UserIcon = () => {
  const [isModalOpne, setIsModalOpen] = useState(false);

  const ModalHandler = () => {
    setIsModalOpen(!isModalOpne);
  };

  return (
    <>
      <div className="relative">
        <FaUserCircle size="50" onClick={ModalHandler} />
      </div>
      {isModalOpne && <UserIconModal />}
    </>
  );
};

export default UserIcon;
