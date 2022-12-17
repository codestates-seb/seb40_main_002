import React, { useEffect, useState } from 'react';
import SelectLocation from './SelectLocation';
import Tag from '../Tag';
import axios from 'axios';

const Location = ({
  setCityId,
}: {
  setCityId: React.Dispatch<React.SetStateAction<string>>;
}) => {
  const [isLocationOpen, setIsLocationOpen] = useState(false);
  const [locations, setLocations] = useState([]);
  const [locationsId, setLocationsId] = useState([]);
  const [selects, setSelects] = useState<boolean[]>([]);
  useEffect(() => {
    axios
      .get(`/api/city`)
      .then((res) => {
        setLocationsId(res.data.map((el: { cityId: number }) => el.cityId));
        setLocations(res.data.map((el: { cityName: string }) => el.cityName));
      })
      .catch((err) => console.log(err));
  }, []);

  useEffect(() => {
    setSelects(new Array(locations.length).fill(false));
  }, [locations]);

  useEffect(() => {
    for (let i = 0; i < selects.length; i++) {
      if (selects[i]) {
        setCityId(locationsId[i]);
        break;
      }
    }
  }, [selects]);

  const modalHandler = () => {
    setIsLocationOpen(!isLocationOpen);
  };
  const onlyTrue = selects.filter((isSelets) => isSelets && true);

  return (
    <>
      <div className="relative flex" onClick={modalHandler}>
        {onlyTrue.length === 0 ? (
          <button className="w-fit"> 지역을 선택해주세요 </button>
        ) : (
          selects.map(
            (type: boolean, idx) =>
              type && <Tag key={idx} name={locations[idx]} />
          )
        )}
      </div>
      {isLocationOpen && (
        <SelectLocation
          locations={locations}
          selects={selects}
          setSelects={setSelects}
          modalHandler={modalHandler}
          onlyTrue={onlyTrue}
        />
      )}
    </>
  );
};

export default Location;
