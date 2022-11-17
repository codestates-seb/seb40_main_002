function Checkbox({
  isChecked,
  setIsChecked,
}: {
  isChecked: boolean;
  setIsChecked: React.Dispatch<React.SetStateAction<boolean>>;
}) {
  return (
    <input
      type="checkbox"
      className="checkbox checkbox-xs checkbox ml-auto mr-[4px] checkbox-primary"
      checked={isChecked}
      onChange={(e) => setIsChecked(e.target.checked)}
    />
  );
}

export default Checkbox;
