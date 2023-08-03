import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import TopBar from "./components/TopBar/TopBar";
import ProductDetail from "./pages/ProductDetail";
import Main from "pages/Main";
// import Main from "./pages/Main";

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
