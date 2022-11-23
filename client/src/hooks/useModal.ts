import { useState } from 'react';

function useModal(): [boolean, () => void] {
  const [isOpen, setIsOpen] = useState(false);

  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return [isOpen, openModalHandler];
}

export default useModal;
