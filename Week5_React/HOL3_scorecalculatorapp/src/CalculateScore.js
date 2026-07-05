import React from 'react';
import './mystyle.css';

// HOL 3 - functional component with props
// calculates average score and displays it
function CalculateScore(props) {
  const avg = (props.Total / 5).toFixed(2);
  const passed = avg >= props.Goal;

  return (
    <div className="score-card">
      <h2>{props.Name}</h2>
      <p>School: {props.School}</p>
      <p>Total Marks: {props.Total}</p>
      <p>Average Score: {avg}</p>
      <p>Goal: {props.Goal}</p>
      <p style={{ color: passed ? 'green' : 'red' }}>
        {passed ? 'Goal Achieved!' : 'Keep trying!'}
      </p>
    </div>
  );
}

export default CalculateScore;
