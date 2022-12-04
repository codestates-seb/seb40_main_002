import { getPrettyDate } from './getPrettyDate';

function getTodayToTomorrow() {
  const today = new Date();
  const tomorrow = new Date(new Date().setDate(today.getDate() + 1));
  const prettyDate = {
    today: getPrettyDate(today),
    tomorrow: getPrettyDate(tomorrow),
  };
  return prettyDate;
}

export default getTodayToTomorrow;
