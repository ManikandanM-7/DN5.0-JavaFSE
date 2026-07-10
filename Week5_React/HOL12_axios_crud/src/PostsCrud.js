import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API = 'https://jsonplaceholder.typicode.com/posts';

// HOL 12 - CRUD operations using axios
// create read update delete against jsonplaceholder
function PostsCrud() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({ title: '', body: '' });
  const [editId, setEditId] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    axios.get(`${API}?_limit=5`)
      .then(res => {
        setPosts(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // CREATE - post to api
  const handleCreate = () => {
    if (!form.title || !form.body) return;
    axios.post(API, { ...form, userId: 1 })
      .then(res => {
        // jsonplaceholder returns id 101 for all new posts
        setPosts([res.data, ...posts]);
        setForm({ title: '', body: '' });
        setMessage('post created!');
        setTimeout(() => setMessage(''), 2000);
      });
  };

  // UPDATE - put to api
  const handleUpdate = () => {
    if (!form.title || !form.body) return;
    axios.put(`${API}/${editId}`, { ...form, userId: 1, id: editId })
      .then(res => {
        setPosts(posts.map(p => p.id === editId ? res.data : p));
        setForm({ title: '', body: '' });
        setEditId(null);
        setMessage('post updated!');
        setTimeout(() => setMessage(''), 2000);
      });
  };

  const startEdit = (post) => {
    setEditId(post.id);
    setForm({ title: post.title, body: post.body });
  };

  // DELETE
  const handleDelete = (id) => {
    axios.delete(`${API}/${id}`)
      .then(() => {
        setPosts(posts.filter(p => p.id !== id));
        setMessage('post deleted');
        setTimeout(() => setMessage(''), 2000);
      });
  };

  if (loading) return <p>loading...</p>;

  return (
    <div style={{ padding: 20 }}>
      <h2>Posts CRUD with Axios</h2>

      {message && <p style={{ color: 'green' }}>{message}</p>}

      <div style={{ marginBottom: 20, padding: 16, background: '#f5f5f5' }}>
        <h3>{editId ? 'Edit Post' : 'New Post'}</h3>
        <div>
          <input
            name="title"
            placeholder="title"
            value={form.title}
            onChange={handleChange}
            style={{ display: 'block', marginBottom: 8, width: 300 }}
          />
          <textarea
            name="body"
            placeholder="body"
            value={form.body}
            onChange={handleChange}
            rows={3}
            style={{ display: 'block', marginBottom: 8, width: 300 }}
          />
          {editId ? (
            <>
              <button onClick={handleUpdate}>Update</button>
              <button onClick={() => { setEditId(null); setForm({ title: '', body: '' }); }} style={{ marginLeft: 8 }}>
                Cancel
              </button>
            </>
          ) : (
            <button onClick={handleCreate}>Create</button>
          )}
        </div>
      </div>

      {posts.map(post => (
        <div key={post.id} style={{ borderBottom: '1px solid #ddd', padding: '12px 0' }}>
          <strong>#{post.id} {post.title}</strong>
          <p style={{ margin: '4px 0', color: '#555' }}>{post.body}</p>
          <button onClick={() => startEdit(post)} style={{ marginRight: 8 }}>Edit</button>
          <button onClick={() => handleDelete(post.id)} style={{ color: 'red' }}>Delete</button>
        </div>
      ))}
    </div>
  );
}

export default PostsCrud;
