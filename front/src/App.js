import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Page1 from "./pages/page1";
import Page2 from "./pages/page2";
import Topbar from "./components/TopBar/TopBar";
import ProductDetail from "./pages/ProductDetail";

function App() {
  return (
    <Router>
      <Topbar/>
      <Routes>
        <Route path="/" element={<ProductDetail />} />
        <Route path="/page1" element={<Page1 />} />
        <Route path="/page2" element={<Page2 />} />
      </Routes>
    </Router>
  );
}

export default App;
