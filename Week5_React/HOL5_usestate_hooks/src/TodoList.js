import React, { useState } from 'react';

// simple todo list using hooks
function TodoList() {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState('');

  const addTodo = () => {
    if (input.trim() === '') return;
    setTodos([...todos, { id: Date.now(), text: input, done: false }]);
    setInput('');
  };

  const toggleDone = (id) => {
    setTodos(todos.map(t => t.id === id ? { ...t, done: !t.done } : t));
  };

  const removeTodo = (id) => {
    setTodos(todos.filter(t => t.id !== id));
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Todo List</h2>
      <input
        value={input}
        onChange={e => setInput(e.target.value)}
        onKeyDown={e => e.key === 'Enter' && addTodo()}
        placeholder="add a task..."
      />
      <button onClick={addTodo} style={{ marginLeft: 8 }}>Add</button>

      <ul style={{ marginTop: 10 }}>
        {todos.map(todo => (
          <li key={todo.id}>
            <span
              onClick={() => toggleDone(todo.id)}
              style={{ textDecoration: todo.done ? 'line-through' : 'none', cursor: 'pointer' }}
            >
              {todo.text}
            </span>
            <button onClick={() => removeTodo(todo.id)} style={{ marginLeft: 10 }}>x</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TodoList;
