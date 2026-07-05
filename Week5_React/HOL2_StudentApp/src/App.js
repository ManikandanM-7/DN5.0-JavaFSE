import React from 'react';
import Home from './components/Home';
import About from './components/About';
import Contact from './components/Contact';

// HOL 2 - StudentApp with class components
// rendering all three components in one page for now
// will add routing in HOL9
function App() {
  return (
    <div>
      <h1>Student App</h1>
      <hr />
      <Home />
      <hr />
      <About />
      <hr />
      <Contact />
    </div>
  );
}

export default App;
