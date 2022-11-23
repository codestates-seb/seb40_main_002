import React, { Dispatch } from 'react';

interface Address {
  zipCode: string;
  guestHouseAddress: string;
  guestHouseLocation: string;
  detailAddress: string;
}

type AddressProps = {
  value?: string;
  access?: boolean;
  style?: string;
  placeholder?: string;
  name?: string;
  setAddress?: Dispatch<React.SetStateAction<Address>>;
};

export default function AddressInput({
  value,
  access,
  style,
  placeholder,
  setAddress,
  name,
}: AddressProps) {
  const changeEvent = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (setAddress && name) {
      setAddress((prev) => {
        return {
          ...prev,
          [e.target.name]: e.target.value,
        };
      });
    }
  };

  return (
    <div>
      <input
        className={`${style} text-font-color text-sm pl-1 focus:border-border-color focus:border focus:outline-none`}
        type="text"
        value={value}
        disabled={access}
        placeholder={placeholder}
        name={name}
        onChange={changeEvent}
      />
    </div>
  );
}
