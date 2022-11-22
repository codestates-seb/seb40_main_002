import React, { useRef } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import { TiDelete } from 'react-icons/ti';

type ImgContainer = {
  imgFiles: File[];
  setImgFiles: React.Dispatch<React.SetStateAction<File[]>>;
};

export default function ImageContainer({
  imgFiles,
  setImgFiles,
}: ImgContainer) {
  // 6번째 줄만 올려서 관리

  const imageRef = useRef<HTMLInputElement>(null);

  const cilckInput = () => {
    if (imageRef.current) {
      imageRef.current.click();
    }
  };

  const selectImg = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedfiles = e.target.files;
    if (selectedfiles) {
      const uploadfiles = Array.from(selectedfiles);
      const filesLength = uploadfiles.length + imgFiles.length;
      if (filesLength < 11) {
        setImgFiles([...imgFiles, ...uploadfiles]);
      } else {
        alert('이미지는 10장 이하만 등록할 수 있습니다.');
      }
    }
  };

  const deleteImg = (deleteImg: File) => {
    const findSameImg = imgFiles.filter((img) => img.name !== deleteImg.name);
    setImgFiles([...findSameImg]);
  };

  return (
    <div className=" md:flex md:flex-col ">
      <p className="font-bold text-lg mb-2.5 md:mb-7">
        대표 사진 선택(최대 10장)
      </p>
      <div
        className="flex justify-center cursor-pointer items-center w-full h-60 rounded-CommentRadius mb-3 md:mb-8 md:rounded-ImgRadius bg-border-color md:w-2/3 md:h-96 md:self-center"
        onClick={cilckInput}
      >
        <BsPlusLg className="w-7 h-7 text-font-color md:h-16 md:w-16" />
      </div>

      {imgFiles.length !== 0 && (
        <div className="w-full self-center flex mb-3 justify-between md:justify-between flex-wrap  md:w-2/3 md:mb-5 ">
          {imgFiles.map((img, idx) => (
            <div className="flex flex-col items-center" key={idx}>
              <div className="w-20 h-20  border-4 rounded-CommentRadius md:w-44 md:h-44">
                <img
                  src={URL.createObjectURL(img)}
                  className=" w-full h-full object-contain"
                />
              </div>
              <TiDelete
                className="h-4 w-4 cursor-pointer mt-1 md:h-5 md:w-5 "
                onClick={() => deleteImg(img)}
              />
            </div>
          ))}
        </div>
      )}

      <input
        type="file"
        multiple
        className="hidden"
        ref={imageRef}
        accept="image/*"
        onChange={selectImg}
      />
    </div>
  );
}
