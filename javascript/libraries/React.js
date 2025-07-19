// React.js - Component-based UI Library Examples
// Learn React fundamentals with hooks, state management, and component composition patterns.

import React, { useState, useEffect, useCallback } from 'react';

// 1. Basic Functional Component with Hooks
function Counter() {
  const [count, setCount] = useState(0);
  const [isRunning, setIsRunning] = useState(false);

  // useEffect for side effects
  useEffect(() => {
    let interval;
    if (isRunning) {
      interval = setInterval(() => {
        setCount(prevCount => prevCount + 1);
      }, 1000);
    }
    
    // Cleanup function
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [isRunning]);

  const handleToggle = useCallback(() => {
    setIsRunning(prev => !prev);
  }, []);

  const handleReset = useCallback(() => {
    setCount(0);
    setIsRunning(false);
  }, []);

  return (
    <div className="counter">
      <h2>Auto Counter: {count}</h2>
      <button onClick={handleToggle}>
        {isRunning ? 'Pause' : 'Start'}
      </button>
      <button onClick={handleReset}>Reset</button>
    </div>
  );
}

// 2. Todo List with State Management
function TodoList() {
  const [todos, setTodos] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [filter, setFilter] = useState('all'); // all, active, completed

  const addTodo = useCallback(() => {
    if (inputValue.trim()) {
      setTodos(prev => [
        ...prev,
        {
          id: Date.now(),
          text: inputValue.trim(),
          completed: false,
          createdAt: new Date().toISOString()
        }
      ]);
      setInputValue('');
    }
  }, [inputValue]);

  const toggleTodo = useCallback((id) => {
    setTodos(prev => prev.map(todo =>
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    ));
  }, []);

  const deleteTodo = useCallback((id) => {
    setTodos(prev => prev.filter(todo => todo.id !== id));
  }, []);

  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.completed;
    if (filter === 'completed') return todo.completed;
    return true;
  });

  return (
    <div className="todo-app">
      <h2>Todo List</h2>
      
      <div className="todo-input">
        <input
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && addTodo()}
          placeholder="Add a new todo..."
        />
        <button onClick={addTodo}>Add</button>
      </div>

      <div className="filters">
        {['all', 'active', 'completed'].map(filterType => (
          <button
            key={filterType}
            onClick={() => setFilter(filterType)}
            className={filter === filterType ? 'active' : ''}
          >
            {filterType.charAt(0).toUpperCase() + filterType.slice(1)}
          </button>
        ))}
      </div>

      <ul className="todo-list">
        {filteredTodos.map(todo => (
          <TodoItem
            key={todo.id}
            todo={todo}
            onToggle={toggleTodo}
            onDelete={deleteTodo}
          />
        ))}
      </ul>

      <div className="todo-stats">
        Total: {todos.length} | 
        Active: {todos.filter(t => !t.completed).length} | 
        Completed: {todos.filter(t => t.completed).length}
      </div>
    </div>
  );
}

// 3. Reusable Todo Item Component
const TodoItem = React.memo(({ todo, onToggle, onDelete }) => {
  return (
    <li className={`todo-item ${todo.completed ? 'completed' : ''}`}>
      <input
        type="checkbox"
        checked={todo.completed}
        onChange={() => onToggle(todo.id)}
      />
      <span className="todo-text">{todo.text}</span>
      <small className="todo-date">
        {new Date(todo.createdAt).toLocaleDateString()}
      </small>
      <button 
        onClick={() => onDelete(todo.id)}
        className="delete-btn"
      >
        Delete
      </button>
    </li>
  );
});

// 4. Custom Hook Example
function useLocalStorage(key, initialValue) {
  const [storedValue, setStoredValue] = useState(() => {
    try {
      const item = window.localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      console.error(`Error reading localStorage key "${key}":`, error);
      return initialValue;
    }
  });

  const setValue = useCallback((value) => {
    try {
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      setStoredValue(valueToStore);
      window.localStorage.setItem(key, JSON.stringify(valueToStore));
    } catch (error) {
      console.error(`Error setting localStorage key "${key}":`, error);
    }
  }, [key, storedValue]);

  return [storedValue, setValue];
}

// 5. Component using Custom Hook
function Settings() {
  const [theme, setTheme] = useLocalStorage('theme', 'light');
  const [language, setLanguage] = useLocalStorage('language', 'en');

  return (
    <div className="settings">
      <h2>Settings</h2>
      
      <div className="setting-group">
        <label>Theme:</label>
        <select value={theme} onChange={(e) => setTheme(e.target.value)}>
          <option value="light">Light</option>
          <option value="dark">Dark</option>
          <option value="auto">Auto</option>
        </select>
      </div>

      <div className="setting-group">
        <label>Language:</label>
        <select value={language} onChange={(e) => setLanguage(e.target.value)}>
          <option value="en">English</option>
          <option value="es">Spanish</option>
          <option value="fr">French</option>
        </select>
      </div>

      <div className="current-settings">
        <p>Current Theme: {theme}</p>
        <p>Current Language: {language}</p>
      </div>
    </div>
  );
}

// 6. Main App Component
function App() {
  return (
    <div className="app">
      <header>
        <h1>React Examples</h1>
      </header>
      
      <main>
        <section>
          <Counter />
        </section>
        
        <section>
          <TodoList />
        </section>
        
        <section>
          <Settings />
        </section>
      </main>
    </div>
  );
}

export default App;
