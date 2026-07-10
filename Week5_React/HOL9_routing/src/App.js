import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/Home';
import About from './components/About';
import { Students, StudentDetail } from './components/Students';

// HOL 9 - React Router
// setting up client side routing
function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/students" element={<Students />} />
        <Route path="/students/:id" element={<StudentDetail />} />
        <Route path="*" element={<h2 style={{padding:20}}>404 - Page not found</h2>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
