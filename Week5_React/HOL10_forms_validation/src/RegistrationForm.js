import React, { useState } from 'react';

// HOL 10 - controlled form with validation
function RegistrationForm() {
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    dept: '',
    gender: ''
  });

  const [errors, setErrors] = useState({});
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    // clear error when user starts typing
    if (errors[e.target.name]) {
      setErrors({ ...errors, [e.target.name]: '' });
    }
  };

  const validate = () => {
    const newErrors = {};

    if (!form.name.trim()) newErrors.name = 'name is required';
    if (!form.email.trim()) {
      newErrors.email = 'email is required';
    } else if (!/\S+@\S+\.\S+/.test(form.email)) {
      newErrors.email = 'email is invalid';
    }
    if (!form.password) {
      newErrors.password = 'password is required';
    } else if (form.password.length < 6) {
      newErrors.password = 'password must be at least 6 characters';
    }
    if (!form.dept) newErrors.dept = 'select a department';
    if (!form.gender) newErrors.gender = 'select gender';

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    setSubmitted(true);
    console.log('form submitted:', form);
  };

  if (submitted) {
    return (
      <div style={{ padding: 20, color: 'green' }}>
        <h3>Registration Successful!</h3>
        <p>Welcome, {form.name}</p>
        <button onClick={() => { setSubmitted(false); setForm({ name:'', email:'', password:'', dept:'', gender:'' }); }}>
          Register Another
        </button>
      </div>
    );
  }

  return (
    <div style={{ padding: 20, maxWidth: 400 }}>
      <h2>Student Registration</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: 12 }}>
          <label>Name:</label><br />
          <input name="name" value={form.name} onChange={handleChange} />
          {errors.name && <p style={{ color: 'red', margin: 0 }}>{errors.name}</p>}
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Email:</label><br />
          <input name="email" value={form.email} onChange={handleChange} />
          {errors.email && <p style={{ color: 'red', margin: 0 }}>{errors.email}</p>}
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Password:</label><br />
          <input type="password" name="password" value={form.password} onChange={handleChange} />
          {errors.password && <p style={{ color: 'red', margin: 0 }}>{errors.password}</p>}
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Department:</label><br />
          <select name="dept" value={form.dept} onChange={handleChange}>
            <option value="">-- select --</option>
            <option value="CSE">CSE</option>
            <option value="IT">IT</option>
            <option value="AI & DS">AI & DS</option>
            <option value="ECE">ECE</option>
          </select>
          {errors.dept && <p style={{ color: 'red', margin: 0 }}>{errors.dept}</p>}
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Gender: </label>
          <input type="radio" name="gender" value="Male" onChange={handleChange} /> Male
          <input type="radio" name="gender" value="Female" onChange={handleChange} style={{ marginLeft: 10 }} /> Female
          {errors.gender && <p style={{ color: 'red', margin: 0 }}>{errors.gender}</p>}
        </div>

        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default RegistrationForm;
