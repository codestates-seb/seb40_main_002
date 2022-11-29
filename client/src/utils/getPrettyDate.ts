export function getPrettyDate(date: Date) {
  const offset = date.getTimezoneOffset() * 60000; // ms단위라 60000곱해줌
  const dateOffset = new Date(date.getTime() - offset);
  const prettyDate = dateOffset.toISOString().substring(0, 10);
  return prettyDate;
}
