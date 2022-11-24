import MapContainer from './MapContainer';
interface LocationPorps {
  ghLocation: string[];
  address?: string;
}
const GhLocation = ({
  ghLocation,
  address = '제주 제주시 애월읍 일주서로 6158',
}: LocationPorps) => {
  return (
    <div className="border-b-[1px] border-[black]">
      <div className="mb-[20px] font-bold">호스팅 지역</div>
      <div>
        <MapContainer
          latitude={Number(ghLocation[0])}
          longitude={Number(ghLocation[1])}
        />
      </div>
      <div className="my-[20px] text-lg">{address}</div>
    </div>
  );
};

export default GhLocation;
