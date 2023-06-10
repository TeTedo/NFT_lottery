import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Page1 from "./pages/page1";
import Page2 from "./pages/page2";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Page1 />} />
        <Route path="/page1" element={<Page1 />} />
        <Route path="/page2" element={<Page2 />} />
      </Routes>
    </Router>
  );
}

export default App;
