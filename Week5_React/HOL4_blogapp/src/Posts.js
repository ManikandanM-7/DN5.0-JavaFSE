import React, { Component } from 'react';

// HOL 4 - class component with lifecycle methods
// fetches posts from jsonplaceholder api
class Posts extends Component {

  constructor(props) {
    super(props);
    this.state = {
      posts: [],
      loading: true,
      error: null
    };
  }

  // runs after component mounts - good place to fetch data
  componentDidMount() {
    fetch('https://jsonplaceholder.typicode.com/posts?_limit=10')
      .then(res => res.json())
      .then(data => {
        this.setState({ posts: data, loading: false });
      })
      .catch(err => {
        this.setState({ error: err.message, loading: false });
      });
  }

  // catches errors from child components
  componentDidCatch(error, info) {
    console.log('Error caught:', error, info);
    this.setState({ error: error.message });
  }

  render() {
    const { posts, loading, error } = this.state;

    if (loading) return <p>loading posts...</p>;
    if (error) return <p>error: {error}</p>;

    return (
      <div>
        <h2>Blog Posts</h2>
        {posts.map(post => (
          <div key={post.id} style={{ borderBottom: '1px solid #eee', marginBottom: 10 }}>
            <h4>{post.title}</h4>
            <p>{post.body}</p>
          </div>
        ))}
      </div>
    );
  }
}

export default Posts;
