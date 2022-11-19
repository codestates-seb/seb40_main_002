import MapContainer from './MapContainer';
interface Porps {
  latitude: number;
  longitude: number;
  address?: string;
}
const GhLocation = ({ latitude, longitude, address = '제주도' }: Porps) => {
  return (
    <div className="border-b-[1px] border-[black]">
      <div>호스팅 지역</div>
      <div>
        <MapContainer latitude={latitude} longitude={longitude}></MapContainer>
      </div>
      <div className="mb-10px">{address}</div>
    </div>
  );
};

export default GhLocation;
