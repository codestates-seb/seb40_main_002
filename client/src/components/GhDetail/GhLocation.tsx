import MapContainer from './MapContainer';
interface Porps {
  latitude: number;
  longitude: number;
}
const GhLocation = ({ latitude, longitude }: Porps) => {
  return (
    <div className="border-b-[1px] border-[black]">
      <div>호스팅 지역</div>
      <div>
        <MapContainer latitude={latitude} longitude={longitude}></MapContainer>
      </div>
      <div className="mb-10px">제주특별자치도 제주시 첨단로 242</div>
    </div>
  );
};

export default GhLocation;
