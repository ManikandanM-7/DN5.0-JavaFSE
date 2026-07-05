import React from 'react';
import CalculateScore from './CalculateScore';

function App() {
  return (
    <div>
      <h1>Score Calculator</h1>
      <CalculateScore Name="Mani" School="MKU" Total={420} Goal={75} />
      <CalculateScore Name="Nithish" School="MKU" Total={380} Goal={80} />
    </div>
  );
}

export default App;
