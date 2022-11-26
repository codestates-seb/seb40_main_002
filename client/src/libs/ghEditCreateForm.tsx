type GuesthouseDtoType = {
  guestHouseName: string;
  guestHouseLocation: string;
  guestHouseAddress: string[];
  guestHousePhone: string;
  guestHouseTag: string[];
  guestHouseInfo: string;
  guestHouseDetails: boolean[];
  cityId: number;
};

type RoomImgType = File[];
type RoomDtoDetail = {
  roomId?: number | null;
  roomInfo: string;
  roomName: string;
  roomPrice: number;
};
type RoomDtoType = {
  roomDto: RoomDtoDetail[];
};

type DtoProps = {
  guest_house_dto: GuesthouseDtoType;
  roomImg: RoomImgType;
  roomDto: RoomDtoType;
  imgFiles: File[];
};

type editDtoProps = {
  guest_house_dto: GuesthouseDtoType;
  roomImg: RoomImgType;
  roomDto: RoomDtoType;
  imgFiles: File[];
  newRoomImage: File[];
};

const stringDto = <T,>(formData: FormData, appendName: string, data: T) => {
  const stringDto = JSON.stringify(data);
  formData.append(
    appendName,
    new Blob([stringDto], { type: 'application/json' })
  );
};

const imgDto = (formData: FormData, appendName: string, data: File[]) => {
  data.forEach((el) => {
    formData.append(appendName, el);
  });
};

export const ghCreateForm = ({
  guest_house_dto,
  roomImg,
  roomDto,
  imgFiles,
}: DtoProps) => {
  const formData = new FormData();
  stringDto(formData, 'guest-house-dto', guest_house_dto);
  stringDto(formData, 'room-dto', roomDto);
  imgDto(formData, 'guestHouseImage', imgFiles);
  imgDto(formData, 'room-image', roomImg);

  return formData;
};

export const ghEditForm = ({
  guest_house_dto,
  roomImg,
  roomDto,
  newRoomImage,
  imgFiles,
}: editDtoProps) => {
  const formData = new FormData();

  stringDto(formData, 'guest-house-dto', guest_house_dto);
  stringDto(formData, 'room-dto', roomDto);
  imgDto(formData, 'guestHouseImage', imgFiles);
  imgDto(formData, 'room-image', roomImg);
  imgDto(formData, 'new-room-image', newRoomImage);

  return formData;
};
