import React, { useState, FormEvent } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { ReducerType } from './store/reducer/rootReducer';
import { User, addUser } from './store/reducer/users';

export default function App() {
  const users = useSelector<ReducerType, User[]>((state) => state.users);
  const dispatch = useDispatch();

  const [name, setName] = useState('');

  const handleChangeName = (e: any) => {
    setName(e.target.value);
  };

  const handleAddUser = (e: FormEvent) => {
    e.preventDefault();
    dispatch(addUser({ name } as User));
    setName('');
  };

  return (
    <div>
      <form onSubmit={handleAddUser}>
        <input type="text" value={name} onChange={handleChangeName} />
        <button
          type="submit"
          className=" text-point-color bg-[#333333] rounded-lg"
        >
          Add User
        </button>
      </form>

      {users.map((user) => (
        <div key={user.id}>{user.name}</div>
      ))}
    </div>
  );
}

// import React from 'react';

// export default function App() {
//   return <div className="text-3xl font-bold underline"></div>;
// }
