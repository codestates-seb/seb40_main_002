import React from 'react';
import { AiOutlineGithub } from 'react-icons/ai';
const Footer = () => {
  const memberList = [
    {
      memberName: '이명규',
      memberLink: 'https://github.com/billy5982',
    },
    {
      memberName: '이상민',
      memberLink: 'https://github.com/cule97',
    },
    {
      memberName: '최주은',
      memberLink: 'https://github.com/callmejeje',
    },
    {
      memberName: '허정우',
      memberLink: 'https://github.com/heoputer',
    },
    {
      memberName: '김영헌',
      memberLink: 'https://github.com/JanuaryKim',
    },
    {
      memberName: '노태윤',
      memberLink: 'https://github.com/NTY-1017',
    },
    {
      memberName: '최수혜',
      memberLink: 'https://github.com/see1237',
    },
  ];

  return (
    <>
      <div className="h-[60px] w-full flex flex-col justify-center items-center fixed bottom-0 bg-footer border-2 border-black-100">
        <p className="font-semibold"> 팀 2rror, 빛과 소금...그리고 우리 </p>
        <div className="flex">
          {memberList.map((member, idx) => {
            return (
              <div key={idx} className="flex">
                <AiOutlineGithub />
                <a href={member.memberLink} className="mr-2 text-footerFont">
                  {member.memberName}
                </a>
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default Footer;
