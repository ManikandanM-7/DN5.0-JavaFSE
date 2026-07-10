import React from 'react';
import { Link } from 'react-router-dom';

// navbar with react router links
function Navbar() {
  return (
    <nav style={{ background: '#333', padding: 10 }}>
      <Link to="/" style={{ color: '#fff', marginRight: 20 }}>Home</Link>
      <Link to="/about" style={{ color: '#fff', marginRight: 20 }}>About</Link>
      <Link to="/students" style={{ color: '#fff', marginRight: 20 }}>Students</Link>
      <Link to="/contact" style={{ color: '#fff' }}>Contact</Link>
    </nav>
  );
}

export default Navbar;
