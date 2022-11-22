import MapContainer from './MapContainer';
interface LocationPorps {
  latitude: number;
  longitude: number;
  address?: string;
}
const GhLocation = ({
  latitude,
  longitude,
  address = '제주 제주시 애월읍 일주서로 6158',
}: LocationPorps) => {
  return (
    <div className="border-b-[1px] border-[black]">
      <div className="mb-[20px] font-bold">호스팅 지역</div>
      <div>
        <MapContainer latitude={latitude} longitude={longitude} />
      </div>
      <div className="my-[20px] text-lg">{address}</div>
    </div>
  );
};

export default GhLocation;
