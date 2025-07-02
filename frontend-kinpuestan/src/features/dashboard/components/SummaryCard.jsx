import React from 'react';
import {useNavigate} from "react-router-dom";

export default function SummaryCard({title,value,link}){
  const navigate = useNavigate();
  return (
      <div
        onClick={() => navigate(link)}
        className="cursor-pointer bg-gray-800 p-4 rounded-lg shadow hover:bg-gray-700 transition duration-300"
      >
        <h3 className="text-sm text-gray-400">{title}</h3>
        <p className="text-2xl font-bold text-white">{value}</p>
      </div>
    );
  }