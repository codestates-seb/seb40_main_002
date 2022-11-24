import { useState, Dispatch } from 'react';
import DaumPostcode from 'react-daum-postcode';
import { GrClose } from 'react-icons/gr';

interface Address {
  zipCode: string;
  guestHouseAddress: string;
  guestHouseLocation: string;
  detailAddress: string;
}

interface Props {
  isOpen?: boolean;
  openAddressHandler?: () => void;
  setAddress: Dispatch<React.SetStateAction<Address>>;
}

const Address = ({ isOpen, openAddressHandler, setAddress }: Props) => {
  const handleComplete = (data: any) => {
    const checkJeju = data.address;
    // 제주도가 아닐 시 창 종료
    if (!checkJeju.includes('제주특별자치도') && openAddressHandler) {
      alert('게스트하우스의 위치는 제주도만 가능합니다.');
      openAddressHandler();
      return;
    }

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

      if (openAddressHandler) {
        setAddress((prev) => {
          return {
            ...prev,
            zipCode,
            guestHouseAddress: fullAddress,
          };
        });
        openAddressHandler();
      }
    }
    // console.log(zipCode); //e.g.05405
    // console.log(fullAddress); // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
    // 건물 명까지 등록하려면 fullAddress 경위도 먼저 테스트를 해야할 것 같음.
  };

  return (
    <div>
      {isOpen && (
        <div className="flex justify-center items-center fixed top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]">
          <div className="w-[600px] h-fit p-[20px] bg-white rounded-[10px]">
            <div className="flex justify-end  mb-[10px]">
              <GrClose onClick={openAddressHandler} size={'20px'}></GrClose>
            </div>
            <DaumPostcode onComplete={handleComplete} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Address;
