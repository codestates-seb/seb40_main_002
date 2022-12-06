import React, { useState } from 'react';
import DatePicker1 from './DatePicker';
import Keyword_Input from './Keyword_input';
import Location from './Location';
import { FaSearch } from 'react-icons/fa';
import TagSearch from './TagSearch';
import { useNavigate } from 'react-router-dom';
import { DateRange } from '../../../types/search';
import { getPrettyDate } from '../../../utils/getPrettyDate';
import getTodayToTomorrow from '../../../utils/getTodayToTomorrow';

const Search = () => {
  const navigate = useNavigate();
  // const [keyword, setKeyword] = useState('');
  const [cityId, setCityId] = useState(''); // cityId 정해지면 넘겨주기
  const [tags, setTags] = useState<string[]>([]);
  const [dateRange, setDateRange] = useState([new Date(), null]);
  const [startDate, endDate] = dateRange;

  const search = () => {
    // 검색 api 들어갈 자리
    // cityId, start, end, tag
    let start, end;
    if (dateRange[0] && dateRange[1]) {
      // navigate(
      //   `/search?cityId=${cityId}&start=${getPrettyDate(
      //     dateRange[0]
      //   )}&end=${getPrettyDate(dateRange[1])}&tag=${tags.join('-')}`
      // );
      // location.reload(); // 임시
      start = getPrettyDate(dateRange[0]);
      end = getPrettyDate(dateRange[1]);
    } else {
      const todayToTomorrow = getTodayToTomorrow();
      start = todayToTomorrow.today;
      end = todayToTomorrow.tomorrow;
    }
    navigate(
      `/search?cityId=${cityId}&start=${start}&end=${end}&tag=${tags.join('-')}`
    );
    location.reload(); // 임시
  };

  return (
    <>
      <div className="flex justify-between px-3 items-center text-base w-[700px] font-semibold h-[50px] border-solid border-4 border-point-color rounded-[30px]">
        <Location setCityId={setCityId} />
        <DatePicker1
          dateRange={
            {
              startDate: startDate,
              endDate: endDate,
              setDateRange: setDateRange,
            } as DateRange
          }
        />
        {/* <Keyword_Input keyword={keyword} setkeyword={setKeyword} /> */}
        <TagSearch tags={tags} setTags={setTags} />
        <FaSearch size={25} className="ml-2 mr-2" onClick={search} />
      </div>
    </>
  );
};

export default Search;
