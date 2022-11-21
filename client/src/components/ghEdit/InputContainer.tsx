import React from 'react';

type InputContainer = {
  text: string;
  value: string | number;
  name: string;
  placeholder: string;

  changeFunc: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

export default function InputContainer({
  text,
  value,
  changeFunc,
  name,
  placeholder,
}: InputContainer) {
  return (
    <div className="flex flex-col items-start md:flex-row">
      <label htmlFor={name} className=" mr-3 w-24">
        {text}
      </label>

      <input
        type="text"
        className="border border-border-color mb-3 md:mb-0 rounded-btnRadius w-8/12 pl-[5px] focus:border-border-color focus : border focus:outline-none"
        id={name}
        name={name}
        value={value}
        onChange={(e) => changeFunc(e)}
        placeholder={placeholder}
      />
    </div>
  );
}
