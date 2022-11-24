import React, { useEffect, useState } from 'react';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import Address from '../Temporary/Address';
import AddressInput from './AddressInput';

declare global {
  interface Window {
    kakao: any;
  }
}

type AddressObj = {
  zipCode: string;
  guestHouseAddress: string;
  guestHouseLocation: string;
  detailAddress: string;
};

type SetAddress = {
  address: Address;
  setAddress: React.Dispatch<React.SetStateAction<AddressObj>>;
};

export default function AddressContainer({ address, setAddress }: SetAddress) {
  const [openAddressModal, setOpenAddressModal] = useState(false);

  // 상위로 올릴것 address

  useEffect(() => {
    if (address.guestHouseAddress) {
      const geocoder = new window.kakao.maps.services.Geocoder();
      const callback = function <M>(result: any, status: M) {
        if (status === kakao.maps.services.Status.OK) {
          setAddress((prev) => {
            return {
              ...prev,
              guestHouseLocation: `${result[0].y} , ${result[0].x}`,
            };
          });
        }
      };
      geocoder.addressSearch(address.guestHouseAddress, callback);
    }
  }, [address.guestHouseAddress]);

  return (
    <div className="flex flex-col w-full md:w-1/2 md:mr-10">
      <p className="font-bold text-lg mb-2.5">숙소 주소</p>
      <div className="flex flex-col w-full pb-1.5 h-28 flex-row  w-full md:flex-row md:items-center md:ml-3">
        <div className="pl-2.5 pr-2.5 flex flex-col h-full md:w-full ">
          <div className="flex items-center mb-1 ">
            <AddressInput
              value={address.zipCode}
              style="min-w-60 mr-3  min-h-6 bg-border-color rounded-btnRadius"
              access={true}
            />
            <CommonBtn
              btnSize="w-20 h-full "
              btnBg="bg-white text-font-color border-border-color border delay-150 transition ease-in-out duration-500 hover:bg-white"
              text="우편 번호"
              btnHandler={() => {
                setOpenAddressModal(!openAddressModal);
              }}
            />
          </div>
          <AddressInput
            access={true}
            value={address.guestHouseAddress}
            style="min-w-60 my-1 w-full min-h-6 bg-border-color rounded-btnRadius"
          />
          <AddressInput
            style="min-w-60 my-1 w-full min-h-6 border border-border-color rounded-btnRadius"
            placeholder="상세 주소를 입력해주세요."
            value={address.detailAddress}
            name={'detailAddress'}
            setAddress={setAddress}
          />
          <Address
            isOpen={openAddressModal}
            openAddressHandler={() => {
              setOpenAddressModal(!openAddressModal);
            }}
            setAddress={setAddress}
          />
        </div>
      </div>
    </div>
  );
}
