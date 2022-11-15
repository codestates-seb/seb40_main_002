import { useEffect } from 'react';

export const useFetch = () => {
  const a = 'b';
  useEffect(() => {
    console.log('hi');
  }, []);
  return a;
};
