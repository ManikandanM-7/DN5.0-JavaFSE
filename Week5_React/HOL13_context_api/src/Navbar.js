import React from 'react';
import { useTheme } from './context/ThemeContext';
import { useUser } from './context/UserContext';

// navbar reads from both contexts - no props needed
function Navbar() {
  const { theme, toggleTheme } = useTheme();
  const { user, logout } = useUser();

  return (
    <nav style={{
      background: theme === 'dark' ? '#333' : '#eee',
      color: theme === 'dark' ? '#fff' : '#000',
      padding: 12,
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center'
    }}>
      <span>My App</span>
      <div>
        {user ? (
          <>
            <span>Hi, {user.name}</span>
            <button onClick={logout} style={{ marginLeft: 10 }}>Logout</button>
          </>
        ) : (
          <span>Not logged in</span>
        )}
        <button onClick={toggleTheme} style={{ marginLeft: 10 }}>
          {theme === 'dark' ? '☀ Light' : '🌙 Dark'}
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
