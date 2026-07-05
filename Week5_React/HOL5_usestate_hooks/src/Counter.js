import React, { useState } from 'react';

// HOL 5 - useState hook
// functional component with state - no need for class component anymore
function Counter() {
  const [count, setCount] = useState(0);
  const [name, setName] = useState('');

  return (
    <div style={{ padding: 20 }}>
      <h2>Counter: {count}</h2>
      <button onClick={() => setCount(count + 1)}>+1</button>
      <button onClick={() => setCount(count - 1)} style={{ marginLeft: 8 }}>-1</button>
      <button onClick={() => setCount(0)} style={{ marginLeft: 8 }}>Reset</button>

      <div style={{ marginTop: 20 }}>
        <input
          type="text"
          placeholder="enter your name"
          value={name}
          onChange={e => setName(e.target.value)}
        />
        {name && <p>Hello, {name}!</p>}
      </div>
    </div>
  );
}

export default Counter;
