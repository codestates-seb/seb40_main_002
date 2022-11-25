import useModal from '../../../hooks/useModal';
import Tag from '../Tag';
import TagSelect from '../TagSelectModal/TagSelect';
const testTags = [
  '오션뷰',
  '노을',
  '대리석',
  '숲',
  '태그1',
  '태그2',
  '태그3',
  '태그4',
  '태그5',
  '태그6',
  '태그7',
];
function TagSearch({
  tags,
  setTags,
}: {
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
}) {
  const [isOpen, openModalHandler] = useModal();
  return (
    <div className="flex" onClick={openModalHandler}>
      {tags.length > 0 || (
        <button onClick={openModalHandler}>태그를 선택해 주세요</button>
      )}
      {isOpen && (
        <div className="fixed top-20 right-80">
          <TagSelect
            tags={testTags}
            setTags={setTags}
            openModalHandler={openModalHandler}
          />
        </div>
      )}
      {tags.map((el) => (
        <Tag key={el} name={el} />
      ))}
    </div>
  );
}

export default TagSearch;
