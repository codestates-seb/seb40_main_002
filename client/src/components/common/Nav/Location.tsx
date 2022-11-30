import React, { useEffect, useState } from 'react';
import SelectLocation from './SelectLocation';
import Tag from '../Tag';
import axios from 'axios';

const Location = () => {
  const [isLocationOpen, setIsLocationOpen] = useState(false);
  const [locations, setLocations] = useState([]);
  const [selects, setSelects] = useState([false]);
  useEffect(() => {
    axios
      .get(`/api/city`)
      .then((res) => {
        // console.log(res);
        setLocations(res.data.map((el: { cityName: string }) => el.cityName));
      })
      .catch((err) => console.log(err));
  }, []);

  useEffect(() => {
    // console.log('selects:', selects);
    setSelects(new Array(locations.length).fill(false));
  }, [locations]);

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
