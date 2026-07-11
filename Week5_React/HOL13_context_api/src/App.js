import React from 'react';
import { ThemeProvider, useTheme } from './context/ThemeContext';
import { UserProvider } from './context/UserContext';
import Navbar from './Navbar';
import LoginForm from './LoginForm';

// wrapping app with providers
// any child component can access theme or user without prop drilling
function AppContent() {
  const { theme } = useTheme();

  return (
    <div style={{
      minHeight: '100vh',
      background: theme === 'dark' ? '#222' : '#fff',
      color: theme === 'dark' ? '#fff' : '#000'
    }}>
      <Navbar />
      <div style={{ padding: 20 }}>
        <h1>Context API Demo</h1>
        <LoginForm />
        <p style={{ marginTop: 20 }}>
          Current theme: <strong>{theme}</strong>
        </p>
      </div>
    </div>
  );
}

function App() {
  return (
    <ThemeProvider>
      <UserProvider>
        <AppContent />
      </UserProvider>
    </ThemeProvider>
  );
}

export default App;
