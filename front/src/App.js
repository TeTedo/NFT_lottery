import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import TopBar from "./components/TopBar/TopBar";
import ProductDetail from "./pages/ProductDetail";
import Main from "pages/Main";
import MyPage from "pages/MyPage";
// import Main from "./pages/Main";

function App() {
  return (
    <Router>
      <TopBar />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/detail/:id" element={<ProductDetail />} />
        <Route path="/page2" element={<Main />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </Router>
  );
}

export default App;
