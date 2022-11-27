interface Props {
  comment: string;
  type: string;
}

const RadioBtn = ({ comment, type }: Props) => {
  return (
    <span className="mr-[60px]">
      <span> {comment} </span>
      <input
        type={'radio'}
        name={type}
        className="appearance-none rounded-[50%] w-[13px] h-[13px] align-baseline border-2 border-#D9D9D9 ease-in duration-150 checked:bg-point-color checked:border-point-color"
      />
    </span>
  );
};

export default RadioBtn;
