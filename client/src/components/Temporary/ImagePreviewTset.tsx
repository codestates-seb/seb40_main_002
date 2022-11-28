import { useState } from 'react';
const ImagePreview = () => {
  const [preview, setPreview] = useState<File | null>(null);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files) return;
    setPreview(event.target.files[0]);
  };
  const removeSelectedImage = () => {
    setPreview(null);
  };
  return (
    <div>
      <div>
        <img
          style={{ width: '300px', objectFit: 'cover', borderRadius: '4px' }}
          src={preview === null ? '' : URL.createObjectURL(preview)}
          alt=""
        />
      </div>
      <form>
        <input type="file" id="file" onChange={handleChange} multiple />
      </form>
      {preview && <button onClick={removeSelectedImage}>이미지 삭제</button>}
    </div>
  );
};

export default ImagePreview;
