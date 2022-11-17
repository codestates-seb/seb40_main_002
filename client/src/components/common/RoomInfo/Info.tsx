function Info({ title, content }: { title: string; content: string | number }) {
  return (
    <div className="flex text-base">
      <div className="text-font-color min-w-[68px] ">{title}</div>
      <div>{content}</div>
    </div>
  );
}

export default Info;
