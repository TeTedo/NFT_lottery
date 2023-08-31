import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import TopBar from "./components/TopBar";
import Main from "./pages/Main";
import ProductDetail from "./pages/ProductDetail";
import MyPage from "./pages/MyPage";

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
