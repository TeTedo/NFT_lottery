import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Page1 from "./pages/page1";
import Page2 from "./pages/page2";
import TopBar from "./components/TopBar/TopBar";
import ProductDetail from "./pages/ProductDetail";
import Main from "./pages/Main";

function App() {
  return (
    <Router>
      <TopBar />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/detail/:id" element={<ProductDetail />} />
        <Route path="/page2" element={<Main />} />
      </Routes>
    </Router>
  );
}

export default App;
