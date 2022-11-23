import { useState } from 'react';
import DaumPostcode from 'react-daum-postcode';
import { GrClose } from 'react-icons/gr';
interface Props {
  isOpen?: boolean;
  openAddressHandler?: () => void;
}
const Address = ({ isOpen, openAddressHandler }: Props) => {
  const handleComplete = (data: any) => {
    let fullAddress = data.address; //주소
    const zipCode = data.zonecode; //우편번호
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }
    console.log(zipCode); //e.g.05405
    console.log(fullAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
  };

  const handleSearch = (data: { q: string; count: number }) => {
    console.log(data); //검색어
  };

  return (
    <div>
      {isOpen && (
        <div className="flex justify-center items-center fixed top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]">
          <div className="w-[600px] h-fit p-[20px] bg-white rounded-[10px]">
            <div className="flex justify-end  mb-[10px]">
              <GrClose
                onClick={() => console.log('hello')}
                size={'20px'}
              ></GrClose>
            </div>
            <DaumPostcode onComplete={handleComplete} onSearch={handleSearch} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Address;
