import React, { useState } from 'react';
import { useUser } from './context/UserContext';

function LoginForm() {
  const [username, setUsername] = useState('');
  const { user, login } = useUser();

  if (user) {
    return (
      <div style={{ padding: 20 }}>
        <p>You are logged in as <strong>{user.name}</strong></p>
      </div>
    );
  }

  return (
    <div style={{ padding: 20 }}>
      <h3>Login</h3>
      <input
        value={username}
        onChange={e => setUsername(e.target.value)}
        placeholder="enter username"
      />
      <button
        onClick={() => username && login(username)}
        style={{ marginLeft: 8 }}
      >
        Login
      </button>
    </div>
  );
}

export default LoginForm;
