import React from 'react';
import ReactDOM from 'react-dom/client';
import {AppRouter} from "./app/Router";
import './shared/ui/index.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AppRouter/>
    </React.StrictMode>
    );
