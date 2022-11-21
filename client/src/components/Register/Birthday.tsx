import { useState } from 'react';

const Birthday = () => {
  // 상태를 위로 올려줘야함
  const [form, setForm] = useState({
    year: new Date().getFullYear(),
    month: '01',
    day: '01',
  });

  const now = new Date();

  const years = [];
  for (let y = now.getFullYear(); y >= 1930; y -= 1) {
    years.push(y);
  }

  const month = [];
  for (let m = 1; m <= 12; m++) {
    if (m < 10) {
      month.push('0' + m.toString());
    } else {
      month.push(m.toString());
    }
  }

  const days = [];
  const date = new Date(form.year, Number(form.month), 0).getDate();
  for (let d = 1; d <= date; d++) {
    if (d < 10) {
      days.push('0' + d.toString());
    } else {
      days.push(d.toString());
    }
  }

  return (
    <>
      <select
        value={form.year}
        onChange={(e) => setForm({ ...form, year: Number(e.target.value) })}
      >
        {years.map((item) => (
          <option className="h-[30px]" value={item} key={item}>
            {item}
          </option>
        ))}
      </select>

      <select
        value={form.month}
        onChange={(e) => setForm({ ...form, month: e.target.value })}
      >
        {month.map((item) => (
          <option value={item} key={item}>
            {item}
          </option>
        ))}
      </select>
      <select
        value={form.day}
        onChange={(e) => setForm({ ...form, day: e.target.value })}
      >
        {days.map((item) => (
          <option value={item} key={item}>
            {item}
          </option>
        ))}
      </select>
    </>
  );
};

export default Birthday;
