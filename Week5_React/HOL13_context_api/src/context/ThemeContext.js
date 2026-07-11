import React, { createContext, useState, useContext } from 'react';

// HOL 13 - Context API
// avoids prop drilling - share state across components without passing props
const ThemeContext = createContext();

export function ThemeProvider({ children }) {
  const [theme, setTheme] = useState('light');

  const toggleTheme = () => {
    setTheme(prev => prev === 'light' ? 'dark' : 'light');
  };

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  );
}

// custom hook so components dont need to import useContext and ThemeContext separately
export function useTheme() {
  return useContext(ThemeContext);
}
