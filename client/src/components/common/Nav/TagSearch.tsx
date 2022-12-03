import useModal from '../../../hooks/useModal';
import Tag from '../Tag';
import TagSelect from '../TagSelectModal/TagSelect';
import { TAGS } from '../../../assets/tags/tags';

function TagSearch({
  tags,
  setTags,
}: {
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
}) {
  const [isOpen, openModalHandler] = useModal();
  return (
    <div>
      <div className="flex relative" onClick={openModalHandler}>
        {tags.length > 0 || (
          <button onClick={openModalHandler}>태그를 선택해 주세요</button>
        )}
      </div>
      <div>
        {isOpen && (
          // <div className="">
          <TagSelect
            tags={TAGS}
            setTags={setTags}
            openModalHandler={openModalHandler}
          />
          // </div>
        )}
        {tags.map((el) => (
          <Tag key={el} name={el} />
        ))}
      </div>
    </div>
  );
}

export default TagSearch;
