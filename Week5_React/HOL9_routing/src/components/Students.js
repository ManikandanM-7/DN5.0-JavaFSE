import React from 'react';
import { useParams, Link } from 'react-router-dom';

const studentList = [
  { id: 1, name: 'Mani', dept: 'AI & DS' },
  { id: 2, name: 'Nithish', dept: 'CSE' },
  { id: 3, name: 'Karmugilan', dept: 'IT' },
];

export function Students() {
  return (
    <div style={{ padding: 20 }}>
      <h2>Students</h2>
      <ul>
        {studentList.map(s => (
          <li key={s.id}>
            <Link to={`/students/${s.id}`}>{s.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

// dynamic route - shows student by id
export function StudentDetail() {
  const { id } = useParams();
  const student = studentList.find(s => s.id === parseInt(id));

  if (!student) return <p>Student not found</p>;

  return (
    <div style={{ padding: 20 }}>
      <h2>{student.name}</h2>
      <p>Dept: {student.dept}</p>
      <Link to="/students">back to list</Link>
    </div>
  );
}
